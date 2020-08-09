package com.example.KnowUrCaller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {

    EditText signInPhoneEditText;
    private boolean noError = true;

    //to go back to signUp page
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
        finish();
        return;
    }

    public void signIn(View view)
    {
        if (signInPhoneEditText.getText().toString().length() == 0) {
            signInPhoneEditText.setError("Enter your phone number");
            signInPhoneEditText.requestFocus();
            noError = false;
        }
        if (noError) {
            String phoneNumber = "+91" + signInPhoneEditText.getText().toString();
            Intent intent = new Intent(SignIn.this, OtpPage.class);
            intent.putExtra("Phone Number", phoneNumber);
            startActivity(intent);
            finish();
        }
        noError = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hiding notification bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in);

        signInPhoneEditText = (EditText) findViewById(R.id.signInPhoneEditText);

    }
}