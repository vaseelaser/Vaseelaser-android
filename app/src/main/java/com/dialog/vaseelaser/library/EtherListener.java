package com.dialog.vaseelaser.library;

import java.util.List;

public interface EtherListener {
    void onEtherReceived(List<String> response, int code, String calledURL);
}