package net.petcontrol.PetControlApi22.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.AdapterSettingsPetControl;
import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.SettingsPetControl;
import net.petcontrol.PetControlApi22.databinding.FragmentSettingsBinding;

import java.util.ArrayList;
import java.util.List;

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

        // Configurar los elementos
        final ListView settings = binding.lvSettings;

        // Asociar cadenas
        String third = getResources().getString(R.string.third_party_data);
        String about = getResources().getString(R.string.about_app);
        String update = getResources().getString(R.string.update_app);

        // Crear la lista de elementos
        List<SettingsPetControl> listOfSettings = new ArrayList<>();
        listOfSettings.add(new SettingsPetControl(R.drawable.third_party_data, third,
                R.drawable.more_than));
        listOfSettings.add(new SettingsPetControl(R.drawable.info_app, about,
                R.drawable.more_than));
        listOfSettings.add(new SettingsPetControl(R.drawable.arrow_update, update,
                R.drawable.more_than));

        // Crear el adaptador y configurarlo en el ListView
        AdapterSettingsPetControl adapter = new AdapterSettingsPetControl(requireContext(),
                listOfSettings);
        settings.setAdapter(adapter);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}