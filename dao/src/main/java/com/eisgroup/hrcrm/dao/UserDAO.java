package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.User;

import java.util.List;

public interface UserDAO extends BaseObjectDAO<User> {
    List<User> getAllActiveUsers();

    List<User> getAllActiveUsersSortByName();

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}
