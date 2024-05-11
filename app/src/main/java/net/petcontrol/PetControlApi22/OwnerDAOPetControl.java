package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz DAO (Data Access Object) que define los métodos para interactuar con la base de datos
 * relacionada a los propietarios.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los propietarios.
 */
@Dao
public interface OwnerDAOPetControl {
    /**
     * Añadir un nuevo propietario a la BD.
     *
     * @param owner Dueño a insertar en la base de datos
     */
    @Insert
    @Transaction
    void insertOwner(OwnerPetControl owner);
    // Modificar algún dato de algún propietario existente
    /**
     * Modificar datos de un propietario ya existente en la BD.
     *
     * @param owner Dueño a quién modificar los datos en la base de datos
     */
    @Update
    @Transaction
    void updateOwner(OwnerPetControl owner);
    // Eliminar un propietario
    /**
     * Eliminar un propietario existente en la BD.
     *
     * @param owner Dueño a eliminar en la base de datos
     */
    @Delete
    @Transaction
    void deleteOwner(OwnerPetControl owner);
    /**
     * Seleccionar todos los propietarios de mascotas existentes en la BD.
     *
     * @return Una lista de toda la información de los propietarios almacenados en la base de datos.
     */
    @Query("SELECT * FROM OwnerPet")
    List<OwnerPetControl> getAllOwners();
    /**
     * Seleccionar un propietario por su ID, existente en la BD.
     *
     * @return Una lista de los propietarios almacenados en la base de datos.
     */
    @Query("SELECT * FROM OwnerPet WHERE IDOwner = :ownerId")
    OwnerPetControl getOwnerById(int ownerId);
    /**
     * Seleccionar todos los propietarios con misma edad, existente en la BD.
     *
     * @return Una lista de los propietarios, con misma edad, almacenados en la base de datos.
     */
    @Query("SELECT * FROM OwnerPet WHERE AgeOwner = :ageOwner")
    List<OwnerPetControl> getOwnersByAge(int ageOwner);
    /**
     * Seleccionar todos los propietarios con determinado género, existente en la BD.
     *
     * @return Una lista de los propietarios, de un determinado género, almacenados en la base de
     *          datos.
     */
    @Query("SELECT * FROM OwnerPet WHERE GenderOwner = :genderOwner")
    List<OwnerPetControl> getOwnersByGender(String genderOwner);
    /**
     * Seleccionar todos los propietarios que cumplan años el mismo día, existentes en la BD.
     *
     * @return Una lista de los propietarios, que cumplan años el mismo día, almacenados en la base
     *          de datos.
     */
    @Query("SELECT * FROM OwnerPet WHERE Birthday = :birthday")
    List<OwnerPetControl> getOwnersByBirthday(LocalDate birthday);
    /**
     * Seleccionar todos los propietarios con correo electrónico como contacto, existente en la BD.
     *
     * @return Una lista de los propietarios, donde el correo electrónico es el contacto,
     * almacenados en la base de datos.
     */
    @Query("SELECT * FROM OwnerPet WHERE Contact = :contact")
    List<OwnerPetControl> getOwnersByContact(String contact);
}