package com.example.pacer;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.HttpConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class getPlaylists {
    private static final String CLIENT_ID = "83bbac4b860942f7813149bdc4093004";
    private static final String ENCODED_REDIRECT_URI = "http%3A%2F%2Flocalhost%3A8888%2Fcallback";
    private static String playlistName = "https://api.spotify.com/v1/search?q="; // GET
    private String playlistid = "";

    public getPlaylists(VolleyCallBack callBack, Context context, int bpm, String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String endpoint = playlistName + String.valueOf(bpm) + "&type=playlist";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    JSONObject playlists = response.optJSONObject("playlists");
                    JSONArray items = playlists.optJSONArray("items");
                    for (int n = 0; n < items.length(); n++) {
                        try {
                            JSONObject item = items.getJSONObject(n);
                            Log.d("getPlaylists", String.valueOf(item.get("name")));

//                            JSONObject object = items.getJSONObject(n);
//                            object = object.optJSONObject("track");
//
//                            Song song = gson.fromJson(object.toString(), Song.class);
//                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    playlistid = (String) items.optJSONObject(0).opt("snapshot_id");
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                //String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public String getPlaylistId(){
        return playlistid;
    }

    /*
    public static void requestAuth(Context c) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "https://accounts.spotify.com/authorize?client_id=" + CLIENT_ID + "&response_type=code&redirect_uri=" + ENCODED_REDIRECT_URI + "&scope=user-modify-playback-state";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("requestAuth", "response: " + response);
//                        Document document = Jsoup.parse(response);
//                        Log.d("requestAuth", "response_after_parsing: " + document);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("requestAuth", "That didn't work!");
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
    */
}