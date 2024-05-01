package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioPetControl extends AppCompatActivity {

    FloatingActionButton fab;
    TextView P, E, T, C, O, N, T2, R1, O2, L, eslogan;
    ImageView logoPC;
    Animation aparecer1, aparecer2, aparecer3, aparecer4, aparecer5, aparecer6, aparecer7,
            aparecer8, aparecer9, aparecer10, aparecer, moving;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_petcontrol);

        // Asociación de variables con sus recursos
        P = findViewById(R.id.tituloP);
        E = findViewById(R.id.tituloE);
        T = findViewById(R.id.tituloT);
        C = findViewById(R.id.tituloC);
        O = findViewById(R.id.tituloO);
        N = findViewById(R.id.tituloN);
        T2 = findViewById(R.id.tituloT2);
        R1 = findViewById(R.id.tituloR);
        O2 = findViewById(R.id.tituloO2);
        L = findViewById(R.id.tituloL);
        logoPC = findViewById(R.id.ivLogo);
        eslogan = findViewById(R.id.txtEslogan);
        fab = findViewById(R.id.floatingActionButton);


        // ANIMACIONES
        showAnimation(aparecer1, R.anim.appearing1, P);
        showAnimation(aparecer2, R.anim.appearing2, E);
        showAnimation(aparecer3, R.anim.appearing3, T);
        showAnimation(aparecer4, R.anim.appearing4, C);
        showAnimation(aparecer5, R.anim.appearing5, O);
        showAnimation(aparecer6, R.anim.appearing6, N);
        showAnimation(aparecer7, R.anim.appearing7, T2);
        showAnimation(aparecer8, R.anim.appearing8, R1);
        showAnimation(aparecer9, R.anim.appearing9, O2);
        showAnimation(aparecer10, R.anim.appearing10, L);
        showAnimation(aparecer, R.anim.appearing, logoPC);

        // Ocultar el eslogan al principio
        //eslogan.setVisibility(View.INVISIBLE);

        // Usar un Handler para retrasar la visibilidad del eslogan
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            /*
            // Crear una animación de desvanecimiento para que el texto aparezca suavemente
            Animation fadeIn = new AlphaAnimation(0, 1);
            // Duración de la animación (2.5 segundos)
            fadeIn.setDuration(2500);
            eslogan.startAnimation(fadeIn);
            */
            eslogan.setVisibility(View.VISIBLE);
            // Animación de traslado
            showAnimation(moving, R.anim.horizontal_movement, eslogan);
        }, 5000); // 5000ms de retraso (5 segundos) antes de aparecer la animación
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Iniciar la actividad a la que quieres cambiar
            Intent intent = new Intent(this, LoginPetControl.class);
            startActivity(intent);
            // Aplicar las transiciones personalizadas de entrada y salida de cada pantalla
            overridePendingTransition(R.anim.fading_entry, R.anim.fading_exit);
            // Cierra la actividad actual para evitar que el usuario regrese a ella
            finish();
        }, 8000); // 8000ms de retraso (8 segundos) antes de cambiar a la próxima actividad
    }
    /**
     * Pasará a la ventana indicada --> Iniciar sesión/Registrarse
     *
     * @param view Elemento sobre el que se ejcutará la acción
     */
    public void next(View view) {
        fab = (FloatingActionButton) view;

        Intent i = new Intent(this, LoginPetControl.class);
        startActivity(i);
    }
    /**
     * Proyectará la animación sobre el elemento pasados como argumentos
     *
     * @param animation Objeto que almacenará la animación
     * @param anim Archivo de animación
     * @param view Elemento sobre el que se va a proyectar la animación
     */
    public void showAnimation(Animation animation, int anim, View view) {
        // Asignación para que cargue la animación (aparición del logo)
        animation = AnimationUtils.loadAnimation(this, anim);
        // Se resetea la animación
        animation.reset();
        // Empezamos la animación sobre la imagen (mediante su variable) que queremos que aparezca
        view.startAnimation(animation);
    }
}