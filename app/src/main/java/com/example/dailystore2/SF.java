package com.example.dailystore2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SF {


    private final String SFName = "GrocerDailySFName";
    private final String SFEmail = "GroceryDailySFEmail";
    private final String SFFirstName = "GroceryDailySFFirstName";
    private final String SFLastName = "GroceryDailySFLastName";
    private final String SFPhoneNumber = "GroceryDailySFPhoneNumber";
    private final String SFUserID = "GroceryDailySFUserID";

    private static SF mInstance;
    private Context context;

    private SF(Context context){
    this.context = context;
    }

    public static synchronized SF getInstance(Context context){
        if(mInstance == null){
            mInstance = new SF(context);
        }
        return mInstance;
    }

    public void userLogin(String userID, String firstName, String lastName, String email, String phoneNumber){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SFName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SFUserID, userID);
        editor.putString(SFFirstName, firstName);
        editor.putString(SFLastName, lastName);
        editor.putString(SFEmail, email);
        editor.putString(SFPhoneNumber, phoneNumber);
        editor.apply();
    }

    public HashMap<String,String> userInfo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SFName, Context.MODE_PRIVATE);
        HashMap<String,String> params = new HashMap<>();
        params.put("userID",sharedPreferences.getString(SFUserID,null));
        params.put("firstName",sharedPreferences.getString(SFFirstName,null));
        params.put("lastName",sharedPreferences.getString(SFLastName,null));
        params.put("email",sharedPreferences.getString(SFEmail,null));
        params.put("phoneNumber",sharedPreferences.getString(SFPhoneNumber,null));
        return params;
    }

    public void signOut(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SFName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
