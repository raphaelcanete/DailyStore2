package com.example.dailystore2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Logout extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        SF.getInstance(getActivity()).signOut();
        Intent intent = new Intent(getActivity(),SignIn.class);
        startActivity(intent);
        return view;
    }
}
