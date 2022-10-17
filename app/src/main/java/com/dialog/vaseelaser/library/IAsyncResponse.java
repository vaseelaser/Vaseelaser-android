package com.dialog.vaseelaser.library;

public interface IAsyncResponse {
    void processFinish(String response, int code, String calledURL);
}