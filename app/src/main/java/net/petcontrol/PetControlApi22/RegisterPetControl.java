package net.petcontrol.PetControlApi22;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class RegisterPetControl extends AppCompatActivity {
    EditText name, validationEmail, validationPass;
    Spinner genders;
    Button searchPicture, next;
    TextView birthday, messageEmail, messagePass, pictureSelected;
    ImageView pictureUser;
    ImageButton cleanName, calendar, cleanEmail, closedEye, openEye;
    ImageFilterButton info;
    String nameUser, nameCorrect, selectedGender, dateBirthday, email, msgEmailCorrect,
            msgEmailIncorrect, emailSaved, pass, msgPassCorrect, msgPassIncorrect, passSaved, access;
    int ageUser, age, yearsUser;
    boolean validateEmail, validatePass;
    private static final int PICK_IMAGE_REQUEST = 1;
    // Permitirá cualquier carácter (sin @), @, cualquier carácter (sin @), ., de 2 a 3 letras minúsculas
    private static final String EMAIL_PATTERN = "^[^@]+@+[^@]+\\.[a-z]{2,3}+$";
    SharedPreferences sharedPreferences, sharedPref;
    SharedPreferences.Editor editor, edit;
    DatabaseManagerPetControl dbPC;
    Calendar cal;
    SimpleDateFormat sdf;
    Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_petcontrol);

        // Abrir base de datos
        dbPC = new DatabaseManagerPetControl(this);
        dbPC.open();


        // Asociar variables con sus recursos
        name = findViewById(R.id.etName);
        cleanName = findViewById(R.id.imgbXName);
        genders = findViewById(R.id.spinnerGender);
        calendar = findViewById(R.id.imgbCalendar);
        cal = Calendar.getInstance();
        birthday = findViewById(R.id.txtDateBirthday);
        validationEmail = findViewById(R.id.etEmailUser);
        messageEmail = findViewById(R.id.txtValidationEmail);
        cleanEmail = findViewById(R.id.imgbXEmail);
        info = findViewById(R.id.imgfbInfo);
        validationPass = findViewById(R.id.etPasswordUser);
        closedEye = findViewById(R.id.imgbClosedEye);
        openEye = findViewById(R.id.imgbOpenEye);
        messagePass = findViewById(R.id.txtValidationPass);
        searchPicture = findViewById(R.id.btnPicture);
        pictureUser = findViewById(R.id.ivUser);
        pictureSelected = findViewById(R.id.txtPictureSelected);
        next = findViewById(R.id.btnNextConfiguration);

        // Formatea la fecha al formato aaaa-MM-dd --> ISO 8601
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


        // SPINNER
        // Crea un ArrayAdapter usando los recursos de cadena y el diseño por defecto del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        // Especifica el diseño a usar cuando se despliega la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Aplica el adaptador al Spinner
        genders.setAdapter(adapter);

        //-EVENTO SPINNER
        // Configura un listener para manejar las selecciones del Spinner
        genders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                // Obtén el elemento seleccionado
                selectedGender = parentView.getItemAtPosition(position).toString();
                Log.d("GÉNERO", "Seleccionaste: " + selectedGender);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        // Establece la opción vacía como la opción seleccionada por defecto
        genders.setSelection(0, true);


        //-EVENTO EDITTEXT
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
                } else {
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
                } else {
                    msgPassIncorrect = getResources().getString(R.string.invalidation_pass);
                    messagePass.setText(msgPassIncorrect);
                    messagePass.setTextColor(Color.RED);
                    validatePass = false;
                }
                muteValidation(pass, messagePass);
            }
        });


        //-EVENTO DE BOTÓN
        //--Buscar imagen
        searchPicture.setOnClickListener(v -> {
            // Crear un Intent para abrir el explorador de archivos
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            // Establecer el tipo de archivo que se puede seleccionar (todos los tipos de archivos)
            intent.setType("*/*");
            // Iniciar la actividad para seleccionar un archivo
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        //--Siguiente
        next.setOnClickListener(v -> {
            // Asegurarse de que email y pass no sean null antes de registrar en el log
            if (email != null)
                Log.d("Email", email);
            else
                Log.d("Email", "Email es null");

            if (pass != null)
                Log.d("Pass", pass);
            else
                Log.d("Pass", "Pass es null");

            sharedPreferences = getSharedPreferences("PetControlPreferences", MODE_PRIVATE);
            editor = sharedPreferences.edit();

            // Almacenar datos si las validaciones son correctas
            if (validateEmail) {
                emailSaved = validationEmail.getText().toString();
                editor.putString("email", emailSaved);
            }
            if (validatePass) {
                passSaved = validationPass.getText().toString();
                editor.putString("password", passSaved);
            }
            // Aplica los cambios de forma asíncrona para que el código no se bloquee
            editor.apply();

            if (validateEmail && validatePass) {
                Log.d("Datos salvados", "Datos guardados: " + emailSaved);

                nameCorrect = name.getText().toString();
                if (!nameCorrect.isEmpty()) {
                    if (withoutDigit(nameCorrect)) {
                        // Obtener el nombre del usuario
                        nameUser = name.getText().toString();

                        /*
                        sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        edit = sharedPreferences.edit();
                        edit.putString("username", nameUser); // Guarda el nombre del usuario obtenido
                        edit.apply();
                        */

                        /* ===VALIDACIONES=== */
                        // Validar el campo de edad
                        try {
                            if (ageUser > 16)
                                yearsUser = Integer.parseInt(String.valueOf(ageUser));
                            /*
                            yearsUser = Integer.parseInt(String.valueOf(ageUser));
                            if (yearsUser < 16) {
                                Toast.makeText(getApplicationContext(), "Debe ser mayor o igual " +
                                        "de 16 años.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            */
                        } catch (NumberFormatException e) {
                            // Manejar el caso donde la cadena está vacía o no es un número válido
                            yearsUser = 16;
                            Log.e("insertOwner", "La edad no es válida: " + e.getMessage());
                        }

                        Drawable drawable = pictureUser.getDrawable();
                        if (drawable instanceof BitmapDrawable) {
                            // Si ya es un BitmapDrawable, simplemente obtenemos el Bitmap
                            picture = ((BitmapDrawable) drawable).getBitmap();
                        } else {
                            // Manejar el caso en el que no es un BitmapDrawable
                            Log.e("DrawableError", "La imagen seleccionada no es un " +
                                    "BitmapDrawable.");
                            // Si no es un BitmapDrawable, lo convertimos a Bitmap
                            picture = convertToBitmap(drawable);
                            Log.d("convertToBitmap", "La imagen seleccionada ya es un " +
                                    "BitmapDrawable.");
                            if (picture == null) {
                                // Asignar una imagen predeterminada según el género seleccionado
                                switch (selectedGender) {
                                    case "Femenino":
                                        picture = BitmapFactory.decodeResource(getResources(),
                                                R.drawable.female);
                                        Log.d("Imagen femenino", "Imagen de mujer.");
                                        break;
                                    case "Masculino":
                                        picture = BitmapFactory.decodeResource(getResources(),
                                                R.drawable.male);
                                        Log.d("Imagen masculino", "Imagen de hombre.");
                                        break;
                                    case "No binario":
                                        picture = BitmapFactory.decodeResource(getResources(),
                                                R.drawable.not_binary);
                                        Log.d("Imagen no binario", "Imagen de persona no binaria.");
                                        break;
                                    case "Genero fluido":
                                        picture = BitmapFactory.decodeResource(getResources(),
                                                R.drawable.gender_fluid);
                                        Log.d("Imagen género fluído", "Imagen de género fluído.");
                                        break;
                                    default:
                                        // Imagen por defecto para géneros no especificados
                                        picture = BitmapFactory.decodeResource(getResources(),
                                                R.drawable.person);
                                        Log.d("Imagen por defecto", "No hay género.");
                                        break;
                                }
                            }
                        }
                        dbPC.insertOwner(nameUser, yearsUser, selectedGender, picture,
                                dateBirthday, emailSaved, passSaved);
                        Log.i("Success", "Se han insertado los datos correctamente.");

                        // Liberar la memoria asociada al objeto Bitmap
                        if (picture != null)
                            picture.recycle();

                        Intent i = new Intent(getApplicationContext(), AddPetPetControl.class);
                        startActivity(i);
                        // Cierra la actividad actual para evitar que el usuario regrese a ella
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "El campo nombre no puede " +
                                "contener dígitos.", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Debe rellenar los campos " +
                            "señalados con el símbolo *.", Toast.LENGTH_LONG).show();
            } else {
                access = getResources().getString(R.string.incorrect_access);
                Toast.makeText(getApplicationContext(), access, Toast.LENGTH_LONG).show();
            }
        });


        //-EVENTO IMAGEBUTTON
        //--Calendario
        calendar.setOnClickListener(v -> {
            // Muestra el DatePickerDialog
            new DatePickerDialog(RegisterPetControl.this, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });
        //--Limpiar campo
        cleanName.setOnClickListener(v -> name.setText(""));
        cleanEmail.setOnClickListener(v -> validationEmail.setText(""));
        //--Mostrar contraseña
        closedEye.setOnClickListener(v -> {
            closedEye.setVisibility(View.GONE);
            openEye.setVisibility(View.VISIBLE);
            validationPass.setTransformationMethod(null);
        });
        //-Ocultar contraseña
        openEye.setOnClickListener(v -> {
            openEye.setVisibility(View.GONE);
            closedEye.setVisibility(View.VISIBLE);
            validationPass.setTransformationMethod(new PasswordTransformationMethod());
        });


        //--EVENTO IMAGEFILTERBUTTON
        // Mostrar en un cuadro de diálogo los requisitos de la contraseña
        info.setOnClickListener(v1 -> showPasswordDialog());
    }
    /**
     * Manejar el resultado cuando el usuario seleccione un archivo.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
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
            //pictureUser.setImageURI(selectedFileUri);
            // Convertimos la URI a Bitmap y lo asignamos a picture
            try {
                picture = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(Objects.requireNonNull(selectedFileUri)));
                pictureUser.setImageDrawable(new BitmapDrawable(getResources(), picture));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Muestra el nombre de la imagen seleccionada
            pictureSelected.setText(namePicture);
        } else {
            // Asignar una imagen predeterminada según el género seleccionado
            switch (selectedGender) {
                case "Femenino":
                    // Convertimos el recurso predeterminado a Bitmap y lo asignamos a picture
                    picture = BitmapFactory.decodeResource(getResources(), R.drawable.female);
                    Log.d("Imagen femenino", "Imagen de mujer.");
                    break;
                case "Masculino":
                    // Convertimos el recurso predeterminado a Bitmap y lo asignamos a picture
                    picture = BitmapFactory.decodeResource(getResources(), R.drawable.male);
                    Log.d("Imagen masculino", "Imagen de hombre.");
                    break;
                case "No binario":
                    // Convertimos el recurso predeterminado a Bitmap y lo asignamos a picture
                    picture = BitmapFactory.decodeResource(getResources(), R.drawable.not_binary);
                    Log.d("Imagen no binario", "Imagen de persona no binaria.");
                    break;
                case "Genero fluido":
                    // Convertimos el recurso predeterminado a Bitmap y lo asignamos a picture
                    picture = BitmapFactory.decodeResource(getResources(), R.drawable.gender_fluid);
                    Log.d("Imagen género fluído", "Imagen de género fluído.");
                    break;
                default:
                    // Imagen por defecto para géneros no especificados
                    picture = BitmapFactory.decodeResource(getResources(), R.drawable.person);
                    Log.d("Imagen por defecto", "No hay género.");
                    break;
            }
            // Si el usuario no selecciona una imagen, asignamos una imagen predeterminada
            pictureUser.setImageBitmap(picture);
            pictureSelected.setText(R.string.default_image_name);
        }
    }
/*
    //Obtener la imagen de la galería.
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
 */
    /* -------------------- MÉTODOS -------------------- */
    /**
     * Método que valida el correo electrónico: Mín. 1 @ y 1 . al final con la extensión (3-4 letras)
     *
     * @param email Correo electrónico del usuario para validar
     * @return true si el correo es correcto; false si es incorrecto
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
     */
    public boolean passValidation(String pass) {
        boolean correct = false;
        int countLow = 0, countUp = 0, countNum = 0, countSpe = 0;

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correct;
    }
    /**
     * Silenciar la validación en caso de que los campos a comprobar estén vacíos
     *
     * @param et   Caja de texto
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
            if (cursor != null && cursor.moveToFirst())
                picName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
        return picName;
    }
    /**
     * Comprueba si la cadena pasada como parámetro contiene algún dígito.
     *
     * @param word Palabra a comprobar si contiene algún dígito
     * @return true si no contiene ningún dígito; false, en caso contrario
     */
    public boolean withoutDigit(String word) {
        for (char w : word.toCharArray()) {
            if (Character.isDigit(w))
                // Contiene, al menos, un dígito
                return false;
        }
        return true;
    }
    private final DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
        /**
         * @param view       the picker associated with the dialog
         * @param year       the selected year
         * @param monthOfYear      the selected month (0-11 for compatibility with
         *                   {@link Calendar#MONTH})
         * @param dayOfMonth the selected day of the month (1-31, depending on
         *                   month)
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Establece la fecha seleccionada en el calendario
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Formatea la fecha y la muestra en el TextView
            dateBirthday = sdf.format(cal.getTime());
            birthday.setText(dateBirthday);

            // Calcula la edad basada en la fecha seleccionada
            ageUser = calculateAge(dateBirthday);
        }
    };
    // Método para calcular la edad basándose en la fecha de nacimiento
    private int calculateAge(String birthDate) {
        try {
            Calendar birthdayDate = Calendar.getInstance();
            birthdayDate.setTime(Objects.requireNonNull(sdf.parse(birthDate)));

            Calendar today = Calendar.getInstance();

            age = today.get(Calendar.YEAR) - birthdayDate.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < birthdayDate.get(Calendar.DAY_OF_YEAR))
                age--;
            return age;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void showPasswordDialog() {
        // Inflar el layout del diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_requirements_password, null);

        String requeriments = getResources().getString(R.string.requirements);
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle(requeriments)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        // Mostrar el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private Bitmap convertToBitmap(Drawable drawable) {
        // Maneja el caso en que drawable es null
        if (drawable == null) {
            // Devuelve un bitmap predeterminado
            Log.e("DrawableErrorNull", "Drawable es null");
            int defaultImageResource;
            // Asignar una imagen predeterminada según el género seleccionado
            switch (selectedGender) {
                case "Femenino":
                    defaultImageResource =  R.drawable.female;
                    Log.d("Imagen femenino", "Imagen de mujer.");
                    break;
                case "Masculino":
                    defaultImageResource =  R.drawable.male;
                    Log.d("Imagen masculino", "Imagen de hombre.");
                    break;
                case "No binario":
                    defaultImageResource =  R.drawable.not_binary;
                    Log.d("Imagen no binario", "Imagen de persona no binaria.");
                    break;
                case "Genero fluido":
                    defaultImageResource =  R.drawable.gender_fluid;
                    Log.d("Imagen género fluído", "Imagen de género fluído.");
                    break;
                default:
                    // Imagen por defecto para géneros no especificados
                    defaultImageResource =  R.drawable.person;
                    Log.d("Imagen por defecto", "No hay género.");
                    break;
            }
            // Devuelve un bitmap predeterminado según el género
            return BitmapFactory.decodeResource(getResources(), defaultImageResource);
            // Lanza una excepción para manejarlo en un nivel superior
            // throw new IllegalArgumentException("Drawable no puede ser null");
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        // Especifica el tamaño del bitmap
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}