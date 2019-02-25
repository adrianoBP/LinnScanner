//package com.example.generalapplication.Adapters;
//
//import android.app.Activity;
//import android.support.constraint.ConstraintLayout;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class OrderAdapter extends BaseAdapter {
//
//    @Override
//    public int getCount() {
//        return myElements.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//        view = ((Activity)classContext).getLayoutInflater().inflate(R.layout.layout_adapter_listview, null);
//
//        final TextView tvText = view.findViewById(R.id.tvCLVtext);
//        Button bButton = view.findViewById(R.id.bCLVbutton);
//        ConstraintLayout clParent = view.findViewById(R.id.clCLVparent);
//
//        tvText.setText(myElements.get(i).myString);
//
//        clParent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(classContext, tvText.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        bButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myElements.get(i).myString = "";
//                tvText.setText("");
//            }
//        });
//        return view;
//    }
//
//}
