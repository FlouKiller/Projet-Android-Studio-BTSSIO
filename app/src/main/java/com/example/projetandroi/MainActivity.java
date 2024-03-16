package com.example.projetandroi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BD bd;

    Button enregistrerProfessionnel;
    Button prendreRdv;
    Button listerProfessionnels;
    Button planning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enregistrerProfessionnel = findViewById(R.id.button);
        prendreRdv = findViewById(R.id.button2);
        listerProfessionnels = findViewById(R.id.button3);
        planning = findViewById(R.id.button4);

        bd = new BD(this);

    }

    public void enregistrerProfessionnelClick(View view){
        Intent intentEnregistrerProfessionnel = new Intent(this, EnregistrerProfessionnel.class);
        startActivity(intentEnregistrerProfessionnel);
    }

    public  void prendreRdvClick(View view){
        Intent intentPrendreRdv = new Intent(this, EnregistrerRdv.class);
        startActivity(intentPrendreRdv);
    }

    public void listerProfessionnelsClick(View view){
        Intent intentListerProfessionnels = new Intent(this, ListerProfessionnels.class);
        startActivity(intentListerProfessionnels);
    }

    public void planningClick(View view){
        Intent intentPlanning = new Intent(this, Planning.class);
        startActivity(intentPlanning);
    }
}