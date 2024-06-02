package net.petcontrol.PetControlApi22;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AboutAppPetControl extends AppCompatActivity {
    ImageButton arrow;
    ListView information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app_petcontrol);

        // Asociar la variable con su recurso
        arrow = findViewById(R.id.imgbBack);
        information = findViewById(R.id.lvAboutApp);

        // Asociar las cadenas con su variable
        String agree = getResources().getString(R.string.user_agreement);
        String policy = getResources().getString(R.string.privacy_policy);
        String experience = getResources().getString(R.string.user_experience_program);

        // Crear la lista de elementos
        List<AboutTheAppPetControl> listOfInformation = new ArrayList<>();
        listOfInformation.add(new AboutTheAppPetControl(agree, R.drawable.forward));
        listOfInformation.add(new AboutTheAppPetControl(policy, R.drawable.forward));
        listOfInformation.add(new AboutTheAppPetControl(experience, R.drawable.forward));

        // Crear el adaptador y configurarlo en el ListView
        AdapterAboutAppPetControl adapter = new AdapterAboutAppPetControl(getApplicationContext(),
                listOfInformation);
        information.setAdapter(adapter);

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