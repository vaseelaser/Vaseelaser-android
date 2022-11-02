package com.dialog.vaseelaser.library;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;



import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

@SuppressWarnings({"Convert2Lambda,Anonymous2MethodRef"})
public class APIRequestHandler {
    private final IAsyncResponse mIAsyncResponse;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private API_CALLS mApiCallType;
    private final AppCompatActivity mActivity;


    public enum API_CALLS {
        TOKEN,
        FETCH_USER_DATA,
        POST_USER_DATA,
        PUT_USER_DATA,
        PATCH_USER_DATA,
        DELETE_USER_DATA,
        TEXT_GENERATOR
    }

    /**
     * Support multiple-sequential fetches progress dialog. SEQUENTIAL_START pops the dialog
     * while SEQUENTIAL_END dismisses it. SEQUENTIAL_BETWEEN will allow AsyncTask to bypass
     * dialog creation without crashing
     *
     * ========================================================
     * Warning: API does not support developer's issue catcher
     * Crash/unwanted case:
     * Use SEQUENTIAL_START and/or SEQUENTIAL_BETWEEN and never use SEQUENTIAL_END
     */

    public APIRequestHandler(AppCompatActivity act, IAsyncResponse IAsyncResponse) {
        mActivity = act;
        mIAsyncResponse = IAsyncResponse;
    }


    public void deleteUserData(String url) {
        mApiCallType = API_CALLS.DELETE_USER_DATA;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code, calledURL);
            }
        });
        task._exec();
    }



    public void requestUserData(String url) {
        mApiCallType = API_CALLS.FETCH_USER_DATA;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code,calledURL);
            }
        });
        task._exec();
    }


    public void textGenerator(String url, RequestBody requestBody) {
        mApiCallType = API_CALLS.TEXT_GENERATOR;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, requestBody, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code,calledURL);
            }
        });
        task._exec();
    }

    public void postUserData(String url, RequestBody requestBody) {
        mApiCallType = API_CALLS.POST_USER_DATA;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, requestBody, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code,calledURL);
            }
        });
        task._exec();
    }

    public void patchUserData(String url, RequestBody requestBody) {
        mApiCallType = API_CALLS.PATCH_USER_DATA;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, requestBody, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code,calledURL);
            }
        });
        task._exec();
    }
    public void putUserData(String url, RequestBody requestBody) {
        mApiCallType = API_CALLS.PUT_USER_DATA;

        EnqueuedHttpTask task = new EnqueuedHttpTask(mActivity, mApiCallType, url, requestBody, new IAsyncResponse() {
            @Override
            public void processFinish(String response, int code, String calledURL) {
                mIAsyncResponse.processFinish(response,code,calledURL);
            }
        });
        task._exec();
    }


}