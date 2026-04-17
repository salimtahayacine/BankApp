package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class ServicesActivity extends AppCompatActivity {

    private static final int CODE_PERMISSION_APPEL = 100;
    private static final int CODE_PERMISSION_SMS = 101;
    private static final int CODE_PERMISSION_LOCALISATION = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Button btnAppeler = findViewById(R.id.btnAppeler);
        Button btnSms = findViewById(R.id.btnSms);
        Button btnEmail = findViewById(R.id.btnEmail);
        Button btnLocaliser = findViewById(R.id.btnLocaliser);
        btnAppeler.setOnClickListener(v -> appelerBanque());
        btnSms.setOnClickListener(v -> envoyerSms());
        btnEmail.setOnClickListener(v -> envoyerEmail());
        btnLocaliser.setOnClickListener(v -> localiserAgence());
    }


    private void appelerBanque() {
        // Vérifier si la permission est déjà accordée
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Permission accordée : lancer l'appel
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:0522000000"));
            startActivity(intent);
        } else { // Permission non accordée : la demander
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}
                     , CODE_PERMISSION_APPEL);

        }
    }
    private void envoyerSms() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:0600000000"));
        intent.putExtra("sms_body", "Confirmation BankApp");
        startActivity(intent);
    }

    private void envoyerEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@bankapp.ma"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Relevé BankApp");
        intent.putExtra(Intent.EXTRA_TEXT, "Voici mon relevé...");
        startActivity(Intent.createChooser(intent, "Envoyer par"));
    }

    private void localiserAgence() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:33.9716,-6.8498?q=Banque+Populaire"));
        startActivity(intent);
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
     if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         // L'utilisateur a accepté
         if (requestCode == CODE_PERMISSION_APPEL) {
             appelerBanque(); // relancer la méthode
              }
         } else {
             // L'utilisateur a refusé
             Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
         }
    }

}