package net.petcontrol.PetControlApi22.ui.pc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.AdapterPCPetControl;
import net.petcontrol.PetControlApi22.AddPetPetControl;
import net.petcontrol.PetControlApi22.DatabaseHelperPetControl;
import net.petcontrol.PetControlApi22.DatabaseManagerPetControl;
import net.petcontrol.PetControlApi22.FormPetsPetControl;
import net.petcontrol.PetControlApi22.PCPetControl;
import net.petcontrol.PetControlApi22.R;
import net.petcontrol.PetControlApi22.databinding.FragmentPcBinding;

import java.util.ArrayList;
import java.util.List;

public class PCFragment extends Fragment {
        //implements DataLoadListenerPetControl {
    private FragmentPcBinding binding;
    TextView data;
    ImageButton add, del, edit, search;
    ListView pets;
    LinearLayout contain;
    private List<PCPetControl> listPets;
    private AdapterPCPetControl adapterPC;
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
        pets = root.findViewById(R.id.lvPets);
        data = root.findViewById(R.id.txtDataUser);
        add = root.findViewById(R.id.añadir);
        del = root.findViewById(R.id.eliminar);
        edit = root.findViewById(R.id.modificar);
        search = root.findViewById(R.id.consultar);
        contain = root.findViewById(R.id.imageContainer);


        // Se obtiene la lista de mascotas de la base de datos
        List<PCPetControl> petsList = getListOfPets();
        // Se inicializa el adaptador con la lista de mascotas
        adapterPC = new AdapterPCPetControl(requireContext(), petsList);
        // Asigna el adaptador al ListView
        pets.setAdapter(adapterPC);

        // Notificamos que se ha producido un cambio y tiene que actualizarse
        adapterPC.notifyDataSetChanged();


        //--EVENTO LISTVIEW
        pets.setOnItemClickListener((parent, view, position, id) -> {
            PCPetControl selectedAnimal = listPets.get(position);

            //TODO AnimalProfilePetControl
            Intent intent = new Intent(getContext(), FormPetsPetControl.class);
            intent.putExtra("animalName", selectedAnimal.getName());
            startActivity(intent);
        });

//-------------------------------------------------------------------------------------------------

        // Asociar listener de clic a cada botón
        add.setOnClickListener(v -> {
            // Acción de añadir datos a la base de datos
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
                //dbManager.deleteAllOwners();
                //Log.d("DeleteAllOwners", "DeleteAllOwners");
                // Eliminar un propietario específico
                //dbManager.deleteOwner(30);
                //Log.d("DeleteOwner", "DeleteOwner");

                // Eliminar todas las mascotas
                //dbManager.deleteAllPets();
                //Log.d("DeleteAllPets", "DeleteAllPets");
                // Eliminar una mascota
//                dbManager.deletePet(35);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(36);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(37);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(38);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(39);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(40);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(41);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(42);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(43);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(44);
//                Log.d("DeletePet", "DeletePet");
//                dbManager.deletePet(45);
//                Log.d("DeletePet", "DeletePet");

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
                        /*@SuppressLint("Range") byte[] ownerPicByteArray = cursor.getBlob(cursor.getColumnIndex
                                (DatabaseHelperPetControl.COLUMN_OWNERS_PIC));
                        Bitmap ownerPic = dbManager.getBitmapFromByteArray(ownerPicByteArray);*/
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

//--------------------------------------------------------------------------------------------------
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

// ------------------------------------------------------------------------------------------------
    /*
    // Carga los datos de la base de datos y actualiza el adaptador
    private void loadAnimals() {
        listPets = new ArrayList<>();
        // Abrir la BD en modo lectura
        db.openRead();

        // Obtener la información de la base de datos
        try (Cursor cursor = db.fetchDataPets()) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String petName = cursor
                        .getString(cursor.getColumnIndex(DatabaseHelperPetControl.COLUMN_PETS_NAME));
                    @SuppressLint("Range") int petAge = cursor
                        .getInt(cursor.getColumnIndex(DatabaseHelperPetControl.COLUMN_PETS_AGE));
                    @SuppressLint("Range") String petSex = cursor
                        .getString(cursor.getColumnIndex(DatabaseHelperPetControl.COLUMN_PETS_SEX));
                    @SuppressLint("Range") byte[] petPicByteArray = cursor
                        .getBlob(cursor.getColumnIndex(DatabaseHelperPetControl.COLUMN_PETS_PIC));
                    Bitmap petPic = db.getBitmapFromByteArray(petPicByteArray);

                    // Creamos objeto --> Animal
                    PCPetControl pet = new PCPetControl(petPic, petName, petSex, petAge);
                    // Añado a la lista al animal con sus datos
                    listPets.add(pet);
                } while (cursor.moveToNext());
            } else {
                Log.e("No data found Pets", "No se encontraron datos de la mascota.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseError", "Error al obtener los datos.");
        }

        // Notificamos que se ha producido un cambio
        adapterPC.notifyDataSetChanged();
    }
    */
    private List<PCPetControl> getListOfPets() {
        listPets = new ArrayList<>();

        try (DatabaseManagerPetControl dbManager = new DatabaseManagerPetControl(requireContext())) {
            // Abrir en modo lectura
            dbManager.openRead();

            try (Cursor cursor = dbManager.fetchDataPets()) {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor
                                .getColumnIndexOrThrow(DatabaseHelperPetControl.COLUMN_PETS_NAME));
                        int age = cursor.getInt(cursor
                                .getColumnIndexOrThrow(DatabaseHelperPetControl.COLUMN_PETS_AGE));
                        String sex = cursor.getString(cursor
                                .getColumnIndexOrThrow(DatabaseHelperPetControl.COLUMN_PETS_SEX));
                        byte[] pic = cursor.getBlob(cursor
                                .getColumnIndexOrThrow(DatabaseHelperPetControl.COLUMN_PETS_PIC));
                        Bitmap photo = getBitmapFromByteArray(pic);

                        listPets.add(new PCPetControl(photo, name, sex, age));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DatabaseError", "Error al obtener los datos.");
            }
        } catch (SQLException e) {
            // Manejar errores de la base de datos
            Log.e("DatabaseError", "Error al interactuar con la base de datos", e);
        } catch (Exception ex) {
            // Manejar otros tipos de errores
            Log.e("GeneralError", "Ocurrió un error", ex);
        }
        return listPets;
    }
    // Convertir Byte Array a Bitmap
    public Bitmap getBitmapFromByteArray(byte[] blob) {
        if (blob == null || blob.length == 0) {
            // Si el parámetro es null o está vacío
            Log.e("DatabaseError", "ByteArray es null o está vacío");
            return null;
        }
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }
}