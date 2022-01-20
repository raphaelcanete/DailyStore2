package com.example.dailystore2;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class CreateDialog {

    private Context context;
    private static ClickListener clickListener;
    private static AlertDialog dialog;
    private static AlertDialog.Builder builder;



    public CreateDialog(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }


    public void alertDialog(String title, String message,
                        boolean isCancelable){

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancelable);


    }



    public void display(){
        dialog = builder.create();
        dialog.show();
    }


    public interface ClickListener{
        void positiveClickListener(AlertDialog.Builder builder);
        void negativeClickListener(AlertDialog.Builder builder);
    }





    static class PositiveAndNegative{

        private PositiveAndNegative pn;
        public PositiveAndNegative(String positive, String negative){
            CreateDialog.builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clickListener.positiveClickListener(builder);
                }
            });
            if(!negative.equals("null")){
                CreateDialog.builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickListener.negativeClickListener(builder);
                    }
                });
            }

            CreateDialog.dialog = builder.create();
            CreateDialog.dialog.show();
        }



        public void setDialogOnClickListener(ClickListener clickListener){
            CreateDialog.clickListener = clickListener;
        }


    }


}
