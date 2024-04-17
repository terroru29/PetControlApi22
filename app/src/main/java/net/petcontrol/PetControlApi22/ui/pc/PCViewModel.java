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

    public LiveData<String> getText() {
        return mText;
    }
}