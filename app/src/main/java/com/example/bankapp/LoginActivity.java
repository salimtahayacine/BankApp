package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.os.CountDownTimer;


public class LoginActivity extends AppCompatActivity {

    // Variables d'instance : utilisées dans plusieurs méthodes
    private EditText etIdentifiant;
    private EditText etMotDePasse;

    private EditText etEmail;

    private CheckBox cbRappeler;
    private TextView tvErreur;

    private Button btnConnexion;

    private static final int MAX_TENTATIVES = 3;
    private static final long DUREE_BLOCAGE_MS = 30_000L;
    private int tentativesEchouees = 0;
    private CountDownTimer blocageTimer;


    // Identifiants en dur (pour cet atelier uniquement)
    private static final String ID_VALIDE = "admin";

    private static final String ID_VALIDE_EMAIL = "admin@bank.com";
    private static final String MDP_VALIDE = "123456";
    private static final String TAG = "LoginActivity";

    private static final String PREFS_NAME = "BankAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Récupération des vues
        etIdentifiant = findViewById(R.id.etIdentifiant);
        etMotDePasse = findViewById(R.id.etMotDePasse);
        cbRappeler = findViewById(R.id.cbRappeler);
        tvErreur = findViewById(R.id.tvErreur);

        etEmail = findViewById(R.id.etEmail);

        btnConnexion = findViewById(R.id.btnConnexion);
        btnConnexion.setOnClickListener(v -> tenterConnexion());

        // Restaurer l'identifiant si sauvegardé
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean seRappeler = prefs.getBoolean("se_rappeler", false);
        if (seRappeler) {
            String idSauve = prefs.getString("identifiant", "");
            etIdentifiant.setText(idSauve);
            etEmail.setText(ID_VALIDE_EMAIL);
            cbRappeler.setChecked(true);
        }

    }

    private void tenterConnexion() {
        // Réinitialiser l'erreur précédente
        tvErreur.setVisibility(View.GONE);
        // Lire les champs
        String id = etIdentifiant.getText().toString().trim();
        String mdp = etMotDePasse.getText().toString().trim();
        String email = etEmail.getText().toString().trim();



        // --- Niveau 1 : champs vides ---
        if (id.isEmpty() || mdp.isEmpty()) {
            afficherErreur(getString(R.string.erreur_champs_vides));
            return; // on s'arrête ici
            }

        // --- Niveau 1.5 : format email (présence de @) ---
        if (!email.contains("@")) {
            afficherErreur(getString(R.string.erreur_email_invalide));
            return;
        }

        // --- Niveau 2 : mot de passe trop court ---
        if (mdp.length() < 6) {
            afficherErreur(getString(R.string.erreur_mdp_court));
            return; // on s'arrête ici
            }
        // --- Niveau 3 : vérification des identifiants ---
        if (id.equals(ID_VALIDE) && mdp.equals(MDP_VALIDE)) {
            Log.d(TAG, "Rappeler : " + cbRappeler.isChecked());
            Intent intent = new Intent(this, AccueilActivity.class);
            intent.putExtra("nom_utilisateur", id);
            startActivity(intent);


        }
        else { afficherErreur(getString(R.string.erreur_identifiants));
        }

        // Niveau 4 : vérification des identifiants
        if (id.equals(ID_VALIDE) && email.equals(ID_VALIDE_EMAIL) && mdp.equals(MDP_VALIDE)) {
            tentativesEchouees = 0;
            Log.d(TAG, "Rappeler : " + cbRappeler.isChecked());
            Intent intent = new Intent(this, AccueilActivity.class);
            intent.putExtra("nom_utilisateur", id);
            startActivity(intent);

            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            if (cbRappeler.isChecked()) {
                editor.putString("identifiant", id);
                editor.putBoolean("se_rappeler", true);
            } else {
                editor.remove("identifiant");
                editor.putBoolean("se_rappeler", false);
            }
            editor.apply();


        } else {
            tentativesEchouees++;
            if (tentativesEchouees >= MAX_TENTATIVES) {
                demarrerBlocage();
            } else {
                afficherErreur(getString(R.string.erreur_identifiants));
            }
        }

    }

    private void afficherErreur(String message) {
        tvErreur.setText(message); tvErreur.setVisibility(View.VISIBLE);
    }

    private void demarrerBlocage() {
        btnConnexion.setEnabled(false);

        if (blocageTimer != null) {
            blocageTimer.cancel();
        }

        blocageTimer = new CountDownTimer(DUREE_BLOCAGE_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondes = (int) (millisUntilFinished / 1000);
                afficherErreur(getString(R.string.erreur_trop_tentatives, secondes));
            }

            @Override
            public void onFinish() {
                tentativesEchouees = 0;
                btnConnexion.setEnabled(true);
                tvErreur.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (blocageTimer != null) {
            blocageTimer.cancel();
        }
        super.onDestroy();
    }



}
