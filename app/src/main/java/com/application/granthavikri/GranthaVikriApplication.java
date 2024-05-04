package com.application.granthavikri;

import android.app.Application;
import com.google.firebase.FirebaseApp;


public class GranthaVikriApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //initializing instance of firebase app
        FirebaseApp.initializeApp(this);

        // adding both kendra details in utility class
        Utility.kendraMap.put("SMBPM", "smbpm_database");
        Utility.kendraMap.put("SDPM", "sdpm_database");

//        Utility.kendraMap.put("kendra_1", "kendra_1_database");
//        Utility.kendraMap.put("kendra_2", "kendra_2_database");
    }
}
