package com.dialog.vaseelaser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dialog.vaseelaser.databinding.ActivityMainBinding;
import com.dialog.vaseelaser.library.PopUp;
import com.dialog.vaseelaser.library.Threading;
import com.dialog.vaseelaser.library.Utils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding __binder;
    protected static final String[] PERMS = {
            Manifest.permission.ACCESS_NETWORK_STATE,

            Manifest.permission.INTERNET,

            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE
    };

    protected PopUp.Native mDeclinedPermsAlert;
    protected ActivityResultLauncher<Intent> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        __binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(__binder.getRoot());


        __binder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One one = new One();
                one.show(getSupportFragmentManager(), "e t h e r");
            }
        });


        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (Utils.PermissionManager.allPermsGranted(MainActivity.this, PERMS)) {
                    Threading.runDelayed(new Runnable() {
                        @Override
                        public void run() {
                            PopUp.Native s = new PopUp.Native(MainActivity.this,
                                    "≋h≋i≋",
                                    "≋g≋o≋o≋d≋ ≋e≋v≋e≋n≋i≋n≋g≋");
                            s.hideNegativeOption();
                            s.pop();

                        }
                    }, 1000);

                } else
                    Utils.PermissionManager.checkPermissions(MainActivity.this, PERMS);
            }
        });
        if (Utils.PermissionManager.allPermsGranted(MainActivity.this, PERMS)) {
            {
                Threading.runDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PopUp.Native s = new PopUp.Native(MainActivity.this,
                                "≋h≋i≋",
                                "≋g≋o≋o≋d≋ ≋e≋v≋e≋n≋i≋n≋g≋");
                        s.hideNegativeOption();
                        s.pop();

                    }
                }, 1000);

            }
        } else
            Utils.PermissionManager.checkPermissions(MainActivity.this, PERMS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == PERMS.length)
            if (Utils.PermissionManager.allPermsGranted(MainActivity.this, PERMS)) //Tricky click the login when all perms are granted
            {
            } else {
                if (mDeclinedPermsAlert == null || !mDeclinedPermsAlert.isShowing()) {

                    mDeclinedPermsAlert = new PopUp.Native(this, "", "\uD83C\uDF82  \uD83C\uDF80  \uD835\uDCC5\uD835\uDCC1\uD835\uDCCF \uD835\uDC54\uD835\uDCBE\uD835\uDCCB\uD835\uDC52 \uD835\uDCBE\uD835\uDCC3\uD835\uDCC9\uD835\uDC52\uD835\uDCC7\uD835\uDCC3\uD835\uDC52\uD835\uDCC9  \uD83C\uDF80  \uD83C\uDF82");
                    mDeclinedPermsAlert.allowCancel(false).hideNegativeOption().setPositiveAction(new Runnable() {
                        @Override
                        public void run() {
                            mGetContent.launch(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                            mDeclinedPermsAlert.dismiss();
                        }
                    });

                    mDeclinedPermsAlert.pop();
                }
            }
    }

}