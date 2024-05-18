package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FormPetsPetControl extends AppCompatActivity {
    EditText name, age, breed, description;
    CheckBox sterilization;
    RadioButton male, female;
    Button accept, cancel;
    Intent i;
    PetsDAOPetControl petsDAO; // Instancia del DAO de Pets
    ExecutorService executorService;

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

        // Inicializar el DAO de Pets
        petsDAO = Room.databaseBuilder(this, DatabasePetControl.class,
                "database_petcontrol").build().petsDAO();
        executorService = Executors.newSingleThreadExecutor();


        //--EVENTO BUTTON
        //-Aceptar
        accept.setOnClickListener(v -> {
            // Enviar datos a la BD (database_petcontrol)
            String petName = name.getText().toString();
            int petAge = Integer.parseInt(age.getText().toString());
            String petBreed = breed.getText().toString();
            String petDescription = description.getText().toString();
            String petSex = male.isChecked() ? "Macho" : "Hembra";
            boolean petSterilization = sterilization.isChecked();

            // Crear una nueva instancia de PetsPetControl con los datos del formulario
            PetsPetControl newPet = new PetsPetControl();
            newPet.name_pet = petName;
            newPet.age_pet = petAge;
            newPet.breed = petBreed;
            newPet.sex_pet = petSex;
            newPet.sterilization = petSterilization;
            newPet.description_pet = petDescription;

            /*
            // Insertar la nueva mascota en la base de datos
            new Thread(() -> {
                if (petsDAO != null)
                    petsDAO.insertPet(newPet);
            }).start();

            // Mostrar mensaje de éxito
            Toast.makeText(this, "Los datos se han añadido con éxito.",
                    Toast.LENGTH_SHORT).show();

            // Retroceder de pantalla
            i = new Intent(getApplicationContext(), AddPetPetControl.class);
            startActivity(i);
             */


            // Insertar la nueva mascota en la base de datos en un hilo de fondo
            executorService.execute(() -> {
                petsDAO.insertPet(newPet);

                runOnUiThread(() -> {
                    // Mostrar mensaje de éxito
                    Toast.makeText(this, "Los datos se han añadido con éxito.",
                            Toast.LENGTH_SHORT).show();

                    // Retroceder de pantalla
                    i = new Intent(getApplicationContext(), AddPetPetControl.class);
                    startActivity(i);
                });
            });

            /*
            // Insertar la nueva mascota en la base de datos en un hilo de fondo y esperar a que termine
            Future<Void> future = executorService.submit(new Callable<Void>() {
                @Override
                public Void call() {
                    petsDAO.insertPet(newPet);
                    return null;
                }
            });
            try {
                // Esperar a que la tarea de inserción termine
                future.get();

                // Mostrar mensaje de éxito en el hilo principal
                runOnUiThread(() -> {
                    Toast.makeText(this, "Los datos se han añadido con éxito.",
                            Toast.LENGTH_SHORT).show();

                    // Retroceder de pantalla
                    i = new Intent(getApplicationContext(), AddPetPetControl.class);
                    startActivity(i);
                });
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                // Manejo de errores si es necesario
                runOnUiThread(() -> Toast.makeText(this, "Error al añadir los datos.",
                        Toast.LENGTH_SHORT).show());
            }
            */
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