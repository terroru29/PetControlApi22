package net.petcontrol.PetControlApi22;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que maneja las operaciones CRUD para interactuar con la base de datos.
 */
public class DatabaseManagerPetControl {
    private DatabaseHelperPetControl dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManagerPetControl(Context context) {
        this.context = context;
    }

    // Apertura BD
    public DatabaseManagerPetControl open() throws SQLException {
        dbHelper = new DatabaseHelperPetControl(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // Cierre BD
    public void close() {
        dbHelper.close();
    }


    // --- INSERCIONES ---
    // Insertar tipo de mascota
    public void insertTypes(String type_pet) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE, type_pet);
        database.insert(DatabaseHelperPetControl.TABLE_TYPES_PETS, null, contentValues);
    }
    // Insertar mascota
    public void insertPets(int id_type, String name, int age, String breed, String sex_pet, String pic_pet,
                           String sterilization, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE, id_type);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_BREED, breed);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_SEX, sex_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_PIC, pic_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION, sterilization);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION, description);

        database.insert(DatabaseHelperPetControl.TABLE_PETS, null, contentValues);
    }
    // Insertar propietario
    public void insertOwner(String name, int age, String gender, String pic_owner,
                           LocalDate birthday, String contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER, gender);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PIC, pic_owner);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY, String.valueOf(birthday));
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_CONTACT, contact);

        database.insert(DatabaseHelperPetControl.TABLE_OWNERS, null, contentValues);
    }
    // Insertar visita veterinaria
    public void insertVisitVet(int id_pet, String name, String loc, LocalDateTime date,
                           String reason, String diagnosis, String treatment, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_ID_PET, id_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_LOC, loc);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DATE, String.valueOf(date));
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_REASON, reason);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DIAGNOSIS, diagnosis);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_TREATMENT, treatment);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_PRICE, price);
        database.insert(DatabaseHelperPetControl.TABLE_VISITS_VET, null, contentValues);
    }
    // Insertar recordatorio
    public void insertReminder(LocalDate date, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_REMINDERS_DATE, String.valueOf(date));
        contentValues.put(DatabaseHelperPetControl.COLUMN_REMINDERS_CONTENT, content);

        database.insert(DatabaseHelperPetControl.TABLE_REMINDERS, null, contentValues);
    }


    // --- CONSULTAS ---
    // Obtener todos los usuarios
    public Cursor fetchAllTypesPets() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE};
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_TYPES_PETS, columns,
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener todas las mascotas
    public Cursor fetchAllPets() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_PETS_ID,
                DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE,
                DatabaseHelperPetControl.COLUMN_PETS_NAME,
                DatabaseHelperPetControl.COLUMN_PETS_AGE,
                DatabaseHelperPetControl.COLUMN_PETS_BREED,
                DatabaseHelperPetControl.COLUMN_PETS_SEX,
                DatabaseHelperPetControl.COLUMN_PETS_PIC,
                DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION,
                DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION};
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_PETS, columns, null,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener todos los propietarios
    public Cursor fetchAllOwners() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_OWNERS_ID,
                DatabaseHelperPetControl.COLUMN_OWNERS_NAME,
                DatabaseHelperPetControl.COLUMN_OWNERS_AGE,
                DatabaseHelperPetControl.COLUMN_OWNERS_GENDER,
                DatabaseHelperPetControl.COLUMN_OWNERS_PIC,
                DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY,
                DatabaseHelperPetControl.COLUMN_OWNERS_CONTACT};
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_OWNERS, columns, null,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener todas las visitas veterinarias
    public Cursor fetchAllVisitsVet() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_VISITS_ID,
                DatabaseHelperPetControl.COLUMN_VISITS_ID_PET,
                DatabaseHelperPetControl.COLUMN_VISITS_NAME,
                DatabaseHelperPetControl.COLUMN_VISITS_LOC,
                DatabaseHelperPetControl.COLUMN_VISITS_DATE,
                DatabaseHelperPetControl.COLUMN_VISITS_REASON,
                DatabaseHelperPetControl.COLUMN_VISITS_DIAGNOSIS,
                DatabaseHelperPetControl.COLUMN_VISITS_TREATMENT,
                DatabaseHelperPetControl.COLUMN_VISITS_PRICE};
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_VISITS_VET, columns, null,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener todas los recordatorios
    public Cursor fetchAllReminders() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_REMINDERS_ID,
                DatabaseHelperPetControl.COLUMN_REMINDERS_DATE,
                DatabaseHelperPetControl.COLUMN_REMINDERS_CONTENT};
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_REMINDERS, columns, null,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }


    // --- ACTUALIZACIONES ---
    // Actualizar tipos de animales
    public int updateTypePet(long id, String name, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE, email);
        return database.update(DatabaseHelperPetControl.TABLE_TYPES_PETS, contentValues,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID + " = " + id, null);
    }
    // Actualizar mascota
    public int updatePet(long id, int id_type, String name, int age, String breed, String sex_pet, String pic_pet,
                             String sterilization, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE, id_type);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_BREED, breed);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_SEX, sex_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_PIC, pic_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION, sterilization);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION, description);
        return database.update(DatabaseHelperPetControl.TABLE_PETS, contentValues,
                DatabaseHelperPetControl.COLUMN_PETS_ID + " = " + id, null);
    }
    // Actualizar propietario
    public int updateOwner(long id, String name, int age, String gender, String pic_owner,
                           LocalDate birthday, String contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER, gender);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PIC, pic_owner);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY, String.valueOf(birthday));
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_CONTACT, contact);
        return database.update(DatabaseHelperPetControl.TABLE_OWNERS, contentValues,
                DatabaseHelperPetControl.COLUMN_OWNERS_ID + " = " + id, null);
    }
    // Actualizar visita al veterinario
    public int updateVisitVet(long id, int id_pet, String name, String loc, LocalDateTime date,
                              String reason, String diagnosis, String treatment, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_ID_PET, id_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_LOC, loc);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DATE, String.valueOf(date));
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_REASON, reason);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DIAGNOSIS, diagnosis);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_TREATMENT, treatment);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_PRICE, price);
        return database.update(DatabaseHelperPetControl.TABLE_VISITS_VET, contentValues,
            DatabaseHelperPetControl.COLUMN_VISITS_ID + " = " + id+ " AND " +
                    DatabaseHelperPetControl.COLUMN_VISITS_ID_PET + " = " + id_pet + " AND " +
                    DatabaseHelperPetControl.COLUMN_VISITS_DATE + " = " + date, null);
    }
    // Actualizar recordatorios
    public int updateReminder(long id, LocalDate date, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_REMINDERS_DATE, String.valueOf(date));
        contentValues.put(DatabaseHelperPetControl.COLUMN_REMINDERS_CONTENT, content);
        return database.update(DatabaseHelperPetControl.TABLE_REMINDERS, contentValues,
                DatabaseHelperPetControl.COLUMN_REMINDERS_ID + " = " + id, null);
    }


    // --- BORRADOS ---
    // Eliminar tipo de animal
    public void deleteTypePet(long id) {
        database.delete(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID + " = " + id, null);
    }
    // Eliminar mascota
    public void deletePet(long id, int id_type) {
        database.delete(DatabaseHelperPetControl.TABLE_PETS,
                DatabaseHelperPetControl.COLUMN_PETS_ID + " = " + id, null);
    }
    // Eliminar propietario
    public void deleteOwner(long id) {
        database.delete(DatabaseHelperPetControl.TABLE_OWNERS,
                DatabaseHelperPetControl.COLUMN_OWNERS_ID + " = " + id, null);
    }
    // Eliminar visita veterinaria  TODO revisar PK
    public void deleteVisitVet(long id, int id_pet, LocalDateTime date) {
        database.delete(DatabaseHelperPetControl.TABLE_VISITS_VET,
                DatabaseHelperPetControl.COLUMN_VISITS_ID + " = " + id + " AND " +
                        DatabaseHelperPetControl.COLUMN_VISITS_ID_PET + " = " + id_pet + " AND " +
                DatabaseHelperPetControl.COLUMN_VISITS_DATE + " = " + date, null);
    }
    // Eliminar recordatorio
    public void deleteReminder(long id) {
        database.delete(DatabaseHelperPetControl.TABLE_REMINDERS,
                DatabaseHelperPetControl.COLUMN_REMINDERS_ID + " = " + id, null);
    }
}