package com.example.bankapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
public class CompteAdapter extends BaseAdapter {

    private ArrayList<Compte> comptes;
    private LayoutInflater inflater;

    public CompteAdapter(Context ctx, ArrayList<Compte> list) {
        this.comptes = list;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount() { return comptes.size(); }

    @Override public Object getItem(int pos) { return comptes.get(pos); }

    @Override public long getItemId(int pos) { return pos; }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_compte, parent, false);
        }
        TextView tvLib = convertView.findViewById(R.id.tvLibelleCompte);
        TextView tvNum = convertView.findViewById(R.id.tvNumeroCompte);
        TextView tvSolde = convertView.findViewById(R.id.tvSoldeCompte);
        Compte c = comptes.get(position); tvLib.setText(c.getLibelle());
        tvNum.setText("N\u00b0 " + c.getNumero());
        tvSolde.setText(String.format("%,.2f MAD", c.getSolde()));
        return convertView;
    }

}
