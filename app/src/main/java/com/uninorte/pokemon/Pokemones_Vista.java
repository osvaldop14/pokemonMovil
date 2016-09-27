package com.uninorte.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class Pokemones_Vista extends AppCompatActivity {

    ListView lv;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemones__vista);

        lv = (ListView)findViewById(R.id.listView);

        List<UsuariosPokemones> datos;

        datos = new Select().from(UsuariosPokemones.class).queryList();

        customAdapter = new CustomAdapter(this,datos);
        lv.setAdapter(customAdapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                llamar(i);
                //Log.d(TAG,"Click en "+i+" "+values[i]+" "+values1[i]+" "+values2[i]);


            }
        });

    }



    public void llamar(int i)
    {
        Intent e = new Intent(this,VistaUnPokemon.class);
        e.putExtra("pos",i);
        startActivity(e);
    }
}
