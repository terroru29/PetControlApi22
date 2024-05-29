package net.petcontrol.PetControlApi22;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

/**
 * Clase que ayudará a gestionar la creación y actualización de la base de datos.
 */
public class DatabaseHelperPetControl extends SQLiteOpenHelper {
    // --- DATOS BASE DE DATOS ---
    private static final String DATABASE_NAME = "PetControl.db";
    private static final int DATABASE_VERSION = 1;

    // --- DATOS TABLAS ---
    // Tabla de TIPOS ANIMALES
    public static final String TABLE_TYPES_PETS = "TypePets";
    public static final String COLUMN_TYPES_PETS_ID = "IDTypePet";
    public static final String COLUMN_TYPES_PETS_TYPE = "TypePet";

    // Tabla de MASCOTAS
    public static final String TABLE_PETS = "Pets";
    public static final String COLUMN_PETS_ID = "IDPet";
    public static final String COLUMN_PETS_ID_TYPE = "IDTypePet";
    public static final String COLUMN_PETS_NAME = "NamePet";
    public static final String COLUMN_PETS_AGE = "AgePet";
    public static final String COLUMN_PETS_BREED = "Breed";
    public static final String COLUMN_PETS_SEX = "SexPet";
    public static final String COLUMN_PETS_PIC = "UriPicPet";
    public static final String COLUMN_PETS_STERILIZATION = "Sterilization";
    public static final String COLUMN_PETS_DESCRIPTION = "DescriptionPet";

    // Tabla de PROPIETARIOS
    public static final String TABLE_OWNERS = "OwnerPet";
    public static final String COLUMN_OWNERS_ID = "IDOwner";
    public static final String COLUMN_OWNERS_NAME = "NameOwner";
    public static final String COLUMN_OWNERS_AGE = "AgeOwner";
    public static final String COLUMN_OWNERS_GENDER = "GenderOwner";
    public static final String COLUMN_OWNERS_BIRTHDAY = "Birthday";
    public static final String COLUMN_OWNERS_PIC = "UriPicOwner";
    public static final String COLUMN_OWNERS_EMAIL = "Email";
    public static final String COLUMN_OWNERS_PASSWORD = "Password";

    // Tabla de VISITAS VETERINARIAS
    public static final String TABLE_VISITS_VET = "VisitsVet";
    public static final String COLUMN_VISITS_ID = "IDVet";
    public static final String COLUMN_VISITS_ID_PET = "IDPet";
    public static final String COLUMN_VISITS_NAME = "NameVet";
    public static final String COLUMN_VISITS_LOC = "LocVet";
    public static final String COLUMN_VISITS_DATE = "Datevisit";
    public static final String COLUMN_VISITS_REASON = "ReasonVisit";
    public static final String COLUMN_VISITS_DIAGNOSIS = "Diagnosis";
    public static final String COLUMN_VISITS_TREATMENT = "Treatment";
    public static final String COLUMN_VISITS_PRICE = "VisitPrice";

    // Tabla de RECORDATORIOS
    public static final String TABLE_REMINDERS = "Reminders";
    public static final String COLUMN_REMINDERS_ID = "IDReminder";
    public static final String COLUMN_REMINDERS_ID_PET = "IDPetReminder";
    public static final String COLUMN_REMINDERS_DATE = "DateReminder";
    public static final String COLUMN_REMINDERS_CONTENT = "ContentReminder";


    // --- CREACIÓN TABLAS ---
    // SQL para crear la tabla de tipos de mascotas
    private static final String TABLE_TYPES_PETS_CREATE =
        "CREATE TABLE if not exists " + TABLE_TYPES_PETS + " (" +
                COLUMN_TYPES_PETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPES_PETS_TYPE + " TEXT);";

    // SQL para crear la tabla de mascotas
    private static final String TABLE_PETS_CREATE =
        "CREATE TABLE if not exists " + TABLE_PETS + " (" +
                COLUMN_PETS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PETS_ID_TYPE + " INTEGER, " +
                COLUMN_PETS_NAME + " TEXT NOT NULL, " +
                COLUMN_PETS_AGE + " INTEGER, " +
                COLUMN_PETS_BREED + " TEXT, " +
                COLUMN_PETS_SEX + " TEXT, " +
                COLUMN_PETS_PIC + " BLOB, " +
                COLUMN_PETS_STERILIZATION + " INTEGER, " +  //0 false; 1 true
                COLUMN_PETS_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_PETS_ID_TYPE + ") " +
                "REFERENCES TABLE_TYPES_PETS(COLUMN_TYPES_PETS_ID));";

