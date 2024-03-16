package com.example.projetandroi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Console;
import java.util.ArrayList;

public class EnregistrerProfessionnel extends AppCompatActivity {

    BD bd;

    Spinner typesSpinner;

    Button enregistrerProfessionnelButton;

    EditText editTextNom;
    EditText editTextPrenom;
    EditText editTextEmail;
    EditText editTextTelephone;
    EditText editTextLigneAdresse;
    EditText editTextCodePostal;
    EditText editTextVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer_professionnel);

        bd = new BD(this);

        typesSpinner = findViewById(R.id.spinner);

        enregistrerProfessionnelButton = findViewById(R.id.button5);

        editTextNom = findViewById(R.id.editTextNom);
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelephone = findViewById(R.id.editTextTelephone);
        editTextLigneAdresse = findViewById(R.id.editTextLigneAdresse);
        editTextCodePostal = findViewById(R.id.editTextCodePostal);
        editTextVille = findViewById(R.id.editTextVille);

        majTypes();
    }

    public void majTypes(){

        try{
            Cursor data = bd.getAllTypes();
            ArrayList<String> listeTypes = new ArrayList<>();
            while(data.moveToNext()){
                listeTypes.add(data.getString(0));
            }

            ArrayAdapter<String> aaTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeTypes);
            aaTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typesSpinner.setAdapter(aaTypes);

        } catch(Exception e){

        }
    }

    public void enregistrerProfessionnelClick(View view){

        try{
            bd.enregistrerProfessionnel(editTextNom.getText().toString(),
                    editTextPrenom.getText().toString(),
                    typesSpinner.getSelectedItem().toString(),
                    editTextEmail.getText().toString(),
                    editTextTelephone.getText().toString(),
                    editTextLigneAdresse.getText().toString(),
                    Integer.parseInt(String.valueOf(editTextCodePostal.getText())),
                    editTextVille.getText().toString());

            finish();

        }catch (Exception e){
            editTextCodePostal.setText(e.getMessage());
        }


    }
}