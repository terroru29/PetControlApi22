package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

/**
 *  Interfaz DAO (Data Access Object) que define los métodos para interactuar con la base de datos
 *  relacioanda a los tipos de mascotas.
 *  Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para tipos de mascotas.
 */
@Dao
public interface TypesPetsDAOPetControl {
    /**
     * Añade un nuevo animal a la BD.
     *
     * @param animal El animal que se va a insertar
     */
    @Insert
    @Transaction
    void addAnimal(TypePetsPetControl animal);
    /**
     * Modifica un animal ya existente en la BD.
     *
     * @param animal El animal que se va a modificar
     */
    @Update
    @Transaction
    void updateAnimal(TypePetsPetControl animal);
    /**
     * Elimina un animal de la BD.
     *
     * @param animal El animal que se va a eliminar
     */
    @Delete
    @Transaction
    void deleteAnimal(TypePetsPetControl animal);
    /**
     * Obtiene todos los tipos de mascotas almacenados en la base de datos.
     *
     * @return Una lista de todos los tipos de mascotas almacenados en la base de datos
     */
    @Query("SELECT * FROM TypePets")
    List<TypePetsPetControl> getAllTypePets();
    /**
     * Obtiene un tipo de animal según su ID de la base de datos.
     *
     * @param id Identificador de cada animal
     * @return Una lista del animal con ese ID
     */
    @Query("SELECT * FROM TypePets WHERE IDTypePet = :id")
    TypePetsPetControl getTypePetById(int id);
    /**
     * Obtiene un tipo de animal según su nombre almacenado en la BD.
     *
     * @param typePet Tipo de animal por el que buscar
     * @return Una lista de animales que sean del mismo tipo almacenados en la base de datos.
     */
    @Query("SELECT * FROM TypePets WHERE TypePet = :typePet")
    TypePetsPetControl getTypePetByName(String typePet);
}