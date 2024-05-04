package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class AddPetPetControl extends AppCompatActivity {
    GridView pets;
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

        AdapterPetsPetControl adapterPets = new AdapterPetsPetControl(this, imagesPets, nameAnimal);
        // Enlazamos el GridView con el adaptador personalizado
        pets.setAdapter(adapterPets);
    }
}