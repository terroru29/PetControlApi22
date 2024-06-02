package net.petcontrol.PetControlApi22;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class UpdateAppPetControl extends AppCompatActivity {
    ImageButton arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app_petcontrol);

        // Asociar la variable con su recurso
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