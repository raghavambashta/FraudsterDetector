package com.example.fraudsterdetector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    EditText editTextPhone;
    Button getOtp;
    EditText dob;
    Calendar myCalendar;
    ImageView loginIcon;
    EditText name;
    EditText emailId;
    AutoCompleteTextView acTV1; //drop down list for gender
    InputMethodManager inputManager;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void signIn(View view)
    {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
        finish();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText("  " + sdf.format(myCalendar.getTime()));
    }

    public void resetItems(View view) {
        //hiding keyboard
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //reset all inputs
        dob.setText(null);
        editTextPhone.setText(null);
        name.setText(null);
        emailId.setText(null);
        acTV1.setText(null);

        //setting all the errors (if any) to null
        name.setError(null);
        emailId.setError(null);
        dob.setError(null);
        acTV1.setError(null);
        editTextPhone.setError(null);
    }

    public void generateOtp(View view) {
        if (editTextPhone.getText().toString().length() < 10) {
            editTextPhone.setError("Phone number is Invalid");
        }
        if (dob.getText().toString().length() == 0) {
            dob.setError("Select your date of birth");
        }
        if (name.getText().toString().length() == 0) {
            name.setError("Enter your Name");
        }
        if (emailId.getText().toString().length() == 0) {
            emailId.setError("Enter your Email Id");
        }
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hiding notification bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);
        getOtp = (Button) findViewById(R.id.getOtp);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        dob = (EditText) findViewById(R.id.dob);
        loginIcon = (ImageView) findViewById(R.id.loginIcon);
        name = (EditText) findViewById(R.id.nameEditText);
        emailId = (EditText) findViewById(R.id.emailEditText);

        loginIcon.setY(-350);
        loginIcon.animate().translationYBy(320).setDuration(2000);//animating login icon

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
                    new DatePickerDialog(SignUp.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        //Implementation Gender drop down list EditText
        acTV1 = findViewById(R.id.acT1);
        final ImageView delButton = findViewById(R.id.delButton);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gender_Names));
        final String[] selection = new String[1];
        acTV1.setAdapter(arrayAdapter);
        acTV1.setCursorVisible(false);
        acTV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acTV1.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), selection[0],
                        Toast.LENGTH_SHORT);
                delButton.setAlpha(.8f);
            }
        });

        acTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                acTV1.showDropDown();
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acTV1.setText(null);
                selection[0] = null;
            }
        });

        dob.setKeyListener(null);
    }
}