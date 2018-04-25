package com.example.administrator.animation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.bluetooth.MainActivity;
import com.example.administrator.bluetooth.R;
import com.example.administrator.constant.Constant;

/**
 * Created by Administrator on 2018/4/25.
 */

public class AlertDlog {

    Context context;

    public AlertDlog(Context context){
        this.context=context;
    }


    public  AlertDialog createAlert(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);
        //  EditText  result = (EditText) findViewById(R.id.editTextResult);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                String strIp=userInput.getText().toString();
                                Constant.ip=strIp;
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
       // alertDialog.show();

        return alertDialog;



    }

    public void show(){
        createAlert().show();
    }



}
