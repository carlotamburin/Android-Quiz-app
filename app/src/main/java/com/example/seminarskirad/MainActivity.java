package com.example.seminarskirad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;//deklaracija-- pristup bazi
    Button startButton;
    Button logout;
    Button rezultati;
    DatabaseReference pitanja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();//bazu
        pitanja = database.getReference("pitanja");

        startButton = findViewById(R.id.startButton);
        logout = findViewById(R.id.logOutButton);
        rezultati = findViewById(R.id.leaderboard);


        loadQuestion(Common.pitanja);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QuizGame.class);
                view.getContext().startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(i);
                finish();
            }
        });

        rezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Rezultati.class);
                v.getContext().startActivity(intent);
            }
        });


    }


    private void loadQuestion(String pitanje) {
        if (Common.listaPitanja.size() > 0)
            Common.listaPitanja.clear();
        pitanja                       //.orderByChild("pitanje").equalTo(pitanje)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnaphsot : snapshot.getChildren()) {
                            Pitanja pit = postSnaphsot.getValue(Pitanja.class);
                            Common.listaPitanja.add(pit);

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        // Shufflaj listu
        Collections.shuffle(Common.listaPitanja);

    }


}


