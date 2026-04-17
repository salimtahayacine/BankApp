package com.example.bankapp;

import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AproposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);

        TextView tvNomApp = findViewById(R.id.tvNomApp);
        TextView tvVersion = findViewById(R.id.tvVersion);
        TextView tvAuteur = findViewById(R.id.tvAuteur);
        TextView tvUtilisateur = findViewById(R.id.tvUtilisateur);
        Button btnRetour = findViewById(R.id.btnRetour);

        String nomUtilisateur = getIntent().getStringExtra("nom_utilisateur");
        if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
            nomUtilisateur = getString(R.string.apropos_utilisateur_inconnu);
        }

        String versionApp = getString(R.string.apropos_version_inconnue);
        try {
            versionApp = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            if (versionApp == null || versionApp.trim().isEmpty()) {
                versionApp = getString(R.string.apropos_version_inconnue);
            }
        } catch (PackageManager.NameNotFoundException e) {
            versionApp = getString(R.string.apropos_version_inconnue);
        }

        tvNomApp.setText(getString(R.string.apropos_app) + " : " + getString(R.string.app_name));
        tvVersion.setText(getString(R.string.apropos_version) + " : " + versionApp);
        tvAuteur.setText(getString(R.string.apropos_auteur) + " : " + getString(R.string.apropos_nom_auteur));
        tvUtilisateur.setText(getString(R.string.apropos_utilisateur) + " : " + nomUtilisateur);

        btnRetour.setOnClickListener(v -> finish());
    }
}

