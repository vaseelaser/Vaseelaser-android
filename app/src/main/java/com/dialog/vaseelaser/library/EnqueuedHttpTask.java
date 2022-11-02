package com.dialog.vaseelaser.library;


import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


@SuppressWarnings({"Convert2Lambda"})
public class EnqueuedHttpTask {

    private final static String TAG = "EnqueuedHttpTask";

    /*
    OkHttp performs best when you create a single OkHttpClient instance and reuse it for all
    of your HTTP calls. This is because each client holds its own connection pool and thread pools.
    Reusing connections and threads reduces latency and saves memory.
    Conversely, creating a client for each request wastes resources on idle pools.
     */
    private String mCred = null;
    private final String mUrl;
    private MediaType mJson = null;
    private final APIRequestHandler.API_CALLS mApiCallType;
    private RequestBody mRequestBody;
    private final IAsyncResponse mIAsyncResponseListener;
    private final WeakReference<AppCompatActivity> mActivityRef;

    private String mMeteoAuthString;
    private String mMeteoDateStamp;


    public EnqueuedHttpTask(AppCompatActivity act, APIRequestHandler.API_CALLS requestType, String cred, String url, MediaType json, IAsyncResponse IAsyncResponse) {
        mCred = cred;
        mJson = json;
        mUrl = url;
        mActivityRef = new WeakReference<>(act);
        mApiCallType = requestType;
        mIAsyncResponseListener = IAsyncResponse;//Assigning call back interface through constructor
    }

    public EnqueuedHttpTask(AppCompatActivity act, APIRequestHandler.API_CALLS requestType, String url, IAsyncResponse IAsyncResponse) {
        mIAsyncResponseListener = IAsyncResponse;
        mActivityRef = new WeakReference<>(act);
        mApiCallType = requestType;
        mUrl = url;
    }

    public EnqueuedHttpTask(AppCompatActivity act, APIRequestHandler.API_CALLS requestType, String authString, String url, String dateStamp, IAsyncResponse IAsyncResponse) {
        mIAsyncResponseListener = IAsyncResponse;
        mActivityRef = new WeakReference<>(act);
        mApiCallType = requestType;
        mUrl = url;
        mMeteoAuthString = authString;
        mMeteoDateStamp = dateStamp;
    }

    public EnqueuedHttpTask(AppCompatActivity act, APIRequestHandler.API_CALLS requestType, String url, RequestBody requestBody, IAsyncResponse IAsyncResponse) {
        mIAsyncResponseListener = IAsyncResponse;
        mActivityRef = new WeakReference<>(act);
        mApiCallType = requestType;
        mRequestBody = requestBody;
        mUrl = url;
    }

