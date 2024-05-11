package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Dao
public interface PetsDAOPetControl {
    // Añadir una nueva mascota
    @Insert
    @Transaction
    void insertPet(PetsPetControl pet) throws SQLException;
    // Modificar algún dato de alguna mascota existente
    @Update
    @Transaction
    void updatePet(PetsPetControl pet) throws SQLException;
    // Eliminar una mascota
    @Delete
    @Transaction
    void deletePet(PetsPetControl pet) throws SQLException;
    // Seleccionar todas las mascotas existentes
    @Query("SELECT * FROM Pets")
    @Transaction
    List<PetsPetControl> getAllPets() throws SQLException;
    // Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId")
    @Transaction
    List<PetsPetControl> getPetsByType(int typePetId) throws SQLException;
    // Seleccionar todas las mascotas que tengan la misma edad
    @Query("SELECT * FROM Pets WHERE AgePet = :agePet")
    @Transaction
    List<PetsPetControl> getPetsByAge(int agePet) throws SQLException;
    // Seleccionar todas las mascotas que sean de la misma raza
    @Query("SELECT * FROM Pets WHERE Breed = :breed")
    @Transaction
    List<PetsPetControl> getPetsByBreed(String breed) throws SQLException;
    // Seleccionar todas las mascotas que compartan sexo
    @Query("SELECT * FROM Pets WHERE SexPet = :sexPet")
    @Transaction
    List<PetsPetControl> getPetsBySex(String sexPet) throws SQLException;
    // Seleccionar todas las mascotas que compartan fecha de primera vez en la familia
    @Query("SELECT * FROM Pets WHERE WelcomeDatePet = :welcomePet")
    @Transaction
    List<PetsPetControl> getPetsByWelcome(LocalDate welcomePet) throws SQLException;
    // Seleccionar todas las mascotas que compartan descripción
    @Query("SELECT * FROM Pets WHERE DescriptionPet = :descriptionPet")
    @Transaction
    List<PetsPetControl> getPetsByDescription(String descriptionPet) throws SQLException;
    // Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal y raza
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId AND Breed = :breed")
    @Transaction
    List<PetsPetControl> getPetsByTypeAndBreed(int typePetId, String breed) throws SQLException;
    // Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal y del mismo sexo
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId AND SexPet = :sexPet")
    @Transaction
    List<PetsPetControl> getPetsByTypeAndSex(int typePetId, String sexPet) throws SQLException;
}