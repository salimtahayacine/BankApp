package com.example.bankapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class HeaderFragment extends Fragment {

    private TextView tvSolde;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Gonfler le layout du fragment
        View vue = inflater.inflate(R.layout.fragment_header, container, false);
        // Récupérer les vues (attention : VUE.findViewById)
        TextView tvNom = vue.findViewById(R.id.tvNomHeader);
        tvSolde = vue.findViewById(R.id.tvSoldeHeader);

        // Récupérer le nom depuis l'Intent de l'Activity parente
        String nom = getActivity().getIntent().getStringExtra("nom_utilisateur");
        if (nom != null) { tvNom.setText("Bonjour, " + nom); }
        // Solde en dur pour l'instant (sera calculé depuis SQLite dans l'atelier 5)
        tvSolde.setText("12 450,00 MAD");

        return vue;
    }

    public void setSoldeTotal(double total) {
        if (tvSolde != null) {
            tvSolde.setText(String.format("%,.2f MAD", total));
        }
    }

}
