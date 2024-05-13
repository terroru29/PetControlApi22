package net.petcontrol.PetControlApi22;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TypePetsPetControl.class, PetsPetControl.class, OwnerPetControl.class,
        VisitsVetPetControl.class}, version = 1)
public abstract class DatabasePetControl extends RoomDatabase {
    // Métodos abstractos para los DAOs de cada entidad --> Room cree implementaciones en tiempo de compilación.
    public abstract TypePetsDAOPetControl typePetsDAO();
    public abstract PetsDAOPetControl petsDAO();
    public abstract OwnerDAOPetControl ownerDAO();
    public abstract VisitsVetDAOPetControl visitsVetDAO();
}