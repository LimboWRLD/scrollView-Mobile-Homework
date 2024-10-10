package com.example.scrollview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IzmeniDodaj extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.izmeni_dodaj);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {

            EditText ime = findViewById(R.id.imeInput);
            ime.setText(intent.getStringExtra("ime"));

            EditText prezime = findViewById(R.id.prezimeInput);
            prezime.setText(intent.getStringExtra("prezime"));

            EditText tel = findViewById(R.id.telInput);
            tel.setText(intent.getStringExtra("telefon"));

            EditText skype = findViewById(R.id.skypeInput);
            skype.setText(intent.getStringExtra("skype"));

        }
    }
}