package com.example.generalapplication.Helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class UI {

    public static void CreateBasicSnack(String message, Integer length, Context context){
        length = length != null ? length : 3000;
        Snackbar.make(((Activity)context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(((Activity)context).getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

}
