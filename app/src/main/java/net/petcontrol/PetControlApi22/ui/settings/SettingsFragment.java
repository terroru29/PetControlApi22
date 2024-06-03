package net.petcontrol.PetControlApi22.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.AdapterSettingsPetControl;
import net.petcontrol.PetControlApi22.DatabaseHelperPetControl;
import net.petcontrol.PetControlApi22.DatabaseManagerPetControl;
import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.SettingsPetControl;
import net.petcontrol.PetControlApi22.databinding.FragmentSettingsBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    SharedPreferences sharedPreferences;
    String userName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Crear el ViewModel
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        // Inflar el layout usando View Binding
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Obtener preferencias y asignar a una variable
        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("username", "persona");

        // Configurar los elementos
        final ImageView owner = binding.imgUser;
        final TextView name = binding.txtUserName;
        final TextView gender = binding.txtUserGender;
        final TextView age = binding.txtUserAge;
        final ListView settings = binding.lvSettings;

        showDataOfUser(owner, name, gender, age);

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
    private void showDataOfUser(ImageView owner, TextView name, TextView gender, TextView age) {
        try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(requireContext())){
            // Intentar abrir la base de datos
            dbManager.openRead();

            try (Cursor cursor = dbManager.fetchOwnerDetails("PetControl")) {
                Log.d("Nombre", userName);
                if (cursor != null && cursor.moveToFirst()) {
                    // Datos a mostrar
                    //do {
                        @SuppressLint("Range") String ownerName = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_NAME));
                        @SuppressLint("Range") int ownerAge = cursor.getInt(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_AGE));
                        @SuppressLint("Range") String ownerGender = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER));
                        @SuppressLint("Range") byte[] ownerPicByteArray = cursor.getBlob(cursor.getColumnIndex
                                (DatabaseHelperPetControl.COLUMN_OWNERS_PIC));
                        Bitmap ownerPic = dbManager.getBitmapFromByteArray(ownerPicByteArray);

                        name.setText(ownerName);
                        gender.setText(ownerGender);
                        age.setText(String.valueOf(ownerAge));
                        // Verificar si ownerPic es nulo
                        if (ownerPic != null)
                            owner.setImageBitmap(ownerPic);
                        else
                            owner.setImageResource(R.drawable.person); // Imagen por defecto si ownerPic es nulo
                    //} while (cursor.moveToNext());
                } else
                    // Si no hay datos en la tabla de usuarios, muestra un mensaje en el TextView
                    Log.e("No data found User", "No se encontraron datos de usuarios.");
            } catch (Exception e) {
                e.printStackTrace(); // Manejar la excepción apropiadamente
                Log.e("OwnerDetails", "Error al obtener los datos.");
            }
        } catch (SQLException e) {
            // Manejar errores de la base de datos
            Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
        } catch (Exception e) {
            // Manejar otros tipos de errores
            Log.e("GeneralError", "Ocurrió un error", e);
        }
    }
}