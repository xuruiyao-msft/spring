package com.springinaction.database;

import com.springinaction.spittr.User;

import java.util.List;

public interface UserRepository {

    long count();

    User save(User user);

    User findOne(long id);

    User findByUserName(String username);

    List<User> findAll();
}
