package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Interfaz DAO (Data Access Object) que define los métodos para interactuar con la base de datos
 * relacionada a las visitas veterinarias de las mascotas.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las visitas veterinarias.
 */
@Dao
public interface VisitsVetDAOPetControl {
    /**
     * Añadir una nueva visita veterinaria a la BD.
     *
     * @param visit Visita a insertar en la base de datos
     */
    @Insert
    @Transaction
    void insertVisit(VisitsVetPetControl visit);
    /**
     * Modificar una visita veterinaria ya existente en la BD.
     *
     * @param visit Visita a modificar en la base de datos
     */
    @Update
    @Transaction
    void updateVisit(VisitsVetPetControl visit);
    /**
     * Eliminar una visita veterinaria de la BD.
     *
     * @param visit Visita a eliminar en la base de datos
     */
    @Delete
    @Transaction
    void deleteVisit(VisitsVetPetControl visit);
    /**
     * Seleccionar todas las visitas veterinarias existentes en la BD.
     *
     * @return Una lista de toda la información de las visitas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet")
    List<VisitsVetPetControl> getAllVisits();
    /**
     * Seleccionar una visita veterinaria según su ID almacenada en la BD.
     *
     * @return Una lista de las visitas, a un centro específico, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE IDVet = :vetId")
    VisitsVetPetControl getVisitById(int vetId);
    /**
     * Seleccionar todas las visitas veterinarias para una mascota específica almacenada en la BD.
     *
     * @return Una lista de las mascotas que han ido al veterinario almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE IDPet = :petId")
    List<VisitsVetPetControl> getVisitsForPet(int petId);
    /**
     * Seleccionar todas las visitas realizadas a un centro veterinario específico almacenada en la
     * BD.
     *
     * @return Una lista de las visitas, según el nombre del local, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE NameVet = :vetName")
    List<VisitsVetPetControl> getVisitsByName(String vetName);
    /**
     * Seleccionar todas las visitas veterinarias ubicadas en una misma localización almacenada en
     * la BD.
     *
     * @return Una lista de las visitas, a un ubicación concreta, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE LocVet = :vetLoc")
    List<VisitsVetPetControl> getVisitsByLoc(String vetLoc);
    /**
     * Seleccionar todas las visitas realizadas en una fecha concreta almacenada en la BD.
     *
     * @return Una lista de las visitas, con fecha específica, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE Datevisit = :visitDate")
    List<VisitsVetPetControl> getVisitsByDate(LocalDateTime visitDate);
    /**
     * Seleccionar todas las visitas con mismo motivo de visita en la BD.
     *
     * @return Una lista de las visitas, con mismo motivo, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE ReasonVisit = :visitReason")
    List<VisitsVetPetControl> getVisitsByReason(String visitReason);
    /**
     * Seleccionar todas las visitas con mismo diagnóstico almacenada en la BD.
     *
     * @return Una lista de las visitas, con mismo diagnóstico, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE Diagnosis = :diagnosis")
    List<VisitsVetPetControl> getVisitsByDiagnosis(String diagnosis);
    /**
     * Seleccionar todas las visitas con el mismo tratamiento almacenada en la BD.
     *
     * @return Una lista de las visitas, con mismo tratamiento, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE Treatment = :treatment")
    List<VisitsVetPetControl> getVisitsByTreatment(String treatment);
    /**
     * Seleccionar todas las visitas con igual precio de coste, almacenada en la BD.
     *
     * @return Una lista de las visitas, con mismo coste, almacenadas en la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE VisitPrice = :visitPrice")
    List<VisitsVetPetControl> getVisitsByPrice(String visitPrice);
    /**
     * Seleccionar todas las visitas de una mascota a un centro determinado, almacenada en la BD.
     *
     * @return Una lista de las visitas, de una mascota a un centro concreto, almacenadas en la
     *          base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE IDVet = :vetId AND IDPet = :petId")
    List<VisitsVetPetControl> getVisitsByVetAndPet(int vetId, int petId);
    /**
     * Seleccionar todas las visitas de una mascota a una localidad específica almacenada en la BD.
     *
     * @return Una lista de las visitas, de una mascota a una ubicación específica, almacenadas en
     *          la base de datos.
     */
    @Query("SELECT * FROM VisitsVet WHERE IDPet = :petId AND LocVet = :vetLoc")
    List<VisitsVetPetControl> getVisitsByPetAndLoc(int petId, String vetLoc);
}