package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface PetsDAOPetControl {
    // Añadir una nueva mascota
    @Insert
    void insertPet(PetsPetControl pet);
    // Modificar algún dato de alguna mascota existente
    @Update
    void updatePet(PetsPetControl pet);
    // Eliminar una mascota
    @Delete
    void deletePet(PetsPetControl pet);
    // Seleccionar todas las mascotas existentes
    @Query("SELECT * FROM Pets")
    List<PetsPetControl> getAllPets();
    // Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId")
    List<PetsPetControl> getPetsByType(int typePetId);
    // Seleccionar todas las mascotas que tengan la misma edad
    @Query("SELECT * FROM Pets WHERE AgePet = :agePet")
    List<PetsPetControl> getPetsByAge(int agePet);
    // Seleccionar todas las mascotas que sean de la misma raza
    @Query("SELECT * FROM Pets WHERE Breed = :breed")
    List<PetsPetControl> getPetsByBreed(String breed);
    // Seleccionar todas las mascotas que compartan sexo
    @Query("SELECT * FROM Pets WHERE SexPet = :sexPet")
    List<PetsPetControl> getPetsBySex(String sexPet);
    // Seleccionar todas las mascotas que compartan fecha de primera vez
    @Query("SELECT * FROM Pets WHERE WelcomeDatePet = :welcomePet")
    List<PetsPetControl> getPetsByWelcome(LocalDate welcomePet);
    // Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal
    @Query("SELECT * FROM Pets WHERE DescriptionPet = :descriptionPet")
    List<PetsPetControl> getPetsByDescription(String descriptionPet);
}