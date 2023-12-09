package com.example.gestion_livraison_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateColisActivity extends AppCompatActivity {

    private EditText editTextNomClt;
    private Spinner spinnerGouvernorat;
    private Spinner spinnerVille;
    private EditText editTextAdresse;
    private EditText editTextTelephone;
    private EditText editTextTelephone2;
    private EditText editTextDesignation;
    private EditText editTextPrix;
    private EditText editTextNbArticle;
    private RadioGroup radioGroupModePaiement;
    private RadioButton radioButtonEspece;
    private RadioButton radioButtonCheque;
    private EditText editTextCommentaire;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_colis);

        editTextNomClt = findViewById(R.id.editTextNomCltUpdate);
        spinnerGouvernorat = findViewById(R.id.spinnerGouvernoratUpdate);
        spinnerVille = findViewById(R.id.spinnerVilleUpdate);
        editTextAdresse = findViewById(R.id.editTextAdresseUpdate);
        editTextTelephone = findViewById(R.id.editTextTelephoneUpdate);
        editTextDesignation = findViewById(R.id.editTextDesignationUpdate);
        editTextPrix = findViewById(R.id.editTextPrixUpdate);
        editTextNbArticle = findViewById(R.id.editTextNbArticleUpdate);
        radioGroupModePaiement = findViewById(R.id.radioGroupModePaiementUpdate);
        radioButtonEspece = findViewById(R.id.radioButtonEspeceUpdate);
        radioButtonCheque = findViewById(R.id.radioButtonChequeUpdate);
        editTextCommentaire = findViewById(R.id.editTextCommentaireUpdate);

        Intent intent = getIntent();
        int codeColis = intent.getIntExtra("code_colis", 0);

        // RequestQueue initialization
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://medali99.pythonanywhere.com/api/colis/" + codeColis + "/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Colis colis = new Colis();
                            colis.setNomClt(response.getString("nom_clt"));
                            colis.setAdresseClt(response.getString("adresse_clt"));
                            colis.setTelClt(response.getString("tel_clt"));
                            colis.setDes(response.getString("des"));
                            colis.setPrix(response.getDouble("prix"));
                            colis.setNbArticle(response.getInt("nb_article"));
                            colis.setCommentaire(response.getString("commentaire"));

                            // Populate the UI fields with the retrieved data
                            editTextNomClt.setText(colis.getNomClt());
                            editTextAdresse.setText(colis.getAdresseClt());
                            editTextTelephone.setText(colis.getTelClt());
                            editTextDesignation.setText(colis.getDes());
                            editTextPrix.setText(String.valueOf(colis.getPrix()));
                            editTextNbArticle.setText(String.valueOf(colis.getNbArticle()));
                            editTextCommentaire.setText(colis.getCommentaire());

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

        queue.add(jsonObjectRequest);

        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Récupérer les valeurs des champs au moment du clic
                    String nomClt = editTextNomClt.getText().toString();
                    String gouvernorat = spinnerGouvernorat.getSelectedItem().toString();
                    String ville = spinnerVille.getSelectedItem().toString();
                    String adresse = editTextAdresse.getText().toString();
                    String tel_clt = editTextTelephone.getText().toString();
                    String tel_clt2 = editTextTelephone2.getText().toString();
                    String des = editTextDesignation.getText().toString();
                    String prix = editTextPrix.getText().toString();
                    String nbarticle = editTextNbArticle.getText().toString();
                    String commentaire = editTextCommentaire.getText().toString();

                    // URL de l'API
                    String urlup = "http://172.20.208.1:8000/api/colis/"+ codeColis;
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());

                    // Création de l'objet JSON pour les paramètres de la requête
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("nom_clt", nomClt);
                    jsonParams.put("adresse_clt", adresse);
                    jsonParams.put("commentaire", commentaire);
                    jsonParams.put("nb_article", nbarticle);
                    jsonParams.put("prix", prix);
                    jsonParams.put("des", des);
                    jsonParams.put("tel_clt", tel_clt);
                    jsonParams.put("tel_clt2", tel_clt2);
                    jsonParams.put("gouvernement", gouvernorat);
                    jsonParams.put("ville", ville);
                    jsonParams.put("expediteur", "1");
                    jsonParams.put("mode_paiement", "espece");
                    jsonParams.put("date",currentDate );
                    Log.i("colis : ",jsonParams.toString());
                    // Création de la requête POST avec Volley
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlup, jsonParams,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Succès de la requête
                                    Log.i("VOLLEY", "Response: " + response.toString());
                                    Toast.makeText(UpdateColisActivity.this, "Colis modifié avec succee ", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UpdateColisActivity.this, ListColisEnattenteExpediteur.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Erreur de la requête
                                    error.printStackTrace();
                                    Log.e("VOLLEY", "Error: " + error.toString());
                                    Toast.makeText(UpdateColisActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            });

                    // Ajout de la requête à la file d'attente
                    requestQueue = Volley.newRequestQueue(UpdateColisActivity.this);
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    // Gestion des exceptions JSON
                    e.printStackTrace();
                    Log.e("JSON_ERROR", e.toString());
                    Toast.makeText(UpdateColisActivity.this, "JSON Error", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Gestion des autres exceptions
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                    Toast.makeText(UpdateColisActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
