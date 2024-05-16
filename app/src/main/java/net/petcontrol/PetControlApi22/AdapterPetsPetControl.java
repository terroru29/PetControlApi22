package net.petcontrol.PetControlApi22;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Locale;

public class AdapterPetsPetControl extends BaseAdapter {
    private TextToSpeech textInVoice;
    private Context context;
    private int[] images;
    private String[] namesPets;
    private boolean[] isImageChanged;
    private Handler mainHandler;


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

        //-Evento de botón de cada animal
        pets.setOnClickListener(v -> {
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

            // Dirá el nombre de cada animal según su posición del elemento pulsado
            Toast.makeText(context.getApplicationContext(), "Elemento de la posición " +
                    position + " que corresponde al animal " + namesPets[position], Toast.LENGTH_SHORT)
                    .show();
            if (textInVoice != null) {
                // Decir el nombre del animal con voz
                textInVoice.speak("Se ha seleccionado " + namesPets[position],
                        TextToSpeech.QUEUE_FLUSH, null, Integer.toString(position));
            }
        });

        // Establecer el listener de finalización del discurso
        textInVoice.setOnUtteranceCompletedListener(utteranceId -> {
            mainHandler.postDelayed(() -> {
                // Navegar a otra pantalla después de un delay de 1 segundo
                Intent intent = new Intent(context, FormPetsPetControl.class);
                context.startActivity(intent);
            }, 1000); // Delay de 1 segundo
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
}