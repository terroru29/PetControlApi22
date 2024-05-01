package net.petcontrol.PetControlApi22;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class AdapterPetsPetControl extends BaseAdapter {
    private Context context;
    private int[] images;
    private int[] names;
    private String[] namesPets;
    String petsNames;

    // Constructor
    public AdapterPetsPetControl(@NonNull Context context, int[] images, int[] names) {
        this.context = context;
        this.images = images;
        this.names = names;

        // Asociamos los nombres de los animales al array indicado cuando se inicializa el adaptador
        namesPets = context.getResources().getStringArray(R.array.pets_names);
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_adapter_pets_petcontrol, parent, false);
        }
        ImageButton pets = convertView.findViewById(R.id.imbPets);
        // Usamos un array de imágenes para cada botón
        pets.setBackgroundResource(images[position]);

        //-Evento de botón de cada animal
        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rotación horizontal (tridimensional)
                ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(pets,
                        "rotationY", 0f, 100f).setDuration(1000);
                // Iniciar la animación
                rotationAnimator.start();
                pets.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Cambiar la imagen después del giro
                        pets.setBackgroundResource(names[position]);
                        // Seguir el giro hasta colocar la imagen normal
                        ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(pets,
                                "rotationY", 100f, 360f).setDuration(1500);
                        rotationAnimator1.start();
                    }
                }, 1100); // Tiempo de retraso que espera el giro complete antes de cambiar la imagen

                // Almacenanos en una variable el nombre de cada animal según su posición
                petsNames = namesPets[position];
                // Position nos indicará cuál fue el elemento clicado
                Toast.makeText(context.getApplicationContext(), "Elemento de la posición " +
                        position + " que corresponde al animal " + petsNames, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        return convertView;
    }
}