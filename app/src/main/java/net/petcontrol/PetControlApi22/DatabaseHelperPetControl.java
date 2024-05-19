package net.petcontrol.PetControlApi22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String COLUMN_TYPES_PETS_ID = "IDTypePet";  //TODO INTEGER, PRIMARY KEY, AUTOINCREMENT
    public static final String COLUMN_TYPES_PETS_TYPE = "TypePet";

    // Tabla de MASCOTAS
    public static final String TABLE_PETS = "Pets";
    public static final String COLUMN_PETS_ID = "IDPet";    //TODO INTEGER, PRIMARY KEY, AUTOINCREMENT
    public static final String COLUMN_PETS_ID_TYPE = "IDTypePet";   //TODO FK TypesPetsPet --> TypePet
    public static final String COLUMN_PETS_NAME = "NamePet";
    public static final String COLUMN_PETS_AGE = "AgePet";
    public static final String COLUMN_PETS_BREED = "Breed";
    public static final String COLUMN_PETS_SEX = "SexPet";
    public static final String COLUMN_PETS_PIC = "UriPicPet";   //TODO IMG O STRING (URI)
    public static final String COLUMN_PETS_STERILIZATION = "Sterilization"; //TODO BOOL?
    public static final String COLUMN_PETS_DESCRIPTION = "DescriptionPet";

    // Tabla de PROPIETARIOS
    public static final String TABLE_OWNERS = "OwnerPet";
    public static final String COLUMN_OWNERS_ID = "IDOwner";    //TODO INTEGER, PRIMARY KEY, AUTOINCREMENT
    public static final String COLUMN_OWNERS_NAME = "NameOwner";
    public static final String COLUMN_OWNERS_AGE = "AgeOwner";
    public static final String COLUMN_OWNERS_GENDER = "GenderOwner";
    public static final String COLUMN_OWNERS_PIC = "UriPicOwner";   //TODO IMG O STRING (URI)
    public static final String COLUMN_OWNERS_BIRTHDAY = "Birthday"; //TODO LOCALDATE
    public static final String COLUMN_OWNERS_CONTACT = "Contact";

    // Tabla de VISITAS VETERINARIAS
    public static final String TABLE_VISITS_VET = "VisitsVet";
    public static final String COLUMN_VISITS_ID = "IDVet";  //TODO INTEGER, PRIMARY KEY, AUTOINCREMENT
    public static final String COLUMN_VISITS_ID_PET = "IDPet";
    public static final String COLUMN_VISITS_NAME = "NameVet";
    public static final String COLUMN_VISITS_LOC = "LocVet";
    public static final String COLUMN_VISITS_DATE = "Datevisit";    //TODO LOCALDATETIME
    public static final String COLUMN_VISITS_REASON = "ReasonVisit";
    public static final String COLUMN_VISITS_DIAGNOSIS = "Diagnosis";
    public static final String COLUMN_VISITS_TREATMENT = "Treatment";
    public static final String COLUMN_VISITS_PRICE = "VisitPrice";  //TODO DOUBLE, BIGDECIMAL

    // Tabla de RECORDATORIOS
    public static final String TABLE_REMINDERS = "Reminders";
    public static final String COLUMN_REMINDERS_ID = "IDReminder";
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
                COLUMN_PETS_ID_TYPE + " INTEGER, " +   //TODO FK TypesPetsPetControl --> TypePet
                COLUMN_PETS_NAME + " TEXT, " +
                COLUMN_PETS_AGE + " INTEGER, " +
                COLUMN_PETS_BREED + " TEXT, " +
                COLUMN_PETS_SEX + " TEXT, " +
                COLUMN_PETS_PIC + " TEXT, " +   //TODO URI?
                COLUMN_PETS_STERILIZATION + " BOOLEAN, " +  //TODO VÁLIDO?
                COLUMN_PETS_DESCRIPTION + " TEXT);";

    // SQL para crear la tabla de propietarios
    private static final String TABLE_OWNERS_CREATE =
        "CREATE TABLE if not exists " + TABLE_OWNERS + " (" +
                COLUMN_OWNERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OWNERS_NAME + " TEXT, " +
                COLUMN_OWNERS_AGE + " INTEGER, " +
                COLUMN_OWNERS_GENDER + " TEXT, " +
                COLUMN_OWNERS_PIC + " TEXT, " +   //TODO URI?
                COLUMN_OWNERS_BIRTHDAY + " LOCALDATE, " +  //TODO VÁLIDO?
                COLUMN_OWNERS_CONTACT + " TEXT);";

    // SQL para crear la tabla de visitas veterinarias
    private static final String TABLE_VISITS_VET_CREATE =
        "CREATE TABLE if not exists " + TABLE_VISITS_VET + " (" +
                COLUMN_VISITS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VISITS_ID_PET + " INTEGER, " +   //TODO FK PetsPetControl --> IDPets
                COLUMN_VISITS_NAME + " TEXT, " +
                COLUMN_VISITS_LOC + " TEXT, " +
                COLUMN_VISITS_DATE + " LOCALDATETIME, " +   //TODO Válido?
                COLUMN_VISITS_REASON + " TEXT, " +
                COLUMN_VISITS_DIAGNOSIS + " TEXT, " +
                COLUMN_VISITS_TREATMENT + " TEXT, " +
                COLUMN_VISITS_PRICE + " BIGDECIMAL);";  //TODO Válido?

    // SQL para crear la tabla de recordatorios
    private static final String TABLE_REMINDERS_CREATE =
        "CREATE TABLE if not exists " + TABLE_REMINDERS + " (" +
                COLUMN_REMINDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REMINDERS_DATE + " LOCALDATE, " +   //TODO Válido?
                COLUMN_REMINDERS_CONTENT + " TEXT);";


    // --- CREACIÓN BD ---
    public DatabaseHelperPetControl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // --- EJECUTAR CREACIÓN TABLAS ---
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TYPES_PETS_CREATE);
        db.execSQL(TABLE_PETS_CREATE);
        db.execSQL(TABLE_OWNERS_CREATE);
        db.execSQL(TABLE_VISITS_VET_CREATE);
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
}