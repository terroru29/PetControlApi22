package net.petcontrol.PetControlApi22;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;


/**
 * Clase entidad (tabla de la BD) que representa al usuario de la aplicación.
 */
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
    LocalDate birthday;
    @ColumnInfo(name = "Contact")   // E-mail
    String contact;


    // Constructores
    /**
     * Constructor por defecto.
     */
    public OwnerPetControl() {}
    /**
     * Constructor con parámetros.
     *
     * @param id_owner ID del usuario
     * @param name_owner Nombre del usuario
     * @param age_owner Edad del usuario
     * @param gender_owner Género del usuario (Masculino/Femenino/No binario)
     * @param uri_pic_owner Ruta de la imagen seleccionada para el usuario
     * @param birthday Fecha de cumpleaños del usuario (aaaa-MM-dd)
     * @param contact Correo electrónico del usuario
     */
    public OwnerPetControl(int id_owner, String name_owner, int age_owner, String gender_owner,
                           String uri_pic_owner, LocalDate birthday, String contact)
            throws IllegalArgumentException  {
        this.id_owner = id_owner;
        this.name_owner = name_owner;
        this.age_owner = age_owner;
        this.gender_owner = gender_owner;
        this.uri_pic_owner = uri_pic_owner;
        this.birthday = birthday;
        this.contact = contact;
        // Valida que los campos no estén vacíos o nulos
        validateFieldsOwners();
    }


    // Getters
    public int getId_owner() {
        return id_owner;
    }
    public String getName_owner() {
        return name_owner;
    }
    public int getAge_owner() {
        return age_owner;
    }
    public String getGender_owner() {
        return gender_owner;
    }
    public String getUri_pic_owner() {
        return uri_pic_owner;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public String getContact() {
        return contact;
    }


    // Setters
    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }
    public void setName_owner(String name_owner) {
        this.name_owner = name_owner;
    }
    public void setAge_owner(int age_owner) {
        this.age_owner = age_owner;
    }
    public void setGender_owner(String gender_owner) {
        this.gender_owner = gender_owner;
    }
    public void setUri_pic_owner(String uri_pic_owner) {
        this.uri_pic_owner = uri_pic_owner;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }


    // ------------------ MÉTODOS ------------------
    /**
     * Valida si el usuario tiene los datos correctos.
     * Usar antes de guardar o procesar una instancia de OwnerPetControl para asegurar que los
     * datos son correctos.
     *
     * @throws IllegalArgumentException Si algún campo tiene datos incorrectos
     */
    public void validateFieldsOwners() {
        if (name_owner == null || name_owner.isEmpty())
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        if (age_owner <= 16)
            throw new IllegalArgumentException("La edad del usuario debe ser mayor a dieciseis.");
        if (gender_owner == null || (!gender_owner.equalsIgnoreCase("Masculino")
                && !gender_owner.equalsIgnoreCase("Femenino")
                && !gender_owner.equalsIgnoreCase("No binario")))
            throw new IllegalArgumentException("El género del usuario debe ser 'Masculino', " +
                    "'Femenino' o 'No binario'.");
        if (uri_pic_owner == null || uri_pic_owner.isEmpty())
            throw new IllegalArgumentException("La ruta de la imagen del usuario no puede estar vacía.");
        if (birthday == null)
            throw new IllegalArgumentException("La fecha de cumpleaños del usuario no puede ser nula.");
        if (contact == null || contact.isEmpty())
            throw new IllegalArgumentException("El correo electrónico del usuario no puede estar vacío.");
    }
}