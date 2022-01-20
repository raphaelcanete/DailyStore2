package com.example.dailystore2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dailystore2.signup.Email;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private TextView signUp;
    private Button signIn;
    private EditText email, password;
    private CreateDialog createDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        if(SF.getInstance(SignIn.this).userInfo().get("email") != null){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }
        init();
        sets();

    }

    private void init(){
        signUp = findViewById(R.id.tv_signup);
        signIn = findViewById(R.id.btn_signin);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);

        createDialog = new CreateDialog(SignIn.this);
    }

    private void sets(){
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == signUp){
            Intent intent = new Intent(getApplicationContext(), Email.class);
            startActivity(intent);

        }
        else if(view == signIn){
            signIn();
        }
    }


    private void signIn(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.userLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error") == false){
                                SF.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getString("userID"),
                                        jsonObject.getString("firstName"),
                                        jsonObject.getString("lastName"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("phoneNumber")
                                );
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{


                                createDialog.alertDialog("Invalid email or password",
                                        "Please try again.",
                                        false);
                                CreateDialog.PositiveAndNegative pn = new CreateDialog.PositiveAndNegative(
                                        "OK", "nulls"
                                );
                                pn.setDialogOnClickListener(new CreateDialog.ClickListener() {
                                    @Override
                                    public void positiveClickListener(AlertDialog.Builder builder) {
                                        Toast.makeText(getApplicationContext(),
                                                "Positve", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void negativeClickListener(AlertDialog.Builder builder) {
                                        Toast.makeText(getApplicationContext(),
                                                "Negative", Toast.LENGTH_SHORT).show();
                                    }
                                });




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                createDialog.alertDialog("Error",error.getMessage(),true);
                createDialog.display();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


}