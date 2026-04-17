package com.example.bankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom et version de la base
    private static final String DB_NAME = "bankapp.db";
    private static final int DB_VERSION = 2; // Incremented version

    // Table transactions
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_LIBELLE = "libelle";
    private static final String COL_MONTANT = "montant";

    // Table comptes
    private static final String TABLE_COMPTES = "comptes";
    private static final String COL_COMPTE_NUMERO = "numero";
    private static final String COL_COMPTE_LIBELLE = "compte_libelle";
    private static final String COL_COMPTE_SOLDE = "solde";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Créer la table comptes
        String sqlComptes = "CREATE TABLE " + TABLE_COMPTES + " ("
                + COL_COMPTE_NUMERO + " TEXT PRIMARY KEY, "
                + COL_COMPTE_LIBELLE + " TEXT, "
                + COL_COMPTE_SOLDE + " REAL)";
        db.execSQL(sqlComptes);

        // Insérer des comptes initiaux
        insererCompte(db, "0011-2233-4455-6677", "Compte courant", 8250.00);
        insererCompte(db, "0011-2233-4455-8899", "Compte épargne", 3700.00);
        insererCompte(db, "0011-2233-4455-1100", "Compte sur carnet", 500.00);

        // Créer la table transactions
        String sql = "CREATE TABLE " + TABLE_TRANSACTIONS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " TEXT, "
                + COL_LIBELLE + " TEXT, "
                + COL_MONTANT + " REAL)";
        db.execSQL(sql);

        // Insérer des données initiales
        insererTransaction(db, "15/03/2025", "Salaire Mars", 8500.00);
        insererTransaction(db, "14/03/2025", "Loyer Appartement", -3200.00);
        insererTransaction(db, "12/03/2025", "Courses Marjane", -450.00);
        insererTransaction(db, "10/03/2025", "Facture Inwi", -199.00);
        insererTransaction(db, "08/03/2025", "Virement reçu", 1200.00);
        insererTransaction(db, "05/03/2025", "Restaurant", -180.00);
        insererTransaction(db, "03/03/2025", "Essence", -350.00);
        insererTransaction(db, "01/03/2025", "Prime trimestrielle", 2500.00);
    }

    private void insererCompte(SQLiteDatabase db, String numero, String libelle, double solde) {
        ContentValues values = new ContentValues();
        values.put(COL_COMPTE_NUMERO, numero);
        values.put(COL_COMPTE_LIBELLE, libelle);
        values.put(COL_COMPTE_SOLDE, solde);
        db.insert(TABLE_COMPTES, null, values);
    }

    // Méthode privée (utilisée dans onCreate pour les données initiales)
    private void insererTransaction(SQLiteDatabase db, String date, String libelle, double montant) {
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_LIBELLE, libelle);
        values.put(COL_MONTANT, montant);
        db.insert(TABLE_TRANSACTIONS, null, values);
    }

    // Méthode publique (utilisée depuis les Activities)
    public void ajouterTransaction(String date, String libelle, double montant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_LIBELLE, libelle);
        values.put(COL_MONTANT, montant);
        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS, null);
        // Parcourir le Cursor ligne par ligne
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
            String libelle = cursor.getString(cursor.getColumnIndexOrThrow(COL_LIBELLE));
            double montant = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MONTANT));
            liste.add(new Transaction(date, libelle, montant));
        }
        cursor.close();
        db.close();
        return liste;
    }

    public ArrayList<Compte> getAllComptes() {
        ArrayList<Compte> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COMPTES, null);
        while (cursor.moveToNext()) {
            String numero = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPTE_NUMERO));
            String libelle = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPTE_LIBELLE));
            double solde = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_COMPTE_SOLDE));
            liste.add(new Compte(numero, libelle, solde));
        }
        cursor.close();
        db.close();
        return liste;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPTES);
        onCreate(db);
    }
}
