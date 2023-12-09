package com.example.gestion_livraison_android;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class AppHomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    Map<Integer, Fragment> fragments = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_home_bar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        FirstFragment fs = new FirstFragment();


        Dashboard dash = new Dashboard();
        AjouterColis aj = new AjouterColis();
         ListColisEnattenteExpediteur list = new ListColisEnattenteExpediteur();


        fragments.put(R.id.nav_home, dash);
        fragments.put(R.id.nav_share, list);
        fragments.put(R.id.ajouter_colis, aj);

        fragments.put(R.id.nav_home, fs);


        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = fragments.get(item.getItemId());
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.replaceableFragment, fragment)
                    .commit();
            return true;
        }


        return false;
    }
}
