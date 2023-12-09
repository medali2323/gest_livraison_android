package com.example.gestion_livraison_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AjouEtat_colis extends AppCompatActivity {

    private List<String> barcodeList = new ArrayList<>();
    private ListView barcodeListView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajou_etat_colis);

        // Initialise le scanner
        initiateScan();

        barcodeListView = findViewById(R.id.barcodeListView);
        spinner = findViewById(R.id.etatColisSpinner);

        // Configure l'adaptateur pour la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, barcodeList);
        barcodeListView.setAdapter(adapter);

        // Initialise le Spinner
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://medali99.pythonanywhere.com/api/etats/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Créez une liste d'objets Etat pour stocker les éléments de la réponse JSON
                        ArrayList<Etat> etats = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Créez un nouvel objet Etat
                                Etat etat = new Etat();
                                etat.setCode_etat(jsonObject.getInt("code_etat"));
                                etat.setLibelle(jsonObject.getString("libelle"));

                                // Ajoutez l'objet Etat à la liste
                                etats.add(etat);
                            }

                            // Après la boucle, initialisez l'adaptateur du Spinner avec la liste d'objets Etat
                            ArrayAdapter<Etat> spinnerAdapter = new ArrayAdapter<>(AjouEtat_colis.this, android.R.layout.simple_spinner_item, etats);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(spinnerAdapter);

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

        // Initialise le bouton pour arrêter le scan
        Button stopScanButton = findViewById(R.id.Scan);
        stopScanButton.setOnClickListener(v -> finishScanning());

        // Initialise le bouton pour envoyer les données
        Button sendDataButton = findViewById(R.id.subbmitButton);
        sendDataButton.setOnClickListener(v -> sendBarcodeData());
    }

    private void initiateScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan a barcode");
        integrator.initiateScan();
    }

    private void finishScanning() {
        // Arrêtez le scan et affichez la liste des codes-barres
        displayBarcodeList();
    }

    private void displayBarcodeList() {
        for (String barcode : barcodeList) {
            // Pour chaque code-barres, effectuer une requête POST
            String selectedSpinnerItem = spinner.getSelectedItem().toString();
            new PostRequestTask().execute(barcode, selectedSpinnerItem);
        }
    }

    private class PostRequestTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            if (params.length < 2) {
                return null;
            }

            String barcode = params[0];
            String selectedSpinnerItem = params[1];

            try {
                // Construire l'URL de votre API
                URL url = new URL("https://medali99.pythonanywhere.com/api/etatcolis/");

                // Créer la connexion HTTP
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    // Configurer la connexion pour une requête POST
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);

                    // Construire le corps de la requête
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("barcode", barcode);
                    requestBody.put("selectedItem", selectedSpinnerItem);

                    // Envoyer le corps de la requête
                    try (OutputStream os = urlConnection.getOutputStream()) {
                        byte[] input = requestBody.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Récupérer la réponse (si nécessaire)
                    // ...

                } finally {
                    urlConnection.disconnect();
                }

            } catch (Exception e) {
                // Gérer les erreurs
            }

            return null;
        }
    }

    private void sendBarcodeData() {
        for (String barcode : barcodeList) {
            String selectedSpinnerItem = spinner.getSelectedItem().toString();
            new PostRequestTask().execute(barcode, selectedSpinnerItem);
        }

        // Effacer la liste après l'envoi des données
        barcodeList.clear();
        updateListView();
    }

    private void updateListView() {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) barcodeListView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Récupérer les données du code-barres depuis l'intent
                String barcode = data.getStringExtra("SCAN_RESULT");

                if (barcode != null) {
                    // Ajouter le code-barres à la liste
                    if (!barcodeList.contains(barcode)) {
                        barcodeList.add(barcode);
                        Toast.makeText(AjouEtat_colis.this, "Code-barres ajouté : " + barcode, Toast.LENGTH_LONG).show();
                    } else {
                        // Le code-barres existe déjà dans la liste
                        Toast.makeText(AjouEtat_colis.this, "Code-barres déjà sélectionné : " + barcode, Toast.LENGTH_LONG).show();
                    }
                    // Mettre à jour la ListView
                    updateListView();

                    // Continuer le scan
                    initiateScan();
                } else {
                    // Gérer le cas où les données du code-barres sont nulles
                    Toast.makeText(AjouEtat_colis.this, "Aucune donnée de code-barres trouvée.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Gérer l'annulation du scan
                Toast.makeText(AjouEtat_colis.this, "Scan annulé", Toast.LENGTH_LONG).show();
            }
        }
    }
}
