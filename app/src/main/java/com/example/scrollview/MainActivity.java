package com.example.scrollview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayList<Kontakt> kontakti = new ArrayList<>();


    private final ActivityResultLauncher<Intent> addKontaktLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
        if (result.getResultCode() == RESULT_OK &&  result.getData() != null){
            Intent data = result.getData();
            System.out.println(data.getStringExtra("ime"));
            String ime = data.getStringExtra("ime");
            String prezime = data.getStringExtra("prezime");
            String tel = data.getStringExtra("telefon");
            String skype = data.getStringExtra("skype");

            Kontakt newKontakt = new Kontakt(ime, prezime, tel, skype);
            kontakti.add(newKontakt);
            generisiKontakte(kontakti);
        }
    });

    private final ActivityResultLauncher<Intent> editKontaktLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
        if (result.getResultCode() == RESULT_OK &&  result.getData() != null){
            Intent data = result.getData();
            System.out.println(data.getIntExtra("position", -1));
            String ime = data.getStringExtra("ime");
            String prezime = data.getStringExtra("prezime");
            String tel = data.getStringExtra("telefon");
            String skype = data.getStringExtra("skype");
            int position = data.getIntExtra("position", -1);
            if(position != -1){
                Kontakt toUpdate = kontakti.get(position);
                toUpdate.setIme(ime);
                toUpdate.setPrezime(prezime);
                toUpdate.setTel(tel);
                toUpdate.setSkype(skype);
                generisiKontakte(kontakti);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent dodaj = new Intent(this, IzmeniDodaj.class);
        Button dodajBtn = findViewById(R.id.dodajBtn);
        dodajBtn.setOnClickListener(v -> addKontaktLauncher.launch(dodaj));

        for ( int i = 0; i<50; i++){
            Kontakt k1 = new Kontakt("ime" + i, "prezime" + i, "tel" + i, "skype" + i);
            kontakti.add(k1);
        }
        SearchView pretraga = findViewById(R.id.pretragaInput);
        pretraga.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            ArrayList<Kontakt> filtrirana = new ArrayList<>();

            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrirana = (ArrayList<Kontakt>) kontakti.stream().filter(x->x.getIme().contains(query) || x.getPrezime().contains(query) || x.getTel().contains(query) || x.getSkype().contains(query)).collect(Collectors.toList());
                generisiKontakte(filtrirana);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    filtrirana.clear();
                    filtrirana = kontakti;
                    generisiKontakte(filtrirana);
                    return false;
                }
                filtrirana = (ArrayList<Kontakt>) kontakti.stream()
                        .filter(x -> x.getIme().contains(newText) || x.getPrezime().contains(newText) ||
                                x.getTel().contains(newText) || x.getSkype().contains(newText))
                        .collect(Collectors.toList());

                generisiKontakte(filtrirana);
                return false;
            }
        });


        generisiKontakte(kontakti);
    }

    private void generisiKontakte(ArrayList<Kontakt> kontakti){
        LinearLayout container = findViewById(R.id.container);
        container.removeAllViews();

        for ( int i =0 ;i < kontakti.size(); i++){
            Intent izmeni = new Intent(this, IzmeniDodaj.class);
            LayoutInflater inf = getLayoutInflater();
            Kontakt k = kontakti.get(i);

            View v = inf.inflate(R.layout.jedanred, null);
            TextView ime = v.findViewById(R.id.ime);
            ime.setText(k.getIme());
            TextView prezime = v.findViewById(R.id.prezime);
            prezime.setText(k.getPrezime());
            TextView tel = v.findViewById(R.id.tel);
            tel.setText(k.getTel());
            TextView skype = v.findViewById(R.id.skype);
            skype.setText(k.getSkype());
            izmeni.putExtra("ime", k.getIme());
            izmeni.putExtra("prezime", k.getPrezime());
            izmeni.putExtra("telefon", k.getTel());
            izmeni.putExtra("skype", k.getSkype());
            izmeni.putExtra("position", i);
            v.setBackgroundColor( i%2==0 ? Color.GRAY : Color.CYAN);
            v.setOnClickListener(v1 -> editKontaktLauncher.launch(izmeni));
            container.addView(v);

            Button obrisiBtn = v.findViewById(R.id.obrisiBtn);

            obrisiBtn.setOnClickListener(v12 -> {
                ((LinearLayout) findViewById(R.id.container)).removeView(v12);
                kontakti.remove(k);
                generisiKontakte(kontakti);
            });
        }
    }
}