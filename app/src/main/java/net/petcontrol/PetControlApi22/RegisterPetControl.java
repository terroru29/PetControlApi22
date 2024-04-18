package net.petcontrol.PetControlApi22;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPetControl extends AppCompatActivity {

    Button searchPicture;
    TextView pictureSelected;
    private static final int REQUEST_CODE_FILE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_petcontrol);

        // Asociar variables con sus recursos
        searchPicture = findViewById(R.id.btnPicture);
        pictureSelected = findViewById(R.id.txtPictureSelected);

        //**Evento de botón
        searchPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir el explorador de archivos
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Establecer el tipo de archivo que se puede seleccionar (todos los tipos de archivos)
                intent.setType("*/*");

                // Iniciar la actividad para seleccionar un archivo
                startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
            }
        });
    }

    /**
     * Manejar el resultado cuando el usuario seleccione un archivo.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK && data != null) {
            // Obtenemos la URI del archivo seleccionado
            Uri selectedFileUri = data.getData();

            // Obtiene el nombre de la imagen seleccionada
            String namePicture = getFileName(selectedFileUri);

            // Muestra el nombre de la imagen seleccionada debajo de ésta
            pictureSelected.setText(namePicture);
        }
    }

    /**
     * Obtener el nombre del archivo a partir de su URI
     *
     * @param uri Path of the picture
     * @return picName Name of the picture
     */
    private String getFileName(Uri uri) {
        String picName = null;
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                picName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return picName;
    }
}