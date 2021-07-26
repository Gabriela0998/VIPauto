package com.app.service;

import com.app.controller.UserController;
import com.app.entity.UserEntity;

public class UserService {

    public UserEntity getById(UserEntity user) {
        return UserController.getById(user);
    }

    public static UserEntity next(int idTF) {
        return UserController.next(idTF);
    }

    public UserEntity update(UserEntity user) {
        return UserController.update(user);
    }
}
