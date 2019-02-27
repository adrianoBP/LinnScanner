package com.example.generalapplication.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.generalapplication.Classes.OrderDetails;
import com.example.generalapplication.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.generalapplication.Helpers.Core.allOrders;
import static com.example.generalapplication.Helpers.Core.allSources;

public class OrderAdapter extends BaseAdapter {

    private Context classContext;
    public static List<UUID> multiSelectedOrders = new ArrayList<>();
    public static Boolean multiSelectEnabled = false;

    public OrderAdapter(Context context){
        classContext = context;
    }

    @Override
    public int getCount() {
        return allOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = ((Activity)classContext).getLayoutInflater().inflate(R.layout.adapter_order, null);

        multiSelectedOrders = new ArrayList<>();
        multiSelectEnabled = false;

        final OrderDetails currentOrder = allOrders.get(i);

        final TextView tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
        final TextView tvOrderTotal = view.findViewById(R.id.tvOrderTotal);
        final TextView tvOrderCurrency = view.findViewById(R.id.tvOrderCurrency);
        final ConstraintLayout clParent = view.findViewById(R.id.clOrder);
        final Boolean[] isSelected = {false};
        final ImageView ivSourceicon = view.findViewById(R.id.ivSourceIcon);

        if(allSources.containsKey(currentOrder.GeneralInfo.Source)){
            final String url  = allSources.get(currentOrder.GeneralInfo.Source).imageUrl;

            try {
                Bitmap bitmap = Picasso.with(classContext).load("http://i.imgur.com/DvpvklR.png").get();
                Log.i("", "");
            } catch (IOException e) {
                e.printStackTrace();
            }


//            new AsyncTask<Void,String,String>(){
//
//                @Override
//                protected String doInBackground(Void... params) {
//                    InputStream is = null;
//                    try {
//                        is = (InputStream) new URL(url).getContent();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Drawable imageFromUrl = Drawable.createFromStream(is, "src name");
//                    ivSourceicon.setImageDrawable(imageFromUrl);
//                    Log.d("", "");
//                    return "";
//                }
//
//                protected void onPostExecute(String results){
//                    // Response returned by doInBackGround() will be received
//                    // by onPostExecute(String results).
//                    // Now manipulate your jason/xml String(results).
//                }
//
//            }.execute();

//                new Thread(){
//                    public void run(){
//                        try {
//                            InputStream is = (InputStream) new URL(url).getContent();
//                            Drawable d = Drawable.createFromStream(is, "src name");
//                            Log.d("", "");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.run();


//                Picasso.with(classContext).load(url).into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        try {
//                            Bitmap b =  Picasso.with(classContext).load(allSources.get(currentOrder.GeneralInfo.Source).imageUrl).get();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("", "");
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//                        Log.d("", "");
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//                        Log.d("", "");
//
//                    }
//                });

        }

        tvOrderNumber.setText(currentOrder.NumOrderId.toString());
        tvOrderTotal.setText(currentOrder.TotalsInfo.TotalCharge.toString());
        tvOrderCurrency.setText(currentOrder.TotalsInfo.Currency);

        clParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(multiSelectEnabled){
                    if(!isSelected[0]){
                        multiSelectedOrders.add(currentOrder.OrderId);
                        clParent.setBackgroundColor(classContext.getResources().getColor(R.color.selected));
                    }else{
                        multiSelectedOrders.remove(currentOrder.OrderId);
                        clParent.setBackgroundColor(classContext.getResources().getColor(R.color.backgroundLight));
                    }

                    isSelected[0] = !isSelected[0];
                    multiSelectEnabled = multiSelectedOrders.size() > 0;

                }else{
                    Toast.makeText(classContext, tvOrderNumber.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        clParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isSelected[0] = true;
                multiSelectEnabled = true;
                multiSelectedOrders.add(currentOrder.OrderId);
                clParent.setBackgroundColor(classContext.getResources().getColor(R.color.selected));
                return true;
            }
        });

        return view;
    }

}
