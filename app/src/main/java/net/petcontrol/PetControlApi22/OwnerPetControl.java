package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;


@Entity(tableName = "OwnerPet")
public class OwnerPetControl {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDOwner")
    int id_owner;
    @ColumnInfo(name = "NameOwner")
    String name_owner;
    @ColumnInfo(name = "AgeOwner")
    int age_owner;
    @ColumnInfo(name = "GenderOwner")
    String gender_owner;
    // Almacenamos la ruta de la imagen que se guardará en el sistema de archivos (Internal Storage)
    // para referenciarla más tarde en la BD
    @ColumnInfo(name = "UriPicOwner")
    String uri_pic_owner;
    @ColumnInfo(name = "Birthday")
    LocalDate birthday;
    @ColumnInfo(name = "Contact")   // E-mail
    String contact;


    // Constructores
    public OwnerPetControl() {}
    public OwnerPetControl(int id_owner, String name_owner, int age_owner, String gender_owner,
                           String uri_pic_owner, LocalDate birthday, String contact) {
        this.id_owner = id_owner;
        this.name_owner = name_owner;
        this.age_owner = age_owner;
        this.gender_owner = gender_owner;
        this.uri_pic_owner = uri_pic_owner;
        this.birthday = birthday;
        this.contact = contact;
    }


    // Getters
    public int getId_owner() {
        return id_owner;
    }
    public String getName_owner() {
        return name_owner;
    }
    public int getAge_owner() {
        return age_owner;
    }
    public String getGender_owner() {
        return gender_owner;
    }
    public String getUri_pic_owner() {
        return uri_pic_owner;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public String getContact() {
        return contact;
    }


    // Setters
    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }
    public void setName_owner(String name_owner) {
        this.name_owner = name_owner;
    }
    public void setAge_owner(int age_owner) {
        this.age_owner = age_owner;
    }
    public void setGender_owner(String gender_owner) {
        this.gender_owner = gender_owner;
    }
    public void setUri_pic_owner(String uri_pic_owner) {
        this.uri_pic_owner = uri_pic_owner;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
}