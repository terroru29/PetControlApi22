package net.petcontrol.PetControlApi22.ui.pc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.AddPetPetControl;
import net.petcontrol.PetControlApi22.DatabaseHelperPetControl;
import net.petcontrol.PetControlApi22.DatabaseManagerPetControl;
import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.databinding.FragmentPcBinding;

public class PCFragment extends Fragment {
        //implements DataLoadListenerPetControl {
    private FragmentPcBinding binding;
    TextView data;
    ImageButton add, del, edit, search;
    ImageView img;
    LinearLayout contain;
    /*
    private DatabasePetControl dbPetControl;
    private PetsDAOPetControl petsDAO;

    private TypePetsDAOPetControl typePetsDAO;
    private OwnerDAOPetControl ownerDAO;
    private VisitsVetDAOPetControl visitsVetDAO;
    */


    /**
     * Asegura que la base de datos se inicialice sólo una vez cuando se crea el fragmento.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Inicializar la base de datos utilizando Room.databaseBuilder()
        dbPetControl = Room.databaseBuilder(requireContext(),
                        DatabasePetControl.class, "database_petcontrol")
                .build();

        // Obtener la instancia de los DAOs
        petsDAO = dbPetControl.petsDAO();


        // Insertar una mascota en un hilo diferente evita bloquear el hilo principal de la UI.

        new Thread(() -> {
            PetsPetControl pets = new PetsPetControl();
            pets.name_pet = "Gaia";
            pets.sex_pet = "Hembra";
            petsDAO.insertPet(pets);

            // Obtener todas las mascotas
            List<PetsPetControl> myPets = petsDAO.getAllPets();

            /**
             * Salir del hilo en segundo plano y volver al hilo principal, evitando problemas de
             * concurrencia y asegurando una experiencia de usuario fluida.
             *
             * Handler y Looper aseguran que las tareas que afectan a la UI se realicen en el hilo
             * principal.
             *
             * .post() garantiza que las operaciones de UI se ejecuten en el hilo principal.
             */
            /*
            new Handler(Looper.getMainLooper()).post(() -> {
                for (PetsPetControl ppc : myPets) {
                    Toast.makeText(requireContext(), "Mascota: " + ppc.name_pet,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }).start();

    }
    */

