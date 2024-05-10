package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Métodos para interactuar con la base de datos (insertar, eliminar, actualizar y consultar datos)
@Dao
public interface TypesPetsDAOPetControl {
    // Añade nuevo registro
    @Insert
    void addAnimal(TypePetsPetControl animal);
    // Modifica uno ya existente
    @Update
    void updateAnimal(TypePetsPetControl animal);
    // Elimina un registro
    @Delete
    void deleteAnimal(TypePetsPetControl animal);
    // Obtiene todos los tipos de mascotas almacenados en la base de datos
    @Query("SELECT * FROM TypePets")
    List<TypePetsPetControl> getAllTypePets();
    // Obtiene un tipo de mascota según su ID
    @Query("SELECT * FROM TypePets WHERE IDTypePet = :id")
    TypePetsPetControl getTypePetById(int id);
    // Obtiene un tipo de mascota por su nombre
    @Query("SELECT * FROM TypePets WHERE TypePet = :typePet")
    TypePetsPetControl getTypePetByName(String typePet);
}