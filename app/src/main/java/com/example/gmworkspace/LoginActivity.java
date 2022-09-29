package com.example.gmworkspace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gmworkspace.helper.Session;

import java.lang.reflect.AccessibleObject;

public class LoginActivity extends AppCompatActivity {

    Activity activity;
    Button btnLogin;
    EditText etMobile,etPassword;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;
        session = new Session(activity);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMobile.getText().toString().trim().equals("")){
                    etMobile.setError("empty");
                    etMobile.requestFocus();
                }
                else if (etPassword.getText().toString().trim().equals("")){
                    etPassword.setError("empty");
                    etPassword.requestFocus();
                }else {
                    session.setBoolean("is_logged_in",true);
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });
    }
}