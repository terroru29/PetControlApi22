package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginPetControl extends AppCompatActivity {

    Button logIn;
    TextView terms, signUp;
    String requirements, conditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petcontrol);

        // Asociación de variables con sus recursos
        logIn = findViewById(R.id.btnLogIn);
        terms = findViewById(R.id.switchTerms);
        signUp = findViewById(R.id.txtSignUp);

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
    }

    public void nextWindow(View view) {
        logIn = (Button) view;

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}