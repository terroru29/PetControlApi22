package net.petcontrol.PetControlApi22.ui.pc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.DatabaseClientPetControl;
import net.petcontrol.PetControlApi22.OwnerDAOPetControl;
import net.petcontrol.PetControlApi22.PetsDAOPetControl;
import net.petcontrol.PetControlApi22.PetsPetControl;
import net.petcontrol.PetControlApi22.TypePetsDAOPetControl;
import net.petcontrol.PetControlApi22.VisitsVetDAOPetControl;
import net.petcontrol.PetControlApi22.databinding.FragmentPcBinding;

import java.util.List;

public class PCFragment extends Fragment {
    private FragmentPcBinding binding;
    private TypePetsDAOPetControl typePetsDAO;
    private PetsDAOPetControl petsDAO;
    private OwnerDAOPetControl ownerDAO;
    private VisitsVetDAOPetControl visitsVetDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtener la instancia de la base de datos
        petsDAO = DatabaseClientPetControl.getInstance(getContext().getApplicationContext()).petsDAO();

        // Insertar una mascota
        PetsPetControl pets = new PetsPetControl();
        pets.name_pet = "Gaia";
        pets.sex_pet = "Hembra";
        // Realizar la inserción en un hilo diferente
        new Thread(() -> {
            petsDAO.insertPet(pets);
        }).start();

        // Obtener todas ls mascotas
        new Thread(() -> {
            List<PetsPetControl> myPets = petsDAO.getAllPets();
            // Procesar datos...
        }).start();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PCViewModel PCViewModel =
                new ViewModelProvider(this).get(PCViewModel.class);

        binding = FragmentPcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPc;
        PCViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}