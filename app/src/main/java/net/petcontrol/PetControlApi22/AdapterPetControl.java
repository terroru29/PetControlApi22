package net.petcontrol.PetControlApi22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class AdapterPetControl extends ArrayAdapter<MascotasPetControl> {
    //Constructor
    public AdapterPetControl(@NonNull Context context, MascotasPetControl[] mascotas) {
        super(context, 0, mascotas);
    }

    public AdapterPetControl(@NonNull Context context, List<MascotasPetControl> listaMascotas) {
        super(context, 0, listaMascotas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Guardamos en un objeto Libros los ítems
        MascotasPetControl mpc = getItem(position);

        //Creamos una vista personalizada
        View v = LayoutInflater.from(getContext()).
                inflate(R.layout.activity_muestra_text_view_petcontrol, parent, false);

        //Asociamos la variable con su recurso y modificamos la etiqueta con el dato obtenido
        TextView tv1 = (TextView) v.findViewById(R.id.tvMuestraCodigo);
        tv1.setText("Código: " + mpc.getCodigo());
        TextView tv2 = (TextView) v.findViewById(R.id.tvMuestraNombre);
        tv2.setText("\nNombre: " + mpc.getNombre());
        TextView tv3 = (TextView) v.findViewById(R.id.tvMuestraDueño);
        tv3.setText("\n\n\nDueño: " + mpc.getDueño());

        //Devolvemos la vista
        return v;
    }
}