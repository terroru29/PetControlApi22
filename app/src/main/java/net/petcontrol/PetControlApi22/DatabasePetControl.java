package net.petcontrol.PetControlApi22;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase que representa la base de datos en Room para la aplicación PetControl.
 * Proporciona una única instancia de la base de datos y maneja adecuadamente el acceso en un
 * entorno multi-hilo.
 */
@Database(entities = {TypePetsPetControl.class, PetsPetControl.class, OwnerPetControl.class,
                VisitsVetPetControl.class},
        version = 1, exportSchema = true)
@TypeConverters(DataTypeConverterPetControl.class)
public abstract class DatabasePetControl extends RoomDatabase {
    // Los cambios serán visibles para todos los hilos inmediatamente
    private static volatile DatabasePetControl INSTANCE;    // Instancia Singleton
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract TypePetsDAOPetControl typePetsDAO();
    public abstract PetsDAOPetControl petsDAO();
    public abstract OwnerDAOPetControl ownerDAO();
    public abstract VisitsVetDAOPetControl visitsVetDAO();

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert pre-populated data into the database
            databaseWriteExecutor.execute(() -> {
                // La tabla TypePets se cree y se rellene con los datos predefinidos una única vez
                // cuando la base de datos se crea por primera vez.
                TypePetsDAOPetControl dao = INSTANCE.typePetsDAO();
                if (dao.getAllTypePets().isEmpty()) {
                    dao.addAll(
                        new TypePetsPetControl(1, "Perro"),
                        new TypePetsPetControl(2, "Gato"),
                        new TypePetsPetControl(3, "Hámster"),
                        new TypePetsPetControl(4, "Pez"),
                        new TypePetsPetControl(5, "Ratón"),
                        new TypePetsPetControl(6, "Pájaro"),
                        new TypePetsPetControl(7, "Conejo"),
                        new TypePetsPetControl(8, "Tortuga"),
                        new TypePetsPetControl(9, "Hurón"),
                        new TypePetsPetControl(10, "Cerdo"),
                        new TypePetsPetControl(11, "Tarántula"),
                        new TypePetsPetControl(12, "Serpiente")
                    );
                }
            });
        }
    };
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
                            .fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    /*
    // Definir el Callback para insertar datos iniciales
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insertar los datos iniciales en un hilo separado
            Executors.newSingleThreadExecutor().execute(() -> {
                // Obtener la instancia de la base de datos y los DAOs
                DatabasePetControl instance = getDatabase((Context) db);
                TypePetsDAOPetControl typePetsDao = instance.typePetsDAO();

                // Insertar los datos iniciales
                typePetsDao.addAll(TypePetsGeneratorPetControl.getInitialTypePets());
            });
        }
    };
    */

    /*
    // Métodos abstractos para los DAOs de cada entidad --> Room crea implementaciones en tiempo de compilación.
    public abstract TypePetsDAOPetControl typePetsDAO();
    public abstract PetsDAOPetControl petsDAO();
    public abstract OwnerDAOPetControl ownerDAO();
    public abstract VisitsVetDAOPetControl visitsVetDAO();
     */
}