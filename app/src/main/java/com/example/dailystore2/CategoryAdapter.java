package com.example.dailystore2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private List<CategoryData> categoryDataList;


    public CategoryAdapter(Context context, List<CategoryData> categoryDataList){
        this.context = context;
        this.categoryDataList = categoryDataList;

    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_category,null);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position) {
    CategoryData categoryData = categoryDataList.get(position);
    categoryHolder.categoryName.setText(categoryData.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_category);
        }
    }
}
