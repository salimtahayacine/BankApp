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

public class ComptesActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);
        
        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<Compte> liste = db.getAllComptes();
        
        CompteAdapter adapter = new CompteAdapter(this, liste);
        ListView lv = findViewById(R.id.lvComptes);
        lv.setAdapter(adapter);

        double total = 0;
        for (Compte compte : liste) {
            total += compte.getSolde();
        }

        HeaderFragment header = (HeaderFragment) getSupportFragmentManager().findFragmentById(R.id.headerFragment);
        if (header != null) {
            header.setSoldeTotal(total);
        }

        lv.setOnItemClickListener((parent, view, pos, id) -> {
            Compte compte = liste.get(pos);
            Toast.makeText(ComptesActivity.this, compte.getLibelle() + " : " + compte.getSolde(), Toast.LENGTH_SHORT).show();
        });
    }

}