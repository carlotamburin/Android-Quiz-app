package com.example.seminarskirad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizGame extends AppCompatActivity implements View.OnClickListener {


    final static long INTERVAL = 1000;
    final static long TIMEOUT = 7000;

    public String dolazniPoziv;
    int progressValue = 0;
    ProgressBar progressBar;

    int index = 0, score = 0, thisquestion = 0, totalQuestion, correctAnswer;
    Button pitanje, odgovor1, odgovor2, odgovor3, odgovor4;
    TextView txtScore, txtQuestionNum, question_text;

    public TextView countdownText;
    public static CountDownTimer countDownTimer;
    private long timerTime = 60000;
    public static boolean timerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_view);


        //Views
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtQuestionNum = (TextView) findViewById(R.id.txtTotalQuestion);
        question_text = (TextView) findViewById(R.id.pitanje);


        odgovor1 = (Button) findViewById(R.id.odgovor1);
        odgovor2 = (Button) findViewById(R.id.odgovor2);
        odgovor3 = (Button) findViewById(R.id.odgovor3);
        odgovor4 = (Button) findViewById(R.id.odgovor4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(60);


        odgovor1.setOnClickListener(this);
        odgovor2.setOnClickListener(this);
        odgovor3.setOnClickListener(this);
        odgovor4.setOnClickListener(this);


        //GLavni timer
        countdownText = findViewById(R.id.countdownText);
        // Pokreni globalni timer
        newTimer();

        timerRunning = true;


        //Telephony Manager

        if (ContextCompat.checkSelfPermission(QuizGame.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(QuizGame.this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(QuizGame.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                ActivityCompat.requestPermissions(QuizGame.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            }
        } else {
            //Do nothing
        }


        //Telephony manager
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Toast.makeText(getApplicationContext(), "Phone Is Riging",
                            Toast.LENGTH_LONG).show();

                    countDownTimer.cancel();
                }
                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(getApplicationContext(), "Phone is Currently in A call",
                            Toast.LENGTH_LONG).show();
                }

                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    Toast.makeText(getApplicationContext(), "phone is neither ringing nor in a call",
                            Toast.LENGTH_LONG).show();

                    countDownTimer.cancel();
                    newTimer();


                    //Pozvati iz onResume




                }
            }
        };
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        // Telephony manager

    }

    public int updateTimer() {
        int minutes = (int) timerTime / 60000;
        int seconds = (int) timerTime % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
        return 0;
    }









    @Override
    public void onClick(View v) {
        //countDownTimer.cancel();
        if (index < totalQuestion) {
            Button clickedButton = (Button) v;
            if (clickedButton.getText().equals(Common.listaPitanja.get(index).getTocanOdgovor())) {
                score += 10;
                correctAnswer++;

            } else {
                score -= 5;
            }
            txtScore.setText(String.format("%d", score));
            showQuestion(++index);


        }

    }

    private void showQuestion(int index) {
        if (index < totalQuestion) {
            thisquestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisquestion, totalQuestion));

            question_text.setText(Common.listaPitanja.get(index).getPitanje());

            odgovor1.setText(Common.listaPitanja.get(index).getOdgovor1());
            odgovor2.setText(Common.listaPitanja.get(index).getOdgovor2());
            odgovor3.setText(Common.listaPitanja.get(index).getOdgovor3());
            odgovor4.setText(Common.listaPitanja.get(index).getOdgovor4());





        } else {
            //Ako je ovo zadnje pitanje
            countDownTimer.cancel();
            Intent intent = new Intent(this, KrajIgre.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Pokreni globalni timer

        totalQuestion = Common.listaPitanja.size();

        showQuestion(index);
        thisquestion--;


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    //Telephony part
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(QuizGame.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void newTimer() {
        countDownTimer = new CountDownTimer(timerTime, 1000) {
            @Override
            public void onTick(long l) {
                timerTime = l;
                updateTimer();
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                Intent intent = new Intent(QuizGame.this,KrajIgre.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
        }.start();
    }


}

