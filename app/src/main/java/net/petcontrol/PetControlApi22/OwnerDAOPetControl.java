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
public interface OwnerDAOPetControl {
    // Añadir nuevo dueño
    @Insert
    @Transaction
    void insertOwner(OwnerPetControl owner) throws SQLException;
    // Modificar algún dato de algún propietario existente
    @Update
    @Transaction
    void updateOwner(OwnerPetControl owner) throws SQLException;
    // Eliminar un propietario
    @Delete
    @Transaction
    void deleteOwner(OwnerPetControl owner) throws SQLException;
    // Seleccionar todos los propietarios de mascotas existentes
    @Query("SELECT * FROM OwnerPet")
    @Transaction
    List<OwnerPetControl> getAllOwners() throws SQLException;
    // Seleccionar un propietario de mascota por su ID
    @Query("SELECT * FROM OwnerPet WHERE IDOwner = :ownerId")
    @Transaction
    OwnerPetControl getOwnerById(int ownerId) throws SQLException;
    // Seleccionar todos los propietarios de mascotas que tengan la misma edad
    @Query("SELECT * FROM OwnerPet WHERE AgeOwner = :ageOwner")
    @Transaction
    List<OwnerPetControl> getOwnersByAge(int ageOwner) throws SQLException;
    // Seleccionar todos los propietarios de mascotas que sean de un determinado género
    @Query("SELECT * FROM OwnerPet WHERE GenderOwner = :genderOwner")
    @Transaction
    List<OwnerPetControl> getOwnersByGender(String genderOwner) throws SQLException;
    // Seleccionar todos los propietarios de mascotas que cumplan años el mismo día
    @Query("SELECT * FROM OwnerPet WHERE Birthday = :birthday")
    @Transaction
    List<OwnerPetControl> getOwnersByBirthday(LocalDate birthday) throws SQLException;
    // Seleccionar todos los propietarios de mascotas que tengan un cierto correo electrónico de contacto
    @Query("SELECT * FROM OwnerPet WHERE Contact = :contact")
    @Transaction
    List<OwnerPetControl> getOwnersByContact(String contact) throws SQLException;
}