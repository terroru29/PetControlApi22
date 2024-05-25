package net.petcontrol.PetControlApi22;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class AdapterPetsPetControl extends BaseAdapter {
    private TextToSpeech textInVoice;
    private Context context;
    private int[] images;
    private String[] namesPets;
    private boolean[] isImageChanged;
    private Handler mainHandler;
    DatabaseManagerPetControl dbPetControl;
    private List<TypePetsPetControl> typePets;  // Lista para almacenar los tipos de mascotas


    /**
     * Constructor para AdapterPetsPetControl.
     *
     * @param context Contexto en el que se utiliza el adaptador (actividad que lo crea)
     * @param images Array de ID de recursos de imágenes (las diferentes mascotas) que se mostrarán
     *               en el adaptador
     *
     * Este constructor inicializa el adaptador con el contexto y las imágenes proporcionados.
     * También configura la funcionalidad de texto a voz para dictar los nombres de las mascotas e
     * inicializa el controlador principal para las operaciones de la interfaz de usuario.
     *
     * Los nombres de las mascotas se recuperan del recurso de array de String y se asocian con el
     * adaptador.
     * Se inicializa una array booleana para realizar un seguimiento del estado de cada ImageButton
     * en el adaptador.
     *
     * La conversión de texto a voz (TTS) se inicializa para proporcionar retroalimentación auditiva
     * cuando se selecciona una mascota. El motor TTS está configurado con la configuración regional
     * predeterminada y se implementa el manejo de errores para registrar problemas si el idioma
     * seleccionado no es compatible.
     */
    public AdapterPetsPetControl(@NonNull Context context, int[] images) {
        this.context = context;
        this.images = images;

        // Asociamos los nombres de los animales al array indicado cuando se inicializa el adaptador
        namesPets = context.getResources().getStringArray(R.array.pets_names);

        // Inicializar el array booleano para mantener el estado de cada ImageButton
        this.isImageChanged = new boolean[images.length];

        // Inicializar el Handler para el hilo principal
        mainHandler = new Handler(Looper.getMainLooper());

        // Inicializar administrador base de datos
        this.dbPetControl = new DatabaseManagerPetControl(context);
        dbPetControl.open();
        // Inicializar la lista de tipos de mascotas
        this.typePets = dbPetControl.fetchAllTypesPets();
        // Cerrar la base de datos después de obtener los datos
        dbPetControl.close();

        // Inicializar la dictado del texto con voz
        textInVoice = new TextToSpeech(context, status -> {
            // Verificación del estado
            if (status == TextToSpeech.SUCCESS) {
                // Configuración del idioma
                int result = textInVoice.setLanguage(Locale.getDefault());

                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    Log.e("TTS", "Idioma no soportado.");
            } else
                Log.e("TTS", "Inicialización fallida.");
        });
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return images.length;
    }
    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return images[position];
    }
    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Si es nulo se podrá reutilizar vistas y evitar problemas de memoria, mejorando el rendimiento
        if (convertView == null) {
            // Inflamos el diseño para cada elemento.
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_adapter_pets_petcontrol,
                    parent, false);
        }
        // Asociar al recurso su variable, aplicándole un estilo
        ImageButton pets = convertView.findViewById(R.id.imbPets);
        TextView names = convertView.findViewById(R.id.txtNamesPets);

        // Establecer la imagen según el estado actual
        if (isImageChanged[position]) {
            // Alternativo
            pets.setBackgroundResource(R.drawable.button_names_pets);
            // Mostramos el texto
            names.setVisibility(View.VISIBLE);
            names.setText(namesPets[position]);
            // Ocultamos la imagen
            pets.setImageResource(0);
        } else {
            // Principal
            pets.setBackgroundResource(R.drawable.circle_button);
            pets.setImageResource(images[position]);
            names.setVisibility(View.GONE);
        }

        /*
        // Asociar el tipo de mascota con el ImageButton usando la lista typePets
        if (position < typePets.size()) {
            TypePetsPetControl typePet = typePets.get(position);
            pets.setTag(typePet.getId_type_pet());  // Asignar el ID como tag del ImageButton
            //names.setText(typePet.getType_pet());   // Opcional: asignar el tipo como texto
            //Toast.makeText(context.getApplicationContext(), "ID: " + typePet.getId_type_pet() +
                 //   "\nType: " + typePet.getType_pet(), Toast.LENGTH_SHORT).show();
            Log.i("ID-Type", "ID: " + typePet.getId_type_pet() + "\nType: " + typePet.getType_pet());
        }
        */

        // Obtener el ID y tipo de la mascota desde la base de datos
        TypePetsPetControl typePet = typePets.get(position);

        int petId = typePet.getId_type_pet();
        String petType = typePet.getType_pet();

        // Asociar el ID y tipo al ImageButton usando tags
        pets.setTag(R.id.pet_id, petId);
        pets.setTag(R.id.pet_type, petType);


        //-Evento de botón de cada animal
        pets.setOnClickListener(v -> {
            // Obtener el ID y tipo del tag
            /*int id = (int) v.getTag(R.id.pet_id);
            String type = (String) v.getTag(R.id.pet_type);
            Log.i("ID-Type (v)", "ID: " + id + "\nType: " + type);*/

            // Rotación horizontal (tridimensional)
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(pets,
                    "rotationY", 0f, 100f).setDuration(1000);
            ObjectAnimator rotationNames = ObjectAnimator.ofFloat(names,
                    "rotationY", 0f, 100f).setDuration(1000);
            // Iniciar la animación
            rotationAnimator.start();
            rotationNames.start();

            pets.postDelayed(() -> {
                if (isImageChanged[position]) {
                    // Principal
                    pets.setBackgroundResource(R.drawable.circle_button);
                    // Vuelve a la imagen original
                    pets.setImageResource(images[position]);
                    // Ocultamos el texto
                    names.setVisibility(View.GONE);
                } else {
                    // Alternativa
                    pets.setBackgroundResource(R.drawable.button_names_pets);
                    // Muestra el texto
                    names.setVisibility(View.VISIBLE);
                    names.setText(namesPets[position]);
                    // Ocultamos la imagen
                    pets.setImageResource(0);
                }

                // Seguir el giro hasta colocar la imagen normal
                ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(pets,
                        "rotationY", 100f, 360f).setDuration(1500);
                ObjectAnimator rotationNames1 = ObjectAnimator.ofFloat(names,
                        "rotationY", 100f, 360f).setDuration(1500);
                rotationAnimator1.start();
                rotationNames1.start();

                // Cambiar el estado en cada click
                isImageChanged[position] = !isImageChanged[position];
            }, 1100); // Tiempo de retraso que espera que el giro complete antes de cambiar la imagen

            // Mostrará el nombre de cada animal según su posición del elemento pulsado
            Log.d("Position", "Elemento de la posición " + position + " que corresponde al " +
                            "animal " + namesPets[position]);
            if (textInVoice != null) {
                // Decir el nombre del animal con voz
                textInVoice.speak("Se ha seleccionado " + namesPets[position],
                        TextToSpeech.QUEUE_FLUSH, null, Integer.toString(position));
            }
        });


        // Capturar el ID y el tipo de animal seleccionado
        //int typeID = (int) pets.getTag(R.id.pet_id);
        int typeID = (int) typePets.get(position).getId_type_pet();
        String typeName = typePets.get(position).getType_pet();
        Log.i("ID-Type", "ID: " + typeID + "\nType: " + typeName);


        // Establecer el listener de finalización del discurso
        textInVoice.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {}
            @Override
            public void onDone(String utteranceId) {
                mainHandler.postDelayed(() -> {
                    // Pasar la información a la siguiente pantalla
                    Intent intent = new Intent(context, FormPetsPetControl.class);

                    intent.putExtra("typeID", typeID);
                    intent.putExtra("typeName", typeName);
                    context.startActivity(intent);
                }, 1000);
            }
            @Override
            public void onError(String utteranceId) {}
        });
        return convertView;
    }
    /**
     * Libera recursos cuando el adaptador ya no se use y se va a destruir para limpiar
     * TextToSpeech y evitar fugas de memoria.
     */
    public void shutdownTextToSpeech () {
        if (textInVoice != null) {
            // Detiene cualquier discurso en curso asegurando que si hay alguna voz hablando,
            // se detenga de inmediato
            textInVoice.stop();
            // Apaga el servicio de texto a voz liberando cualquier recurso asociado y evitando las
            // fugas de memoria
            textInVoice.shutdown();
        }
    }
    public void verifyData() {
        for (int i = 0; i < getCount(); i++) {
            TypePetsPetControl typePet = typePets.get(i);

            int expectedId = typePet.getId_type_pet();
            String expectedType = typePet.getType_pet();

            View view = getView(i, null, null);
            ImageButton pets = view.findViewById(R.id.imbPets);

            int actualId = (int) pets.getTag(R.id.pet_id);
            String actualType = (String) pets.getTag(R.id.pet_type);

            if (expectedId != actualId || !expectedType.equals(actualType)) {
                Log.e("Verification Error", "Error en la posición " + i + ": se esperaba " +
                        "ID " + expectedId + " y tipo " + expectedType + ", pero se obtuvo ID " +
                        actualId + " y tipo " + actualType);
            } else {
                Log.i("Verification Success", "Correcto en la posición " + i + ": ID " +
                        actualId + " y tipo " + actualType);
            }
        }
    }
}