package com.example.gestion_livraison_android;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeScanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        // Initialise le scanner
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);  // Optionnel : permettre la rotation du dispositif pendant le scan
        integrator.setPrompt("Scan a barcode");
        integrator.initiateScan();  // Démarre le scan
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Récupère le résultat du scan
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Annulation du scan
                // Gérer ici le cas où l'utilisateur annule le scan
            } else {
                // Résultat du scan
                String barcode = result.getContents();
                // Faites quelque chose avec le code-barres, par exemple, l'afficher dans un TextView
            }
        }
    }
}
