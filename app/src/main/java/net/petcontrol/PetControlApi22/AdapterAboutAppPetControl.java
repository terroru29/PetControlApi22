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

public class AdapterAboutAppPetControl extends ArrayAdapter<AboutTheAppPetControl> {
    Intent intent = null;


    public AdapterAboutAppPetControl (Context context, List<AboutTheAppPetControl> infor) {
        super(context, 0, infor);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        AboutTheAppPetControl atpPC = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_about_app_petcontrol, parent, false);

        // Asociar variables con sus recursos
        TextView textInformation = convertView.findViewById(R.id.txtTypeInformation);
        ImageView arrows = convertView.findViewById(R.id.ivForward);

        // Modificar según valor almacenado
        textInformation.setText(atpPC.information);


        //--EVENTO ITEM LISTVIEW
        convertView.setOnClickListener(v -> {
            // Determinar qué actividad iniciar en función de la posición
            switch (position) {
                case 0:
                    intent = new Intent(getContext(), UserAgreementPetControl.class);
                    break;
                case 1:
                    intent = new Intent(getContext(), PrivacyPolicyPetControl.class);
                    break;
                case 2:
                    intent = new Intent(getContext(), UserExperienceProgramPetControl.class);
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