package my.edu.utar.individualassignment;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private List<HighlightView> views;
    private int currentLevel;
    private int score;
    Handler handler;
    //private ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        views = new ArrayList<>();
        currentLevel = 1;
        score = 0;

        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome to the Game!");
        builder.setMessage("Your objective is to tap the highlighted square as quickly as possible. The game gets harder as you progress through levels. Good luck!"
                );


        // Add a button to dismiss the dialog
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                populateViews(4); // start with 4 views in level 1
                startLevel();

                Button quitButton = findViewById(R.id.quit_button);
                quitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quitG();
                    }
                });
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void populateViews(int numViews) {
        LinearLayout container = findViewById(R.id.container);
        TextView scoreTextView = findViewById(R.id.score_text_view);
        container.removeAllViews();
        views.clear();
        for (int i = 0; i < numViews; i++) {
            HighlightView view = new HighlightView(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (view.isHighlighted) {
                        score++;
                        scoreTextView.setText("Score " + score);
                        view.unhighlight();
                        highlightRandomView();
                    }
                }
            });
            views.add(view);
            container.addView(view);
        }
    }

    private void startLevel() {
        TextView levelTextView = findViewById(R.id.level_text_view);
        levelTextView.setText("Level " + currentLevel);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                endLevel();
            }
        }, 5000); // level lasts 5 seconds
        if (currentLevel == 1) {
            populateViews(4);
        }
        else if (currentLevel == 2) {
            populateViews(9);
        }
        else if (currentLevel == 3) {
            populateViews(16);}
        else if (currentLevel == 4) {
            populateViews(25);}
        else if (currentLevel == 5) {
            populateViews(36);
        }
        else {
            int numViews = currentLevel * currentLevel;
            populateViews(numViews);
        }
        highlightRandomView();
    }

    private void highlightRandomView() {
        int size = views.size();
        int randomIndex = (int) (Math.random() * size);
        HighlightView randomView = views.get(randomIndex);
        randomView.highlight();
    }

    private void endLevel() {
        currentLevel++;
        if (currentLevel <= 5) {
            /*int numViews = currentLevel * currentLevel;
            populateViews(numViews);*/
            startLevel();
        } else {
            enterN();
        }
        //handler.removeCallbacksAndMessages(null);
    }

    //when click quit button
    // Define the quit button in your layout file with the id "quitButton"

    private void quitG() {
        //Stop the timer
        handler.removeCallbacksAndMessages(null);

        //Show a confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Game");
        builder.setMessage("Are you sure you want to quit the game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Close the activity and return to the previous screen
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }





    //let users to enter their name
    private void enterN() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New high score! Enter your name:");
        final EditText nameEditText = new EditText(this);
        builder.setView(nameEditText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                saveS(name, score);
                showHS();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }



    //when hit top 25 scores then save into shared preference
    private void saveS(String name, int score) {
        SharedPreferences sharedPreferences = getSharedPreferences("high_scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, score);
        editor.apply();
    }

    //direct to other page to show top 25 highest scores
    private void showHS() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }



}