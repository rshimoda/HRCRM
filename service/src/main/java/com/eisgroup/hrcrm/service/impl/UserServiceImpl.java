package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.UserDAO;
import com.eisgroup.hrcrm.model.User;
import com.eisgroup.hrcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public void create(User user) {
//        user.setFirstName(user.getFirstName().trim());
//        user.setLastName(user.getLastName().trim());
        userDAO.create(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        user.setDeleted(true);
        userDAO.update(user);
    }


    @Override
    public User find(long id) {
        return userDAO.find(id);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userDAO.getAllActiveUsers();
    }

    @Override
    public List<User> getAllActiveUsersSortByName() {
        return userDAO.getAllActiveUsersSortByName();
    }

    @Override
    public User findUserByLogin(String login) {
        return userDAO.findUserByLogin(login);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetail = userDAO.findUserByLogin(username);

        if (userDetail == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        List<String> roles = humanResourceManagerDAO.getRoles(userDetail.getId());
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }

        return new org.springframework.security.core.userdetails.User(username, userDetail.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    }
}
