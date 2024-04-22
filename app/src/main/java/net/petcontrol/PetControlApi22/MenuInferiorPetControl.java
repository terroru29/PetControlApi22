package net.petcontrol.PetControlApi22;

import android.os.Bundle;

//import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.petcontrol.PetControlApi22.databinding.ActivityMenuInferiorPetcontrolBinding;

public class MenuInferiorPetControl extends AppCompatActivity {

    private ActivityMenuInferiorPetcontrolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuInferiorPetcontrolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //BottomNavigationView navViewBottom = findViewById(R.id.nav_view_bottom);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_notifications, R.id.navigation_calendar, R.id.navigation_PC,
                R.id.navigation_location, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewBottom, navController);
    }
}