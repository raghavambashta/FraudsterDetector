package com.example.fraudsterdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    EditText editTextPhone;
    Button getOtp;
    EditText firstName;
    EditText lastName;
    EditText dob;
    Calendar myCalendar;

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText("  " + sdf.format(myCalendar.getTime()));
    }

    public void resetItems(View view) {
        firstName.setText(null);
        lastName.setText(null);
        dob.setText(null);
        editTextPhone.setText(null);
    }

    public void generateOtp(View view) {
        if (editTextPhone.getText().toString().length() < 10) {
            editTextPhone.setError("Phone number is Invalid");
        }
        if (firstName.getText().toString().length() == 0) {
            firstName.setError("Please enter your first name");
        }
        if (lastName.getText().toString().length() == 0) {
            lastName.setError("Please enter your last name");
        }
        if (dob.getText().toString().length() == 0) {
            dob.setError("Select your date of birth");
        }
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getOtp = (Button) findViewById(R.id.getOtp);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        dob = (EditText) findViewById(R.id.dob);

        dob.setInputType(InputType.TYPE_NULL);//disabling keyboard popup for dob EditText
        dob.setTextIsSelectable(true);

        //implementation of calendar
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    new DatePickerDialog(SignUp.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }
}