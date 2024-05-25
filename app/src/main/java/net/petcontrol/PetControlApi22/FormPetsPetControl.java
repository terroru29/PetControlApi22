package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FormPetsPetControl extends AppCompatActivity {
    EditText name, age, breed, description;
    ImageView photo;
    CheckBox sterilization;
    RadioButton male, female;
    Button accept, cancel;
    Intent i;
    // Define un código de solicitud para identificar la respuesta de la galería
    private static final int PICK_IMAGE_REQUEST = 1;
    /*
    PetsDAOPetControl petsDAO; // Instancia del DAO de Pets
    ExecutorService executorService;
    */

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
        photo = findViewById(R.id.ivPhotoPet);
        sterilization = findViewById(R.id.chkbSterilizationPet);
        description = findViewById(R.id.etDescriptionPet);
        accept = findViewById(R.id.btnAcceptDataPet);
        cancel = findViewById(R.id.btnCancelDataPet);

        // Recuperar el ID y tipo de animal pasados de la actividad anterior
        @SuppressLint("UnsafeIntentLaunch") Intent intent = getIntent();
        int typeID = intent.getIntExtra("typeID", -1);
        String typeName = intent.getStringExtra("typeName");

        /*
        // Inicializar el DAO de Pets
        petsDAO = Room.databaseBuilder(this, DatabasePetControl.class,
                "database_petcontrol").build().petsDAO();
        executorService = Executors.newSingleThreadExecutor();
        */

        //--EVENTO IMAGEVIEW
        photo.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setType("image/*");
            startActivityForResult(gallery, PICK_IMAGE_REQUEST);
        });

        //--EVENTO BUTTON
        //-Aceptar
        accept.setOnClickListener(v -> {
            // Se inicializa el objeto DatabaseManagerPetControl
            try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(this)) {
                // Enviar datos a la BD (database_petcontrol)
                // Recoger los datos escritor por el usuario
                String petName = name.getText().toString();
                int petAge = Integer.parseInt(age.getText().toString());
                String petBreed = breed.getText().toString();
                String petSex = male.isChecked() ? "Macho" : "Hembra";
                Bitmap petPic = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                //byte[] petPicByte = dbManager.getBitmapAsByteArray(petPicBitmap);
                boolean isSterilization = sterilization.isChecked();
                int petSterilization;
                if (isSterilization)
                    petSterilization = 1;
                else
                    petSterilization = 0;
                String petDescription = description.getText().toString();

                // Abrir en modo escritura la base de datos
                dbManager.open();
                // Añadir al animal a la base de datos
                dbManager.insertPets(typeID, petName, petAge, petBreed, petSex, petPic,
                        petSterilization, petDescription);
            } catch (SQLException e) {
                // Manejar errores de la base de datos
                Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
            } catch (Exception e) {
                // Manejar otros tipos de errores
                Log.e("GeneralError", "Ocurrió un error", e);
            }
            // Retroceder la pantalla
            i = new Intent(getApplicationContext(), AddPetPetControl.class);
            startActivity(i);


            /*
            // Crear una nueva instancia de PetsPetControl con los datos del formulario
            PetsPetControl newPet = new PetsPetControl();
            newPet.name_pet = petName;
            newPet.age_pet = petAge;
            newPet.breed = petBreed;
            newPet.sex_pet = petSex;
            newPet.sterilization = petSterilization;
            newPet.description_pet = petDescription;
            */

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

            /*
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
            */

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
            description.setText("");
            // Desmarcar RadioButton y CheckBox
            male.setChecked(false);
            female.setChecked(false);
            sterilization.setChecked(false);

            // Retroceder la pantalla
            i = new Intent(getApplicationContext(), AddPetPetControl.class);
            startActivity(i);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                // Obtén el bitmap de la imagen seleccionada
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Establece el bitmap en tu ImageView
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}