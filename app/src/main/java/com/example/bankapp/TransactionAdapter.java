package com.example.bankapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
public class TransactionAdapter extends BaseAdapter {

    private ArrayList<Transaction> transactions;

    private LayoutInflater inflater;
    private Context context;
    public TransactionAdapter(Context ctx, ArrayList<Transaction> list) {
        this.context = ctx; this.transactions = list; this.inflater = LayoutInflater.from(ctx);
    }

    @Override public int getCount() { return transactions.size(); }

    @Override public Object getItem(int position) { return transactions.get(position); }

    @Override public long getItemId(int position) { return position; }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        // Étape 1 : créer ou réutiliser la vue
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_transaction, parent, false);
        }
        // Étape 2 : récupérer les vues de l'item
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvLibelle = convertView.findViewById(R.id.tvLibelle);
        TextView tvMontant = convertView.findViewById(R.id.tvMontant);
        ImageView ivIconTransaction = convertView.findViewById(R.id.ivIconTransaction);
        // Étape 3 : récupérer la transaction à cette position
        Transaction t = transactions.get(position);
        // Étape 4 : remplir les vues
        tvDate.setText(t.getDate()); tvLibelle.setText(t.getLibelle());

        // Étape 5 : formater le montant
        String fmt = String.format("%+,.2f MAD", t.getMontant());
        tvMontant.setText(fmt);
        // Étape 6 : couleur verte pour crédit, rouge pour débit
        if (t.estCredit()) {
            tvMontant.setTextColor( ContextCompat.getColor(context, android.R.color.holo_green_dark));
            ivIconTransaction.setImageResource(R.drawable.ic_arrow_down);
        }
        else { tvMontant.setTextColor( ContextCompat.getColor(context, R.color.error));
            ivIconTransaction.setImageResource(R.drawable.ic_arrow_up);
        }
        return convertView;
    }
}
