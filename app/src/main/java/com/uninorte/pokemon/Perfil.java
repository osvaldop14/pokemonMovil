package com.uninorte.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class Perfil extends AppCompatActivity {

    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        t1=(TextView) findViewById(R.id.tv1);
        t2=(TextView) findViewById(R.id.tv2);

        List<UsuarioObjetos> POK;
        POK=new Select().from(UsuarioObjetos.class).queryList();

        t1.setText(POK.get(0).User);
        List<UsuariosPokemones> datos;
        datos = new Select().from(UsuariosPokemones.class).queryList();

        if(datos.size()>=0 && datos.size()<2) {
            t2.setText("LV: "+1);//POK.get(0).UserLevel);
        }
        if(datos.size()>=2 && datos.size()<3) {
            t2.setText("LV: " + 2);//POK.get(0).UserLevel);
        } if(datos.size()>=3) {
            t2.setText("LV: " + 3);//POK.get(0).UserLevel);
        }
    }

    public void VerPokemones(View view) {

        Intent e = new Intent(this, Pokemones_Vista.class);
        startActivity(e);

    }
}
