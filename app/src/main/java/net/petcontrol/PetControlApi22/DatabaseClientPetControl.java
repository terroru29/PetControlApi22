package net.petcontrol.PetControlApi22;

import android.content.Context;

import androidx.room.Room;

/**
 * Base de datos del cliente en donde se crea una instancia de nuestra clase, que posee la base de
 * datos, para acceder a ella, mediante un patrón 'Singleton' (getInstance), asegurando que exista
 * una instancia única de la base de datos y pudiendo acceder a ella en cualquier momento y desde
 * cualquier parte del código.
 */
public class DatabaseClientPetControl {
    // Volatile asegura que cuando se use la instancia, ya tendrás las modificaciones correspondientes
    private static volatile DatabasePetControl dbPetControl;

    // Método con patrón Singleton que garantiza que sólo se pueda acceder a una instancia de la
    // base de datos a la vez, evitando así los problemas de concurrencia cuando múltiples hilos
    // intentan acceder y crear la instancia de la base de datos al mismo tiempo, ya que sólo podrá
    // ser ejecutado por un único hilo a la vez.
    public static synchronized DatabasePetControl getInstance(Context context) {
        // Si no se ha creado ninguna instancia de la BD
        if (dbPetControl == null)
            // Se crea la instancia, a la cual se le asignará la BD, construyendo y configurando
            // ésta mediante el contexto, su clase y su nombre (database_petcontrol)
            dbPetControl = Room.databaseBuilder(context.getApplicationContext(),
                    DatabasePetControl.class, "database_petcontrol").build();
        return dbPetControl;
    }
}