package com.example.tcc_abcancer;

import android.app.Activity;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tcc_abcancer.databinding.ActivityOngBinding;
import com.example.tcc_abcancer.databinding.ActivityPrincipalBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OngActivity extends AppCompatActivity {

    private ActivityOngBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding = ActivityOngBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view_ong);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ongperfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_ong);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewOng, navController);


    }
}
