package com.dialog.vaseelaser.library;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Vaporizer {

    public static void translate(AppCompatActivity act,String s, EtherListener listener) {

        APIRequestHandler h = new APIRequestHandler(act, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                if (code >= 200 && code <= 300){
                    Log.e("sdsds "+code,response);
                }
            }
        });
        h.postUserData("https://fancy-generator.com/generate",createPostBody(s));

    }

    private static RequestBody createPostBody(String s ){

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String bd = "{\"text\":\"{text}\",\"custom_font\":\"\"}";

        bd = bd.replace("{text}",s);
        Log.e("sdsdsds",bd);
        RequestBody body = RequestBody.create(bd, JSON);
        return body;
    }
}
