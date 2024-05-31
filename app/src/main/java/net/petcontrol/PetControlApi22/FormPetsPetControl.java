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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormPetsPetControl extends AppCompatActivity {
    EditText name, age, breed, description;
    ImageView photo;
    CheckBox sterilization;
    RadioButton male, female;
    Button accept, cancel;
    Intent i;
    Bitmap petPic;
    String petSex, message;
    int typeID, petAge;
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
        typeID = intent.getIntExtra("typeID", -1);
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


        //--EVENTO EDITTEXT (descripción)
        // Escucha los cambios de texto en el EditText
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            // Cuando el usuario ha terminado de escribir
            @Override
            public void afterTextChanged(Editable s) {
                // Convierte a cadena lo que el usuario ha escrito en el campo
                String text = s.toString();
                // Convierte el texto a emojis
                String textWithEmojis = convertTextToEmoji(text);

                if (!text.equals(textWithEmojis)) {
                    description.removeTextChangedListener(this); // Evita bucles infinitos
                    description.setText(textWithEmojis);
                    // Mantiene el cursor al final
                    description.setSelection(textWithEmojis.length());
                    // Reanuda la escucha de cambios de texto
                    description.addTextChangedListener(this);
                }
            }
        });

        //--EVENTO BUTTON
        //-Aceptar
        accept.setOnClickListener(v -> {
            // Validar los datos antes de iniciar el hilo de inserción
            String petName = name.getText().toString();

            if (petName.isEmpty()) {
                message = getResources().getString(R.string.name_animal_required);
                // Mostrar un mensaje indicando que se requiere, al menos, el nombre
                showToast(message);
                return; // Detener el proceso si el nombre está vacío
            }

            // Crear un nuevo hilo para realizar la inserción en segundo plano
            new Thread(() -> {
                // Se inicializa el objeto DatabaseManagerPetControl
                try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(FormPetsPetControl.this)) {
                    // Abrir en modo escritura la base de datos
                    dbManager.open();

                    // Recoger los datos escritos por el usuario
                    String petBreed = breed.getText().toString();
                    boolean isSterilization = sterilization.isChecked();
                    String petDescription = description.getText().toString();

                    /* ===VALIDACIONES=== */
                    // Validar el campo de edad
                    try {
                        if (!age.getText().toString().isEmpty()) {
                            petAge = Integer.parseInt(age.getText().toString());
                            //yearsPet = Integer.parseInt(String.valueOf(petAge));
                        }
                    } catch (NumberFormatException e) {
                        // Manejar el caso donde la cadena es vacía o no es un número válido
                        petAge = 16;
                        Log.e("insertPets", "La edad no es válida: " + e.getMessage());
                    }
                    // Validar campo sexo
                    if (male.isChecked())
                        petSex = "Macho";
                    else if (female.isChecked())
                        petSex = "Hembra";
                    else
                        petSex = "";    // Si no hay sexo marcado, el valor será una cadena vacía
                    // Validar el campo esterilización
                    int petSterilization = isSterilization ? 1 : 0;

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
                        // Validar y asignar la imagen del ImageView
                        if (petPic == null) {
                            // Asignar una imagen por defecto
                            int defaultImageResId = getDefaultImageResIdForAnimal(typeID);

                            petPic = BitmapFactory.decodeResource(getResources(), defaultImageResId);
                            Log.d("Imagen por defecto", "La imagen es predeterminada.");
                        }
                    }
                    // Añadir al animal a la base de datos
                    dbManager.insertPets(typeID, petName, petAge, petBreed, petSex, petPic,
                            petSterilization, petDescription);
                    Log.d("Success", "Se han insertado los datos correctamente.");
                    Log.i("Emojis", "Descripción: " + petDescription);

                    // Liberar la memoria asociada al objeto Bitmap
                    if (petPic != null)
                        petPic.recycle();
                    // Informar al hilo de la interfaz de usuario que la inserción se ha completado
                    uiHandler.sendEmptyMessage(INSERTION_COMPLETED);
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
            // Si no se selecciona ninguna imagen, asignar la imagen predeterminada según el tipo de animal
            int defaultImageResId = getDefaultImageResIdForAnimal(typeID);
            // Convertimos el recurso predeterminado a Bitmap y lo asignamos a petPic
            petPic = BitmapFactory.decodeResource(getResources(), defaultImageResId);
            // Si el usuario no selecciona imagen, asignamos una imagen predeterminada
            photo.setImageResource(defaultImageResId);
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
        // Maneja el caso en que drawable es null
        if (drawable == null) {
            // Devuelve un bitmap predeterminado
            Log.e("DrawableErrorNull", "Drawable es null");
            /*
            int defaultImageResource;
            // Asignar una imagen predeterminada según el animal seleccionado
            switch (typeID) {
                case 1:
                    defaultImageResource =  R.drawable.dog_default;
                    Log.d("Imagen perro", "Imagen de perro.");
                    break;
                case 2:
                    defaultImageResource =  R.drawable.cat_default;
                    Log.d("Imagen gato", "Imagen de gato.");
                    break;
                case 3:
                    defaultImageResource =  R.drawable.hamster_default;
                    Log.d("Imagen hámster", "Imagen de hámster.");
                    break;
                case 4:
                    defaultImageResource =  R.drawable.fish_default;
                    Log.d("Imagen pez", "Imagen de pez.");
                    break;
                case 5:
                    defaultImageResource =  R.drawable.mouse_default;
                    Log.d("Imagen ratón", "Imagen de ratón.");
                    break;
                case 6:
                    defaultImageResource =  R.drawable.bird_default;
                    Log.d("Imagen pájaro", "Imagen de pájaro.");
                    break;
                case 7:
                    defaultImageResource =  R.drawable.rabbit_default;
                    Log.d("Imagen conejo", "Imagen de conejo.");
                    break;
                case 8:
                    defaultImageResource =  R.drawable.tortoise_default;
                    Log.d("Imagen tortuga", "Imagen de tortuga.");
                    break;
                case 9:
                    defaultImageResource =  R.drawable.ferret_default;
                    Log.d("Imagen hurón", "Imagen de hurón.");
                    break;
                case 10:
                    defaultImageResource =  R.drawable.pig_default;
                    Log.d("Imagen cerdo", "Imagen de cerdo.");
                    break;
                case 11:
                    defaultImageResource =  R.drawable.tarantula_default;
                    Log.d("Imagen tarántula", "Imagen de tarántula.");
                    break;
                case 12:
                    defaultImageResource =  R.drawable.snake_default;
                    Log.d("Imagen serpiente", "Imagen de serpiente.");
                    break;
                default:
                    // Imagen por defecto para animales no especificados
                    defaultImageResource =  R.drawable.animal_default;
                    Log.d("Imagen por defecto", "No hay animal.");
                    break;
            }
            */
            // Devuelve un bitmap predeterminado según el animal
            return BitmapFactory.decodeResource(getResources(), getDefaultImageResIdForAnimal(typeID));
            // Lanza una excepción para manejarlo en un nivel superior
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
            // Devuelve un bitmap predeterminado basado en el tipo de animal
            Log.d("DrawableSizeDefault", "Bitmap predeterminado.");
            return BitmapFactory.decodeResource(getResources(), getDefaultImageResIdForAnimal(typeID));
            // Lanza una excepción para manejarlo en un nivel superior
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
    // Define un mapa de texto a emoji
    private final Map<String, String> emojiMap = new HashMap<String, String>() {{
        put(":)", "\uD83D\uDE42"); // 😊 Cara sonriente
        put(":(", "\uD83D\uDE41"); // 🙁 Cara triste
        put(";)", "\uD83D\uDE09"); // 😉 Guiño
        put(";(", "\uD83D\uDE22"); // 😢 Cara con lagrimita
    }};
    // Método para reemplazar texto con emojis
    public String convertTextToEmoji(String text) {
        // Expresión regular para encontrar los emoticonos en el texto
        Pattern pattern = Pattern.compile(":\\)|:\\(|;\\)|;\\(");
        Matcher matcher = pattern.matcher(text);

        // Reemplaza cada emoticono encontrado con su correspondiente emoji
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String emoticon = matcher.group();
            String emoji = emojiMap.get(emoticon);

            if (emoji != null)
                matcher.appendReplacement(sb, emoji);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    // Se le asigna a cada animal una imagen predeterminada
    private final Map<Integer, Integer> defaultAnimalImages = new HashMap<Integer, Integer>() {{
        put(1, R.drawable.dog_default); // Perros
        put(2, R.drawable.cat_default); // Gatos
        put(3, R.drawable.hamster_default); // Hámsters
        put(4, R.drawable.fish_default); // Peces
        put(5, R.drawable.mouse_default); // Ratones
        put(6, R.drawable.bird_default); // Pájaros
        put(7, R.drawable.rabbit_default); // Ratones
        put(8, R.drawable.tortoise_default); // Tortugas
        put(9, R.drawable.ferret_default); // Hurones
        put(10, R.drawable.pig_default); // Cerdos
        put(11, R.drawable.tarantula_default); // Tarántulas
        put(12, R.drawable.snake_default); // Serpientes
    }};
    // Selecciona la imagen asignada a ese animal o pone una genérica
    private int getDefaultImageResIdForAnimal(int typeId) {
        Integer defaultImageResId = defaultAnimalImages.get(typeId);

        if (defaultImageResId != null)
            return defaultImageResId;
        else
            // Devuelve una imagen por defecto genérica si el tipo de animal no tiene una específica
            return R.drawable.animal_default;
    }
}