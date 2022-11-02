package com.dialog.vaseelaser.library;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.dialog.vaseelaser.IImageSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Vaporizer {

    private static String getRealPathFromURIForGallery(AppCompatActivity act,Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = act.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        assert false;
        cursor.close();
        return uri.getPath();
    }

    public static void __i_m_a_g_e_b_e_n_d_power_fulLlLly(AppCompatActivity act, Uri uri, IImageSelectedListener listener) {

        final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/*");

        Log.e("sdsdsds",uri.getPath());
        String selectedImagePath = getRealPathFromURIForGallery(act,uri);
        File sourceFile = new File(selectedImagePath);

        MultipartBody.Builder postBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "img_is_now_fixed.jpg", RequestBody.create(sourceFile, MEDIA_TYPE_IMG));

        RequestBody body =  postBody.build();

        APIRequestHandler h = new APIRequestHandler(act, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                if (code == 500) {
                    listener.onImageSelected("__dead__");
                } else {
                    try {
                        JSONObject obj = new JSONObject(response);
                        listener.onImageSelected(obj.getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        h.postUserData("http://54.72.86.60:1000/api/imagebend/ultra_bend_image/",body);
    }


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
