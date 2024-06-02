package net.petcontrol.PetControlApi22.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.ThirdPartyDataPetControl;
//import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    //TODO Hacer que el paso de pantalla sea con alpha (difuminado)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Crear el ViewModel
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        // Inflar el layout usando View Binding
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Asociar recursos
        //boton = root.findViewById(R.id.button);
        // Configurar el botón
        final Button boton = binding.button;

        //--EVENTO BOTÓN
        boton.setOnClickListener(v -> {
            // Iniciar la nueva actividad
            Intent i = new Intent(requireContext(), ThirdPartyDataPetControl.class);
            startActivity(i);
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}