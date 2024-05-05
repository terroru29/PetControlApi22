package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class FastConfigurationPetControl extends AppCompatActivity {

    Button btnNextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_configuration_petcontrol);

        // Asociar variables con sus recursos
        btnNextActivity = findViewById(R.id.btnNextBottomMenu);

        //-Evento de botón
        btnNextActivity.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MenuInferiorPetControl.class);
            startActivity(i);
        });
    }
}