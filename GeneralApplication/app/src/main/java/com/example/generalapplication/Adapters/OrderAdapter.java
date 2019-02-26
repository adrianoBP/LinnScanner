package com.example.generalapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.generalapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.generalapplication.Helpers.Core.allOrders;

public class OrderAdapter extends BaseAdapter {

    // TODO: filter by location

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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = ((Activity)classContext).getLayoutInflater().inflate(R.layout.adapter_order, null);

        multiSelectedOrders = new ArrayList<>();
        multiSelectEnabled = false;

        final TextView tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
        final TextView tvOrderTotal = view.findViewById(R.id.tvOrderTotal);
        final TextView tvOrderCurrency = view.findViewById(R.id.tvOrderCurrency);
        final ConstraintLayout clParent = view.findViewById(R.id.clOrder);
        final Boolean[] isSelected = {false};

        tvOrderNumber.setText(allOrders.get(i).NumOrderId.toString());
        tvOrderTotal.setText(allOrders.get(i).TotalsInfo.TotalCharge.toString());
        tvOrderCurrency.setText(allOrders.get(i).TotalsInfo.Currency);

        clParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(multiSelectEnabled){
                    if(!isSelected[0]){
                        multiSelectedOrders.add(allOrders.get(i).OrderId);
                        clParent.setBackgroundColor(classContext.getResources().getColor(R.color.selected));
                    }else{
                        multiSelectedOrders.remove(allOrders.get(i).OrderId);
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
                multiSelectedOrders.add(allOrders.get(i).OrderId);
                clParent.setBackgroundColor(classContext.getResources().getColor(R.color.selected));
                return true;
            }
        });

        return view;
    }

}
