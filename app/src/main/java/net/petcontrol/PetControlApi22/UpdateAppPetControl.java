package net.petcontrol.PetControlApi22;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class UpdateAppPetControl extends AppCompatActivity {
    ImageButton arrow;
    ImageView update;
    Animation reboundAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app_petcontrol);

        // Asociar la variable con su recurso
        update = findViewById(R.id.ivUpdate);
        arrow = findViewById(R.id.imgbBack);

        // Cargar la animación desde el archivo XML
        reboundAnimation = AnimationUtils.loadAnimation(this, R.anim.rebound);


        //--EVENTO IMAGEBUTTON
        arrow.setOnClickListener(v -> onBackPressed());

        // Registrar un callback para manejar el botón de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();  // Asegura que la actividad se cierra
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


        //---EVENTO IMAGEVIEW
        update.setOnClickListener(v -> {
            // Aplicar la animación de rebote cuando se hace clic en la imagen
            v.startAnimation(reboundAnimation);
        });
    }
    @Override
    public void onBackPressed() {
        // Asegurará que la actividad actual se cierra y vuelve al fragmento anterior
        super.onBackPressed();
        finish();  // Asegurar que la actividad se cierra
    }
}