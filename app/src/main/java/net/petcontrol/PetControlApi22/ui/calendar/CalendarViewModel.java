package net.petcontrol.PetControlApi22.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is calendar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}