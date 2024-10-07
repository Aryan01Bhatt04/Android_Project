package com.example.smallbasket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Grocery> {
    Context context;
    int design;
    ArrayList<Grocery> groceryList;
    DatabaseReference dbDelete,dbUpdate;
    StorageReference deleteImage;


    CustomAdapter(Context context,int design,ArrayList<Grocery> groceryList){
    super(context,design,groceryList);
    this.context = context;
    this.design = design;
    this.groceryList = groceryList;
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
        ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        ImageButton updateButton = view.findViewById(R.id.updateButton);
        ImageView cardImg = view.findViewById(R.id.cardimg);

        Grocery grocery = groceryList.get(position);
        productName.setText(grocery.getProductName());
        quantity.setText(""+grocery.getProductStock());
        unit.setText(grocery.getMeasurement());
        cost.setText(""+grocery.getProductPrice());
        description.setText(grocery.getDescription());

        Glide.with(context).load(grocery.getImgUrl()).into(cardImg);
        dbDelete = FirebaseDatabase.getInstance().getReference("Grocery");
        dbUpdate = FirebaseDatabase.getInstance().getReference("Grocery");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deleteGrocery(grocery);
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(context, UpdateProduct.class);
                ii.putExtra("glist",groceryList);
                ii.putExtra("position",position);
                context.startActivity(ii);
            }
        });

        return view;

    }

    public void deleteGrocery(Grocery grocery){
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        build.setTitle("Are You Sure? ");

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query deleteQuery = dbDelete.orderByChild("gid").equalTo(grocery.getGid());
                deleteImage = FirebaseStorage.getInstance().getReferenceFromUrl(grocery.getImgUrl());
                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()){
                            snap.getRef().removeValue();

                            deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Image Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        AlertDialog alert = build.create();
        alert.show();
    }
}
