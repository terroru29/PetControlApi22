package net.petcontrol.PetControlApi22;

import androidx.annotation.NonNull;

public class SettingsPetControl {
    public int imageSettings;
    public String typeSettings;
    public int arrow;


    // CONSTRUCTOR
    public SettingsPetControl(int imageSettings, String typeSettings, int arrow) {
        this.imageSettings = imageSettings;
        this.typeSettings = typeSettings;
        this.arrow = arrow;
    }


    // GETTERS
    public int getImageSettings() {
        return imageSettings;
    }
    public String getTypeSettings() {
        return typeSettings;
    }


    // SETTERS
    public void setImageSettings(int imageSettings) {
        this.imageSettings = imageSettings;
    }
    public void setTypeSettings(String typeSettings) {
        this.typeSettings = typeSettings;
    }


    // toString()
    @NonNull
    @Override
    public String toString() {
        return "SettingsPetControl: \n- Imagen --> " + imageSettings + "\n- Tipo de ajuste --> " +
                typeSettings;
    }
}
