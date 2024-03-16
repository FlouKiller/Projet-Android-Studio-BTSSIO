package com.example.projetandroi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ListerProfessionnels extends AppCompatActivity {

    BD bd;

    Button rechercherProfessionnel;

    EditText editTextCodePostalRecherche;

    TextView textViewNomListerProfessionnel;
    TextView textViewPrenomListerProfessionnel;
    TextView textViewCodePostalListerProfessionnel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_professionnels);

        bd = new BD(this);

        rechercherProfessionnel = findViewById(R.id.buttonRechercherProfessionnel);

        editTextCodePostalRecherche = findViewById(R.id.editTextCodePostalRecherche);

        textViewNomListerProfessionnel = findViewById(R.id.textViewNomListerProfessionnel);
        textViewPrenomListerProfessionnel = findViewById(R.id.textViewPrenomListerProfessionnel);
        textViewCodePostalListerProfessionnel = findViewById(R.id.textViewCodePostalListerProfessionnel);

        majListe(0);
    }

    public void majListe(int codePostal){

        if(codePostal == 0){

            try{
                Cursor data = bd.getAllProfessionnels();
                String prenoms = "";
                String noms = "";
                String codePostaux = "";
                while(data.moveToNext()){
                    noms += data.getString(1) + "\n";
                    prenoms += data.getString(2) + "\n";
                    codePostaux += data.getInt(7) + "\n";
                }

                textViewNomListerProfessionnel.setText(noms);
                textViewPrenomListerProfessionnel.setText(prenoms);
                textViewCodePostalListerProfessionnel.setText(codePostaux);

            } catch(Exception e){

            }

        } else {

            try{
                Cursor data = bd.getProfessionnelsByCodePostal(codePostal);
                String prenoms = "";
                String noms = "";
                String codePostaux = "";
                while(data.moveToNext()){
                    noms += data.getString(1) + "\n";
                    prenoms += data.getString(2) + "\n";
                    codePostaux += data.getInt(7) + "\n";
                }

                textViewNomListerProfessionnel.setText(noms);
                textViewPrenomListerProfessionnel.setText(prenoms);
                textViewCodePostalListerProfessionnel.setText(codePostaux);

            } catch(Exception e){

            }

        }

    }

    public void rechercheProfessionnelParCodePostal(View view){
        if(editTextCodePostalRecherche.getText().toString().equals("")){
            majListe(0);
        } else {
            majListe(Integer.parseInt(editTextCodePostalRecherche.getText().toString()));
        }
    }

}