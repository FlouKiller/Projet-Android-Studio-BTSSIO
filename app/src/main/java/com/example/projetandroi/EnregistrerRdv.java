package com.example.projetandroi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EnregistrerRdv extends AppCompatActivity {

    BD bd;

    Spinner professionnelsSpinner;

    CalendarView calendarViewDate;

    EditText editTextHeureDebut;
    EditText editTextMotif;

    TextView textViewRdvDuJour;

    Button enregistrerRdv;

    String selectedDateString;
    long selectedDateInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer_rdv);

        bd = new BD(this);

        professionnelsSpinner = findViewById(R.id.professionnelsSpinner);

        calendarViewDate = findViewById(R.id.calendarView2);
        editTextHeureDebut = findViewById(R.id.editTextHeureDebutRdv);
        editTextMotif = findViewById(R.id.editTextMotifRdv);

        textViewRdvDuJour = findViewById(R.id.textViewRdvDuJour);

        enregistrerRdv = findViewById(R.id.buttonEnregistrerRdv);

        majProfessionnels();

        calendarViewDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDateString = day + "/" + (month + 1) + "/" + year;
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, day);
                selectedDateInMillis = selectedCalendar.getTimeInMillis();

                String texteRdvDuJour = "Rendez vous prévus ce jour (" + selectedDateString + ") :\n\n";

                try{

                    Date selectedDate = new Date(selectedDateInMillis);
                    Instant instant = selectedDate.toInstant();
                    LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    Cursor data = bd.getRdvByDay(localDate);
                    while(data.moveToNext()){
                        texteRdvDuJour += data.getString(1)  + " avec " + bd.getNomProfessionnelById(data.getInt(3)) + "\n";
                    }

                } catch(Exception e){
                    editTextMotif.setText(e.getMessage());
                }

                textViewRdvDuJour.setText(texteRdvDuJour);
            }
        });

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String input = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
            if(input.equals("24")){
                return source;
            }
            if(!input.matches("^([01]?[0-9]|2[0-4])?$")){
                return "";
            }
            return null;
        };

        editTextHeureDebut.setFilters(new InputFilter[]{filter});
    }

    public void majProfessionnels(){

        try{
            Cursor data = bd.getAllProfessionnels();
            ArrayList<String> listeProfessionnels = new ArrayList<>();
            while(data.moveToNext()){
                listeProfessionnels.add(data.getInt(0) + " " +  data.getString(1) + " " + data.getString(2));
            }

            ArrayAdapter<String> aaProfessionnels = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeProfessionnels);
            aaProfessionnels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            professionnelsSpinner.setAdapter(aaProfessionnels);

        } catch(Exception e){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void clicEnregistrerRdv(View view){

        if(editTextHeureDebut.getText().toString().equals("")){
            editTextHeureDebut.setText("00");
        }

        Date selectedDate = new Date(selectedDateInMillis);
        Instant instant = selectedDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        String timeStr = editTextHeureDebut.getText().toString();
        String formattedTime = timeStr + ":00";
        LocalTime localTime = LocalTime.parse(formattedTime);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        int id_professionnel = Integer.parseInt(professionnelsSpinner.getSelectedItem().toString().split(" ")[0]);

        try{

            bd.enregistrerRdv(localDateTime, editTextMotif.getText().toString(), id_professionnel);

            finish();

        }catch (Exception e){

        }


    }

}