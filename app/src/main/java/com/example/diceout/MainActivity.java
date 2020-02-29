package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // ArrayList and variables to store the values of the dice
    ArrayList<Integer> dice;
    int die1;
    int die2;
    int die3;

    // ArrayList to hold all three dice images
    ArrayList<ImageView> diceImageViews;

    int score;
    Random rand;

    // UI components
    TextView rollResult;

    // Field to hold the score text
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        // Initialise the score
        score = 0;

        rollResult = findViewById(R.id.rollResult);
        scoreText = findViewById(R.id.scoreText);
        rand = new Random();

        // Create Arraylist container for the dice values
        dice = new ArrayList<Integer>();

        ImageView die1Image = findViewById(R.id.die1_image);
        ImageView die2Image = findViewById(R.id.die2_image);
        ImageView die3Image = findViewById(R.id.die3_image);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

        // Create a greeting that displays when the app is opened
        Toast.makeText(getApplicationContext(), "Welcome to DiceOut", Toast.LENGTH_SHORT).show();
    }

    public void rollDice(View view) {
        // Roll Dice
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;

        // Set dice values into the ArrayLisy
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++) {
            String imageName = "die_" + dice.get(dieOfSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Build the message
        String message;

        if (die1 == die2 && die1 == die3) {
            int scoreDelta = die1 * 100;
            message = "You rolled a triple " + die1 + "! You score " + scoreDelta + " points!";
            score += scoreDelta;
        } else if (die1 == die2 || die1 == die3 || die2 == die3) {
            message = "You rolled doubles for 50 points!";
            score += 50;
        } else {
            message = "You didn't score any points this roll. Try Again!";
        }

        // Udpdate the UI
        rollResult.setText(message);
        scoreText.setText("Score: " + score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
