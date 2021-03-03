package com.example.seminarskirad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rezultati extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    TextView rezultatKorisnika;
    DatabaseReference userScore;
    DatabaseReference topScores;

    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    myAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati);

        //Botuni


        database = FirebaseDatabase.getInstance();//bazu
        mAuth = FirebaseAuth.getInstance();

        // User score
        userScore = database.getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("score");








        rezultatKorisnika = (TextView) findViewById(R.id.mojRezultatTag2);
        loadUsersScore();


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<PitanjaRezultat> options =
                new FirebaseRecyclerOptions.Builder<PitanjaRezultat>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("user").orderByChild("score").limitToLast(10), PitanjaRezultat.class)
                        .build();


        mLayoutManager = new LinearLayoutManager(Rezultati.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);



        myAdapter = new myAdapter(options);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myAdapter);






    }

    // Userov rezultat
    private void loadUsersScore() {
        userScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rezultatKorisnika.setText(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
        loadUsersScore();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}