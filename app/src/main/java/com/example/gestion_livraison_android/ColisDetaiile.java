package com.example.gestion_livraison_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class ColisDetaiile extends AppCompatActivity {
    private Button btnModifier;
    private TextView nom_clt, tel_clt, adresse, codebarre,prix;
    private  int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaille_colis);

        // Initialisation des vues
        codebarre = findViewById(R.id.codebarre1);
        nom_clt = findViewById(R.id.nom_clt);
        tel_clt = findViewById(R.id.tel_clt);
        adresse = findViewById(R.id.Adresse1);
        btnModifier= findViewById(R.id.modifier);
        prix=findViewById(R.id.prix);
        // Récupération du code colis depuis l'intent
        Intent i = getIntent();
        int codecolis = i.getIntExtra("codecolis", 0);
    code=codecolis;
        // Initialisation de la RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://medali99.pythonanywhere.com/api/colis/" + codecolis + "/";

        // Création de la requête JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Analyse de la réponse JSON
                            Colis colis = new Colis();
                            colis.setNomClt(response.getString("nom_clt"));
                            colis.setAdresseClt(response.getString("adresse_clt"));
                            colis.setTelClt(response.getString("tel_clt"));
                            colis.setDes(response.getString("des"));
                            colis.setPrix(response.getDouble("prix"));
                            colis.setNbArticle(response.getInt("nb_article"));
                            colis.setCommentaire(response.getString("commentaire"));

                            // Affichage des données dans les TextViews
                            nom_clt.setText(colis.getNomClt());
                            adresse.setText(colis.getAdresseClt());
                            tel_clt.setText(colis.getTelClt());
                            codebarre.setText(String.valueOf(colis.getCodeColis()));
                            prix.setText(String.valueOf(colis.getPrix()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("erreur bakend", "Erreur backend");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Ajout de la requête à la RequestQueue
        queue.add(jsonObjectRequest);

        // Ajout du listener sur le bouton de confirmation
        btnModifier.setOnClickListener(v -> gotomodifier(this));
    }

    private void gotomodifier(Context context) {

            Intent intent = new Intent(context, UpdateColisActivity.class);
            intent.putExtra("code_colis", code);
            context.startActivity(intent);


    }


}
