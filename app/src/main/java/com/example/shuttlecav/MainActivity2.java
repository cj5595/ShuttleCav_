// MainActivity2.java
package com.example.shuttlecav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    private TextView textView6;
    private FloatingActionButton btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_page);

        textView6 = findViewById(R.id.textViewUser);
        btn_Register = findViewById(R.id.btn_Register);

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user_data", null);

        if (userData != null) {
            try {
                JSONObject user = new JSONObject(userData);
                // Use the user data as needed
                String username = user.getString("surName") + ", " + user.getString("firstName") + " " + user.getString("middleName");
                textView6.setText(username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        btn_Register.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, admin_cardreg_1.class);
            startActivity(intent);
        });
    }
}