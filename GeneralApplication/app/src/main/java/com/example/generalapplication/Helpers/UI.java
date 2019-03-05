package com.example.generalapplication.Helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.generalapplication.Adapters.OrderAdapter;
import com.example.generalapplication.Classes.TemplateHeader;
import com.example.generalapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.generalapplication.APIHelper.Internal.GetTemplateList;
import static com.example.generalapplication.Activities.MainActivity.llMainLayout;
import static com.example.generalapplication.Activities.MainActivity.views;
import static com.example.generalapplication.Activities.PrintActivity.allTemplates;
import static com.example.generalapplication.Activities.PrintActivity.sTemplateName;
import static com.example.generalapplication.Activities.PrintActivity.sTemplateType;

public class UI {

    public static OrderAdapter orderAdapter;

    public static void CreateBasicSnack(String message, Integer length, Context context) {
        length = length != null ? length : 3000;
        Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, length)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(context.getResources().getColor(R.color.colorAccent))
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

    public static void UpdateTemplateTypes(Context context){

        if(allTemplates.size() == 0 ){
            GetTemplateList(context, true);
        }else{

            List<String> types = new ArrayList<>();
            for (TemplateHeader template :
                    allTemplates.values()) {
                if(!types.contains(template.TemplateType))
                    types.add(template.TemplateType);
            }

            if(types.size() > 0){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line, types);
                sTemplateType.setAdapter(adapter);
                UpdateTemplateNames(context, sTemplateType.getSelectedItem().toString());
            }else{
                CreateBasicSnack("Unable to retrieve template types.", null, context);
            }
        }
    }

    public static void UpdateTemplateNames(Context context, String type){

        if(allTemplates.size() > 0){
            List<String> templateNames = new ArrayList<>();
            for (TemplateHeader template :
                    allTemplates.values()) {
                if(!templateNames.contains(template.TemplateName) && template.TemplateType.equals(type))
                    templateNames.add(template.TemplateName);
            }
            if(templateNames.size() > 0){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line, templateNames);
                sTemplateName.setAdapter(adapter);
            }else{
                CreateBasicSnack("Unable to retrieve template names from template type.", null, context);
            }
        }else{
            CreateBasicSnack("Unable to retrieve template names.", null, context);
        }
    }

}
