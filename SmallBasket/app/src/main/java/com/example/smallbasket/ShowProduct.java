package com.example.smallbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.smallbasket.model.Grocery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowProduct extends Activity {

    ListView listItems;
    ArrayList<Grocery> listgrocery = new ArrayList<>();

    ImageButton deleteButton,updateButton;
    DatabaseReference dbref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

        listItems = findViewById(R.id.listItems);


        dbref = FirebaseDatabase.getInstance().getReference("Grocery");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listgrocery.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Grocery g = snap.getValue(Grocery.class);
                    listgrocery.add(g);
                }

                CustomAdapter adapter = new CustomAdapter(ShowProduct.this,R.layout.item_grocery,listgrocery);
                listItems.setAdapter(adapter);

                // Notify adapter about data changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
}