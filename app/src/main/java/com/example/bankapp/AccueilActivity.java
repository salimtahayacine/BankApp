package com.example.bankapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccueilActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Récupération des vues
        TextView tvBienvenue = findViewById(R.id.tvBienvenue);
        CardView cardTransactions = findViewById(R.id.cardTransactions);
        CardView cardRecherche = findViewById(R.id.cardRecherche);
        CardView cardVirement = findViewById(R.id.cardVirement);
        CardView cardComptes = findViewById(R.id.cardComptes);





        // Récupérer le nom passé par LoginActivity
        String nom = getIntent().getStringExtra("nom_utilisateur");
        if (nom != null) {
            tvBienvenue.setText(getString(R.string.accueil_bienvenue, nom));
        }

        cardTransactions.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransactionsActivity.class);
            intent.putExtra("nom_utilisateur", nom);
            startActivity(intent);
        });

        cardComptes.setOnClickListener(v -> {
            Intent intent = new Intent(this, ComptesActivity.class);
            intent.putExtra("nom_utilisateur", nom);
            startActivity(intent);
        });

        // Clics sur les cartes (Toast provisoire)
//        cardTransactions.setOnClickListener(v -> afficherToast());
        cardRecherche.setOnClickListener(v -> afficherToast());
        cardVirement.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, VirementActivity.class);
            intent.putExtra("nom_utilisateur", nom);
            startActivity(intent);
        });
//        cardComptes.setOnClickListener(v -> afficherToast());
        findViewById(R.id.cardStats).setOnClickListener(v -> afficherToast());

        findViewById(R.id.cardParams).setOnClickListener(v -> {
//            Intent intent = new Intent(AccueilActivity.this, AproposActivity.class);
            Intent intent = new Intent(AccueilActivity.this, ServicesActivity.class);
            intent.putExtra("nom_utilisateur", nom);
            startActivity(intent);
        });

        Button btnDeconnexion = findViewById(R.id.btnDeconnexion);

        btnDeconnexion.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


    }

    private void afficherToast() {
        Toast.makeText(this, R.string.toast_a_venir, Toast.LENGTH_SHORT).show();
    }



}
