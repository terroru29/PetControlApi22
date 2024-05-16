package net.petcontrol.PetControlApi22;

import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/**
 * Clase entidad (tabla en la BD) que representa las mascotas del usuario.
 */
@Entity(tableName = "Pets",
        foreignKeys = @ForeignKey(entity = TypePetsPetControl.class,
                // Nombre columna en la entidad padre --> TypePetsPetControl
                parentColumns = "IDTypePet",
                // Nombre columna en la entidad hijo --> PetsPetControl
                childColumns = "IDTypePet",
                // Si el tipo de animal se extingue, el registro del animal se elimina --> Cascade
                onDelete = ForeignKey.CASCADE), //TODO pensar si poner mejor RESTRICT --> VisitsVet
        // Mejora el rendimiento de las consultas que usan esta columna como referencia de la FK
        indices = {@Index("IDTypePet")})
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
    @ColumnInfo(name = "Sterilization")
    public boolean sterilization;
    @ColumnInfo(name = "DescriptionPet")
    public String description_pet;


    // Constructores
    /**
     * Constructor por defecto
     */
    public PetsPetControl() {}
    /**
     * Constructor con parámetros
     *
     * @param id_pet ID de la mascota
     * @param id_type_pet ID del tipo de animal
     * @param name_pet Nombre de la mascota
     * @param age_pet Edad de la mascota
     * @param breed Raza de la mascota
     * @param sex_pet Sexo de la mascota (Macho/Hembra)
     * @param uri_pic_pet Ruta de dónde se almacena la foto elegida para la mascota
     * @param sterilization Si el animal está esterilizado
     * @param description_pet Descripción físico y/o personal de la mascota
     */
    public PetsPetControl(int id_pet, int id_type_pet, String name_pet, int age_pet, String breed,
                          String sex_pet, String uri_pic_pet, boolean sterilization,
                          String description_pet) throws IllegalArgumentException {
        this.id_pet = id_pet;
        this.id_type_pet = id_type_pet;
        this.name_pet = name_pet;
        this.age_pet = age_pet;
        this.breed = breed;
        this.sex_pet = sex_pet;
        this.uri_pic_pet = uri_pic_pet;
        this.sterilization = sterilization;
        this.description_pet = description_pet;
        // Valida que los campos no estén vacíos o nulos
        validateFieldsPets();
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
    public boolean getSterilization() {
        return sterilization;
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
    public void setSterilization(boolean sterilization) {
        this.sterilization = sterilization;
    }
    public void setDescriptionPet(String description_pet) {
        this.description_pet = description_pet;
    }


    // ------------------ MÉTODOS ------------------
    /**
     * Valida si la mascota actual tiene los datos correctos.
     * Usar antes de guardar o procesar una instancia de PetsPetControl para asegurar que los
     * datos son correctos.
     *
     * @throws IllegalArgumentException Si algún campo tiene datos incorrectos o nulos
     */
    public void validateFieldsPets() {
        if (name_pet == null || name_pet.isEmpty())
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacío.");
        if (age_pet <= 0)
            throw new IllegalArgumentException("La edad de la mascota debe ser mayor a cero.");
        if (breed == null || breed.isEmpty())
            throw new IllegalArgumentException("La raza de la mascota no puede estar vacía.");
        if (sex_pet == null || (!sex_pet.equalsIgnoreCase("Macho")
                && !sex_pet.equalsIgnoreCase("Hembra")))
            throw new IllegalArgumentException("El sexo de la mascota debe ser 'Macho' o 'Hembra'.");
        if (uri_pic_pet == null || uri_pic_pet.isEmpty())
            throw new IllegalArgumentException("La ruta de la imagen de la mascota no puede estar " +
                    "vacía.");
        if (description_pet == null || description_pet.isEmpty())
            throw new IllegalArgumentException("La descripción de la mascota no puede estar vacía.");
    }
}