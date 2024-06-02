package net.petcontrol.PetControlApi22.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is settings fragment");
    }
    //Actualiza la interfaz de usuario (IU) de manera reactiva en un Fragment
    public LiveData<String> getText() {
        return mText;
    }
}