package net.petcontrol.PetControlApi22;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import net.petcontrol.PetControlApi22.ui.settings.SettingsFragment;

public class ThirdPartyDataPetControl extends AppCompatActivity {
    ImageButton arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdparty_data_petcontrol);

        /*
        // Obtener el FragmentManager y comenzar una transacción
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Reemplazar el contenido del contenedor con el nuevo fragmento
        transaction.replace(R.id.fragment_container, new SettingsFragment())
                // Añadir la transacción a la pila trasera para poder navegar hacia atrás
                .addToBackStack(null)
                .commit();// Confirmar la transacción
        */


        // Asociar variables con sus recursos
        arrow = findViewById(R.id.imgbBack);

        //--EVENTO IMAGEBUTTON
        arrow.setOnClickListener(v -> onBackPressed());

        // Registrar un callback para manejar el botón de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();  // Asegura que la actividad se cierra
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
    @Override
    public void onBackPressed() {
        // Asegurará que la actividad actual se cierra y vuelve al fragmento anterior
        super.onBackPressed();
        finish();  // Asegurar que la actividad se cierra
    }
}