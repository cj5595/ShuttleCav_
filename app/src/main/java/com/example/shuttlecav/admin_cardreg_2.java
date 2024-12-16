package com.example.shuttlecav;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_cardreg_2 extends AppCompatActivity {
    private TextView textViewNFC;
    private NfcAdapter nfcAdapter;
    private Button btnConfirm;
    private EditText editTextBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_cardreg_page2);

        textViewNFC = findViewById(R.id.textView21);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        btnConfirm = findViewById(R.id.button5);
        editTextBalance = findViewById(R.id.editTextText6);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user_data", null);

        btnConfirm.setOnClickListener(v -> {
            String balance = editTextBalance.getText().toString();
            String rfid_tag = textViewNFC.getText().toString();
            String url = getString(R.string.webservice_url) + "newuser.php?rfid_tag=" + rfid_tag
                                                            + "&balance=" + balance
                                                            + "&function=1";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                    JSONObject user = new JSONObject(userData);
                                    // Use the user data as needed
                                    Logger.log(admin_cardreg_2.this, "INFO", "New user registered", "New user registered successfully", user.getString("id"));
                                    Toast.makeText(admin_cardreg_2.this, "RFID tag registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(admin_cardreg_2.this, admin_confirm.class);
                                    startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(admin_cardreg_2.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                String errorMsg = new String(error.networkResponse.data);
                                Toast.makeText(admin_cardreg_2.this, "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(admin_cardreg_2.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            // Add the request to the RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(admin_cardreg_2.this);
            requestQueue.add(jsonObjectRequest);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String tagId = bytesToHex(tag.getId());

            textViewNFC.setText(tagId);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
