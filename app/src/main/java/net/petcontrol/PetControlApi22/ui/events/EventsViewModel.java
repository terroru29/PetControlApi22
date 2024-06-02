package net.petcontrol.PetControlApi22.ui.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventsViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is reminders fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}