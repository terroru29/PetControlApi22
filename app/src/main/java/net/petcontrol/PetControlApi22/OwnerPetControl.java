package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "OwnerPet")
public class OwnerPetControl {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDOwner")
    int id_owner;
    @ColumnInfo(name = "NameOwner")
    String name_owner;
    @ColumnInfo(name = "AgeOwner")
    int age_owner;
    @ColumnInfo(name = "GenderOwner")
    String gender_owner;
    // Almacenamos la ruta de la imagen que se guardará en el sistema de archivos (Internal Storage)
    // para referenciarla más tarde en la BD
    @ColumnInfo(name = "UriPicOwner")
    String uri_pic_owner;
    @ColumnInfo(name = "Birthday")
    Date birthday;
    @ColumnInfo(name = "Contact")   // E-mail
    String contact;
}