package com.example.bankapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VirementActivity extends AppCompatActivity {

    private EditText etBeneficiaire, etMontant, etMotif;
    private TextView tvErreur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virement);
        etBeneficiaire = findViewById(R.id.etBeneficiaire);
        etMontant = findViewById(R.id.etMontant);
        etMotif = findViewById(R.id.etMotif);
        tvErreur = findViewById(R.id.tvErreurVirement);
        Button btnVirement = findViewById(R.id.btnVirement);
        btnVirement.setOnClickListener(v -> effectuerVirement());
    }

    private void effectuerVirement() {
        tvErreur.setVisibility(View.GONE);
     String benef = etBeneficiaire.getText().toString().trim();
     String montantStr = etMontant.getText().toString().trim();
     String motif = etMotif.getText().toString().trim();

     // Validation
        if (benef.isEmpty() || montantStr.isEmpty() || motif.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs.");
         return;
        }
        double montant;
        try {
            montant = Double.parseDouble(montantStr);
        } catch (NumberFormatException e) {
            afficherErreur("Montant invalide.");
        return;
        }
        if (montant <= 0) {
        afficherErreur("Le montant doit être positif.");
        return;
    }
        // Insérer dans SQLite (montant négatif car c'est un débit)
        DatabaseHelper db = new DatabaseHelper(this);
        String aujourdhui = new java.text.SimpleDateFormat("dd/MM/yyyy")
                .format(new java.util.Date());
        db.ajouterTransaction(aujourdhui, "Virement : " + benef, -montant);
        Toast.makeText(this, "Virement effectué", Toast.LENGTH_SHORT).show();
        finish(); // revenir à l'écran précédent
    }
         private void afficherErreur(String msg) {
            tvErreur.setText(msg);
            tvErreur.setVisibility(View.VISIBLE);
    }

}