package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase entidad (Tabla en la BD) que representa las visitas a veterinarios
 */
@Entity(tableName = "VisitsVet",
        foreignKeys = @ForeignKey(entity = PetsPetControl.class,
                // Nombre columna en la entidad padre --> PetsPetControl
                parentColumns = "IDPet",
                // Nombre columna en la entidad hijo --> VisitsVetPetControl
                childColumns = "IDPet",
                // La visita no se podrá eliminar, si algún animal la tiene --> Restrict
                onDelete = ForeignKey.RESTRICT),
        // Mejora el rendimiento de las consultas que usan esta columna como referencia de la FK
        indices = {@Index("IDPet")})
@TypeConverters(DataTypeConverterPetControl.class)
public class VisitsVetPetControl {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDVet")
    int id_vet;
    @ColumnInfo(name = "IDPet")
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
    BigDecimal visit_price;


    // Constructores
    /**
     * Constructor por defecto
     */
    public VisitsVetPetControl() {}
    /**
     * Constructor con todos los parámetros
     *
     * @param id_vet El ID del veterinario
     * @param id_pet El ID de la mascota
     * @param name_vet El nombre del veterinario
     * @param loc_vet La localización del veterinario
     * @param date_visit La fecha de la visita al veterinario
     * @param reason_visit El motivo por el que se ha ido al veterinario
     * @param diagnosis El resultado de la visita, tras las pruebas realizadas por un veterinario
     * @param treatment El tratamiento a seguir, en su caso.
     * @param visit_price El precio de la visita
     */
    public VisitsVetPetControl(int id_vet, int id_pet, String name_vet, String loc_vet,
                               LocalDateTime date_visit, String reason_visit, String diagnosis,
                               String treatment, BigDecimal visit_price)
            throws IllegalArgumentException{
        this.id_vet = id_vet;
        this.id_pet = id_pet;
        this.name_vet = name_vet;
        this.loc_vet = loc_vet;
        this.date_visit = date_visit;
        this.reason_visit = reason_visit;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        setVisitPrice(visit_price);
        // Valida que los demás campos no estén vacíos o nulos
        validateFieldsVisits();
    }


    // Getters
    public int getId_vet() {
        return id_vet;
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
    public BigDecimal getVisitPrice() {
        return visit_price;
    }


    // Setters
    public void setId_vet(int id_vet) {
        this.id_vet = id_vet;
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
    public void setVisitPrice(BigDecimal visit_price) {
        if (visit_price == null || visit_price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio de la visita no puede ser nulo o negativo.");
        this.visit_price = visit_price;
    }


    // ------------------ MÉTODOS ------------------
    /**
     * Valida si la visita actual tiene sus datos correctos.
     * Usar antes de guardar o procesar una instancia de VisitsVetPetControl para asegurar que los
     * datos son correctos.
     *
     * @throws IllegalArgumentException Si algún campo tiene datos incorrectos
     */
    public void validateFieldsVisits() {
        if (name_vet == null || name_vet.isEmpty())
            throw new IllegalArgumentException("El nombre del veterinario no puede estar vacío.");
        if (loc_vet == null || loc_vet.isEmpty())
            throw new IllegalArgumentException("La localziación del veterinario no puede estar vacía.");
        if (date_visit == null)
            throw new IllegalArgumentException("La fecha de visita no puede ser nula.");
        if (reason_visit == null || reason_visit.isEmpty())
            throw new IllegalArgumentException("El motivo de la visita no puede estar vacío.");
        if (diagnosis == null || diagnosis.isEmpty())
            throw new IllegalArgumentException("El diagnóstico de la visita no puede estar vacío.");
        if (treatment == null || treatment.isEmpty())
            throw new IllegalArgumentException("El tratamiento de la visita no puede ser nulo.");
        if (visit_price == null || visit_price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio de la visita al veterinario no puede ser " +
                    "negativo.");
    }
}