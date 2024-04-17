package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InicioPetControl extends AppCompatActivity {

    FloatingActionButton fab;
    Animation aparecer, moving;
    ImageView ivLogo;
    TextView txtEslogan;

    // PRUEBA
    Animation aparecer1, aparecer2, aparecer3, aparecer4, aparecer5, aparecer6, aparecer7, aparecer8, aparecer9, aparecer10;
    TextView P, E, T, C, O, N, T2, R2, O2, L;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_petcontrol);

        // Asociación de variables con sus recursos
        fab = findViewById(R.id.floatingActionButton);
        ivLogo = findViewById(R.id.logoPC);
        txtEslogan = findViewById(R.id.eslogan);


        metodoAnimacion(aparecer, R.anim.appearing, ivLogo);
        metodoAnimacion(moving, R.anim.traslado, txtEslogan);

        // PRUEBA

        P = findViewById(R.id.tituloP);
        E = findViewById(R.id.tituloE);
        T = findViewById(R.id.tituloT);
        C = findViewById(R.id.tituloC);
        O = findViewById(R.id.tituloO);
        N = findViewById(R.id.tituloN);
        T2 = findViewById(R.id.tituloT2);
        R2 = findViewById(R.id.tituloR);
        O2 = findViewById(R.id.tituloO2);
        L = findViewById(R.id.tituloL);

        metodoAnimacion(aparecer1, R.anim.appearing1, P);
        metodoAnimacion(aparecer2, R.anim.appearing2, E);
        metodoAnimacion(aparecer3, R.anim.appearing3, T);
        metodoAnimacion(aparecer4, R.anim.appearing4, C);
        metodoAnimacion(aparecer5, R.anim.appearing5, O);
        metodoAnimacion(aparecer6, R.anim.appearing6, N);
        metodoAnimacion(aparecer7, R.anim.appearing7, T2);
        metodoAnimacion(aparecer8, R.anim.appearing8, R2);
        metodoAnimacion(aparecer9, R.anim.appearing9, O2);
        metodoAnimacion(aparecer10, R.anim.appearing10, L);

        //();
    }

    public void next(View view) {
        fab = (FloatingActionButton) view;

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    /*
    public void espera() {
        try {
            Thread.sleep(10000);c
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    */
    public void metodoAnimacion(Animation animation, int anim, View view) {
        // Asignación para que cargue la animación (aparición del logo)
        animation = AnimationUtils.loadAnimation(this, anim);
        // Se resetea la animación
        animation.reset();
        // Empezamos la animación sobre la imagen (mediante su variable) que queremos que aparezca
        view.startAnimation(animation);
    }
    /*
    public void animacionAparecer() {
        // Asignación para que cargue la animación (aparición del logo)
        aparecer = AnimationUtils.loadAnimation(this, R.anim.appearing);
        // Se resetea la animación
        aparecer.reset();
        // Empezamos la animación sobre la imagen (mediante su variable) que queremos que aparezca
        ivLogo.startAnimation(aparecer);
    }
    public void animacionTraslado() {
        // Asignación para que cargue la animación (aparición del logo)
        moving = AnimationUtils.loadAnimation(this, R.anim.traslado);
        // Se resetea la animación
        moving.reset();
        // Empezamos la animación sobre la imagen (mediante su variable) que queremos que aparezca
        txtEslogan.startAnimation(moving);
    }
    */
}