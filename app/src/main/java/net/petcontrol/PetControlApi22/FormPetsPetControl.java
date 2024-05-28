package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import java.util.Objects;
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
    Bitmap petPic;
    String petSex, message;
    int petAge, yearsPet;
    // Define un código de solicitud para identificar la respuesta de la galería
    private static final int PICK_IMAGE_REQUEST = 1;
    // Constantes para identificar los mensajes
    private static final int INSERTION_COMPLETED = 1;
    private static final int INSERTION_ERROR = 2;
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
        //String typeName = intent.getStringExtra("typeName");

        /*
        // Inicializar el DAO de Pets
        petsDAO = Room.databaseBuilder(this, DatabasePetControl.class,
                "database_petcontrol").build().petsDAO();
        executorService = Executors.newSingleThreadExecutor();
        */

        //--EVENTO IMAGEVIEW
        photo.setOnClickListener(v -> {
            /*Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setType("image/*");
            startActivityForResult(gallery, PICK_IMAGE_REQUEST);*/
            // Crear un Intent para abrir el explorador de archivos
            Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            // Establecer el tipo de archivo que se puede seleccionar (todos los tipos de archivos)
            gallery.setType("image/*");
            // Iniciar la actividad para seleccionar un archivo
            startActivityForResult(gallery, PICK_IMAGE_REQUEST);
        });

        //--EVENTO BUTTON
        //-Aceptar
        accept.setOnClickListener(v -> {
            // Crear un nuevo hilo para realizar la inserción en segundo plano
            new Thread(() -> {
                // Se inicializa el objeto DatabaseManagerPetControl
                try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(FormPetsPetControl.this)) {
                    // Abrir en modo escritura la base de datos
                    dbManager.open();

                    // Recoger los datos escritos por el usuario
                    String petName = name.getText().toString();
                    //int petAge = Integer.parseInt(age.getText().toString());
                    String petBreed = breed.getText().toString();
                    //String petSex = male.isChecked() ? "Macho" : "Hembra";
                    //Bitmap petPic = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                    boolean isSterilization = sterilization.isChecked();
                    String petDescription = description.getText().toString();

                    /* ===VALIDACIONES=== */
                    // Validar el campo de edad
                    try {
                        if (!age.getText().toString().isEmpty())
                            petAge = Integer.parseInt(age.getText().toString());
                        //if (petAge > 0)
                            //yearsPet = Integer.parseInt(String.valueOf(petAge));
                    } catch (NumberFormatException e) {
                        // Manejar el caso donde la cadena es vacía o no es un número válido
                        yearsPet = 0;
                        Log.e("insertPets", "La edad no es válida: " + e.getMessage());
                    }
                    // Validar campo sexo
                    if (male.isChecked())
                        petSex = "Macho";
                    else if (female.isChecked())
                        petSex = "Hembra";
                    else
                        // Si no se ha seleccionado ningún sexo, establece el valor como vacío
                        petSex = "";
                    // Validar el campo esterilización
                    int petSterilization;
                    if (isSterilization)
                        petSterilization = 1;
                    else
                        petSterilization = 0;
                    /*
                    // Valida la imagen del ImageView
                    if (photo.getDrawable() != null) {
                        //petPic = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                        if (petPic != null) {
                            // Validar que se haya proporcionado al menos el nombre
                            if (!petName.isEmpty()) {
                                // Añadir al animal a la base de datos
                                dbManager.insertPets(typeID, petName, yearsPet, petBreed, petSex, petPic,
                                        petSterilization, petDescription);
                                Toast.makeText(this, "Se han insertado los datos correctamente.",
                                        Toast.LENGTH_SHORT).show();
                                Log.i("Success", "Se han insertado los datos correctamente.");
                                // Liberar la memoria asociada al objeto Bitmap
                                petPic.recycle();
                                // Informar al hilo de la interfaz de usuario que la inserción se ha completado
                                uiHandler.sendEmptyMessage(INSERTION_COMPLETED);
                            } else {
                                // Mostrar un mensaje indicando que se requiere al menos el nombre
                                Toast.makeText(this, "Por favor, ingrese el nombre del animal.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    */

                    Drawable drawable = photo.getDrawable();
                    if (drawable instanceof BitmapDrawable) {
                        // Si ya es un BitmapDrawable, simplemente obtenemos el Bitmap
                        petPic = ((BitmapDrawable) drawable).getBitmap();
                    } else {
                        // Manejar el caso en el que no es un BitmapDrawable
                        Log.e("DrawableError", "La imagen seleccionada no es un " +
                                "BitmapDrawable.");
                        // Si no es un BitmapDrawable, lo convertimos a Bitmap
                        petPic = convertToBitmap(drawable);
                        Log.d("convertToBitmap", "La imagen seleccionada ya es un " +
                                "BitmapDrawable.");
                        // Asignar una imagen por defecto
                        petPic = BitmapFactory.decodeResource(getResources(), R.drawable.pig);
                        Log.d("Imagen por defecto", "La imagen es predeterminada.");
                    }
                    // Añadir al animal a la base de datos
                    dbManager.insertPets(typeID, petName, yearsPet, petBreed, petSex, petPic,
                            petSterilization, petDescription);
                    //Toast.makeText(this, "Se han insertado los datos correctamente.",
                            //Toast.LENGTH_SHORT).show();
                    message = getResources().getString(R.string.correct_data);
                    showToast(message);
                    Log.i("Success", "Se han insertado los datos correctamente.");
                    // Liberar la memoria asociada al objeto Bitmap
                    petPic.recycle();
                } catch (SQLException e) {
                    // Manejar errores de la base de datos
                    Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
                    uiHandler.sendEmptyMessage(INSERTION_ERROR);
                } catch (Exception ex) {
                    // Manejar otros tipos de errores
                    Log.e("GeneralError", "Ocurrió un error", ex);
                    // Informar al hilo de la interfaz de usuario que se ha producido un error
                    uiHandler.sendEmptyMessage(INSERTION_ERROR);
                }
            }).start();

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
        /*
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
        } else {
            // Si el usuario no selecciona ninguna imagen, establece una imagen predeterminada
            Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pig);
            photo.setImageBitmap(defaultBitmap);
        }
        */
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            // Obtenemos la URI del archivo seleccionado
            Uri selectedFileUri = data.getData();
            // Obtiene el nombre de la imagen seleccionada
            //String namePicture = getFileName(selectedFileUri);

            // Obtenemos la imagen seleccionada por el usuario
            //photo.setImageURI(selectedFileUri);
            // Convertimos la URI a Bitmap y lo asignamos a petPic
            try {
                petPic = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(Objects.requireNonNull(selectedFileUri)));
                photo.setImageDrawable(new BitmapDrawable(getResources(), petPic));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Si el usuario no selecciona una imagen, asignamos una imagen predeterminada
            photo.setImageResource(R.drawable.pig);
            // Convertimos el recurso predeterminado a Bitmap y lo asignamos a petPic
            petPic = BitmapFactory.decodeResource(getResources(), R.drawable.pig);
        }
    }
    // Handler para manejar mensajes del hilo de fondo y actualizar la UI
    private final Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INSERTION_COMPLETED:
                    // Actualizar la interfaz de usuario si la inserción se ha completado correctamente
                    Toast.makeText(FormPetsPetControl.this, "Se han insertado los datos " +
                            "correctamente.", Toast.LENGTH_SHORT).show();
                    break;
                case INSERTION_ERROR:
                    // Informar al usuario si se ha producido un error durante la inserción
                    Toast.makeText(FormPetsPetControl.this, "Error al insertar los datos.",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private Bitmap convertToBitmap(Drawable drawable) {
        if (drawable == null) {
            // Maneja el caso en que drawable es null, devolviendo un bitmap predeterminado o lanzando una excepción
            Log.e("DrawableErrorNull", "Drawable es null");
            // O devolver un bitmap predeterminado
            return BitmapFactory.decodeResource(getResources(), R.drawable.pig);
            // O puedes lanzar una excepción si quieres manejar esto en un nivel superior
            // throw new IllegalArgumentException("Drawable no puede ser null");
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        // Verifica que el ancho y alto sean mayores que 0
        if (width <= 0 || height <= 0) {
            Log.e("DrawableErrorSize", "Drawable tiene dimensiones no válidas: width=" +
                    width + ", height=" + height);
            // Devuelve un bitmap predeterminado o lanza una excepción
            Log.d("DrawableSizeDefault", "Bitmap predeterminado.");
            return BitmapFactory.decodeResource(getResources(), R.drawable.pig);
            // O puedes lanzar una excepción si quieres manejar esto en un nivel superior
            // throw new IllegalArgumentException("Drawable tiene dimensiones no válidas: width=" +
            // width + ", height=" + height);
        }
        // Especifica el tamaño del bitmap
        //Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private void showToast(String message) {
        // Verifica si estamos en el hilo principal
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // Estamos en el hilo principal, muestra el Toast directamente
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            // No estamos en el hilo principal, así que usa runOnUiThread para mostrar el Toast
            runOnUiThread(() -> Toast.makeText(FormPetsPetControl.this, message,
                    Toast.LENGTH_SHORT).show());
        }
    }
}