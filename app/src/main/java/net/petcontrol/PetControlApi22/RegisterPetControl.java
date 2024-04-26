package net.petcontrol.PetControlApi22;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class RegisterPetControl extends AppCompatActivity {

    EditText validationEmail, validationPass;
    Button searchPicture, next;
    TextView messageEmail, messagePass, pictureSelected;
    ImageView pictureUser;
    String email, msgEmailCorrect, msgEmailIncorrect, emailSaved,
            pass, msgPassCorrect, msgPassIncorrect, passSaved, access;
    boolean validateEmail, validatePass;
    private static final int PICK_IMAGE_REQUEST = 1;
    // Permitirá cualquier carácter (sin @), @, cualquier carácter (sin @), ., de 2 a 3 letras minúsculas
    private static final String EMAIL_PATTERN = "^[^@]+@+[^@]+\\.[a-z]{2,3}+$";
    OwnerPetControl opc = new OwnerPetControl();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_petcontrol);

        // Asociar variables con sus recursos
        validationEmail = findViewById(R.id.etEmailUser);
        messageEmail = findViewById(R.id.txtValidationEmail);
        validationPass = findViewById(R.id.etPasswordUser);
        messagePass = findViewById(R.id.txtValidationPass);
        searchPicture = findViewById(R.id.btnPicture);
        pictureUser = findViewById(R.id.ivUser);
        pictureSelected = findViewById(R.id.txtPictureSelected);
        next = findViewById(R.id.btnNextConfiguration);


        //-Evento EditText
        //--Email
        validationEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable e) {
                email = validationEmail.getText().toString();
                // Validación de correo --> Si es correcto aparece en verde, sino, en rojo
                if (emailValidation(email)) {
                    msgEmailCorrect = getResources().getString(R.string.validation_email);
                    messageEmail.setText(msgEmailCorrect);
                    messageEmail.setTextColor(Color.parseColor("#FF4DBC34"));
                    validateEmail = true;
                }
                else {
                    msgEmailIncorrect = getResources().getString(R.string.invalidation_email);
                    messageEmail.setText(msgEmailIncorrect);
                    messageEmail.setTextColor(Color.RED);
                    validateEmail = false;
                }
                muteValidation(email, messageEmail);
            }
        });
        //--Password
        validationPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                pass = validationPass.getText().toString();
                // Validación de contraseña --> Si es correcta aparece en verde, sino, en rojo
                if (passValidation(pass)) {
                    msgPassCorrect = getResources().getString(R.string.validation_pass);
                    messagePass.setText(msgPassCorrect);
                    messagePass.setTextColor(Color.parseColor("#FF4DBC34"));
                    validatePass = true;
                }
                else {
                    msgPassIncorrect = getResources().getString(R.string.invalidation_pass);
                    messagePass.setText(msgPassIncorrect);
                    messagePass.setTextColor(Color.RED);
                    validatePass = false;
                }
                muteValidation(pass, messagePass);
            }
        });


        //-Evento de botón
        //--Buscar imagen
        searchPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir el explorador de archivos
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Establecer el tipo de archivo que se puede seleccionar (todos los tipos de archivos)
                intent.setType("*/*");

                // Iniciar la actividad para seleccionar un archivo
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        //--Siguiente
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (validateEmail) {
                    // Almacenamos el email introducido
                    opc.setEmail(email);
                    emailSaved = opc.getEmail();
                    //Toast.makeText(this, emailSaved, Toast.LENGTH_LONG).show();
                }
                if (validatePass) {
                    opc.setPassword(pass);
                    passSaved = opc.getPassword();
                    //Toast.makeText(this, passSaved, Toast.LENGTH_LONG).show();
                }

                sharedPreferences = getSharedPreferences("PetControlPreferences", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("password", pass);
                Log.d(TAG, email);
                Log.d(TAG, pass);
                // Aplica los cambios de forma asíncrona para que el código no se bloquee
                editor.apply();
*/
                sharedPreferences = getSharedPreferences("PetControlPreferences", MODE_PRIVATE);
                editor = sharedPreferences.edit();

                // Guardar datos solo si las validaciones son correctas
                if (validateEmail) {
                    emailSaved = validationEmail.getText().toString();
                    editor.putString("email", emailSaved);
                }

                if (validatePass) {
                    passSaved = validationPass.getText().toString();
                    editor.putString("password", passSaved);
                }

                editor.apply();

                // Para verificar, puedes usar Toast o Log.d
                Toast.makeText(getApplicationContext(), "Datos guardados: " + emailSaved + " / "
                        + passSaved, Toast.LENGTH_LONG).show();

                if (validateEmail && validatePass) {
                    Toast.makeText(getApplicationContext(), email + " --> " + pass, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), AddPetPetControl.class);
                    //i.putExtra("email", emailSaved);
                    //i.putExtra("pass", passSaved);
                    startActivity(i);
                }
                else {
                    access = getResources().getString(R.string.incorrect_access);
                    Toast.makeText(getApplicationContext(), access, Toast.LENGTH_LONG).show();
                }
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Obtenemos la URI del archivo seleccionado
            Uri selectedFileUri = data.getData();

            // Obtiene el nombre de la imagen seleccionada
            String namePicture = getFileName(selectedFileUri);

            // Obtenemos la imagen seleccionada por el usuario
            pictureUser.setImageURI(selectedFileUri);

            // Muestra el nombre de la imagen seleccionada
            pictureSelected.setText(namePicture);
        }
    }
