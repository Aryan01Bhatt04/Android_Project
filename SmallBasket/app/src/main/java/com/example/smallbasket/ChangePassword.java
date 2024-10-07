package com.example.smallbasket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePassword extends Activity {

    EditText oldpass,newpass;
    TextView errorMsg;
    Button btUp,btCancel;
    SharedPreferences shad;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        btUp = findViewById(R.id.btUp);
        btCancel = findViewById(R.id.btCancel);
        errorMsg = findViewById(R.id.errorMsg);

        shad = getSharedPreferences("adminCredentials",MODE_PRIVATE);
        String existing_pass = shad.getString("Password","admin2024");

        btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verify_pass = oldpass.getText().toString();
                if (verify_pass.equals(existing_pass)){
                    String set_pass = newpass.getText().toString();
                    SharedPreferences.Editor edit = shad.edit();
                    edit.putString("Password",set_pass);
                    edit.commit();
                    Toast.makeText(ChangePassword.this, "Password Updated", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(ii);
                }
                else {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anything);
                    errorMsg.setText("Old Password Does Not Match");
                    errorMsg.startAnimation(animation);

//                    Toast.makeText(ChangePassword.this, "Old Password did not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass.setText("");
                newpass.setText("");
            }
        });
    }
}