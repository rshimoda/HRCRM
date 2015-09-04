package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.User;

import java.util.List;

public interface UserService {
    void create(User manager);

    void update(User manager);

    void delete(User manager);

    User find(long id);

    List<User> getAllActiveUsers();

    List<User> getAllActiveUsersSortByName();

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}
