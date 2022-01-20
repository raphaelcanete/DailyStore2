package com.example.dailystore2.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailystore2.R;
import com.example.dailystore2.SignIn;
import com.google.android.material.textfield.TextInputEditText;


public class PersonalInfo extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText firstName, lastName, password;
    private TextView signIn;
    private ProgressDialog progressDialog;
    private Button next;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        init();
        sets();
    }

    private void init(){
        progressDialog = new ProgressDialog(this);
        firstName = findViewById(R.id.signup_firstName);
        lastName = findViewById(R.id.signup_lastName);
        password = findViewById(R.id.signup_password);

        signIn = findViewById(R.id.tv_signin);
        next = findViewById(R.id.btn_personal_next);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            email = bundle.getString("email");
        }

    }

    private void sets(){
        next.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void goToSignUp(){
    if(firstName.getText().toString().equals("") || lastName.getText().toString().equals("") ||
    password.getText().toString().equals("")){
        Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
        if(firstName.getText().toString().equals("")){
            firstName.setError("This field is required");

        }
        else if(lastName.getText().toString().equals("")){
            lastName.setError("This field is required");

        }
        else if(password.getText().toString().equals("")){
            password.setError("This field is required");

        }

    }
    else{
        Intent intent = new Intent(getApplicationContext(),PhoneNumber.class);
        intent.putExtra("firstName", firstName.getText().toString());
        intent.putExtra("lastName", lastName.getText().toString());
        intent.putExtra("password",password.getText().toString());
        intent.putExtra("email",email);
        startActivity(intent);
    }
    }

    @Override
    public void onClick(View view) {
        if(view == signIn){
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
            finish();
        }
        else if(view == next){
            goToSignUp();
        }
    }
}