package net.petcontrol.PetControlApi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginPetControl extends AppCompatActivity {

    Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petcontrol);

        // Asociación de variables con sus recursos
        logIn = findViewById(R.id.btnLogIn);
    }

    public void nextWindow(View view) {
        logIn = (Button) view;

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}