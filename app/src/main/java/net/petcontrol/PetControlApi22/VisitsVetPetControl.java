package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

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
    Date date_visit;
    @ColumnInfo(name = "ReasonVisit")
    String reason_visit;
    @ColumnInfo(name = "Diagnosis")
    String diagnosis;
    @ColumnInfo(name = "Treatment")
    String treatment;
    @ColumnInfo(name = "VisitPrice")
    Double visit_price;
}
