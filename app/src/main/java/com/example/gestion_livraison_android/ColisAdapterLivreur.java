
package com.example.gestion_livraison_android;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class ColisAdapterLivreur extends ArrayAdapter<Colis> {
    public ColisAdapterLivreur(@NonNull Context context, ArrayList<Colis> dataArrayList) {
        super(context, R.layout.itemcolislivreur, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        Colis currentColis = getItem(position);
        Log.d("UpdateColisActivity", "position: " + position);
        Log.d("UpdateColisActivity", "COLIS: " + currentColis);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_colis, parent, false);
        }
        ImageButton editButton = view.findViewById(R.id.edit);
        ImageButton phoneButton = view.findViewById(R.id.phoneButton);
        // Bouton d'édition
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), UpdateColisActivity.class);
                intent.putExtra("code_colis", currentColis.getCodeColis());


                getContext().startActivity(intent);
            }
        });


        TextView nom_clt = view.findViewById(R.id.nom_clt);
        TextView adresse = view.findViewById(R.id.adresse);
        TextView tel = view.findViewById(R.id.tel_clt);
        TextView des = view.findViewById(R.id.des);
        TextView prix = view.findViewById(R.id.prix);
        TextView nbarticle = view.findViewById(R.id.nbarticle);

        nom_clt.setText("Nom Client: " + currentColis.getNomClt());
        adresse.setText("Adresse: " + currentColis.getAdresseClt());
        tel.setText("Téléphone Client: " + currentColis.getTelClt());
        des.setText("Description: " + currentColis.getDes());
        prix.setText("Prix: " + currentColis.getPrix());
        nbarticle.setText("Nombre d'Articles: " + currentColis.getNbArticle());
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+216"+currentColis.getTelClt()));
                getContext().startActivity(intent);
            }
        });
        return view;
    }

}
