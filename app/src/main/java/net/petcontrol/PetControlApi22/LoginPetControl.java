package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPetControl extends AppCompatActivity {

    Button logIn;
    TextView forgotPassword, terms, signUp, messageEmail, messagePass;
    EditText validationEmail, validationPass, newPassword, confirmPassword;
    String email, mensajeEmailCorrect, cadenaCorrecta, mensajeEmailIncorrect, cadenaIncorrecta,
            pass, passCorrect, msgPassCorrect, passIncorrect, msgPassIncorrect, requirements,
            conditions, passwordNew, passwordConfirm;
    // Permitirá cualquier carácter (sin @), @, cualquier carácter (sin @), ., de 2 a 3 letras minúsculas
    private static final String EMAIL_PATTERN = "^[^@]+@+[^@]+\\.[a-z]{2,3}+$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petcontrol);

        // Asociación de variables con sus recursos
        validationEmail = findViewById(R.id.etEmail);
        messageEmail = findViewById(R.id.txtValidationEmail);
        validationPass = findViewById(R.id.etPassword);
        messagePass = findViewById(R.id.txtValidationPass);
        forgotPassword = findViewById(R.id.txtForgotPassword);
        terms = findViewById(R.id.txtTerms);
        logIn = findViewById(R.id.btnLogIn);
        signUp = findViewById(R.id.txtSignUp);


        //-Evento EditText
        //--Email
        validationEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                email = validationEmail.getText().toString();
                // Validación de correo --> Si es correcto aparece en verde, sino, en rojo
                if (emailValidation(email)) {
                    mensajeEmailCorrect = getResources().getString(R.string.validation_email);
                    cadenaCorrecta = String.format(mensajeEmailCorrect);
                    messageEmail.setText(cadenaCorrecta);
                    messageEmail.setTextColor(Color.parseColor("#FF4DBC34"));
                }
                else {
                    mensajeEmailIncorrect = getResources().getString(R.string.invalidation_email);
                    cadenaIncorrecta = String.format(mensajeEmailIncorrect);
                    messageEmail.setText(cadenaIncorrecta);
                    messageEmail.setTextColor(Color.RED);
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
                // Validación de correo --> Si es correcto aparece en verde, sino, en rojo
                if (passValidation(pass)) {
                    passCorrect = getResources().getString(R.string.validation_pass);
                    msgPassCorrect = String.format(passCorrect);
                    messagePass.setText(msgPassCorrect);
                    messagePass.setTextColor(Color.parseColor("#FF4DBC34"));
                }
                else {
                    passIncorrect = getResources().getString(R.string.invalidation_pass);
                    msgPassIncorrect = String.format(passIncorrect);
                    messagePass.setText(msgPassIncorrect);
                    messagePass.setTextColor(Color.RED);
                }
                muteValidation(pass, messagePass);
            }
        });


        // ----- CAMBIAR COLOR DEL TEXTO DEL SWITCH -----
        // Obtener String del Switch
        requirements = getResources().getString(R.string.terms);
        conditions = String.format(requirements);

        // Crear una instancia de SpannableString con el texto completo
        SpannableString spannableString = new SpannableString(conditions);

        // Definir los índices de inicio y fin de los caracteres que deseas cambiar de color
        int init = 11; // El índice del primer caracter a cambiar de color (0-indexed)
        int end = 42;    // El índice del último caracter a cambiar de color (no inclusivo)

        // Crear un objeto ForegroundColorSpan para definir el color del texto
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.MAGENTA);

        // Aplicar el color sólo a la parte específica del texto
        spannableString.setSpan(colorSpan, init, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Establecer la cadena formateada en el TextView
        terms.setText(spannableString);


        // ----- SUBRAYAR PALABRA PARA IMITAR UN ENLACE ----
        // Subrayar Registro
        signUp.setPaintFlags(signUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signUp.setText(R.string.sign_up);
        // Subrayar Olvidar contraseña
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgotPassword.setText(R.string.forgot_password);
    }

    // Pasar a la pantalla de inicio de sesión
    public void nextWindow(View view) {
        logIn = (Button) view;

        Intent i = new Intent(this, MenuInferiorPetControl.class);
        startActivity(i);
    }

    // Cambio de pantalla al registro
    public void register(View view) {
        signUp = (TextView) view;

        Intent i = new Intent(this, RegisterPetControl.class);
        startActivity(i);
    }
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

    public void forgotPassword(View v) {
        forgotPassword = (TextView) v;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Establece el diseño personalizado para el diálogo
        View design = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        builder.setView(design);

        // Configuración de los botones
        //-Aceptar
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newPassword = design.findViewById(R.id.etNewPassword);
                confirmPassword = design.findViewById(R.id.etConfirmPassword);

                passwordNew = newPassword.getText().toString();
                passwordConfirm = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(passwordNew) || TextUtils.isEmpty(passwordConfirm))
                    Toast.makeText(getApplicationContext(), "Por favor ingresa ambos campos",
                            Toast.LENGTH_SHORT).show();
                else if (!TextUtils.equals(passwordNew, passwordConfirm))
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Contraseña cambiada con éxito",
                            Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cierra el diálogo sin hacer nada
                dialog.dismiss();
            }
        });

        // Muestra el diálogo
        AlertDialog dialog = builder.create();
        // Configurar el tamaño deseado del diálogo
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                // Establecer el tamaño del diálogo
                dialog.getWindow().setLayout(
                        // 90% del ancho de la pantalla
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
            }
        });
        dialog.show();
    }
}