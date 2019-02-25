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

import static com.example.generalapplication.Helpers.Core.allOrders;

public class OrderAdapter extends BaseAdapter {

    // TODO: filter by location

    private Context classContext;

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

        final TextView tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
        ConstraintLayout clParent = view.findViewById(R.id.clOrder);

        tvOrderNumber.setText(allOrders.get(i).NumOrderId.toString());

        clParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(classContext, tvOrderNumber.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
