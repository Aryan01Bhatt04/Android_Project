package com.example.smallbasket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopAdapter extends ArrayAdapter<Grocery> {
    Context context;
    int design;
    int cartValue = 0;
    ArrayList<Grocery> groceryList;
    int qty[];
    int price[];
    ArrayList<Grocery> cartList = new ArrayList<>();


    ShopAdapter(Context context,int design,ArrayList<Grocery> groceryList) {
        super(context,design,groceryList);
        this.context = context;
        this.design = design;
        this.groceryList = groceryList;
        qty = new int[groceryList.size()];
        price = new int[groceryList.size()];
//        Toast.makeText(context,""+qty,Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(design,null,false);

        TextView productName = view.findViewById(R.id.productName);
        TextView quantity = view.findViewById(R.id.quantity);
        TextView unit = view.findViewById(R.id.unit);
        TextView cost = view.findViewById(R.id.cost);
        TextView description = view.findViewById(R.id.description);
        ImageButton PlusButton = view.findViewById(R.id.PlusButton);
        ImageButton MinusButton = view.findViewById(R.id.MinusButton);
        TextView numberOfProduct = view.findViewById(R.id.numberOfProduct);
        ImageView cardImg = view.findViewById(R.id.cardimg);
        Button ProceedButton = ((Activity)context).findViewById(R.id.ProceedButton);

        Grocery grocery = groceryList.get(position);
        productName.setText(grocery.getProductName());
        quantity.setText(""+grocery.getProductStock());
        unit.setText(grocery.getMeasurement());
        cost.setText(""+grocery.getProductPrice());
        description.setText(grocery.getDescription());

        Glide.with(context).load(grocery.getImgUrl()).into(cardImg);

        qty[position] = 0;
        price[position] = groceryList.get(position).getProductPrice();

        PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty[position] = qty[position] + 1;
                grocery.setOrder(qty[position]);
                numberOfProduct.setText(""+grocery.getOrder());
                cartValue = cartValue + price[position];
                TextView Total = ((Activity)context).findViewById(R.id.Total);
                Total.setText(""+cartValue);
            }
        });

        MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty[position] -= 1;
                grocery.setOrder(qty[position]);
                numberOfProduct.setText(""+grocery.getOrder());
                cartValue = cartValue - price[position];
                TextView Total = ((Activity)context).findViewById(R.id.Total);
                Total.setText(""+cartValue);
            }
        });

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i <= groceryList.size()-1; i++){
                    Grocery gr = groceryList.get(i);
                    if (gr.getOrder() > 0){
                        cartList.add(gr);
                        Toast.makeText(context, "ListSize:"+cartList.size(), Toast.LENGTH_SHORT).show();
                    }
                }

                Intent ii = new Intent(context, YourCart.class);
                ii.putExtra("selection",cartList);
                context.startActivity(ii);
            }
        });

//        for(int i=0; i <= groceryList.size(); i++){
//            Grocery gr = groceryList.get(i);
//            if (gr.getOrder() > 0){
//                cartList.add(gr);
//            }
//        }

        return view;

    }


}