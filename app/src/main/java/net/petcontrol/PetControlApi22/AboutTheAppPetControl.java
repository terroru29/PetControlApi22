package net.petcontrol.PetControlApi22;

import androidx.annotation.NonNull;

public class AboutTheAppPetControl {
    public String information;
    public int arrow;


    // CONSTRUCTOR
    public AboutTheAppPetControl(String information, int arrow) {
        this.information = information;
        this.arrow = arrow;
    }


    // GETTER
    public String getInformation() {
        return information;
    }


    // SETTER
    public void setInformation(String information) {
        this.information = information;
    }


    // toString()
    @NonNull
    @Override
    public String toString() {
        return "AboutTheAppPetControl: \n- Tipo de información --> " + information;
    }
}