package com.app.helper;

import com.app.view.Login;
import com.app.view.Register;

public class StartHelper {
    public StartHelper(Boolean st){
        if(st=true){
            login();
        }else{
            register();
        }
    }

    StartHelper login(){
        Login start = new Login();
        return null;
    }
    StartHelper register(){
        Register start = new Register();
        return null;
    }
}
