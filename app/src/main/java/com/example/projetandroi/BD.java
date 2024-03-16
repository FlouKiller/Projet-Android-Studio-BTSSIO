package com.example.projetandroi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BD extends SQLiteOpenHelper {

    public BD(Context context){
        super(context, "ProjetAndroidStudio.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE types(id_type INTEGER PRIMARY KEY NOT NULL, nom VARCHAR(50));");
        bd.execSQL("CREATE TABLE professionnels(id_professionnel INTEGER PRIMARY KEY NOT NULL,nom VARCHAR(50),prenom VARCHAR(50),email VARCHAR(50),tel VARCHAR(10),ligne_adresse VARCHAR(100),ville VARCHAR(50),code_postal INT,id_type VARCHAR(50) NOT NULL,FOREIGN KEY(id_type) REFERENCES types(id_type));");
        bd.execSQL("CREATE TABLE rendez_vous(id_rdv INTEGER PRIMARY KEY NOT NULL,date_heure DATETIME,motif TEXT,id_professionnel INT NOT NULL,FOREIGN KEY(id_professionnel) REFERENCES professionnels(id_professionnel));");

        bd.execSQL("INSERT INTO types (nom) VALUES ('Pharmacien')");
        bd.execSQL("INSERT INTO types (nom) VALUES ('Généraliste')");
        bd.execSQL("INSERT INTO types (nom) VALUES ('ORL')");
        bd.execSQL("INSERT INTO types (nom) VALUES ('Podologue')");
        bd.execSQL("INSERT INTO types (nom) VALUES ('Cardiologue')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS types");
        bd.execSQL("DROP TABLE IF EXISTS professionnels");
        bd.execSQL("DROP TABLE IF EXISTS rendez_vous");
        onCreate(bd);
    }

    public int getIdTypeByName(String name){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT id_type FROM types WHERE nom = '" + name + "'", null);

        if(result != null){
            result.moveToFirst();
            return result.getInt(0);
        } else {
            return -1;
        }

    }

    public Cursor getAllTypes(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT nom FROM types", null);

        return result;
    }

    public void enregistrerProfessionnel(String nom, String prenom, String type, String email, String telephone, String ligneAdresse, int codePostal, String ville){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("email", email);
        contentValues.put("tel", telephone);
        contentValues.put("ligne_adresse", ligneAdresse);
        contentValues.put("ville", ville);
        contentValues.put("code_postal", codePostal);
        contentValues.put("id_type", getIdTypeByName(type));
        bd.insert("professionnels", null, contentValues);
    }

    public Cursor getAllProfessionnels(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT * FROM professionnels ORDER BY nom", null);

        return result;
    }

    public Cursor getProfessionnelsByCodePostal(int codePostal){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT * FROM professionnels WHERE code_postal = " + codePostal + " ORDER BY nom", null);

        return result;
    }

    public void enregistrerRdv(LocalDateTime date_heure, String motif, int id_professionnel){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date_heure", String.valueOf(date_heure));
        contentValues.put("motif", motif);
        contentValues.put("id_professionnel", id_professionnel);
        bd.insert("rendez_vous", null, contentValues);
    }

    public Cursor getRdvByDay(LocalDate dateRdv){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT id_rdv, TIME(date_heure), motif, id_professionnel FROM rendez_vous WHERE DATE(date_heure) = '" + dateRdv + "' ORDER BY date_heure", null);

        return result;
    }

    public String getNomProfessionnelById(int id){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT * FROM professionnels WHERE id_professionnel = " + id, null);
        if (result.moveToFirst()) {
            return result.getString(1) + " " + result.getString(2);
        }
        return null;
    }

    public Cursor getAllRdv(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor result = bd.rawQuery("SELECT id_rdv, DATE(date_heure), TIME(date_heure), motif, id_professionnel FROM rendez_vous ORDER BY date_heure", null);

        return result;
    }

    public void deleteRdvById(int id_rdv){
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.execSQL("DELETE FROM rendez_vous WHERE id_rdv = " + id_rdv);
    }

}
