package com.app.service;

import com.app.controller.MyDataController;
import com.app.entity.MyDataEntity;

public class MyDataService {
    public MyDataEntity insert(MyDataEntity myData) {
        return MyDataController.insert(myData);
    }

}
