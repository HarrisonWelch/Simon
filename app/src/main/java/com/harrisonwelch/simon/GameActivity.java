package com.harrisonwelch.simon;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

enum Buttons {RED, BLUE, GREEN, PURPLE}

public class GameActivity extends Activity {

    private Queue<Buttons> sequence;            //holds the entire sequence
    private Queue<Buttons> playerSequence;      //used to track where player is in sequence


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //setup variables
        sequence = new LinkedList<>();
        playerSequence = new LinkedList<>();
        rand = new Random();
        //setup listeners
        findViewById(R.id.image_red).setOnClickListener(new ButtonListener(Buttons.RED));
        findViewById(R.id.image_blue).setOnClickListener(new ButtonListener(Buttons.BLUE));
        findViewById(R.id.image_green).setOnClickListener(new ButtonListener(Buttons.GREEN));
        findViewById(R.id.image_purple).setOnClickListener(new ButtonListener(Buttons.PURPLE));


        //debug adding to sequence
        for(int i = 0; i < 8; i++){
            addRandomToSequence();
        }

        TextView tv = findViewById(R.id.textview_debugSequenceOutput);
        tv.setText(sequence.toString());
        TextView tv2 = findViewById(R.id.textview_debugPlayerSequence);
        tv2.setText(playerSequence.toString());
    }

    //whenever a button is clicked, removes that button from the listing
    private class ButtonListener implements View.OnClickListener {
        Buttons thisButton;
        ButtonListener(Buttons thisButton){
            this.thisButton = thisButton;
        }

        //if correct button, continue down sequence.
        //if wrong button, restart game with failure.
        //if right sequence, restart game with success.
        @Override
        public void onClick(View view) {
            Buttons nextButton = playerSequence.remove();

            if (nextButton == thisButton){
                //play correct sound
                TextView tv = findViewById(R.id.textview_debugPlayerSequence);
                tv.setText(playerSequence.toString());

            } else {
                makeToast("You failed.");
                //play incorrect sound
                restartGame();
            }

            if (playerSequence.isEmpty()){
                makeToast("You won!");
                restartGame();
            }


        }
    }

    private void restartGame() {
        sequence.clear();

        for (int i = 0; i < 8; i++){
            addRandomToSequence();
        }
        TextView tv = findViewById(R.id.textview_debugSequenceOutput);
        tv.setText(sequence.toString());
        TextView tv2 = findViewById(R.id.textview_debugPlayerSequence);
        tv2.setText(playerSequence.toString());
    }

    //Add x Buttons to the sequence and reset playerSequence
    private void addRandomToSequence(){
        sequence.add(getRandomButton());
        playerSequence = sequence;
    }

    //Generates a random button and returns it as a type Button.
    private Random rand;
    private static final List<Buttons> VALUES = Arrays.asList(Buttons.values());
    private Buttons getRandomButton(){
        return VALUES.get(rand.nextInt(VALUES.size()));
    }

    //makes a toast and cancels a previous toast if there was one.
    private Toast toast;
    public void makeToast(String text){
        if (toast != null){
            toast.cancel();
        }

        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();

    }
}
