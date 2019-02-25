package com.example.generalapplication.Helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.generalapplication.Adapters.OrderAdapter;

import static com.example.generalapplication.Activities.MainActivity.llMainLayout;
import static com.example.generalapplication.Activities.MainActivity.views;

public class UI {

    public static OrderAdapter orderAdapter;

    public static void CreateBasicSnack(String message, Integer length, Context context){
        length = length != null ? length : 3000;
        Snackbar.make(((Activity)context).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public static void ChangeView(View view) {  // Delete all views and add specified one
        for (View v : views) {
            llMainLayout.removeView(v);
        }
        try {
            view.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT));
            llMainLayout.addView(view);
        } catch (Exception ex) {
            Log.e("MAIN.CHANGEVIEW", ex.getMessage());
        }
    }

}
