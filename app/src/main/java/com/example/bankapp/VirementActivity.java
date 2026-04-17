package com.example.bankapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;

public class VirementActivity extends AppCompatActivity {

    private EditText etBeneficiaire, etMontant, etMotif;
    private TextView tvErreur;
    private Spinner spCompteSource;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virement);
        
        spCompteSource = findViewById(R.id.spCompteSource);
        etBeneficiaire = findViewById(R.id.etBeneficiaire);
        etMontant = findViewById(R.id.etMontant);
        etMotif = findViewById(R.id.etMotif);
        tvErreur = findViewById(R.id.tvErreurVirement);
        
        db = new DatabaseHelper(this);
        ArrayList<Compte> comptes = db.getAllComptes();
        ArrayAdapter<Compte> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comptes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompteSource.setAdapter(adapter);

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
        String aujourdhui = new java.text.SimpleDateFormat("dd/MM/yyyy")
                .format(new java.util.Date());
        db.ajouterTransaction(aujourdhui, "Virement : " + benef, -montant);
        
        proposerSms(benef, montant);
    }
    
    private void proposerSms(String beneficiaire, double montant) {
        new AlertDialog.Builder(this)
                .setTitle("Virement réussi")
                .setMessage("Voulez-vous envoyer un SMS de confirmation au bénéficiaire ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    String message = "Bonjour " + beneficiaire + ", un virement de " + montant + " MAD a été effectué en votre faveur.";
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:")); // This ensures only SMS apps respond
                    intent.putExtra("sms_body", message);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Non", (dialog, which) -> finish())
                .show();
    }

         private void afficherErreur(String msg) {
            tvErreur.setText(msg);
            tvErreur.setVisibility(View.VISIBLE);
    }

}