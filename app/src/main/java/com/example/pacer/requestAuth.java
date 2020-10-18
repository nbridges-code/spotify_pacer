package com.example.pacer;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class requestAuth {
    public static void requestAuth(Context c) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "https://accounts.spotify.com/authorize";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("requestAuth", "response: " + response);
                        Document document = Jsoup.parse(response);
                        Log.d("requestAuth", "response_after_parsing: " + document);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("skipSong", "That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void postRequest(Context c){
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "https://accounts.spotify.com/api/token";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "L");
                    }
                }
        );
        queue.add(postRequest);
    }
}