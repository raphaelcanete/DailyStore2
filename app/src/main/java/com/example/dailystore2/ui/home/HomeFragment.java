package com.example.dailystore2.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dailystore2.CategoryAdapter;
import com.example.dailystore2.CategoryData;
import com.example.dailystore2.CreateDialog;
import com.example.dailystore2.R;
import com.example.dailystore2.Singleton;
import com.example.dailystore2.URLs;
import com.example.dailystore2.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {


private FragmentHomeBinding binding;
private RecyclerView recyclerView_category;
private List<CategoryData> categoryDataList;
private ProgressDialog progressDialog;
private CreateDialog createDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
            progressDialog = new ProgressDialog(getActivity());
            createDialog = new CreateDialog(getActivity());


    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView_category = root.findViewById(R.id.recyclerView_category);
        recyclerView_category.setHasFixedSize(true);
        recyclerView_category.setLayoutManager(linearLayoutManager);

        categoryDataList = new ArrayList<>();

        display();

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void display(){
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.displayCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        try {


                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CategoryData categoryData = new CategoryData(
                                        jsonObject.getString("category")
                                );
                                categoryDataList.add(categoryData);
                                CategoryAdapter adapter = new CategoryAdapter(
                                        getActivity(),categoryDataList
                                );
                            recyclerView_category.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            createDialog = new CreateDialog(getActivity());
                            createDialog.alertDialog("Null",e.getMessage(),true);
                            createDialog.display();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
               createDialog.alertDialog("Error",error.getMessage(),true);
                createDialog.display();
            }
        });
        Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}