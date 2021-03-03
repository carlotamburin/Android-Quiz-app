package com.example.seminarskirad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class KrajIgre extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnTryAgain;
    TextView txtResultScore, getTxtResultQuestion;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference question_score;
    DatabaseReference userScore;

    LinearLayout group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kraj_igre);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("pitanja");

        userScore = database.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();

        // Animacije je na ovome
        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        group=(LinearLayout)findViewById(R.id.btnGroup);



        getTxtResultQuestion = (TextView) findViewById(R.id.txtTotalQuestions);
        progressBar = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button) findViewById(R.id.playAgainButton);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KrajIgre.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");


            txtResultScore.setText(String.format("SCORE: %d", score));
            getTxtResultQuestion.setText(String.format("PASSED: %d / %d", correctAnswer, totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            //Upload points to db
            showSaveDialog(score);
            startAnimation();


        }
    }

    private void startAnimation(){
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.anim);
        group.startAnimation(animation);

    }

    private void showSaveDialog(final int score) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(KrajIgre.this);
        alertDialog.setTitle("Spremanje rezultata");
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Želite li spremiti rezultat?");

        alertDialog.setNegativeButton("Ne želim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.show().dismiss();

            }
        });

        alertDialog.setPositiveButton("Želim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userScore.child(mAuth.getCurrentUser().getUid()).child("score").setValue(score);
                userScore.child(mAuth.getCurrentUser().getUid()).child("Email").setValue(mAuth.getCurrentUser().getEmail());
                alertDialog.show().dismiss();
            }
        });
        alertDialog.show();
    }
}