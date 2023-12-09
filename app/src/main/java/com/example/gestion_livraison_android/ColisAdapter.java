package com.example.gestion_livraison_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ColisAdapter extends RecyclerView.Adapter<ColisViewHolder> {

    private List<Colis> colisList;
    private Context context;

    public ColisAdapter(List<Colis> colisList, Context context) {
        this.colisList = colisList;
        this.context = context;
    }

    @NonNull
    @Override
    public ColisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.colis_card, parent, false);
        return new ColisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColisViewHolder holder, int position) {
        Colis colis = colisList.get(position);
        holder.bind(colis);
    }

    @Override
    public int getItemCount() {
        return colisList.size();
    }
}
