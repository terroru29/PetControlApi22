package net.petcontrol.PetControlApi22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class AdapterPCPetControl extends ArrayAdapter<PCPetControl> {
    private final Context context;
    private final List<PCPetControl> pets;

    public AdapterPCPetControl(@NonNull Context context, @NonNull List<PCPetControl> pets) {
        super(context, R.layout.adapter_pc_petcontrol, pets);

        this.context = context;
        this.pets = pets;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_pc_petcontrol, parent, false);
        }

        PCPetControl pc = pets.get(position);

        ImageView photo = convertView.findViewById(R.id.ivPet);
        TextView name = convertView.findViewById(R.id.txtPetName);
        TextView sex = convertView.findViewById(R.id.txtPetSex);
        TextView age = convertView.findViewById(R.id.txtPetAge);

        photo.setImageBitmap(pc.getPhoto());
        name.setText(Objects.requireNonNull(pc).getName());
        sex.setText(pc.getSex());
        age.setText(String.valueOf(pc.getAge()));

        return convertView;
    }
}