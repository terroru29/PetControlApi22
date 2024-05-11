package net.petcontrol.PetControlApi22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;

@Dao
public interface VisitsVetDAOPetControl {
    // Añadir una nueva visita veterinaria
    @Insert
    @Transaction
    void insertVisit(VisitsVetPetControl visit) throws SQLException;
    // Modificar los datos de una visita veterinaria existente
    @Update
    @Transaction
    void updateVisit(VisitsVetPetControl visit) throws SQLException;
    // Eliminar una visita veterinaria
    @Delete
    @Transaction
    void deleteVisit(VisitsVetPetControl visit) throws SQLException;
    // Seleccionar todas las visitas veterinarias realizadas
    @Query("SELECT * FROM VisitsVet")
    @Transaction
    List<VisitsVetPetControl> getAllVisits() throws SQLException;
    // Seleccionar una visita veterinaria por su ID (referencia al local)
    @Query("SELECT * FROM VisitsVet WHERE IDVet = :vetId")
    @Transaction
    VisitsVetPetControl getVisitById(int vetId) throws SQLException;
    // Seleccionar todas las visitas veterinarias para una mascota específica
    @Query("SELECT * FROM VisitsVet WHERE IDPetVet = :petvetId")
    @Transaction
    List<VisitsVetPetControl> getVisitsForPet(int petvetId) throws SQLException;
    // Seleccionar todas las visitas veterinarias realizadas a un centro veterinario específico
    @Query("SELECT * FROM VisitsVet WHERE NameVet = :vetName")
    @Transaction
    List<VisitsVetPetControl> getVisitsByName(String vetName) throws SQLException;
    // Seleccionar todas las visitas veterinarias ubicadas en una misma localización
    @Query("SELECT * FROM VisitsVet WHERE LocVet = :vetLoc")
    @Transaction
    List<VisitsVetPetControl> getVisitsByLoc(String vetLoc) throws SQLException;
    // Seleccionar todas las visitas veterinarias realizadas en una fecha concreta
    @Query("SELECT * FROM VisitsVet WHERE Datevisit = :visitDate")
    @Transaction
    List<VisitsVetPetControl> getVisitsByDate(LocalDateTime visitDate) throws SQLException;
    // Seleccionar todas las visitas veterinarias realizadas que compartan motivo de visita
    @Query("SELECT * FROM VisitsVet WHERE ReasonVisit = :visitReason")
    @Transaction
    List<VisitsVetPetControl> getVisitsByReason(String visitReason) throws SQLException;
    // Seleccionar todas las visitas veterinarias en las que se obtuviera el mismo diagnóstico
    @Query("SELECT * FROM VisitsVet WHERE Diagnosis = :diagnosis")
    @Transaction
    List<VisitsVetPetControl> getVisitsByDiagnosis(String diagnosis) throws SQLException;
    // Seleccionar todas las visitas veterinarias con el mismo tratamiento
    @Query("SELECT * FROM VisitsVet WHERE Treatment = :treatment")
    @Transaction
    List<VisitsVetPetControl> getVisitsByTreatment(String treatment) throws SQLException;
    // Seleccionar todas las visitas veterinarias que costaran de igual precio
    @Query("SELECT * FROM VisitsVet WHERE VisitPrice = :visitPrice")
    @Transaction
    List<VisitsVetPetControl> getVisitsByPrice(String visitPrice) throws SQLException;
    // Seleccionar todas las visitas veterinarias de una mascota en un centro determinado
    @Query("SELECT * FROM VisitsVet WHERE IDVet = :vetId AND IDPetVet = :petvetId")
    @Transaction
    List<VisitsVetPetControl> getVisitsByVetAndPet(int vetId, int petvetId) throws SQLException;
    // Seleccionar todas las visitas veterinarias de una mascota a una localidad específica
    @Query("SELECT * FROM VisitsVet WHERE IDPetVet = :petvetId AND LocVet = :vetLoc")
    @Transaction
    List<VisitsVetPetControl> getVisitsByPetAndLoc(int petvetId, String vetLoc) throws SQLException;
}