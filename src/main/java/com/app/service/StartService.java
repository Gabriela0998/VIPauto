package com.app.service;

import com.app.controller.StartController;
import com.app.entity.UserEntity;

import java.util.List;

public class StartService {
    public static List<UserEntity> getAll() {
        return StartController.getAll();
    }
}
