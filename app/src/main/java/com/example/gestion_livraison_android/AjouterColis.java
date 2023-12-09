package com.example.gestion_livraison_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class AjouterColis extends Fragment {
    private EditText editTextcodebarre;
    private EditText editTextNomClt;
    private Spinner spinnerGouvernorat;
    private Spinner spinnerVille;
    private EditText editTextAdresse;
    private EditText editTextTelephone;
    private EditText editTextTelephone2;
    private EditText editTextDesignation;
    private EditText editTextPrix;
    private EditText editTextNbArticle;
    private EditText editTextCommentaire;
    private Button buttonSave;
    private ArrayList<String> gouvernorats = new ArrayList<>();
    private ArrayList<String> villes = new ArrayList<>();
    private RequestQueue requestQueue;

    public AjouterColis() {

    }

    public static AjouterColis newInstance(String param1, String param2) {

        return new AjouterColis();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_ajouter_colis, container, false);
        editTextcodebarre = view.findViewById(R.id.codebarre);

        // Générer un code-barres à 12 chiffres
        String barcodeNumber = generateBarcodeNumber();

        try {
            Bitmap barcodeBitmap = encodeAsBitmap(barcodeNumber, BarcodeFormat.CODE_128, 600, 300);
            // barcodeImageView.setImageBitmap(barcodeBitmap);

            // Afficher le numéro de code-barres dans la TextView
            editTextcodebarre.setText(barcodeNumber);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        editTextNomClt = view.findViewById(R.id.editTextNomClt);
        spinnerGouvernorat = view.findViewById(R.id.spinnerGouvernorat);
        spinnerVille = view.findViewById(R.id.spinnerVille);
        editTextAdresse = view.findViewById(R.id.editTextAdresse);
        editTextTelephone = view.findViewById(R.id.editTextTelephone);
        editTextDesignation = view.findViewById(R.id.editTextDesignation);
        editTextPrix = view.findViewById(R.id.editTextPrix);
        editTextNbArticle = view.findViewById(R.id.editTextNbArticle);
        editTextCommentaire = view.findViewById(R.id.editTextCommentaire);
        buttonSave = view.findViewById(R.id.btnsave);

        // Initialisation des gouvernorats
        gouvernorats.add("Gouvernorat 1");
        gouvernorats.add("Gouvernorat 2");

        // Initialisation des villes
        villes.add("Ville 1");
        villes.add("Ville 2");

        // Adapter pour les spinners
        ArrayAdapter<String> gouvernoratAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, gouvernorats);
        ArrayAdapter<String> villeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, villes);


        // Appliquer les adaptateurs aux spinners
        spinnerGouvernorat.setAdapter(gouvernoratAdapter);
        spinnerVille.setAdapter(villeAdapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
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
                    String url = "https://medali99.pythonanywhere.com/api/colis/";
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());

                    // Création de l'objet JSON pour les paramètres de la requête
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("expediteur", 1);


                    jsonParams.put("code_barre", editTextcodebarre.getText().toString());

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
                    jsonParams.put("date", currentDate);
                    Log.i("colis : ", jsonParams.toString());

                    // Création de la requête POST avec Volley
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Succès de la requête
                                    Log.i("VOLLEY", "Response: " + response.toString());
                                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(requireActivity(), ListColisEnattenteExpediteur.class);
                                    startActivity(intent);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Erreur de la requête
                                    error.printStackTrace();
                                    Log.e("VOLLEY", "Error: " + error.toString());
                                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show();
                                }
                            });

                    // Ajout de la requête à la file d'attente
                    requestQueue = Volley.newRequestQueue(requireContext());
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    // Gestion des exceptions JSON
                    e.printStackTrace();
                    Log.e("JSON_ERROR", e.toString());
                    Toast.makeText(requireContext(), "JSON Error", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Gestion des autres exceptions
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                    Toast.makeText(requireContext() , "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }


    private static String generateBarcodeNumber() {
        // Générer un nombre aléatoire à 12 chiffres
        Random random = new Random();
        long randomNumber = 100000000000L + (long) (random.nextDouble() * 900000000000L);
        return String.valueOf(randomNumber);
    }
    public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        Code128Writer code128Writer = new Code128Writer();
        BitMatrix bitMatrix = code128Writer.encode(contents, format, width, height);

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