    // SQL para crear la tabla de propietarios
    private static final String TABLE_OWNERS_CREATE =
        "CREATE TABLE if not exists " + TABLE_OWNERS + " (" +
                COLUMN_OWNERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OWNERS_NAME + " TEXT NOT NULL, " +
                COLUMN_OWNERS_AGE + " INTEGER, " +
                COLUMN_OWNERS_GENDER + " TEXT, " +
                COLUMN_OWNERS_BIRTHDAY + " TEXT, " +  // Formato ISO8601 (YYYY-MM-DD)
                COLUMN_OWNERS_PIC + " BLOB, " +
                COLUMN_OWNERS_EMAIL + " TEXT NOT NULL, " +
                COLUMN_OWNERS_PASSWORD + " TEXT NOT NULL);";

    // SQL para crear la tabla de visitas veterinarias
    private static final String TABLE_VISITS_VET_CREATE =
        "CREATE TABLE if not exists " + TABLE_VISITS_VET + " (" +
                COLUMN_VISITS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VISITS_ID_PET + " INTEGER, " +
                COLUMN_VISITS_NAME + " TEXT, " +
                COLUMN_VISITS_LOC + " TEXT, " +
                COLUMN_VISITS_DATE + " TEXT, " +   //TODO formato ISO8601 (YYYY-MM-DD HH:MM:SS)
                COLUMN_VISITS_REASON + " TEXT, " +
                COLUMN_VISITS_DIAGNOSIS + " TEXT, " +
                COLUMN_VISITS_TREATMENT + " TEXT, " +
                COLUMN_VISITS_PRICE + " REAL, " +
                "FOREIGN KEY (" + COLUMN_VISITS_ID_PET + ") " +
                "REFERENCES TABLE_PETS(COLUMN_PETS_ID));";

    // SQL para crear la tabla de recordatorios
    private static final String TABLE_REMINDERS_CREATE =
        "CREATE TABLE if not exists " + TABLE_REMINDERS + " (" +
                COLUMN_REMINDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REMINDERS_ID_PET + " INTEGER, " +
                COLUMN_REMINDERS_DATE + " TEXT, " +   //TODO formato ISO8601 (YYYY-MM-DD HH:MM:SS)
                COLUMN_REMINDERS_CONTENT + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_REMINDERS_ID_PET + ") " +
                "REFERENCES TABLE_PETS(COLUMN_PETS_ID));";


    // --- CREACIÓN BD ---
    public DatabaseHelperPetControl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // --- EJECUTAR CREACIÓN TABLAS ---
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Verificar si la tabla tipo de animales existe
        if (!isTableExists(db, TABLE_TYPES_PETS)) {
            // Si no existe, se crea
            db.execSQL(TABLE_TYPES_PETS_CREATE);
            // Inserción de tipos de animales predeterminados
            insertDefaultTypes(db);
        }
        // Verificar si la tabla mascotas existe
        if (!isTableExists(db, TABLE_PETS))
            // Si no existe, se crea
            db.execSQL(TABLE_PETS_CREATE);
        // Verificar si la tabla usuarios existe
        if (!isTableExists(db, TABLE_OWNERS))
            // Si no existe, se crea
            db.execSQL(TABLE_OWNERS_CREATE);
        // Verificar si la tabla visitas veterinarias existe
        if (!isTableExists(db, TABLE_VISITS_VET))
            // Si no existe, se crea
            db.execSQL(TABLE_VISITS_VET_CREATE);
        // Verificar si la tabla recordatorios existe
        if (!isTableExists(db, TABLE_REMINDERS))
            // Si no existe, se crea
            db.execSQL(TABLE_REMINDERS_CREATE);
    }


    // --- ACTUALIZAR BD (eliminar tablas existentes y llamar a crear las nuevas actualizadas) ---
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES_PETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITS_VET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(db);
    }


    // --------------- MÉTODOS ---------------
    // Método para verificar si una tabla existe en la base de datos
    private boolean isTableExists(@NonNull SQLiteDatabase db, String tableName) {
        /**
         * La consulta se adapta a los nombres de las tablas definidos en tu clase y te permite
         * verificar la existencia de cualquier tabla según sea necesario.
         *
         * name: Columna que contiene los nombres de las tablas en la tabla de sistema específica.
         * sqlite_master: Tabla de sistema específica que contiene información sobre las tablas.
         * type='table': Identificar las entradas que representan tablas en la tabla de sistema
         *                  específica.
         */
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{tableName});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    private void insertDefaultTypes(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        String[] defaultTypes = {"Perro", "Gato", "Hamster", "Pez", "Raton", "Pajaro", "Conejo",
                "Tortuga", "Huron", "Cerdo", "Tarantula", "Serpiente"};

        for (String type : defaultTypes) {
            contentValues.put(COLUMN_TYPES_PETS_TYPE, type);
            db.insert(TABLE_TYPES_PETS, null, contentValues);
        }
    }
}