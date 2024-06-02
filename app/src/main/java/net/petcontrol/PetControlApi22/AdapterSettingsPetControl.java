package net.petcontrol.PetControlApi22;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class AdapterSettingsPetControl extends ArrayAdapter<SettingsPetControl> {
    Intent intent = null;


    public AdapterSettingsPetControl (Context context, List<SettingsPetControl> settings) {
        super(context, 0, settings);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsPetControl sPC = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_settings_petcontrol, parent, false);

        // Asociar variables con sus recursos
        ImageView imgSettings = convertView.findViewById(R.id.ivTypeSettings);
        TextView textSettings = convertView.findViewById(R.id.txtTypeSettings);
        ImageView arrows = convertView.findViewById(R.id.ivMoreThan);

        // Modificar según valor almacenado
        imgSettings.setImageResource(Objects.requireNonNull(sPC).imageSettings);
        textSettings.setText(sPC.typeSettings);


        //--EVENTO ITEM LISTVIEW
        convertView.setOnClickListener(v -> {
            // Determinar qué actividad iniciar en función de la posición
            switch (position) {
                case 0:
                    intent = new Intent(getContext(), ThirdPartyDataPetControl.class);
                    break;
                case 1:
                    intent = new Intent(getContext(), AboutAppPetControl.class);
                    break;
                case 2:
                    intent = new Intent(getContext(), UpdateAppPetControl.class);
                    break;
                default:
                    break;
            }
            // Iniciar la actividad si el intent no es null
            if (intent != null)
                getContext().startActivity(intent);
        });
        return convertView;
    }
}
