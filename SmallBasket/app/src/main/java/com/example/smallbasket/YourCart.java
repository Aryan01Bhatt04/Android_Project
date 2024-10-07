package com.example.smallbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smallbasket.model.Grocery;

import java.util.ArrayList;

public class YourCart extends Activity {

    ListView listItems;
    ArrayList<Grocery> selections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        listItems = findViewById(R.id.listItems);

        Intent ii = getIntent();
        selections = (ArrayList<Grocery>) ii.getSerializableExtra("selection");
        Toast.makeText(YourCart.this,"list size:"+selections.size(), Toast.LENGTH_SHORT).show();

        ProceedAdapter adapter = new ProceedAdapter(YourCart.this,R.layout.selected_items,selections);
        listItems.setAdapter(adapter);

    }
}