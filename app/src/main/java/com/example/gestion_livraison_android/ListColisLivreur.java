package com.example.gestion_livraison_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListColisLivreur extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_colis_enattente_expediteur);

        final ListView lv = findViewById(R.id.lv);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.208.1:8000/api/colis/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Colis> colisList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Colis colis = new Colis();
                                colis.setCodeColis(jsonObject.getInt("code_colis"));

                                colis.setNomClt(jsonObject.getString("nom_clt"));
                                colis.setAdresseClt(jsonObject.getString("adresse_clt"));
                                colis.setTelClt(jsonObject.getString("tel_clt"));
                                colis.setDes(jsonObject.getString("des"));
                                colis.setPrix(jsonObject.getDouble("prix"));
                                colis.setNbArticle(jsonObject.getInt("nb_article"));

                                colisList.add(colis);
                            }

                            ColisAdapterLivreur adapter = new ColisAdapterLivreur(ListColisLivreur.this, colisList);
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest);
    }
// ...
}