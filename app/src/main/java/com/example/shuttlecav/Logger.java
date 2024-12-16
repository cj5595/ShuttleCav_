package com.example.shuttlecav;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Logger {
    public static void log(Context context, String log_level, String event, String details, String user_id) {
        String url = context.getString(R.string.webservice_url) + "log.php?"
                + "log_level=" + log_level
                + "&event=" + event
                + "&details=" + details
                + "&user_id=" + user_id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        Toast.makeText(context, "Log entry created successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Toast.makeText(context, "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}