package com.example.smallbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {

    EditText etUsername,etPassword;
    Button btLogin,btChange,btShop;

    SharedPreferences shad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btChange = findViewById(R.id.btChange);
        btShop = findViewById(R.id.btShop);

        shad = getSharedPreferences("adminCredentials", Context.MODE_PRIVATE);
        String mail = shad.getString("Mail","admin");
        String password = shad.getString("Password","admin2024");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (email.equals(mail) && pass.equals(password)){
                    Intent ii = new Intent(getApplicationContext(),AdminZone.class);
                    startActivity(ii);
                }
                else{
                    Toast.makeText(MainActivity.this, "UnAuthorized User", Toast.LENGTH_LONG).show();
                }
            }
        });

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(ii);
            }
        });

        btShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(MainActivity.this, ShopScreen.class);
                startActivity(ii);
            }
        });
    }
}