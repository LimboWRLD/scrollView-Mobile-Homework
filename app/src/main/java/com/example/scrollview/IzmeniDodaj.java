package com.example.scrollview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IzmeniDodaj extends AppCompatActivity {

    int position = -1;
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

        EditText ime = findViewById(R.id.imeInput);
        EditText prezime = findViewById(R.id.prezimeInput);
        EditText tel = findViewById(R.id.telInput);
        EditText skype = findViewById(R.id.skypeInput);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {

            ime.setText(intent.getStringExtra("ime"));

            prezime.setText(intent.getStringExtra("prezime"));

            tel.setText(intent.getStringExtra("telefon"));

            skype.setText(intent.getStringExtra("skype"));

            position = intent.getIntExtra("position", -1);
        }

        Button saveBtn = findViewById(R.id.sacuvajBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rezultat = new Intent();
                rezultat.putExtra("ime", ime.getText().toString());
                rezultat.putExtra("prezime", prezime.getText().toString());
                rezultat.putExtra("telefon", tel.getText().toString());
                rezultat.putExtra("skype", skype.getText().toString());
                rezultat.putExtra("position", position);
                //System.out.println(ime.getText());
                setResult(RESULT_OK, rezultat);
                finish();
            }
        });
    }
}