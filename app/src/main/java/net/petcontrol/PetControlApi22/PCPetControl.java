package net.petcontrol.PetControlApi22;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class PCPetControl {
    private Bitmap photo;
    private String name;
    private String sex;
    private int age;


    // Constructor
    public PCPetControl(Bitmap photo, String name, String sex, int age) {
        this.photo = photo;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }


    // Getters
    public Bitmap getPhoto() {
        return photo;
    }
    public String getName() {
        return name;
    }
    public String getSex() {
        return sex;
    }
    public int getAge() {
        return age;
    }


    // Setters
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setAge(int age) {
        this.age = age;
    }


    // toString()
    @NonNull
    @Override
    public String toString() {
        return "PCPetControl\n" +
                "photo=" + photo +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age;
    }
}