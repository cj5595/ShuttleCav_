package com.example.shuttlecav;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class admin_cardreg_1 extends AppCompatActivity {

    private Button btnNext, btnBack;
    private EditText surName, firstName, middleName, userName, gender, phone, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cardreg_page1);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        surName = findViewById(R.id.editTextText2);
        firstName = findViewById(R.id.editTextText3);
        middleName = findViewById(R.id.editTextText4);
        userName = findViewById(R.id.editTextText6);
        gender = findViewById(R.id.editTextText7);
        phone = findViewById(R.id.editTextText8);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnNext.setOnClickListener(v -> {
            String surNameValue = surName.getText().toString();
            String firstNameValue = firstName.getText().toString();
            String middleNameValue = middleName.getText().toString();
            String userNameValue = userName.getText().toString();
            String genderValue = gender.getText().toString();
            String phoneValue = phone.getText().toString();
            String passwordValue = password.getText().toString();
            String confirmPasswordValue = confirmPassword.getText().toString();

            if (surNameValue.isEmpty() ||
                    firstNameValue.isEmpty() ||
                    middleNameValue.isEmpty() ||
                    userNameValue.isEmpty() ||
                    genderValue.isEmpty() ||
                    phoneValue.isEmpty() ||
                    passwordValue.isEmpty() ||
                    confirmPasswordValue.isEmpty()) {

                Toast.makeText(admin_cardreg_1.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!passwordValue.equals(confirmPasswordValue)) {
                Toast.makeText(admin_cardreg_1.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                String url = getString(R.string.webservice_url) + "newuser.php?username=" + userNameValue
                        + "&password=" + passwordValue
                        + "&surName=" + surNameValue
                        + "&firstName=" + firstNameValue
                        + "&middleName=" + middleNameValue
                        + "&gender=" + genderValue
                        + "&phone=" + phoneValue
                        + "&function=0&role=1";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle the response
                                Toast.makeText(admin_cardreg_1.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(admin_cardreg_1.this, admin_cardreg_2.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    String errorMsg = new String(error.networkResponse.data);
                                    Toast.makeText(admin_cardreg_1.this, "Error1: " + errorMsg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(admin_cardreg_1.this, "Error2: " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // Add the request to the RequestQueue
                RequestQueue requestQueue = Volley.newRequestQueue(admin_cardreg_1.this);
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}