package net.petcontrol.PetControlApi22;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.petcontrol.PetControlApi22.databinding.ActivityMenuInferiorPetcontrolBinding;

import java.util.Objects;

public class ViewBottomPetControl extends AppCompatActivity {
    //private ActivityMenuInferiorPetcontrolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMenuInferiorPetcontrolBinding binding = ActivityMenuInferiorPetcontrolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navViewBottom = findViewById(R.id.nav_view_bottom);

        // Aseguramos que el ID coincide con el diseño XML
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_view_bottom);
        /*NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_view_bottom);*/
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        // Pasando cada ID de menú como un conjunto de ID porque cada menú debe considerarse como
        // destino de nivel superior
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_events, R.id.navigation_calendar, R.id.navigation_PC,
                R.id.navigation_location, R.id.navigation_settings).build();

        // Configuración de ActionBar y BottomNavigationView con NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewBottom, navController);
    }
}