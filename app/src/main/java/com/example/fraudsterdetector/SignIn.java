package com.example.fraudsterdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {

    EditText signInPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInPhoneEditText = (EditText) findViewById(R.id.signInPhoneEditText);
    }
}