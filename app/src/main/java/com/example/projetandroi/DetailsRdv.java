package com.example.projetandroi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsRdv extends AppCompatActivity {

    BD bd;

    TextView textViewDate;
    TextView textViewHeure;
    TextView textViewAvec;
    TextView textViewMotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_rdv);

        bd = new BD(this);

        textViewDate = findViewById(R.id.textViewDetailDate);
        textViewHeure = findViewById(R.id.textViewDetailHeure);
        textViewAvec = findViewById(R.id.textViewDetailAvec);
        textViewMotif = findViewById(R.id.textViewDetailMotif);

        majDetails();
    }

    public void majDetails(){

        String dateRdv = getIntent().getStringExtra("DATE_RDV");
        String heureRdv = getIntent().getStringExtra("HEURE_RDV");
        String avecRdv = getIntent().getStringExtra("AVEC_RDV");
        String motifRdv = getIntent().getStringExtra("MOTIF_RDV");

        textViewDate.setText(dateRdv);
        textViewHeure.setText(heureRdv);
        textViewAvec.setText(avecRdv);
        textViewMotif.setText(motifRdv);
    }

    public void clicSupprimer(View view){

        String id_rdv = getIntent().getStringExtra("ID_RDV");

        try{

            bd.deleteRdvById(Integer.parseInt(id_rdv));

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

        }catch(Exception e){
            textViewMotif.setText(e.getMessage());
        }
    }

}