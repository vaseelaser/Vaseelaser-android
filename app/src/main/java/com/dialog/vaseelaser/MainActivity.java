package com.dialog.vaseelaser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dialog.vaseelaser.databinding.ActivityMainBinding;
import com.dialog.vaseelaser.library.PopUp;
import com.dialog.vaseelaser.library.Threading;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding __binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        __binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(__binder.getRoot());


        __binder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One one = new One();
                one.show(getSupportFragmentManager(),"e t h e r");
            }
        });

        Threading.runDelayed(new Runnable() {
            @Override
            public void run() {
                PopUp.Native s = new PopUp.Native(MainActivity.this,
                        "≋h≋i≋",
                        "\uD835\uDC21̷̡̡̳̦͋̆̎̑̀̈́͝\uD835\uDC1E̴̢͓̩̱̊́̈͝\uD835\uDC25̷̮̤͖̺͈͍͕̼̲̇̿̄̚\uD835\uDC25̶̯͋̋͌͋͗̋͠\uD835\uDC28̴̨̡̜̦͉̤̺͈̹͙̒̓͛̏̿͛̀̿͝͝ ̷̖̬̌̈́͆͗͘̚͠\uD835\uDC30̷̻̯̺͈͋͝\uD835\uDC28̵̡̰̩͕̱͚̠̤̗̦̅̆̋̿̏͑̍̕͝\uD835\uDC2B̶̛̛͉̻̰̋̈́͑̈́̅\uD835\uDC25̶̛̱̱̅̂͆̿̎͛\uD835\uDC1D̴̝̒͒̀́̐̇̒̈̈́");
                s.hideNegativeOption();
                s.pop();

            }
        },1000);

    }
}