package com.example.dailystore2.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailystore2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumber extends AppCompatActivity {

    private TextInputEditText phoneNumber;
    private Button signUp;
    private ProgressDialog progressDialog;
    private String email, firstName, lastName, password, verficationID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        init();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVerifyPhoneNumber();
           
            }
        });

    }


    private void init(){
        phoneNumber = findViewById(R.id.signup_phoneNumber);
        signUp = findViewById(R.id.btn_signUp);

        progressDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            email = bundle.getString("email");
            firstName = bundle.getString("firstName");
            lastName = bundle.getString("lastName");
            password = bundle.getString("password");
        }
        Toast.makeText(getApplicationContext(),email+firstName+password,Toast.LENGTH_LONG).show();

        changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.hide();



            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
               progressDialog.hide();
                verficationID = s;
               mResendToken = forceResendingToken;
                Intent intent = new Intent(getApplicationContext(), SendCode.class);
                intent.putExtra("verificationID",verficationID);
                intent.putExtra("firstName",firstName);
                intent.putExtra("lastName",lastName);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("phoneNumber",phoneNumber.getText().toString());
                startActivity(intent);
                finish();
            }
        };
    }





    private void startVerifyPhoneNumber(){
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String str_phoneNumber = "+63"+phoneNumber.getText().toString();
        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(str_phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(changedCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

    }

}