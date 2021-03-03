package com.example.seminarskirad;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import static androidx.core.content.ContextCompat.startActivity;

public class myAdapter extends FirebaseRecyclerAdapter<PitanjaRezultat, myAdapter.myviewholder> {

    public Context mContext;

    public myAdapter(@NonNull FirebaseRecyclerOptions<PitanjaRezultat> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull PitanjaRezultat model) {

        holder.mail.setText(model.getEmail());
        holder.rezultat.setText(String.valueOf(model.getScore()));
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        mContext = parent.getContext();
        return new myviewholder(view);


    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView mail;
        TextView rezultat;


        //LinearLayout linearLayout;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            mail = (TextView) itemView.findViewById(R.id.prikazMail);
            rezultat = (TextView) itemView.findViewById(R.id.prikazRezultata);


        }
    }


}
