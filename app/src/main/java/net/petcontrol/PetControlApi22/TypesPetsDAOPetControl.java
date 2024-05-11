package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.sql.SQLException;
import java.util.List;

// Métodos para interactuar con la base de datos (insertar, eliminar, actualizar y consultar datos)
@Dao
public interface TypesPetsDAOPetControl {
    // Añade nuevo registro
    @Insert
    @Transaction
    void addAnimal(TypePetsPetControl animal) throws SQLException;
    // Modifica uno ya existente
    @Update
    @Transaction
    void updateAnimal(TypePetsPetControl animal) throws SQLException;
    // Elimina un registro
    @Delete
    @Transaction
    void deleteAnimal(TypePetsPetControl animal) throws SQLException;
    // Obtiene todos los tipos de mascotas almacenados en la base de datos
    @Query("SELECT * FROM TypePets")
    @Transaction
    List<TypePetsPetControl> getAllTypePets() throws SQLException;
    // Obtiene un tipo de mascota según su ID
    @Query("SELECT * FROM TypePets WHERE IDTypePet = :id")
    @Transaction
    TypePetsPetControl getTypePetById(int id) throws SQLException;
    // Obtiene un tipo de mascota por su nombre
    @Query("SELECT * FROM TypePets WHERE TypePet = :typePet")
    @Transaction
    TypePetsPetControl getTypePetByName(String typePet) throws SQLException;
}