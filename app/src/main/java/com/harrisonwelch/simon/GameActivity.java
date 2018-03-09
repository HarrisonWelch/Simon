package com.harrisonwelch.simon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

enum Buttons {RED, BLUE, GREEN, PURPLE}

public class GameActivity extends Activity {
    private boolean isDebug = true;

    private int[] buttonIds = {R.id.image_red, R.id.image_blue, R.id.image_green, R.id.image_purple};
    private Queue<Buttons> sequence;            //holds the entire sequence
    private Queue<Buttons> playerSequence;      //used to track where player is in sequence
    private int maxScore;

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

        findViewById(R.id.button_restartGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
        findViewById(R.id.button_toMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toggleMainButtons();    //Have to force buttons false so they'll be set true in startGame
        startGame();
    }

    //Handles button pressing and what action is done when a button is pressed
    private class ButtonListener implements View.OnClickListener {
        Buttons thisButton;
        ButtonListener(Buttons thisButton){
            this.thisButton = thisButton;
        }

        //does appropriate action for whether button pressed was correct or not
        @Override
        public void onClick(View view) {
            Buttons nextButton = playerSequence.remove();

            if (nextButton == thisButton){      //if correct button, continue down sequence.
                //play correct sound
                updateDebugTextViews();
            } else {                            //if wrong button, continue to next sequence
                //play incorrect sound
                endGame();
            }

            if (playerSequence.isEmpty()){      //if right sequence, restart game with success.
                endTurn();
            }
        }
    }

    // Called when user moves into activity or presses "restart"
    private void startGame() {
        toggleMenuButtons();
        toggleMainButtons();
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

    // Called when the player has successfully completed a sequence.
    private void endTurn(){
        MakeToast.toast(getApplicationContext(), "Good, continue on with the next sequence");
        addRandomToSequence();
        updateDebugTextViews();
    }

    // Called when user fails a sequence.
    // Reset all local variables.
    private void endGame() {
        MakeToast.toast(getApplicationContext(), "Game Over... score: " + (sequence.size() - 1));

        if (sequence.size() > 0){
            maxScore = sequence.size() - 1; //exclude last elem in sequence since they failed that one
        }
        sequence.clear();
        toggleMenuButtons();
        toggleMainButtons();
    }

    //toggles between enabled and disabled on main Simon buttons
    private void toggleMainButtons(){
        for(int id : buttonIds){
            ImageButton btn = findViewById(id);
            btn.setClickable(!(btn.isClickable()));
            Log.i("TOGGLE", "ImageButton " + btn.getId() + " : isEnabled = " + btn.isClickable());
        }
    }

    //toggles between visible and invisible on bottom menu buttons
    private void toggleMenuButtons(){
        Button btn = findViewById(R.id.button_restartGame);
        btn.setVisibility(btn.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        btn = findViewById(R.id.button_toMenu);
        btn.setVisibility(btn.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
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

}
