package com.example.shuttlecav;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_confirm extends AppCompatActivity {

    private TextView textViewSurname;
    private TextView textViewFirstname;
    private TextView textViewMiddlename;
    private TextView textViewUsername;
    private TextView textViewGender;
    private TextView textViewPhone;
    private TextView textViewCardTag;
    private TextView textViewCardBalance;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cardregsave_page);

        textViewSurname = findViewById(R.id.textView26);
        textViewFirstname = findViewById(R.id.textView31);
        textViewMiddlename = findViewById(R.id.textView33);
        textViewUsername = findViewById(R.id.textView35);
        textViewGender = findViewById(R.id.textView37);
        textViewPhone = findViewById(R.id.textView39);
        textViewCardTag = findViewById(R.id.textView41);
        textViewCardBalance = findViewById(R.id.textView43);
        btnSave = findViewById(R.id.button5);

        String url = getString(R.string.webservice_url) + "getlastuser.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String surName = response.getString("surName");
                            String firstName = response.getString("firstName");
                            String middleName = response.getString("middleName");
                            String userName = response.getString("username");
                            String gender = response.getString("Gender");
                            String phone = response.getString("phone");
                            String cardTag = response.getString("rfid_tag");
                            String cardBalance = response.getString("balance");

                            textViewSurname.setText(surName);
                            textViewFirstname.setText(firstName);
                            textViewMiddlename.setText(middleName);
                            textViewUsername.setText(userName);
                            textViewGender.setText(gender);
                            textViewPhone.setText(phone);
                            textViewCardTag.setText(cardTag);
                            textViewCardBalance.setText(cardBalance);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(admin_confirm.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_confirm.this, "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        // Set OnClickListener for btnSave
        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(admin_confirm.this, admin_cardreg_success.class);
            startActivity(intent);
        });
    }
}