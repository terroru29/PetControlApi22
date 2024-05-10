package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Pets",
        foreignKeys = @ForeignKey(entity = TypePetsPetControl.class,
                // Nombre columna en la entidad padre --> TypePetsPetControl
                parentColumns = "id_type_pet",
                // Nombre columna en la entidad hijo --> PetsPetControl
                childColumns = "id_type_pet",
                // Si el tipo de animal se extingue, el registro del animal se elimina --> Cascade
                onDelete = ForeignKey.CASCADE)) //TODO pensar si poner mejor RESTRICT (VisitsVet)
public class PetsPetControl {
    //Atributos --> Columnas
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDPet")
    int id_pet;
    int id_type_pet;    // FK que referencia a la entidad TypesPetsPetControl --> Tipo animal
    @ColumnInfo(name = "NamePet")
    String name_pet;
    @ColumnInfo(name = "AgePet")
    int age_pet;
    @ColumnInfo(name = "Breed")
    String breed;
    @ColumnInfo(name = "SexPet")
    String sex_pet;
    // Almacenamos la ruta de la imagen que se guardará en el sistema de archivos (Internal Storage)
    // para referenciarla más tarde en la BD
    @ColumnInfo(name = "UriPicPet")
    String uri_pic_pet;
    @ColumnInfo(name = "WelcomeDatePet")
    Date welcome_date_pet;
    @ColumnInfo(name = "DescriptionPet")
    String description_pet;




}