    private void informMainThread(final String response, final int code) {
        mActivityRef.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIAsyncResponseListener.processFinish(response, code, mUrl);
            }
        });
    }

    private void _run() {
        Request request;
        RequestBody requestBody;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(mUrl);
        switch (mApiCallType) {
            case TOKEN:
                requestBody = RequestBody.create(mCred, mJson);
                requestBuilder.post(requestBody);
                break;
            case FETCH_USER_DATA:

                requestBuilder.get();
                break;
            case DELETE_USER_DATA:

                requestBuilder.delete();
                break;
            case TEXT_GENERATOR:

                requestBuilder.post(mRequestBody);
                requestBuilder.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                requestBuilder.addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("X-CSRF-TOKEN", "oNxaWMQF6lprtTZb6NmKelhekIVjYBzzUI8MMGx2");
                requestBuilder.addHeader("X-Requested-With", "XMLHttpRequest");
                requestBuilder.addHeader("Origin", "https://fancy-generator.com");
                requestBuilder.addHeader("Connection", "keep-alive");
                requestBuilder.addHeader("Referer", "https://fancy-generator.com/vaporwave-text-generator");
                requestBuilder.addHeader("Cookie", "XSRF-TOKEN=eyJpdiI6IlRsWFFPbSttZHdBbEt5Q1l0bzYrYWc9PSIsInZhbHVlIjoiekE1TUdQa3VmdjlsUVhyNTNQMUxZeTZBZ3JQWXNBOWFoSnkwT2d1RHlReCt6TStyTElCNmdMUVVmK0hLQ2Vhc3JKNE1ldXNtc1pyTU85azd4emhobUluVG5Db0p1ejZaSDlCK1YrTjVtak9BY0J1dDQzVkExeUpmVHhrKzNhSHMiLCJtYWMiOiJiNWFmMmI3ZDgyZTE3ZmIxNGYzNWU2YzY2MDc0ZjNhMGVkM2ExYTkwN2FjNTE5YjY0OWI2ODI0OWFjZDJhNjg0In0%3D; fancy_generator_session=eyJpdiI6IkNHamNyQ2RjaCtMdFZNYkt3VnlIdWc9PSIsInZhbHVlIjoiSms5eDhseVlmTzFsZTMxRGVFWnl6Y2p2VHFiRzMwaFYvVmkybGhRNGxOYmc5VXd5QlpQUk0ydm5XdEh0eFlmVmVjMjZTWi82SDlRUzkrWldyUHkvcWRuSVJsQ3ozczBrSGJWMlNtR3VyVzFNbEM0YWV6ckl6cGtrV2FsQnBLMWIiLCJtYWMiOiJlOGI2MWZkODkzNDgwZDc3NDVmZTcwNTBhOTI0ZTg3ZDUyNWFjMWE4NjM5YjczNDMwY2M5MmFjOTA5OWExOWE1In0%3D; _ga_YXH3LCMM3E=GS1.1.1666043982.2.1.1666043983.0.0.0; _ga=GA1.1.1935777706.1666041242");

                break;
            case PUT_USER_DATA:
                requestBuilder.put(mRequestBody);

                break;
            case PATCH_USER_DATA:
                requestBuilder.patch(mRequestBody);

                break;

        }
        request = requestBuilder.build();

        try {
            OkHttpClient mHttpClient;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(60, TimeUnit.SECONDS);
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);
            mHttpClient = builder.build();
            mHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();

                    String message = e.getMessage() + "";
                    if (message.contains("timeout"))
                        informMainThread("timeout", -1);
                    else  // no internet connection is active
                        informMainThread(null, -1);
                }

                // this routine is triggered via other thread
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    // now trigger the listener from other thread

                    final String[] body = {null};
                    final int[] code = new int[1];

                    try {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            body[0] = responseBody.string(); // one shot consuming
                            code[0] = response.code();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (body[0] != null) {
                        Log.e(TAG, response.toString());
                    } else {
                        Log.e(TAG, "Null response - final");
                    }
                    informMainThread(body[0], code[0]);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _runParallel(OkHttpClient client) {
        Request request;
        RequestBody requestBody;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(mUrl);
        switch (mApiCallType) {
            case TOKEN:
                requestBody = RequestBody.create(mCred, mJson);
                requestBuilder.post(requestBody);
                break;
            case FETCH_USER_DATA:

                requestBuilder.get();
                break;
            case DELETE_USER_DATA:

                requestBuilder.delete();
                break;
            case POST_USER_DATA:
                requestBuilder.post(mRequestBody);

                break;
            case PUT_USER_DATA:
                requestBuilder.put(mRequestBody);

                break;
            
        }
        request = requestBuilder.build();

        try {
            client.dispatcher().setMaxRequestsPerHost(20);
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                // this routine is triggered via other thread
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {

                    // now trigger the listener from other thread
                    final String[] body = {null};
                    final int[] code = new int[1];

                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        try {

                            body[0] = responseBody.string(); // one shot consuming
                            code[0] = response.code();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (body[0] != null) {
                        Log.e(TAG, response.toString());
                    } else {
                        Log.e(TAG, "Null response - final");
                    }
                    informMainThread(body[0], code[0]);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void _exec() {
        _run();
    }

    public void _execWithThreadPool(OkHttpClient client) {
        _runParallel(client);
    }


}