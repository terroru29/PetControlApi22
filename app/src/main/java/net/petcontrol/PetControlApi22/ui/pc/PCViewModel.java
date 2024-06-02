package net.petcontrol.PetControlApi22.ui.pc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PCViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PCViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is PC fragment");
    }
    //Actualiza la interfaz de usuario (IU) de manera reactiva en un Fragment
    public LiveData<String> getText() {
        return mText;
    }
    /*
    private final MutableLiveData<String> mText;
    private final LiveData<List<PetsPetControl>> allPets;
    private final ExecutorService executorService;
    private final PetsDAOPetControl petsDAO;

    public PCViewModel(@NonNull Application application) {
        super((Closeable) application);

        mText = new MutableLiveData<>();
        //mText.setValue("Este es el fragmento de PC");
        //executorService = Executors.newSingleThreadExecutor();
        DatabasePetControl dbPetControl = Room.databaseBuilder(application,
                DatabasePetControl.class, "database_petcontrol").build();
        petsDAO = dbPetControl.petsDAO();
        executorService = Executors.newSingleThreadExecutor();

        allPets = petsDAO.getAllPetsLiveData();
    }
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<PetsPetControl>> getAllPets() {
        return allPets;
    }
    */
}