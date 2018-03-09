package com.harrisonwelch.simon;

import android.app.Activity;
import android.content.Intent;
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
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
            }
        });

        findViewById(R.id.button_playMode2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast.toast(getApplicationContext(), "mode 2 button");
            }
        });

        findViewById(R.id.button_playMode3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast.toast(getApplicationContext(), "mode 3 button");
            }
        });

        findViewById(R.id.button_howToPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HowToPlayActivity.class));
            }
        });

        findViewById(R.id.button_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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
}
