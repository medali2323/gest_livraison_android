package com.example.gestion_livraison_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.List;

public class ListColisEnattenteExpediteur extends Fragment {

    private RecyclerView recyclerView;
    private ColisAdapter colisAdapter;
    private List<Colis> colisList;

    public ListColisEnattenteExpediteur() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_colis, container, false);

        recyclerView = view.findViewById(R.id.lits);
        colisList = new ArrayList<>(); // Initialize an empty list

        // Call the API to populate the colisList
        fetchDataFromAPI();

        colisAdapter = new ColisAdapter(colisList, getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(colisAdapter);

        return view;
    }

    private void fetchDataFromAPI() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://medali99.pythonanywhere.com/api/colis/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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

                            // Notify the adapter that the data set has changed
                            colisAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}
