package com.harrisonwelch.simon;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.TextView;

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
        sequence = new LinkedList<>();
        playerSequence = new LinkedList<>();

        rand = new Random();

        for(int i = 0; i < 8; i++){
            addRandomToSequence();
        }

        TextView tv = findViewById(R.id.textview_debugSequenceOutput);
        tv.setText(sequence.toString());
    }

    //Add x Buttons to the sequence and reset playerSequence
    private void addRandomToSequence(){
        sequence.add(getRandomButton());
        //playerSequence = sequence;
    }

    //Generates a random button and returns it as a type Button.
    private Random rand;
    private static final List<Buttons> VALUES = Arrays.asList(Buttons.values());
    private Buttons getRandomButton(){
        return VALUES.get(rand.nextInt(VALUES.size()));
    }
}
