package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Indicamos que es una Entidad (tabla)
@Entity(tableName = "TypePets")
public class TypePetsPetControl {
    @PrimaryKey(autoGenerate = true)    // No se podrá repetir y se autoincrementará
    @ColumnInfo(name = "IDTypePet")
    private int id_type_pet;
    @ColumnInfo(name = "TypePet")   // Nombre de la columna en la BD
    private String type_pet;


    // Constructores
    public TypePetsPetControl() {}
    public TypePetsPetControl(int id_type_pet, String type_pet) {
        this.id_type_pet = id_type_pet;
        this.type_pet = type_pet;
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
        this.type_pet = type_pet;
    }
}