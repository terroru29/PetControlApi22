package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Clase entidad (tabla de la BD) que representa los diferentes tipos de animales disponibles.
 */
@Entity(tableName = "TypePets")
public class TypePetsPetControl {
    @PrimaryKey(autoGenerate = true)    // No se podrá repetir y se autoincrementará
    @ColumnInfo(name = "IDTypePet")
    private int id_type_pet;
    @ColumnInfo(name = "TypePet")   // Nombre de la columna en la BD
    private String type_pet;


    // Constructores
    /**
     * Constructor por defecto.
     */
    public TypePetsPetControl() {}
    /**
     * Cosntructor con parámetros.
     *
     * @param id_type_pet EL ID del tipo de animal
     * @param type_pet El tipo de animal
     */
    public TypePetsPetControl(int id_type_pet, String type_pet) {
        this.id_type_pet = id_type_pet;
        setType_pet(type_pet);
    }


    // Getters
    public int getId_type_pet() {
        return id_type_pet;
    }
    public String getType_pet() {
        return type_pet;
    }


    // Setters
    public void setId_type_pet(int id_type_pet) {
        this.id_type_pet = id_type_pet;
    }
    public void setType_pet(String type_pet) {
        if (type_pet == null || type_pet.isEmpty())
            throw new IllegalArgumentException("El tipo de animal no puede estar vacío.");
        this.type_pet = type_pet;
    }
}