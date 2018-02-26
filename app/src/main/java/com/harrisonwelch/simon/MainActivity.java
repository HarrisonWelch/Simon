package com.harrisonwelch.simon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends Activity {
    static public boolean soundOn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("play button");
            }
        });

        findViewById(R.id.button_playMode2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("mode 2 button");
            }
        });

        findViewById(R.id.button_playMode3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("mode 3 button");
            }
        });

        findViewById(R.id.button_howToPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("How to play");
            }
        });

        findViewById(R.id.button_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("about");
            }
        });

        CompoundButton soundSwitch = findViewById(R.id.switch_sound);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                soundOn = b;
            }
        });
    }

    private Toast toast;
    private void makeToast(String text){
        if (toast != null){
            toast.cancel();
        }

        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();

    }
}
