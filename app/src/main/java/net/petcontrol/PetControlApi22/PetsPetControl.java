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
    int id_pet;
    @ColumnInfo(name = "IDTypePet")
    int id_type_pet;    // FK que referencia a la entidad TypesPetsPetControl --> Tipo animal
    @ColumnInfo(name = "NamePet")
    String name_pet;
    @ColumnInfo(name = "AgePet")
    int age_pet;
    @ColumnInfo(name = "Breed")
    String breed;
    @ColumnInfo(name = "SexPet")
    String sex_pet;
    // Almacenamos la ruta de la imagen que se guardará en el sistema de archivos (Internal Storage)
    // para referenciarla más tarde en la BD
    @ColumnInfo(name = "UriPicPet")
    String uri_pic_pet;
    @ColumnInfo(name = "WelcomeDatePet")
    LocalDate welcome_date_pet;
    @ColumnInfo(name = "DescriptionPet")
    String description_pet;


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
    public int getId_pet() {
        return id_pet;
    }
    public int getId_type_pet() {
        return id_type_pet;
    }
    public String getName_pet() {
        return name_pet;
    }
    public int getAge_pet() {
        return age_pet;
    }
    public String getBreed() {
        return breed;
    }
    public String getSex_pet() {
        return sex_pet;
    }
    public String getUri_pic_pet() {
        return uri_pic_pet;
    }
    public LocalDate getWelcome_date_pet() {
        return welcome_date_pet;
    }
    public String getDescription_pet() {
        return description_pet;
    }


    // Setters
    public void setId_pet(int id_pet) {
        this.id_pet = id_pet;
    }
    public void setId_type_pet(int id_type_pet) {
        this.id_type_pet = id_type_pet;
    }
    public void setName_pet(String name_pet) {
        this.name_pet = name_pet;
    }
    public void setAge_pet(int age_pet) {
        this.age_pet = age_pet;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public void setSex_pet(String sex_pet) {
        this.sex_pet = sex_pet;
    }
    public void setUri_pic_pet(String uri_pic_pet) {
        this.uri_pic_pet = uri_pic_pet;
    }
    public void setWelcome_date_pet(LocalDate welcome_date_pet) {
        this.welcome_date_pet = welcome_date_pet;
    }
    public void setDescription_pet(String description_pet) {
        this.description_pet = description_pet;
    }
}