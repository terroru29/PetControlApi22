package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class AddPetPetControl extends AppCompatActivity {
    GridView pets;
    int[] imagesPets = {R.drawable.perro, R.drawable.gato, R.drawable.hamster, R.drawable.pez,
            R.drawable.raton, R.drawable.pajaro, R.drawable.conejo, R.drawable.tortuga,
            R.drawable.huron, R.drawable.cerdo, R.drawable.tarantula, R.drawable.serpiente};
    int[] nameAnimal = {R.drawable.serpiente, R.drawable.perro, R.drawable.gato, R.drawable.hamster};

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