package net.petcontrol.PetControlApi22;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPetControl extends AppCompatActivity {

    Button logIn;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch termsAndConditions;
    TextView forgotPassword, terms, signUp;
    EditText emailUser, passUser, newPassword, confirmPassword;
    String email, savedEmail, pass, savedPassword, passwordNew, passwordConfirm, conditions,
            credentials, acceptTerms;
    boolean change = false, termsCheck;
    OwnerPetControl opc = new OwnerPetControl();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petcontrol);

        // Asociación de variables con sus recursos
        emailUser = findViewById(R.id.etEmail);
        passUser = findViewById(R.id.etPassword);
        forgotPassword = findViewById(R.id.txtForgotPassword);
        terms = findViewById(R.id.txtTerms);
        termsAndConditions = findViewById(R.id.switchTerms);
        logIn = findViewById(R.id.btnLogIn);
        signUp = findViewById(R.id.txtSignUp);


        //-Evento EditText
        //--Email
        emailUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable e) {
                // Obtenemos el texto escrito por el usuario en la caja
                email = emailUser.getText().toString();
            }
        });
        //--Contraseña
        passUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable edit) {
                // Obtenemos el texto escrito por el usuario en la caja
                pass = passUser.getText().toString();
            }
        });

        // ----- CAMBIAR COLOR DEL TEXTO DEL SWITCH -----
        // Obtener String del Switch
        conditions = getResources().getString(R.string.terms);

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

        //-Evento botón
        //--Iniciar sesión
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWindow(logIn);
            }
        });
    }

    // Pasar a la pantalla de inicio de sesión
    public void nextWindow(View view) {
        //logIn = (Button) view;

        if (termsAndConditions.isChecked())
            termsCheck = true;
        else {
            acceptTerms = getResources().getString(R.string.accept_conditions);
            Toast.makeText(this, acceptTerms, Toast.LENGTH_LONG).show();
            termsCheck = false;
        }

        sharedPreferences = getSharedPreferences("PetControlPreferences", MODE_PRIVATE);
        // Le asigna una cadena vacía por defecto si no recibe valores
        savedEmail = sharedPreferences.getString("email", "");
        savedPassword = sharedPreferences.getString("password", "");
        // Verificar que los datos recuperados son correctos
        Toast.makeText(this, "Email recuperado: " + savedEmail, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Password recuperado: " + savedPassword, Toast.LENGTH_SHORT).show();

        Log.d(TAG, savedEmail);
        Log.d(TAG, savedPassword);

        // Si las credenciales coinciden
        if (email.equals(savedEmail) && pass.equals(savedPassword)) {
            Toast.makeText(this, email + "···" + pass, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "¡Acceso permitido!", Toast.LENGTH_LONG).show();

            if (termsCheck) {
                Intent i = new Intent(this, MenuInferiorPetControl.class);
                startActivity(i);
            }
        }
        else {
            credentials = getResources().getString(R.string.incorrect_credentials);
            Toast.makeText(this, credentials, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Cambio de pantalla al registro --> Datos de usuario
     *
     * @param view Elemento sobre el que se interactúa
     */
    public void register(View view) {
        signUp = (TextView) view;

        Intent i = new Intent(this, RegisterPetControl.class);
        startActivity(i);
    }

    /**
     * Aparece cuadro de diálogo para cambiar la contraseña.
     *
     * @param v
     */
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
                else {
                    Toast.makeText(getApplicationContext(), "Contraseña cambiada con éxito",
                            Toast.LENGTH_SHORT).show();
                    change = true;
                }

                // Si la contraseña fue cambiada y se confirmó
                if (change) {
                    opc.setPassword(passwordConfirm);
                    Toast.makeText(getApplicationContext(), "Change: " + opc.toString(),
                            Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences("PetControlPreferences", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("password", passwordConfirm);
                    // Aplica los cambios
                    editor.apply();
                }
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