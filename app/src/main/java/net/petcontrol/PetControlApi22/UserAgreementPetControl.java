package net.petcontrol.PetControlApi22;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserAgreementPetControl extends AppCompatActivity {
    ImageButton arrow;
    TextView agrees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement_petcontrol);

        // Asociar la variable con su recurso
        arrow = findViewById(R.id.imgbBack);
        agrees = findViewById(R.id.txtAgreements);

        // Acoger el recurso de cadena y transformar el texto escrito en HTML
        String htmlString = getString(R.string.agreement);
        //agrees.setText(Html.fromHtml(getString(R.string.agreement)));
        agrees.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));

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
    }
    @Override
    public void onBackPressed() {
        // Asegurará que la actividad actual se cierra y vuelve al fragmento anterior
        super.onBackPressed();
        finish();  // Asegurar que la actividad se cierra
    }
}