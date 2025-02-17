package net.petcontrol.PetControlApi22;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que maneja las operaciones CRUD para interactuar con la base de datos.
 */
public class DatabaseManagerPetControl implements AutoCloseable {
    private DatabaseHelperPetControl dbHelper;
    private final Context context;
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
    public DatabaseManagerPetControl openRead() throws SQLException {
        dbHelper = new DatabaseHelperPetControl(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }


    // Cierre BD
    public void close() {
        dbHelper.close();
    }


    // --- INSERCIONES ---
    /**
     * Inserta un nuevo tipo de mascota en la base de datos, en caso de que no exista previamente.
     *
     * @param type_pet El tipo de mascota que se va a insertar.
     *                 La primera letra se convertirá en mayúscula antes de la inserción para estar
     *                 en consonancia con los demás tipos.
     * @throws UnsupportedOperationException Si el tipo de mascota ya existe en la base de datos,
     *                                       se lanza esta excepción y la inserción no se produce.
     */
    public void insertTypes(String type_pet) {
        try {
            // Comprobar si el tipo de animal ya existe
            if (isValueExists(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                    DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE, type_pet)) {
                // No permitir inserciones a la tabla TABLE_TYPES_PETS
                throw new UnsupportedOperationException("No se permite la inserción de ese tipo de " +
                        "animal.");
            } else {
                ContentValues contentValues = new ContentValues();

                // El tipo de animal se insertará con la primera letra mayúscula
                contentValues.put(DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE,
                        StringUtils.capitalize(type_pet));
                database.insert(DatabaseHelperPetControl.TABLE_TYPES_PETS, null,
                        contentValues);
            }
        } catch (SQLException sql) {
            Log.e("DatabaseManagerPetControl", "Error al insertar tipo de mascota", sql);
        }
    }
    /**
     * Inserta una nueva mascota en la base de datos.
     *
     * @param id_type      El ID del tipo de mascota.
     * @param name         El nombre de la mascota.
     * @param age          La edad de la mascota.
     * @param breed        La raza de la mascota.
     * @param sex_pet      El sexo de la mascota (Hembra/Macho).
     * @param pic_pet      La imagen de la mascota en formato Bitmap.
     * @param sterilization Indica si la mascota está esterilizada (0 si no lo está; 1 si lo está).
     * @param description  Una descripción física y/o personal de la mascota.
     * @throws UnsupportedOperationException Si la mascota ya existe en la base de datos, se lanza
     *                                          esta excepción y la inserción no se produce.
     */
    public void insertPets(int id_type, String name, int age, String breed, String sex_pet,
                           Bitmap pic_pet, int sterilization, String description) {
        try {
            // Verifica si el nombre de la mascota ya existe en la base de datos
            if (isValueExists(DatabaseHelperPetControl.TABLE_PETS,
                    DatabaseHelperPetControl.COLUMN_PETS_NAME, name)) {
                // Si el nombre de la mascota ya está guardado para otro animal, lanza una excepción
                throw new IllegalArgumentException("El nombre de la mascota ya está guardado para otro " +
                        "animal.");
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE, id_type);
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_NAME, name); // Obligatorio
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_AGE, age);   //0 por defecto
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_BREED,
                        breed != null ? breed : ""); // Si no se indica raza, inserta cadena vacía
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_SEX,
                        sex_pet != null ? sex_pet : ""); // Si no se proporciona sexo, inserta cadena vacía
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_PIC,
                        pic_pet != null ? getBitmapAsByteArray(pic_pet) : null); // Si no se proporciona imagen, insertar null
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION, sterilization);   //0 por defecto
                contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION,
                        description != null ? description : ""); // Si no se proporciona una descripción, inserta una cadena vacía

                // Inserta la mascota en su respectiva tabla
                long result = database.insert(DatabaseHelperPetControl.TABLE_PETS, null,
                        contentValues);

                if (result == -1)
                    // Error en la inserción
                    Log.e("DatabaseManagerPetControl", "Error al insertar la mascota.");
                else
                    // Inserción exitosa
                    Log.d("DatabaseManagerPetControl", "Mascota insertada correctamente.");
            }
        } catch (Exception e) {
            // Manejo de cualquier excepción que pueda ocurrir
            Log.e("DatabaseManagerPetControl", "Exception: " + e.getMessage(), e);
        }
    }
    /**
     * Inserta un nuevo propietario en la base de datos.
     *
     * @param name       El nombre del propietario.
     * @param age        La edad del propietario.
     * @param gender     El género del propietario (Masculino/Femenino).
     * @param pic_owner  La imagen del propietario en formato Bitmap.
     * @param birthday   La fecha de nacimiento del propietario en formato aaaa-MM-dd.
     * @param email      El correo electrónico (e-mail)
     * @param pass       La contraseña para acceder a la app
     * @throws UnsupportedOperationException Si existe algún propietario en la base de datos, se
     *                                          lanza esta excepción y la inserción no se produce.
     */
    public void insertOwner(String name, int age, String gender, Bitmap pic_owner, String birthday,
                            String email, String pass) {
        // Verifica si ya existe un propietario almacenado
        if (isOwnerStored()) {
            // Si ya existe un propietario almacenado, muestra un mensaje y no permite la inserción
            throw new IllegalStateException("El número máximo de usuarios debe ser 1.");
        } else {
            try {
                ContentValues contentValues = new ContentValues();
                // Verifica cada campo y si es nulo, inserta un valor por defecto o null
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_NAME, name);   // Obligatorio
                // Si no se inserta fecha de nacimiento, la edad por defecto será de 16 años
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_AGE, age != 0 ? age : 16);
                // Si no se elige género, inserta cadena vacía
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER,
                        gender != null ? gender : "");
                // Si no se indica fecha de cumpleaños, se inserta una cadena vacía
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY,
                        birthday != null ? birthday : "");
                // Si no hay imagen, insertar null
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PIC,
                        pic_owner != null ? getBitmapAsByteArray(pic_owner) : null);
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL, email); // Obligatorio
                contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD, pass);   // Obligatorio

                // Inserta al propietario en su respectiva tabla
                long result = database.insert(DatabaseHelperPetControl.TABLE_OWNERS, null,
                        contentValues);

                if (result == -1)
                    // Error en la inserción
                    Log.e("DatabaseManagerPetControl", "Error al insertar el propietario.");
                else
                    // Inserción exitosa
                    Log.d("DatabaseManagerPetControl", "Propietario insertado correctamente.");
            } catch (Exception e) {
                // Manejo de cualquier excepción que pueda ocurrir
                Log.e("DatabaseManagerPetControl", "Exception: " + e.getMessage(), e);
            }
        }
    }
    //TODO Eliminar tabla
    /**
     * Inserta una nueva visita al veterinario en la base de datos.
     *
     * @param id_pet     El ID de la mascota asociada a la visita.
     * @param name       El nombre del veterinario.
     * @param loc        La ubicación de la clínica veterinaria.
     * @param date       La fecha de la visita en formato aaaa-MM-dd HH:mm:ss.
     * @param reason     El motivo de la visita.
     * @param diagnosis  El diagnóstico realizado durante la visita.
     * @param treatment  El tratamiento recomendado por el veterinario.
     * @param price      El precio de la visita.
     */
    public void insertVisitVet(int id_pet, String name, String loc, String date,
                           String reason, String diagnosis, String treatment, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_ID_PET, id_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_LOC, loc);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DATE, date);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_REASON, reason);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DIAGNOSIS, diagnosis);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_TREATMENT, treatment);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_PRICE, price);

        // Inserta la visita al veterinario en su respectiva tabla
        database.insert(DatabaseHelperPetControl.TABLE_VISITS_VET, null, contentValues);
    }
    /**
     * Inserta un nuevo evento en la base de datos.
     *
     * @param date    La fecha del evento con formato aaaa-MM-dd ~HH:mm:ss~.
     * @param title   El título del evento.
     * @param content El contenido del evento.
     */
    public void insertEvent(String date, String title, String content) {
        /*try {
            // Verifica si el título del evento ya existe en la base de datos
            if (isValueExists(DatabaseHelperPetControl.TABLE_EVENTS,
                    DatabaseHelperPetControl.COLUMN_EVENTS_TITLE, title)) {
                // Si el título del evento ya está guardado para otro evento, lanza una excepción
                throw new IllegalArgumentException("El título del evento ya está guardado para otro " +
                        "evento.");
            } else {*/
                ContentValues contentValues = new ContentValues();

                contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_DATE, date);   // Obligatorio
                // El título del evento tendrá la primera letra mayúscula
                contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_TITLE,
                        StringUtils.capitalize(title)); // Obligatorio
                contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_CONTENT,
                        content != null ? content : "");    // Si no texto, BD tendrá cadena vacía

                // Inserta el evento en su respectiva tabla
                //database.insert(DatabaseHelperPetControl.TABLE_EVENTS, null, contentValues);
                // Inserta el evento en su respectiva tabla
                long result = database.insert(DatabaseHelperPetControl.TABLE_EVENTS, null,
                        contentValues);

                if (result == -1)
                    // Error en la inserción
                    Log.e("DatabaseManagerPetControl", "Error al insertar el evento.");
                else
                    // Inserción exitosa
                    Log.d("DatabaseManagerPetControl", "Evento agregado correctamente.");
            /*}
        } catch (Exception e) {
            // Manejo de cualquier excepción que pueda ocurrir
            Log.e("DatabaseManagerPetControl", "Exception: " + e.getMessage(), e);
        }*/
    }


    // --- CONSULTAS ---
    // Obtener todos los tipos de animales almacenados
    public List<TypePetsPetControl> fetchAllTypesPets() {
        List<TypePetsPetControl> typePets = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                     new String[]{DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID,
                             DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE},
                     null, null, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow
                            (DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow
                            (DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE));
                    typePets.add(new TypePetsPetControl(id, type));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Database Error", "Error while trying to fetch types of pets from database", e);
        }
        return typePets;
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
    // Obtener una mascota concreta
    public Cursor fetchOnePet(int id) {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_PETS_ID,
                DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE,
                DatabaseHelperPetControl.COLUMN_PETS_NAME,
                DatabaseHelperPetControl.COLUMN_PETS_AGE,
                DatabaseHelperPetControl.COLUMN_PETS_BREED,
                DatabaseHelperPetControl.COLUMN_PETS_SEX,
                DatabaseHelperPetControl.COLUMN_PETS_PIC,
                DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION,
                DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION};

        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_PETS, columns,
                DatabaseHelperPetControl.COLUMN_PETS_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener datos básicos de las mascotas
    public Cursor fetchDataPets() {
        String[] columns = new String[] { DatabaseHelperPetControl.COLUMN_PETS_NAME,
                DatabaseHelperPetControl.COLUMN_PETS_AGE,
                DatabaseHelperPetControl.COLUMN_PETS_SEX,
                DatabaseHelperPetControl.COLUMN_PETS_PIC };

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
                DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY,
                DatabaseHelperPetControl.COLUMN_OWNERS_PIC,
                DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL,
                DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD};

        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_OWNERS, columns,
                null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener un propietario
    public Cursor fetchOneOwner(int id) {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_OWNERS_ID,
                DatabaseHelperPetControl.COLUMN_OWNERS_NAME,
                DatabaseHelperPetControl.COLUMN_OWNERS_AGE,
                DatabaseHelperPetControl.COLUMN_OWNERS_GENDER,
                DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY,
                DatabaseHelperPetControl.COLUMN_OWNERS_PIC,
                DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL,
                DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD};

        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_OWNERS, columns,
                DatabaseHelperPetControl.COLUMN_OWNERS_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener datos específicos del propietario
    public Cursor fetchOwnerDetails(String name) {
        // Definir las columnas que quieres recuperar
        String[] columns = new String[] { DatabaseHelperPetControl.COLUMN_OWNERS_NAME,
                DatabaseHelperPetControl.COLUMN_OWNERS_AGE,
                DatabaseHelperPetControl.COLUMN_OWNERS_GENDER,
                DatabaseHelperPetControl.COLUMN_OWNERS_PIC };

        // Realizar la consulta
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_OWNERS,  // Tabla
                columns,                               // Columnas a recuperar
                DatabaseHelperPetControl.COLUMN_OWNERS_NAME + " = ?",
                new String[]{name},                     // Argumentos
                null,                                  // Group by
                null,                                  // Having
                null                                   // Order by
        );

        // Si el cursor no es nulo y tiene al menos una fila, obtener los datos
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    // Obtener todas las visitas veterinarias
    //TODO Eliminar tabla
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
    // Obtener todas los eventos
    public Cursor fetchAllEvents() {
        String[] columns = new String[] {DatabaseHelperPetControl.COLUMN_EVENTS_ID,
                //DatabaseHelperPetControl.COLUMN_EVENTS_ID_PET,
                DatabaseHelperPetControl.COLUMN_EVENTS_DATE,
                DatabaseHelperPetControl.COLUMN_EVENTS_TITLE,
                DatabaseHelperPetControl.COLUMN_EVENTS_CONTENT};

        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_EVENTS, columns, null,
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }


    // --- UPDATES ---
    /**
     * Actualiza un tipo de animal en la base de datos.
     *
     * @param type El nombre del tipo del animal a actualizar.
     * @throws UnsupportedOperationException Si el tipo de animal ya existe en la base de datos, se
     *                                          lanza esta excepción y la modificación no se produce.
     */
    public void updateTypePet(String type) {
        if (isValueExists(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_TYPE, type))
            // No permitir actualizaciones a los tipos de animales predeterminados
            throw new UnsupportedOperationException("Las actualizaciones en este tipo de animal no " +
                    "están permitidas.");
    }
    // Actualizar mascota
    public int updatePet(int id, int id_type, String name, int age, String breed, String sex_pet,
                         Bitmap pic_pet, int sterilization, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_ID_TYPE, id_type);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_BREED, breed);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_SEX, sex_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_PIC, getBitmapAsByteArray(pic_pet));
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_STERILIZATION, sterilization);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PETS_DESCRIPTION, description);
        return database.update(DatabaseHelperPetControl.TABLE_PETS, contentValues,
                DatabaseHelperPetControl.COLUMN_PETS_ID + " = " + id, null);
    }
    // Actualizar propietario
    public int updateOwner(int id, String name, int age, String gender, Bitmap pic_owner,
                           String birthday, String email, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_AGE, age);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_GENDER, gender);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_BIRTHDAY, birthday);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PIC, getBitmapAsByteArray(pic_owner));
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL, email);
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD, pass);
        return database.update(DatabaseHelperPetControl.TABLE_OWNERS, contentValues,
                DatabaseHelperPetControl.COLUMN_OWNERS_ID + " = " + id, null);
    }
    // Actualizar contraseña usuario
    public int updatePasswordOwner(String pass, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_OWNERS_PASSWORD, pass);

        String whereClause = DatabaseHelperPetControl.COLUMN_OWNERS_EMAIL + " = ?";
        // Valores que reemplazan los marcadores de posición (?) en la cláusula 'Where'
        String[] whereArgs = new String[]{email};

        return database.update(DatabaseHelperPetControl.TABLE_OWNERS, contentValues,
                whereClause, whereArgs);
    }
    // Actualizar visita al veterinario
    //TODO Eliminar tabla
    public int updateVisitVet(int id, int id_pet, String name, String loc, String date,
                              String reason, String diagnosis, String treatment, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_ID_PET, id_pet);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_LOC, loc);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DATE, date);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_REASON, reason);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_DIAGNOSIS, diagnosis);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_TREATMENT, treatment);
        contentValues.put(DatabaseHelperPetControl.COLUMN_VISITS_PRICE, price);
        return database.update(DatabaseHelperPetControl.TABLE_VISITS_VET, contentValues,
            DatabaseHelperPetControl.COLUMN_VISITS_ID + " = " + id + " AND " +
                    DatabaseHelperPetControl.COLUMN_VISITS_ID_PET + " = " + id_pet + " AND " +
                    DatabaseHelperPetControl.COLUMN_VISITS_DATE + " = " + date, null);
    }
    // Actualizar eventos
    //TODO Revisar cláusula Where --> parametrizarla (=?)
    public int updateEvent(int id, String date, String title, String content) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_DATE, date);
        contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_TITLE, title);
        contentValues.put(DatabaseHelperPetControl.COLUMN_EVENTS_CONTENT, content);
        return database.update(DatabaseHelperPetControl.TABLE_EVENTS, contentValues,
                DatabaseHelperPetControl.COLUMN_EVENTS_ID + " = " + id, null);
    }


    // --- BORRADOS ---
    // Eliminar tipo de animal
    public void deleteTypePet(int id, String type_pet) {
        if (isValueExists(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID, type_pet)) {
            // No permitir eliminar los tipos de animales predeterminados
            throw new UnsupportedOperationException("No está permitido borrar un tipo de animal.");
        }
        database.delete(DatabaseHelperPetControl.TABLE_TYPES_PETS,
                DatabaseHelperPetControl.COLUMN_TYPES_PETS_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
    // Eliminar mascota
    public void deletePet(int id) {
        database.delete(DatabaseHelperPetControl.TABLE_PETS,
                DatabaseHelperPetControl.COLUMN_PETS_ID + " = " + id, null);
    }
    // Eliminar todas las mascotas
    public void deleteAllPets() {
        database.delete(DatabaseHelperPetControl.TABLE_PETS, null, null);
    }
    // Eliminar propietario
    public void deleteOwner(int id) {
        database.delete(DatabaseHelperPetControl.TABLE_OWNERS,
                DatabaseHelperPetControl.COLUMN_OWNERS_ID + " = " + id, null);
    }
    // Eliminar todos los propietarios
    public void deleteAllOwners() {
        database.delete(DatabaseHelperPetControl.TABLE_OWNERS, null, null);
    }
    // Eliminar visita veterinaria
    //TODO Revisar PK y eliminar tabla
    public void deleteVisitVet(int id, int id_pet, LocalDateTime date) {
        database.delete(DatabaseHelperPetControl.TABLE_VISITS_VET,
                DatabaseHelperPetControl.COLUMN_VISITS_ID + " = " + id + " AND " +
                        DatabaseHelperPetControl.COLUMN_VISITS_ID_PET + " = " + id_pet + " AND " +
                DatabaseHelperPetControl.COLUMN_VISITS_DATE + " = " + date, null);
    }
    // Eliminar evento
    public void deleteEvent(int id) {
        database.delete(DatabaseHelperPetControl.TABLE_EVENTS,
                DatabaseHelperPetControl.COLUMN_EVENTS_ID + " = " + id, null);
    }


    // --------------- MÉTODOS ---------------
    // Recuperar y usar la imagen de un registro específico
    public Bitmap getPetImage(int id) {
        Cursor cursor = fetchOnePet(id);

        if (cursor != null && cursor.getCount() > 0) {
            byte[] blob = cursor.getBlob(cursor.getColumnIndexOrThrow
                    (DatabaseHelperPetControl.COLUMN_PETS_PIC));
            cursor.close();
            return getBitmapFromByteArray(blob);
        }
        return null;
    }
    public Bitmap getUserImage(int id) {
        Cursor cursor = fetchOneOwner(id);

        if (cursor != null && cursor.getCount() > 0) {
            byte[] blob = cursor.getBlob(cursor.getColumnIndexOrThrow
                    (DatabaseHelperPetControl.COLUMN_OWNERS_PIC));
            cursor.close();
            return getBitmapFromByteArray(blob);
        }
        return null;
    }
    // Convertir Bitmap a Byte Array
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("getBitmapAsByteArray", "Bitmap es null.");
            return new byte[0]; // Devuelve un array vacío o maneja el caso de manera adecuada
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
    // Convertir Byte Array a Bitmap
    public Bitmap getBitmapFromByteArray(byte[] blob) {
        if (blob == null || blob.length == 0) {
            // Manejar el caso en el que byteArray es null o está vacío
            Log.e("DatabaseError", "ByteArray es null o está vacío");
            return null;
        }
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }
    @SuppressLint("Range")
    public void formatDate(final String table, final String column) {
        try (Cursor cursor = database.query(table, new String[]{column}, null, null,
                null, null, null)) {
            if (cursor.moveToFirst()) {
                String dateStr = cursor.getString(cursor.getColumnIndex(column));
                LocalDateTime dateTime = LocalDateTime.parse(dateStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    @SuppressLint("Range")
    public void formatPrice(final String table, final String column) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        try (Cursor cursor = database.query(table, new String[]{column}, null, null,
                null, null, null)) {
            if (cursor.moveToFirst()) {
                double price = cursor.getDouble(cursor.getColumnIndex(column));
                String formattedPrice = decimalFormat.format(price);
                System.out.println("Formatted Price: " + formattedPrice); // Example usage
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    /**
     * Verifica si un valor específico ya existe en una columna de una tabla en la base de datos.
     *
     * @param tableName El nombre de la tabla a verificar.
     * @param columnName El nombre de la columna a verificar.
     * @param value El valor a buscar en la columna.
     * @return {@code true} si el valor ya existe en la columna de la tabla,
     *          {@code false} lo contrario.
     */
    private boolean isValueExists(String tableName, String columnName, String value) {
        // Consulta SQL para verificar si el valor ya existe en la columna de la tabla
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";

        // Inicializar el cursor
        try (Cursor cursor = database.rawQuery(query, new String[]{value})) {
            // Si el cursor no es nulo y tiene al menos una fila, significa que el valor ya existe
            if (cursor != null && cursor.moveToFirst()) {
                // Obtiene el valor de la primera columna de dicha fila
                int count = cursor.getInt(0);
                return count > 0;
            }
        } catch (SQLiteException e) {
            Log.e("Database Error", "Error while checking value existence in database", e);
        }
        // Si el cursor es nulo o no tiene filas, el valor no existe o ha ocurrido un error
        return false;
    }
    /**
     * Verifica si ya se ha almacenado un propietario en la base de datos.
     *
     * @return {@code true} si ya existe al menos un propietario almacenado,
     *          {@code false} de lo contrario.
     */
    private boolean isOwnerStored() {
        // Consulta SQL para contar el número de propietarios almacenados
        String query = "SELECT COUNT(*) FROM " + DatabaseHelperPetControl.TABLE_OWNERS;

        // Ejecuta la consulta
        Cursor cursor = database.rawQuery(query, null);

        // Obtiene el número de propietarios almacenados
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        // Retorna true si ya existe al menos un propietario almacenado, false de lo contrario
        return count > 0;
    }
    // Contar todos los propietarios
    public int countAllOwners() {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelperPetControl.TABLE_OWNERS;
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }
}