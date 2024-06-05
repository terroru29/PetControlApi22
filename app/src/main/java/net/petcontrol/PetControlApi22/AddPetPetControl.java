package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class AddPetPetControl extends AppCompatActivity {
    GridView pets;
    Button mainWindow;
    int[] imagesPets = {R.drawable.dog, R.drawable.cat, R.drawable.hamster, R.drawable.fish,
            R.drawable.mouse, R.drawable.bird, R.drawable.rabbit, R.drawable.tortoise,
            R.drawable.ferret, R.drawable.pig, R.drawable.tarantula, R.drawable.snake};
    private AdapterPetsPetControl adapter;
    List<TypePetsPetControl> typePets = new ArrayList<TypePetsPetControl>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_petcontrol);

        // Asociamos recursos con sus variables
        pets = findViewById(R.id.gvPets);
        mainWindow = findViewById(R.id.btnNextMainWindow);


        // Creamos objeto de la clase del adaptador, pasando los dos arrays de imágenes a intercambiar
        AdapterPetsPetControl adapterPets = new AdapterPetsPetControl(this, imagesPets, typePets);
        // Enlazamos el GridView con el adaptador personalizado
        pets.setAdapter(adapterPets);
        // Verificar que los ID y tipos corresponden con los seleccionados
        adapterPets.verifyData();


        //-Evento de botón
        mainWindow.setOnClickListener(v -> next(mainWindow));
    }
    /**
     * Este método es parte del ciclo de vida de la actividad y se llama cuando la actividad ya no
     * está en uso y se destruye, eliminándose de la memoria.
     *
     * Esta implementación garantiza que los recursos TextToSpeech se liberen correctamente para
     * evitar pérdidas de memoria. Comprueba si el adaptador no es nulo, y si es así, llama al
     * método {@link AdapterPetsPetControl#shutdownTextToSpeech()} para detener y apagar el motor
     * TextToSpeech.
     *
     * @see AdapterPetsPetControl#shutdownTextToSpeech()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Liberar recursos de TextToSpeech
        if (adapter != null) {
            adapter.shutdownTextToSpeech();
        }
    }
    /**
     * Pasará a la siguiente ventana
     *
     * @param view Elemento sobre el que se ejecutará la acción
     */
    public void next(View view) {
        Intent i = new Intent(this, ViewBottomPetControl.class);
        startActivity(i);
        // Cierra la actividad actual para evitar que el usuario regrese a ella
        //finish();
    }
}