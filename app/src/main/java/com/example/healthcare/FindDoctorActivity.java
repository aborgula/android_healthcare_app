package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        CardView exit = findViewById(R.id.back);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindDoctorActivity.this, HomeActivity.class));
            }
        });

        CardView dermatologist = findViewById(R.id.dermatologist);
        dermatologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailActivity.class);
                it.putExtra("title", "dermatologist");
                startActivity(it);
            }
        });

        CardView dietician = findViewById(R.id.dietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailActivity.class);
                it.putExtra("title", "dietician");
                startActivity(it);
            }
        });

        CardView dentist = findViewById(R.id.dentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailActivity.class);
                it.putExtra("title", "dentist");
                startActivity(it);
            }
        });

        CardView surgeon = findViewById(R.id.surgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailActivity.class);
                it.putExtra("title", "surgeon");
                startActivity(it);
            }
        });

        CardView cardiologist = findViewById(R.id.cardiolog);
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FindDoctorActivity.this, DoctorDetailActivity.class);
                it.putExtra("title", "cardiologist");
                startActivity(it);
            }
        });


    }


}