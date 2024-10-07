package com.example.smallbasket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;

import java.util.ArrayList;

public class ProceedAdapter extends ArrayAdapter {
    Context context;
    int design;
    ArrayList<Grocery> selectedList;

    ProceedAdapter(Context context,int design,ArrayList<Grocery> selectedList){
        super(context,design,selectedList);
        this.context = context ;
        this.design = design;
        this.selectedList = selectedList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(design,null,false);

        TextView productName = view.findViewById(R.id.productName);
        TextView productQuantity = view.findViewById(R.id.productQuantity);
        TextView Unit = view.findViewById(R.id.Unit);
        TextView selectedNumber = view.findViewById(R.id.selectedNumber);
        TextView productPrice = view.findViewById(R.id.productPrice);
        ImageView productImg = view.findViewById(R.id.productImg);

        Grocery grocery = selectedList.get(position);
        productName.setText(grocery.getProductName());
        productQuantity.setText(""+grocery.getProductStock());
        Unit.setText(grocery.getMeasurement());
        Glide.with(context).load(grocery.getImgUrl()).into(productImg);
        selectedNumber.setText(""+grocery.getOrder());
        productPrice.setText(""+(grocery.getProductPrice() * grocery.getOrder()));

        return view;
    }
}
