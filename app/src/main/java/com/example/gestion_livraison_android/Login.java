package com.example.gestion_livraison_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    // [END declare_auth]
    private EditText logintxt;
    private EditText mdptxt;
    private Button btn;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Assurez-vous que le layout correct est utilisé

        logintxt = findViewById(R.id.username);
        mdptxt = findViewById(R.id.password);
        btn = findViewById(R.id.loginbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                try {
                    // Récupérer les valeurs des champs au moment du clic
                    String login = logintxt.getText().toString();
                    String passwoed = mdptxt.getText().toString();


                    // URL de l'API
                    String url = "https://medali99.pythonanywhere.com/login";

                    // Création de l'objet JSON pour les paramètres de la requête
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("username", login);
                    jsonParams.put("passwoed", passwoed);
                   // jsonParams.put("expediteur", "1");

                    Log.i("user : ",jsonParams.toString());
                    // Création de la requête POST avec Volley
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Succès de la requête
                                    Log.i("VOLLEY", "Response: " + response.toString());
                                    Toast.makeText(Login.this, "Success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, AppHomeActivity.class);
                                    startActivity(intent);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Erreur de la requête
                                    error.printStackTrace();
                                    Log.e("VOLLEY", "Error: " + error.toString());
                                    Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            });

                    // Ajout de la requête à la file d'attente
                    requestQueue = Volley.newRequestQueue(Login.this);
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    // Gestion des exceptions JSON
                    e.printStackTrace();
                    Log.e("JSON_ERROR", e.toString());
                    Toast.makeText(Login.this, "JSON Error", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Gestion des autres exceptions
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                    Toast.makeText(Login.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
                */
                if (!logintxt.getText().toString().equals("admin")) {
                    Intent intent = new Intent(Login.this, AppHomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Login.this, AjouEtat_colis.class);
                    startActivity(intent);
                }

            }
        });

    }

    private void signIn(String login, String password) {

    }


}


