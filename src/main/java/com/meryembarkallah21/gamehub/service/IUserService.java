package com.meryembarkallah21.gamehub.service;

import com.meryembarkallah21.gamehub.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);

}
