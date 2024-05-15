package net.petcontrol.PetControlApi22;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Clase que representa la base de datos en Room para la aplicación PetControl.
 * Proporciona una única instancia de la base de datos y maneja adecuadamente el acceso en un
 * entorno multi-hilo.
 */
@Database(entities = {TypePetsPetControl.class, PetsPetControl.class, OwnerPetControl.class,
        VisitsVetPetControl.class}, version = 1)
@TypeConverters(DataTypeConverterPetControl.class)
public abstract class DatabasePetControl extends RoomDatabase {
    // Los cambios serán visibles para todos los hilos inmediatamente
    private static volatile DatabasePetControl INSTANCE;    // Instancia Singleton

    /**
     * Método para obtener la instancia única de la base de datos.
     *
     * @param context El contexto de la aplicación.
     * @return La instancia única de la base de datos.
     */
    public static DatabasePetControl getDatabase(final Context context) {
        if (INSTANCE == null) {
            // Asegura que la instancia de la BD se crea de manera segura en un entorno multi-hilo.
            synchronized (DatabasePetControl.class) {
                if (INSTANCE == null) {
                    // Construye la BD si no existe y maneja las migraciones de ésta sencillamente
                    // durante el desarrollo
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabasePetControl.class, "database_petcontrol")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Métodos abstractos para los DAOs de cada entidad --> Room cree implementaciones en tiempo de compilación.
    public abstract TypePetsDAOPetControl typePetsDAO();
    public abstract PetsDAOPetControl petsDAO();
    public abstract OwnerDAOPetControl ownerDAO();
    public abstract VisitsVetDAOPetControl visitsVetDAO();
}