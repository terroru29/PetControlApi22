package net.petcontrol.PetControlApi22.ui.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LocationViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is location fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}