    @Nullable
    @Override
    @SuppressLint({"SuspiciousIndentation", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        PCViewModel PCViewModel = new ViewModelProvider(this).get(PCViewModel.class);

        binding = FragmentPcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPc;
        PCViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Asociar recursos
        data = root.findViewById(R.id.txtDataUser);
        img = root.findViewById(R.id.imageView);
        add = root.findViewById(R.id.añadir);
        del = root.findViewById(R.id.eliminar);
        edit = root.findViewById(R.id.modificar);
        search = root.findViewById(R.id.consultar);
        contain = root.findViewById(R.id.imageContainer);

        // Asociar listener de clic a cada botón
        add.setOnClickListener(v -> {
            // Acción de añadir datos a la base de datos
            //addDataToDatabase();
            Intent i = new Intent(requireContext(), AddPetPetControl.class);
            startActivity(i);
        });
        del.setOnClickListener(v -> {
            try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(requireContext())){
                // Intentar abrir la base de datos
                dbManager.open();

                // Contar todos los propietarios
                int ownerCount = dbManager.countAllOwners();
                Log.d("OwnerCount", "Total Owners: " + ownerCount);

                // Eliminar todos los propietarios
                // dbManager.deleteAllOwners();
                //Log.d("DeleteAllOwners", "DeleteAllOwners");
                // Eliminar un propietario específico
                //dbManager.deleteOwner(24);
                //Log.d("DeleteOwner", "DeleteOwner");

                // Eliminar todas las mascotas
                //dbManager.deleteAllPets();
                //Log.d("DeleteAllPets", "DeleteAllPets");

            } catch (SQLException e) {
                // Manejar errores de la base de datos
                Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
            } catch (Exception e) {
                // Manejar otros tipos de errores
                Log.e("GeneralError", "Ocurrió un error", e);
            }
        });
        edit.setOnClickListener(v -> {
            // Acción de modificar datos en la base de datos
            // Buscar datos en la base de datos
            try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(requireContext())) {
                dbManager.openRead();

                try (Cursor cursor = dbManager.fetchAllPets()) {
                    if (cursor != null && cursor.moveToFirst()) {
                        // Datos a mostrar
                        StringBuilder petData = new StringBuilder();

                        int idIndex = cursor.getColumnIndex(DatabaseHelperPetControl.COLUMN_PETS_ID);
                        int idTypeIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_ID_TYPE);
                        int nameIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_NAME);
                        int ageIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_AGE);
                        int breedIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_BREED);
                        int sexIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_SEX);
                        int picIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_PIC);
                        int sterilizationIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_STERILIZATION);
                        int descriptionIndex = cursor.getColumnIndex(DatabaseHelperPetControl
                                .COLUMN_PETS_DESCRIPTION);

                        contain.removeAllViews(); // Limpiar el contenedor de imágenes

                        do {
                            int petId = cursor.getInt(idIndex);
                            int petIdType = cursor.getInt(idTypeIndex);
                            String petName = cursor.getString(nameIndex);
                            int petAge = cursor.getInt(ageIndex);
                            String petBreed = cursor.getString(breedIndex);
                            String petSex = cursor.getString(sexIndex);
                            byte[] petPicByteArray = cursor.getBlob(picIndex);
                            Bitmap petPic = dbManager.getBitmapFromByteArray(petPicByteArray);
                            int petSterilization = cursor.getInt(sterilizationIndex);
                            String petDescription = cursor.getString(descriptionIndex);

                            // Agregar los datos de la mascota a la cadena de texto
                            petData.append("ID: ").append(petId).append(", Tipo: ").append(petIdType)
                                    .append(", Nombre: ").append(petName).append(", Edad: ").append(petAge)
                                    .append(", Raza: ").append(petBreed).append(", Sexo: ").append(petSex)
                                    .append(", Esterilización: ").append(petSterilization)
                                    .append(", Descripción: ").append(petDescription).append("\n");
                            //img.setImageBitmap(petPic);
                            // Añadir la imagen a un ImageView dinámicamente
                            ImageView petImageView = new ImageView(requireContext());
                            petImageView.setImageBitmap(petPic);
                            // Añadir el ImageView a un layout existente (por ejemplo, un LinearLayout)
                            contain.addView(petImageView);
                        } while (cursor.moveToNext());
                        // Configura la cadena de datos en el TextView
                        data.setText(petData.toString());
                    } else
                        // Si no hay datos en la tabla de mascotas, muestra un mensaje en el TextView
                        data.setText("No se encontraron datos de mascotas.");
                } catch (Exception e) {
                    e.printStackTrace(); // Manejar la excepción apropiadamente
                }
            } catch (SQLException e) {
                // Manejar errores de la base de datos
                Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
            } catch (Exception e) {
                // Manejar otros tipos de errores
                Log.e("GeneralError", "Ocurrió un error", e);
            }
        });
        search.setOnClickListener(v -> {
            // Buscar datos en la base de datos
            DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(requireContext());
            dbManager.openRead();

            try (Cursor cursor = dbManager.fetchAllOwners()) {
                if (cursor != null && cursor.moveToFirst()) {
                    // Datos a mostrar
                    StringBuilder userData = new StringBuilder();
                    do {
                        @SuppressLint("Range") int ownerId = cursor.getInt(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_ID));
                        @SuppressLint("Range") String ownerName = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_NAME));
                        @SuppressLint("Range") int ownerAge = cursor.getInt(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_AGE));
                        @SuppressLint("Range") String ownerGender = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER));
                        @SuppressLint("Range") byte[] ownerPicByteArray = cursor.getBlob(cursor.getColumnIndex
                                (DatabaseHelperPetControl.COLUMN_OWNERS_PIC));
                        Bitmap ownerPic = dbManager.getBitmapFromByteArray(ownerPicByteArray);
                        @SuppressLint("Range") String ownerBirthday = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY));
                        @SuppressLint("Range") String ownerEmail = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL));
                        @SuppressLint("Range") String ownerPass = cursor.getString(cursor.
                                getColumnIndex(DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD));

                        // Agrega los datos del propietario a la cadena de texto
                        userData.append("ID: ").append(ownerId).append(", Nombre: ").append(ownerName)
                                .append(", Edad: ").append(ownerAge).append(", Género: ").append(ownerGender)
                                .append(", Birthday: ").append(ownerBirthday).append(", Email: ")
                                .append(ownerEmail).append(", Pass: ").append(ownerPass);
                        // Verificar si ownerPic es nulo
                        if (ownerPic != null) {
                            img.setImageBitmap(ownerPic);
                        } else {
                            img.setImageResource(R.drawable.ferret); // Imagen por defecto si ownerPic es nulo
                        }
                    } while (cursor.moveToNext());
                    // Configura la cadena de datos en el TextView
                    data.setText(userData.toString());
                } else
                    // Si no hay datos en la tabla de usuarios, muestra un mensaje en el TextView
                    data.setText("No se encontraron datos de usuarios.");
            } catch (Exception e) {
                e.printStackTrace(); // Manejar la excepción apropiadamente
                data.setText("Error al obtener los datos.");
            } finally {
                dbManager.close();
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // El Toast se mostrará sólo después de que los datos se hayan cargado completamente desde
        // la base de datos.
        getDataFromDataBase();
    }

    // Método para añadir datos a la base de datos
    public void addDataToDatabase() {
        // Aquí puedes iniciar la navegación a la Activity para añadir una nueva mascota
        Intent intent = new Intent(requireContext(), FormPetsPetControl.class);
        startActivity(intent);
    }
    // Método para eliminar datos de la base de datos
    private void deleteDataFromDatabase() {
        // Aquí puedes obtener el ID o cualquier otro identificador de la mascota que deseas eliminar
        // Luego, puedes usar el DAO para eliminar la mascota de la base de datos
        // Ejemplo: petsDAO.deletePetById(id);
    }
    // Método para modificar datos en la base de datos
    private void editDataInDatabase() {
        // Aquí puedes obtener el ID o cualquier otro identificador de la mascota que deseas modificar
        // Luego, puedes usar el DAO para obtener la mascota de la base de datos, realizar las modificaciones necesarias y actualizarla
        // Ejemplo: PetsPetControl petToUpdate = petsDAO.getPetById(id);
        // petToUpdate.setName("Nuevo nombre");
        // petsDAO.updatePet(petToUpdate);
    }
    public void getDataFromDataBase() {
        new Thread(() -> {
            if (petsDAO != null) {
                List<PetsPetControl> myPets = petsDAO.getAllPets();
                StringBuilder message = new StringBuilder("Mascotas almacenadas:\n");

                for (PetsPetControl ppc : myPets) {
                    message.append(ppc.name_pet).append("\n");
                }
                onDataLoaded(message.toString());
            }
        }).start();
    }
    @Override
    public void onDataLoaded(String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
    */
}