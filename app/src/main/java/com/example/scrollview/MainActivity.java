package com.example.scrollview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

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

        ArrayList<Kontakt> kontakti = new ArrayList<Kontakt>();
        for ( int i = 0; i<50; i++){
            Kontakt k1 = new Kontakt("ime" + i, "prezime" + i, "tel" + i, "skype" + i);
            kontakti.add(k1);
        }
        SearchView pretraga = findViewById(R.id.pretragaInput);
        pretraga.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            ArrayList<Kontakt> filtrirana = new ArrayList<Kontakt>();

            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrirana = (ArrayList<Kontakt>) kontakti.stream().filter(x->x.ime.contains(query) || x.prezime.contains(query) || x.tel.contains(query) || x.skype.contains(query)).collect(Collectors.toList());
                generisiKontakte(filtrirana);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    filtrirana.clear();
                    filtrirana = kontakti;
                    return false;
                }
                filtrirana = (ArrayList<Kontakt>) kontakti.stream()
                        .filter(x -> x.ime.contains(newText) || x.prezime.contains(newText) ||
                                x.tel.contains(newText) || x.skype.contains(newText))
                        .collect(Collectors.toList());

                generisiKontakte(filtrirana);
                return false;
            }
        });


        generisiKontakte(kontakti);
    }

    private void generisiKontakte(ArrayList kontakti){
        LinearLayout container = findViewById(R.id.container);
        container.removeAllViews();

        for ( int i =0 ;i < kontakti.size(); i++){
            Intent izmeni = new Intent(this, IzmeniDodaj.class);
            LayoutInflater inf = getLayoutInflater();
            Kontakt k = (Kontakt) kontakti.get(i);

            View v = inf.inflate(R.layout.jedanred, null);
            TextView ime = v.findViewById(R.id.ime);
            ime.setText(k.ime);
            TextView prezime = v.findViewById(R.id.prezime);
            prezime.setText(k.prezime);
            TextView tel = v.findViewById(R.id.tel);
            tel.setText(k.tel);
            TextView skype = v.findViewById(R.id.skype);
            skype.setText(k.skype);
            izmeni.putExtra("ime", k.ime);
            izmeni.putExtra("prezime", k.prezime);
            izmeni.putExtra("telefon", k.tel);
            izmeni.putExtra("skype", k.skype);
            v.setBackgroundColor( i%2==0 ? Color.GRAY : Color.CYAN);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(izmeni);
                }
            });
            container.addView(v);

            Button obrisiBtn = v.findViewById(R.id.obrisiBtn);

            obrisiBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout) findViewById(R.id.container)).removeView(v);
                    kontakti.remove(k);
                    generisiKontakte(kontakti);
                    System.out.println("kontakti");
                }
            });
        }
    }

    public static class Kontakt
    {
        private String ime;

        private String prezime;

        private String tel;

        private String skype;

        public String getIme() {
            return ime;
        }

        public Kontakt(String ime, String prezime, String tel, String skype) {
            this.ime = ime;
            this.prezime = prezime;
            this.tel = tel;
            this.skype = skype;
        }

        public void setIme(String ime) {
            this.ime = ime;
        }

        public String getPrezime() {
            return prezime;
        }

        public void setPrezime(String prezime) {
            this.prezime = prezime;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }
    }
}