package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "Pets",
        foreignKeys = @ForeignKey(entity = TypePetsPetControl.class,
                // Nombre columna en la entidad padre --> TypePetsPetControl
                parentColumns = "id_type_pet",
                // Nombre columna en la entidad hijo --> PetsPetControl
                childColumns = "id_type_pet",
                // Si el tipo de animal se extingue, el registro del animal se elimina --> Cascade
                onDelete = ForeignKey.CASCADE)) //TODO pensar si poner mejor RESTRICT (VisitsVet)
public class PetsPetControl {
    //Atributos --> Columnas
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDPet")
    public int id_pet;
    @ColumnInfo(name = "IDTypePet")
    public int id_type_pet;    // FK que referencia a la entidad TypesPetsPetControl --> Tipo animal
    @ColumnInfo(name = "NamePet")
    public String name_pet;
    @ColumnInfo(name = "AgePet")
    public int age_pet;
    @ColumnInfo(name = "Breed")
    public String breed;
    @ColumnInfo(name = "SexPet")
    public String sex_pet;
    // Almacenamos la ruta de la imagen que se guardará en el sistema de archivos (Internal Storage)
    // para referenciarla más tarde en la BD
    @ColumnInfo(name = "UriPicPet")
    public String uri_pic_pet;
    @ColumnInfo(name = "WelcomeDatePet")
    public LocalDate welcome_date_pet;
    @ColumnInfo(name = "DescriptionPet")
    public String description_pet;


    // Constructores
    public PetsPetControl() {}
    public PetsPetControl(int id_pet, int id_type_pet, String name_pet, int age_pet, String breed,
                          String sex_pet, String uri_pic_pet, LocalDate welcome_date_pet,
                          String description_pet) {
        this.id_pet = id_pet;
        this.id_type_pet = id_type_pet;
        this.name_pet = name_pet;
        this.age_pet = age_pet;
        this.breed = breed;
        this.sex_pet = sex_pet;
        this.uri_pic_pet = uri_pic_pet;
        this.welcome_date_pet = welcome_date_pet;
        this.description_pet = description_pet;
    }


    // Getters
    public int getIDPet() {
        return id_pet;
    }
    public int getIDTypePet() {
        return id_type_pet;
    }
    public String getNamePet() {
        return name_pet;
    }
    public int getAgePet() {
        return age_pet;
    }
    public String getBreed() {
        return breed;
    }
    public String getSexPet() {
        return sex_pet;
    }
    public String getUriPicPet() {
        return uri_pic_pet;
    }
    public LocalDate getWelcomeDatePet() {
        return welcome_date_pet;
    }
    public String getDescriptionPet() {
        return description_pet;
    }


    // Setters
    public void setIDPet(int id_pet) {
        this.id_pet = id_pet;
    }
    public void setIDTypePet(int id_type_pet) {
        this.id_type_pet = id_type_pet;
    }
    public void setNamePet(String name_pet) {
        this.name_pet = name_pet;
    }
    public void setAgePet(int age_pet) {
        this.age_pet = age_pet;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public void setSexPet(String sex_pet) {
        this.sex_pet = sex_pet;
    }
    public void setUriPicPet(String uri_pic_pet) {
        this.uri_pic_pet = uri_pic_pet;
    }
    public void setWelcomeDatePet(LocalDate welcome_date_pet) {
        this.welcome_date_pet = welcome_date_pet;
    }
    public void setDescriptionPet(String description_pet) {
        this.description_pet = description_pet;
    }
}