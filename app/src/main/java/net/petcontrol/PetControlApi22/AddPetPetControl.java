package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.Locale;

public class AddPetPetControl extends AppCompatActivity {
    GridView pets;
    Button fastConfiguration;
    int[] imagesPets = {R.drawable.dog, R.drawable.cat, R.drawable.hamster, R.drawable.fish,
            R.drawable.mouse, R.drawable.bird, R.drawable.rabbit, R.drawable.tortoise,
            R.drawable.ferret, R.drawable.pig, R.drawable.tarantula, R.drawable.snake};
    //TODO Cambiar imágenes por palabras de cada animal
    int[] nameAnimal = {R.drawable.snake, R.drawable.dog, R.drawable.cat, R.drawable.hamster};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_petcontrol);

        // Asociamos recursos con sus variables
        pets = findViewById(R.id.gvPets);
        fastConfiguration = findViewById(R.id.btnNextFastConfiguration);


        // Creamos objeto de la clase del adaptador, pasando los dos arrays de imágenes a intercambiar
        AdapterPetsPetControl adapterPets = new AdapterPetsPetControl(this, imagesPets, nameAnimal);
        // Enlazamos el GridView con el adaptador personalizado
        pets.setAdapter(adapterPets);


        //-Evento de botón
        fastConfiguration.setOnClickListener(v -> next(fastConfiguration));
    }
    /**
     * Método perteneciente del ciclo de vida de una actividad en Android y que se llama justo antes
     * de que la actividad se destruya y se elimine de la memoria de manera permanente, como por
     * ejemplo, cuando se cierra la aplicación o el sistema la descarta para liberar recursos.
     *
     * Sirve para limpiar recursos y detener servicios para asegurarse de que no existan fugas de
     * memoria ni problemas relacionados con recursos no liberados y que podrían causar problemas
     * en el rendimiento de la aplicación o incluso bloqueos.
     */
    @Override
    protected void onDestroy() {
        if (textInVoice != null) {
            // Detiene cualquier discurso en curso asegurando que si hay alguna voz hablando,
            // se detenga de inmediato
            textInVoice.stop();
            // Apaga el servicio de texto a voz liberando cualquier recurso asociado y evitando las
            // fugas de memoria
            textInVoice.shutdown();
        }
        // El sistema Android realiza su propia limpieza y libera recursos internos
        super.onDestroy();
    }
    /**
     * Pasará a la siguiente ventana
     *
     * @param view Elemento sobre el que se ejecutará la acción
     */
    public void next(View view) {
        Intent i = new Intent(this, FastConfigurationPetControl.class);
        startActivity(i);
    }
}