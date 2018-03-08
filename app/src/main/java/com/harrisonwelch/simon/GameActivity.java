package com.harrisonwelch.simon;

import android.app.Activity;
import android.content.Context;
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
    private int maxScore;
    private boolean isDebug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //setup variables
        sequence = new LinkedList<>();
        playerSequence = new LinkedList<>();
        rand = new Random();
        maxScore = 0;
        //setup listeners
        findViewById(R.id.image_red).setOnClickListener(new ButtonListener(Buttons.RED));
        findViewById(R.id.image_blue).setOnClickListener(new ButtonListener(Buttons.BLUE));
        findViewById(R.id.image_green).setOnClickListener(new ButtonListener(Buttons.GREEN));
        findViewById(R.id.image_purple).setOnClickListener(new ButtonListener(Buttons.PURPLE));

        restartGame();
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
                updateDebugTextViews();

            } else {
                makeToast(getApplicationContext(), "Game Over... score: " + (sequence.size() - 1));
                //play incorrect sound
                restartGame();
            }

            if (playerSequence.isEmpty()){
                makeToast(getApplicationContext(), "Good, continue on with the next sequence");
                addRandomToSequence();
                updateDebugTextViews();
            }


        }
    }

    //Reset all local variables and re-start the sequence.
    private void restartGame() {
        if (sequence.size() > 0){
            maxScore = sequence.size() - 1; //exclude last elem in sequence since they failed that one
        }
        sequence.clear();
        addRandomToSequence();

        updateDebugTextViews();
    }

    //Add x Buttons to the sequence and reset playerSequence
    private void addRandomToSequence(){
        sequence.add(getRandomButton());
        playerSequence.clear();
        playerSequence.addAll(sequence);
    }

    //Generates a random button and returns it as a type Button.
    private Random rand;
    private static final List<Buttons> VALUES = Arrays.asList(Buttons.values());
    private Buttons getRandomButton(){
        return VALUES.get(rand.nextInt(VALUES.size()));
    }

    //Only works in debug mode:
    // update the sequence textview and playerSequence textview
    private void updateDebugTextViews(){
        if (isDebug) {
            TextView tv = findViewById(R.id.textview_debugSequenceOutput);
            tv.setText(sequence.toString());
            TextView tv2 = findViewById(R.id.textview_debugPlayerSequence);
            tv2.setText(playerSequence.toString());
        }
    }

    //makes a toast and cancels a previous toast if there was one.
    private static Toast toast;
    public static void makeToast(Context context, String text){
        if (toast != null){
            toast.cancel();
        }

        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();

    }
}
