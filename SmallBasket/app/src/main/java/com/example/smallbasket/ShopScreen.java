package com.example.smallbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smallbasket.model.Grocery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopScreen extends Activity  {

    ListView Products;
    ArrayList<Grocery> listgrocery = new ArrayList<>();
    DatabaseReference dbref;
    Button ProceedtoPay;
    ArrayList<Grocery> cartList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop_screen);

        Products = findViewById(R.id.Products);
        ProceedtoPay = findViewById(R.id.ProceedButton);

        dbref = FirebaseDatabase.getInstance().getReference("Grocery");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listgrocery.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Grocery g = snap.getValue(Grocery.class);
                    listgrocery.add(g);
                }

                // Notify adapter about data changes
                ShopAdapter adapter = new ShopAdapter(ShopScreen.this,R.layout.product_item,listgrocery);
                Products.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }

        });

//        for(int i=0; i <= listgrocery.size(); i++){
//            Grocery gr = listgrocery.get(i);
//            if (gr.getOrder() > 0){
//                cartList.add(gr);
//            }
//        }
//        Toast.makeText(ShopScreen.this, "ListSize:"+listgrocery.size(), Toast.LENGTH_SHORT).show();
//        ProceedtoPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ii = new Intent(getApplicationContext(), YourCart.class);
//                ii.putExtra("selection",cartList);
//                startActivity(ii);
//            }
//        });

    }
}
