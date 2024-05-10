package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;


@Entity(tableName = "VisitsVet",
        foreignKeys = @ForeignKey(entity = PetsPetControl.class,
                // Nombre columna en la entidad padre --> PetsPetControl
                parentColumns = "id_pet",
                // Nombre columna en la entidad hijo --> VisitsVetPetControl
                childColumns = "id_pet",
                // La visita no se podrá eliminar, si algún animal la tiene --> Restrict
                onDelete = ForeignKey.RESTRICT))
public class VisitsVetPetControl {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDVet")
    int id_vet;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDPetVet")
    int id_pet_vet;
    int id_pet; // FK que referencia a la entidad PetsPetControl --> Mascota
    @ColumnInfo(name = "NameVet")
    String name_vet;
    @ColumnInfo(name = "LocVet")
    String loc_vet;
    @ColumnInfo(name = "Datevisit")
    LocalDateTime date_visit;
    @ColumnInfo(name = "ReasonVisit")
    String reason_visit;
    @ColumnInfo(name = "Diagnosis")
    String diagnosis;
    @ColumnInfo(name = "Treatment")
    String treatment;
    @ColumnInfo(name = "VisitPrice")
    Double visit_price;


    // Constructores
    public VisitsVetPetControl() {}
    public VisitsVetPetControl(int id_vet, int id_pet_vet, int id_pet, String name_vet,
                               String loc_vet, LocalDateTime date_visit, String reason_visit,
                               String diagnosis, String treatment, Double visit_price) {
        this.id_vet = id_vet;
        this.id_pet_vet = id_pet_vet;
        this.id_pet = id_pet;
        this.name_vet = name_vet;
        this.loc_vet = loc_vet;
        this.date_visit = date_visit;
        this.reason_visit = reason_visit;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.visit_price = visit_price;
    }


    // Getters
    public int getId_vet() {
        return id_vet;
    }
    public int getId_pet_vet() {
        return id_pet_vet;
    }
    public int getId_pet() {
        return id_pet;
    }
    public String getName_vet() {
        return name_vet;
    }
    public String getLoc_vet() {
        return loc_vet;
    }
    public LocalDateTime getDate_visit() {
        return date_visit;
    }
    public String getReason_visit() {
        return reason_visit;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public String getTreatment() {
        return treatment;
    }
    public Double getVisit_price() {
        return visit_price;
    }


    // Setters
    public void setId_vet(int id_vet) {
        this.id_vet = id_vet;
    }
    public void setId_pet_vet(int id_pet_vet) {
        this.id_pet_vet = id_pet_vet;
    }
    public void setId_pet(int id_pet) {
        this.id_pet = id_pet;
    }
    public void setName_vet(String name_vet) {
        this.name_vet = name_vet;
    }
    public void setLoc_vet(String loc_vet) {
        this.loc_vet = loc_vet;
    }
    public void setDate_visit(LocalDateTime date_visit) {
        this.date_visit = date_visit;
    }
    public void setReason_visit(String reason_visit) {
        this.reason_visit = reason_visit;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    public void setVisit_price(Double visit_price) {
        this.visit_price = visit_price;
    }
}