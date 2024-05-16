package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class FormPetsPetControl extends AppCompatActivity {
    EditText name, age, breed, description;
    CheckBox sterilization;
    RadioButton male, female;
    Button accept, cancel;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pets_petcontrol);

        // ASociar variables con sus respectivos recursos
        name = findViewById(R.id.etNamePet);
        age = findViewById(R.id.etAgePet);
        breed = findViewById(R.id.etBreedPet);
        male = findViewById(R.id.rbMalePet);
        female = findViewById(R.id.rbFemalePet);
        sterilization = findViewById(R.id.chkbSterilizationPet);
        description = findViewById(R.id.etDescriptionPet);
        accept = findViewById(R.id.btnAcceptDataPet);
        cancel = findViewById(R.id.btnCancelDataPet);

        //--EVENTO BUTTON
        //-Aceptar
        accept.setOnClickListener(v -> {
            // Enviar datos a la BD (database_petcontrol)


            // Retroceder de pantalla
            i = new Intent(getApplicationContext(), AddPetPetControl.class);
            startActivity(i);
        });
        //-Cancelar
        cancel.setOnClickListener(v -> {
            // Vaciar campos
            name.setText("");
            age.setText("");
            breed.setText("");
            // Desmarcar RadioButton y CheckBox
            male.setChecked(false);
            female.setChecked(false);
            sterilization.setChecked(false);
            description.setText("");

            // Retroceder la pantalla
            i = new Intent(getApplicationContext(), AddPetPetControl.class);
            startActivity(i);
        });
    }
}