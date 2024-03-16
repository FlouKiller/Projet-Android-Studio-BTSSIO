package com.example.projetandroi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Planning extends AppCompatActivity {

    BD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        bd = new BD(this);

        majPlanning();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    public void majPlanning(){

        LinearLayout containerLayout = findViewById(R.id.containerLinearLayout);

        Cursor result = bd.getAllRdv();
        Map<String, List<String>> rendezVousParDate = new LinkedHashMap<>();
        String id_rdv;
        String date = "";
        String heure = "";
        String professionnel = "";
        String motif = "";

        while (result.moveToNext()) {
            id_rdv = result.getString(0);
            date = result.getString(1);
            heure = result.getString(2);
            motif = result.getString(3);
            professionnel = bd.getNomProfessionnelById(result.getInt(4));

            LinearLayout dateLayout = null;
            for (int i = 0; i < containerLayout.getChildCount(); i++) {
                if (containerLayout.getChildAt(i) instanceof LinearLayout) {
                    TextView textView = (TextView) ((LinearLayout) containerLayout.getChildAt(i)).getChildAt(0);
                    if (textView.getText().toString().equals("Rendez-vous du " + date)) {
                        dateLayout = (LinearLayout) containerLayout.getChildAt(i);
                        break;
                    }
                }
            }

            if (dateLayout == null) {
                dateLayout = new LinearLayout(this);
                dateLayout.setOrientation(LinearLayout.VERTICAL);

                TextView dateTextView = new TextView(this);
                dateTextView.setText("Rendez-vous du " + date);
                dateTextView.setTextSize(20);

                dateLayout.addView(dateTextView);
                containerLayout.addView(dateLayout);
            }

            LinearLayout rendezVousLayout = new LinearLayout(this);
            rendezVousLayout.setOrientation(LinearLayout.HORIZONTAL);
            rendezVousLayout.setGravity(Gravity.CENTER);

            TextView heureTextView = new TextView(this);
            LinearLayout.LayoutParams heureParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            heureTextView.setLayoutParams(heureParams);
            heureTextView.setText("- " + heure);

            TextView professionnelTextView = new TextView(this);
            LinearLayout.LayoutParams proParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            professionnelTextView.setLayoutParams(proParams);
            professionnelTextView.setText(professionnel);

            Button afficherPlusButton = new Button(this);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            afficherPlusButton.setLayoutParams(buttonParams);
            afficherPlusButton.setText("Détails");
            String finalMotif = motif;
            String finalProfessionnel = professionnel;
            String finalHeure = heure;
            String finalDate = date;
            String finalId_rdv = id_rdv;
            afficherPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Planning.this, DetailsRdv.class);

                    intent.putExtra("ID_RDV", finalId_rdv);
                    intent.putExtra("DATE_RDV", finalDate);
                    intent.putExtra("HEURE_RDV", finalHeure);
                    intent.putExtra("AVEC_RDV", finalProfessionnel);
                    intent.putExtra("MOTIF_RDV", finalMotif);

                    startActivityForResult(intent, 1);
                }
            });

            rendezVousLayout.addView(heureTextView);
            rendezVousLayout.addView(professionnelTextView);
            rendezVousLayout.addView(afficherPlusButton);

            dateLayout.addView(rendezVousLayout);
        }

    }

}