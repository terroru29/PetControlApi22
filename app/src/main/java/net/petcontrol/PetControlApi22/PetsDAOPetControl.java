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
 * relacionada a las mascotas.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las mascotas.
 */
@Dao
public interface PetsDAOPetControl {
    /**
     * Añadir una nueva mascota a la BD.
     *
     * @param pet Mascota a insertar en la base de datos
     */
    @Insert
    @Transaction
    void insertPet(PetsPetControl pet);
    /**
     * Modificar información de alguna mascota existente en la base de datos.
     *
     * @param pet Mascota a la que se le modificará algún dato en la base de datos
     */
    @Update
    @Transaction
    void updatePet(PetsPetControl pet);
    /**
     * Eliminar una mascota de la base de datos.
     *
     * @param pet Mascota que se eliminará de la base de datos
     */
    @Delete
    @Transaction
    void deletePet(PetsPetControl pet);
    /**
     * Seleccionar todas las mascotas existentes en la BD.
     *
     * @return Una lista de de toda la información de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets")
    List<PetsPetControl> getAllPets();
    /**
     * Seleccionar todas las mascotas que pertenezcan al mismo tipo de la BD.
     *
     * @param typePetId El identificador del tipo de animal
     * @return Una lista de las mascotas del mismo tipo almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId")
    List<PetsPetControl> getPetsByType(int typePetId);
    /**
     * Seleccionar todas las mascotas que tengan la misma edad almacenadas en la BD.
     *
     * @param agePet La edad de la mascota
     * @return Una lista con la edad de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE AgePet = :agePet")
    List<PetsPetControl> getPetsByAge(int agePet);
    /**
     * Seleccionar todas las mascotas que sean de la misma raza almacenadas en al BD.
     *
     * @param breed La raza del animal
     * @return Una lista con las razas de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE Breed = :breed")
    List<PetsPetControl> getPetsByBreed(String breed);
    /**
     * Seleccionar todas las mascotas que compartan sexo almacenadas en la BD.
     *
     * @param sexPet El sexo de cada mascota (Macho/Hembra)
     * @return Una lista con el sexo de cada mascota almacenada en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE SexPet = :sexPet")
    List<PetsPetControl> getPetsBySex(String sexPet);
    /**
     * Seleccionar todas las mascotas que compartan fecha de primera vez en la familia almacenadas
     * en la BD.
     *
     * @param welcomePet Fecha (dd/MM/yyyy) de primera vez en la familia de la mascota
     * @return Una lista con la edad de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE WelcomeDatePet = :welcomePet")
    List<PetsPetControl> getPetsByWelcome(LocalDate welcomePet);
    /**
     * Seleccionar todas las mascotas que compartan descripción almacenadas en la BD.
     *
     * @param descriptionPet Descripción física y/o personal de la mascota
     * @return Una lista con la descripción de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE DescriptionPet = :descriptionPet")
    List<PetsPetControl> getPetsByDescription(String descriptionPet);
    /**
     * Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal y raza almacenadas en
     * la BD.
     *
     * @param typePetId Tipo de animal al que pertenece la mascota
     * @param breed Raza de la mascota
     * @return Una lista con el tipo y raza de las mascotas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId AND Breed = :breed")
    List<PetsPetControl> getPetsByTypeAndBreed(int typePetId, String breed);
    /**
     * Seleccionar todas las mascotas que pertenezcan al mismo tipo de animal y del mismo sexo
     * almacenadas en la BD.
     *
     * @param typePetId Tipo al que pertenece la mascota
     * @param sexPet Sexo de la mascota (Macho/Hembra)
     * @return Una lista con el tipo de mascota y el sexo de las mascotas almacenadas en la base de
     *          datos.
     */
    @Query("SELECT * FROM Pets WHERE IDTypePet = :typePetId AND SexPet = :sexPet")
    List<PetsPetControl> getPetsByTypeAndSex(int typePetId, String sexPet);
}