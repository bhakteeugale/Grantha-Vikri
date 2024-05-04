package com.application.granthavikri.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.application.granthavikri.GranthaVikriApplication;
import com.application.granthavikri.NetworkUtils;
import com.application.granthavikri.R;
import com.application.granthavikri.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button kendra1 = findViewById(R.id.kendra1_btn);
        Button kendra2 = findViewById(R.id.kendra2_btn);
        kendra1.setEnabled(false);
        kendra2.setEnabled(false);

        kendra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setKendra("SMBPM");
                loggedIn();
            }
        });

        kendra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setKendra("SDPM");
                loggedIn();
            }
        });

        if(NetworkUtils.isNetworkConnected(getApplicationContext())){
            kendra1.setEnabled(true);
            kendra2.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(), "No internet connectivity!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loggedIn(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}