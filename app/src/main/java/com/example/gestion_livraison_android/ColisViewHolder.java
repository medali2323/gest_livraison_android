package com.example.gestion_livraison_android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ColisViewHolder extends RecyclerView.ViewHolder {

    TextView nomclt, adresse;
    Button btnApply;
    Colis colis;

    public ColisViewHolder(@NonNull View itemView) {
        super(itemView);
        nomclt = itemView.findViewById(R.id.nom_clt);
        adresse = itemView.findViewById(R.id.adresse);
        btnApply = itemView.findViewById(R.id.btnApply);

        btnApply.setOnClickListener(v -> openColisDetailActivity(v.getContext()));
    }

    public void bind(Colis colis) {
        // Set data to the views
        this.colis = colis;
        nomclt.setText(colis.getNomClt());
        adresse.setText("adresse: " + colis.getAdresseClt());
    }

    private void openColisDetailActivity(Context context) {
        if (colis != null) {
            Intent intent = new Intent(context, ColisDetaiile.class);
            intent.putExtra("codecolis", colis.getCodeColis());
            context.startActivity(intent);

        } else {
        }
    }

}
