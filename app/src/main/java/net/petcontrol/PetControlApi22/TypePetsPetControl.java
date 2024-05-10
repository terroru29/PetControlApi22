package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Indicamos que es una Entidad (tabla)
@Entity(tableName = "TypePets")
public class TypePetsPetControl {
    @PrimaryKey(autoGenerate = true)    // No se podrá repetir y se autoincrementará
    @ColumnInfo(name = "IDTypePet")
    int id_type_pet;
    @ColumnInfo(name = "TypePet")   // Nombre de la columna en la BD
    String type_pet;
}