/*
    //Obtener la imagen de la galería.
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
 */
    /**
     *  Método que valida el correo electrónico: Mín. 1 @ y 1 . al final con la extensión (3-4 letras)
     *
     * @param email Correo electrónico del usuario para validar
     * @return true si el correo es correcto; false si es incorrecto
     *
     */
    public boolean emailValidation(String email) {
        boolean correct = false;
        int countArroba = 0, countPoint = 0;

        if (email != null) {
            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    countArroba++;
                }
                if (email.charAt(i) == '.') {
                    countPoint++;
                }
            }
            if (countArroba == 1 && countPoint >= 1 && email.matches(EMAIL_PATTERN))
                correct = true;
        }
        return correct;
    }
    /**
     * Método que valida la contraseña: Mínimo una minúscula, una mayúscula, un dígito,
     * un carácter especial y que tenga una longitud entre 8 y 12 caracteres.
     *
     * @param pass Contraseña del usuario a validar
     * @return true si cumple los requisitos anteriores; false en caso contrario
     *
     */
    public boolean passValidation(String pass) {
        boolean correct = false;
        int countLow = 0, countUp = 0, countNum = 0, countSpe = 0;

        if (pass != null) {
            for (int i = 0; i < pass.length(); i++) {
                if (pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z')
                    countLow++;
                else if (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')
                    countUp++;
                else if (pass.charAt(i) >= '0' && pass.charAt(i) <= '9')
                    countNum++;
                else
                    countSpe++;
            }
            if (countLow >= 1 && countUp >= 1 && countNum >= 1 &&
                    (pass.length() >= 8 && pass.length() <= 12) && countSpe >= 1)
                correct = true;
        }
        return correct;
    }
    /**
     * Silenciar la validación en caso de que los campos a comprobar estén vacíos
     *
     * @param et Caja de texto
     * @param text Mensaje a mostrar para la validación
     */
    public void muteValidation(String et, TextView text) {
        if (et.isEmpty())
            text.setText("");
    }
    /**
     * Obtener el nombre del archivo a partir de su URI
     *
     * @param uri Path of the picture
     * @return picName Name of the picture selected by the user
     */
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String picName = null;
        ContentResolver contentResolver = getContentResolver();

        // El try-with-resources permite que se cierren automáticametne los recursos (sin finally)
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                picName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
        return picName;
    }
/*
    // Pasar a la siguiente ventana de configuración (elección de animales)
    public void nextWindowOfConfiguration(View view) {
        next = (Button) view;

        if (validateEmail) {
            // Almacenamos el email introducido
            opc.setEmail(email);
            emailSaved = opc.getEmail();
            //Toast.makeText(this, emailSaved, Toast.LENGTH_LONG).show();
        }
        if (validatePass) {
            opc.setPassword(pass);
            passSaved = opc.getPassword();
            //Toast.makeText(this, passSaved, Toast.LENGTH_LONG).show();
        }
        if (validateEmail && validatePass) {
            Toast.makeText(this, emailSaved + " " + passSaved, Toast.LENGTH_LONG).show();

            Intent i = new Intent(this, LoginPetControl.class);
            i.putExtra("email", emailSaved);
            i.putExtra("pass", passSaved);
            startActivity(i);
        }
        else {
            access = getResources().getString(R.string.incorrect_access);
            Toast.makeText(this, access, Toast.LENGTH_LONG).show();
        }
    }
    */
}
//TODO Guardar correo y contraseña de registro