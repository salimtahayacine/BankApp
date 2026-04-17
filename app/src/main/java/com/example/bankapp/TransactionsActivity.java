package com.example.bankapp;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        // Données fictives (sera remplacé par SQLite dans l'atelier 5)
       // ArrayList<Transaction> liste = new ArrayList<>();
//        liste.add(new Transaction("15/03/2025", "Salaire Mars", 8500.00));
//        liste.add(new Transaction("14/03/2025", "Loyer Appartement", -3200.00));
//        liste.add(new Transaction("12/03/2025", "Courses Marjane", -450.00));
//        liste.add(new Transaction("10/03/2025", "Facture Inwi", -199.00));
//        liste.add(new Transaction("08/03/2025", "Virement reçu", 1200.00));
//        liste.add(new Transaction("05/03/2025", "Restaurant", -180.00));
//        liste.add(new Transaction("03/03/2025", "Essence", -350.00));
//        liste.add(new Transaction("01/03/2025", "Prime trimestrielle", 2500.00));

        // APRES (données depuis SQLite) :
        try {
            DatabaseHelper db = new DatabaseHelper(this);
            ArrayList<Transaction> liste = db.getAllTransactions();

            // Créer l'Adapter et l'associer à la ListView
            TransactionAdapter adapter = new TransactionAdapter(this, liste);
            ListView lv = findViewById(R.id.lvTransactions);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener((parent, view, pos, id) -> {
                Transaction transaction = liste.get(pos);
                Toast.makeText(TransactionsActivity.this, transaction.getLibelle() + " : " + transaction.getMontant(), Toast.LENGTH_SHORT).show();
            });

        }catch (Exception e) {
            Toast.makeText(this, "Erreur lors de la récupération des transactions : " + e.getMessage(), Toast.LENGTH_LONG).show();
            ArrayList<Transaction> liste = new ArrayList<>(); // Liste vide en cas d'erreur
        }


    }

}