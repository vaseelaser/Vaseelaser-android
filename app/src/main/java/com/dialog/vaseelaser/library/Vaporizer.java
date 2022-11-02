package com.dialog.vaseelaser.library;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Vaporizer {

    public static void translate(AppCompatActivity act, String s, EtherListener listener) {

        APIRequestHandler h = new APIRequestHandler(act, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                if (code >= 200 && code <= 300) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response.trim());
                        Log.e("gamisw", response);
                        Iterator<String> keys = jsonObject.keys();

                        List<String> forreturn = new ArrayList<>();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            forreturn.add(jsonObject.get(key) + "");
                        }
                        listener.onEtherReceived(forreturn,code,calledURL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        h.textGenerator("https://fancy-generator.com/generate", createPostBody(s));

    }

    private static RequestBody createPostBody(String s) {

        MediaType JSON = MediaType.parse("application/json");
        String bd = "{\"text\":\"{text}\",\"custom_font\":\"\"}";

        bd = bd.replace("{text}", s);
        Log.e("sdsdsds", bd);
        RequestBody body = RequestBody.create(bd, JSON);
        return body;
    }
}
