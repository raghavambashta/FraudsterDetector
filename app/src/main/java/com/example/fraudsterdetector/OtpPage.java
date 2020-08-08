package com.example.fraudsterdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static android.view.KeyEvent.KEYCODE_DEL;

public class OtpPage extends AppCompatActivity {

    EditText edOtp1;
    EditText edOtp2;
    EditText edOtp3;
    EditText edOtp4;
    TextView textView;
    private String otpEntered = "";
    Button validateButton;
    Button tryAgainButton;

    public void resend (View view)
    {
        randomNumberGenerator();
        tryAgainButton.setVisibility(View.GONE );
        tryAgainButton.setEnabled(false);
        validateButton.setVisibility(View.VISIBLE);
        validateButton.setEnabled(true);
    }

    public void tryAgain(View view)
    {
        tryAgainButton.setVisibility(View.GONE);
        tryAgainButton.setEnabled(false);
        validateButton.setVisibility(View.VISIBLE);
        validateButton.setEnabled(true);
        edOtp1.setText(null);
        edOtp2.setText(null);
        edOtp3.setText(null);
        edOtp4.setText(null);
    }

    public void validate(View view)
    {
        Boolean noError = true;
        if (edOtp1.getText().toString().equals(null)){
            noError = false;
        }
        else if (edOtp2.getText().toString().equals(null)){
            noError = false;
        }
        else if (edOtp3.getText().toString().equals(null)){
            noError = false;
        }
        else if (edOtp4.getText().toString().equals(null)){
            noError = false;
        }
        if (noError){
            randomNumberString = "";
            otpEntered = edOtp1.getText().toString() + edOtp2.getText().toString() + edOtp3.getText().toString() + edOtp4.getText().toString();

            //checking if the entered otp is correct
            if (otpEntered.equals(Integer.toString(myOTP))){
                Toast.makeText(OtpPage.this, "Validation successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(OtpPage.this, "Validation failed", Toast.LENGTH_SHORT).show();
                tryAgainButton.setVisibility(View.VISIBLE);
                tryAgainButton.setEnabled(true);
                validateButton.setVisibility(View.GONE);
                validateButton.setEnabled(false);
            }
        }else{
            Toast.makeText(OtpPage.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    //generating 4 digit random number for OTP
    String randomNumberString = "";
    int otpGenerated;
    int a, b, c, d;
    int myOTP;

    public void randomNumberGenerator() {
        Random random = new Random();
        a = random.nextInt(9)+1;
        b = random.nextInt(9);
        c = random.nextInt(9);
        d = random.nextInt(9);

        randomNumberString = Integer.toString(a) + Integer.toString(b) + Integer.toString(c) + Integer.toString(d);
        otpGenerated = Integer.parseInt(randomNumberString);

        //storing the otp
        SharedPreferences sharedPreferences = getSharedPreferences("OTP", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("OTP generated", otpGenerated);
        editor.commit();

        myOTP = sharedPreferences.getInt("OTP generated", -1);

        Log.i("my OTP : ", Integer.toString(myOTP));
    }

    //OTP text watcher
    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.edOtp1:
                    if (text.length() == 1)
                        edOtp2.requestFocus();
                    break;
                case R.id.edOtp2:
                    if (text.length() == 1)
                        edOtp3.requestFocus();
                    else if (text.length() == 0)
                        edOtp1.requestFocus();
                    break;
                case R.id.edOtp3:
                    if (text.length() == 1)
                        edOtp4.requestFocus();
                    else if (text.length() == 0)
                        edOtp2.requestFocus();
                    break;
                case R.id.edOtp4:
                    if (text.length() == 0)
                        edOtp3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

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
        Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show();

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

        edOtp1 = (EditText)findViewById(R.id.edOtp1);
        edOtp2 = (EditText)findViewById(R.id.edOtp2);
        edOtp3 = (EditText)findViewById(R.id.edOtp3);
        edOtp4 = (EditText)findViewById(R.id.edOtp4);

        edOtp1.addTextChangedListener(new GenericTextWatcher(edOtp1));
        edOtp2.addTextChangedListener(new GenericTextWatcher(edOtp2));
        edOtp3.addTextChangedListener(new GenericTextWatcher(edOtp3));
        edOtp4.addTextChangedListener(new GenericTextWatcher(edOtp4));

        textView = (TextView) findViewById(R.id.textView);
        //animating "Enter OTP" TextView
        textView.setY(-350);
        textView.animate().translationYBy(320).setDuration(1000);

        //generating otp
        randomNumberGenerator();

        validateButton = (Button) findViewById(R.id.validateButton);
        tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
    }
}