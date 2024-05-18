package net.petcontrol.PetControlApi22;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManagerPetControl {

    private DatabaseHelperPetControl dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManagerPetControl(Context context) {
        this.context = context;
    }

    public DatabaseManagerPetControl open() throws SQLException {
        dbHelper = new DatabaseHelperPetControl(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Insertar usuario
    public void insertUser(String name, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_USER_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_USER_EMAIL, email);
        database.insert(DatabaseHelperPetControl.TABLE_USERS, null, contentValues);
    }

    // Obtener todos los usuarios
    public Cursor fetchAllUsers() {
        String[] columns = new String[] {
                DatabaseHelperPetControl.COLUMN_USER_ID,
                DatabaseHelperPetControl.COLUMN_USER_NAME,
                DatabaseHelperPetControl.COLUMN_USER_EMAIL
        };
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_USERS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Actualizar usuario
    public int updateUser(long id, String name, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_USER_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_USER_EMAIL, email);
        return database.update(DatabaseHelperPetControl.TABLE_USERS, contentValues, DatabaseHelperPetControl.COLUMN_USER_ID + " = " + id, null);
    }

    // Eliminar usuario
    public void deleteUser(long id) {
        database.delete(DatabaseHelperPetControl.TABLE_USERS, DatabaseHelperPetControl.COLUMN_USER_ID + " = " + id, null);
    }

    // Inserción, actualización, eliminación para productos seguiría un patrón similar
    // Insertar producto
    public void insertProduct(String name, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_PRODUCT_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PRODUCT_PRICE, price);
        database.insert(DatabaseHelperPetControl.TABLE_PRODUCTS, null, contentValues);
    }

    // Obtener todos los productos
    public Cursor fetchAllProducts() {
        String[] columns = new String[] {
                DatabaseHelperPetControl.COLUMN_PRODUCT_ID,
                DatabaseHelperPetControl.COLUMN_PRODUCT_NAME,
                DatabaseHelperPetControl.COLUMN_PRODUCT_PRICE
        };
        Cursor cursor = database.query(DatabaseHelperPetControl.TABLE_PRODUCTS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Actualizar producto
    public int updateProduct(long id, String name, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperPetControl.COLUMN_PRODUCT_NAME, name);
        contentValues.put(DatabaseHelperPetControl.COLUMN_PRODUCT_PRICE, price);
        return database.update(DatabaseHelperPetControl.TABLE_PRODUCTS, contentValues, DatabaseHelperPetControl.COLUMN_PRODUCT_ID + " = " + id, null);
    }

    // Eliminar producto
    public void deleteProduct(long id) {
        database.delete(DatabaseHelperPetControl.TABLE_PRODUCTS, DatabaseHelperPetControl.COLUMN_PRODUCT_ID + " = " + id, null);
    }
}
