package com.example.fraudsterdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class OtpPage extends AppCompatActivity {

    //to go back to signUp page
    private boolean doubleBackToGoBack = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToGoBack) {
            Intent intent = new Intent(OtpPage.this, SignUp.class);
            startActivity(intent);
            finish();
            return;
        }

        this.doubleBackToGoBack = true;
        Toast.makeText(this, "Press back again to go back", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToGoBack = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hiding notification bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_otp_page);
    }
}