package com.uninorte.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class VistaUnPokemon extends AppCompatActivity {
int i;
    TextView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_un_pokemon);

        logo=(ImageView) findViewById(R.id.imageView17);
        i1=(TextView) findViewById(R.id.textView24);  i2=(TextView) findViewById(R.id.textView25);
        i3=(TextView) findViewById(R.id.textView26);  i4=(TextView) findViewById(R.id.textView27);
        i5=(TextView) findViewById(R.id.textView28);  i6=(TextView) findViewById(R.id.textView29);
        i7=(TextView) findViewById(R.id.textView30);  i8=(TextView) findViewById(R.id.textView31);
        i9=(TextView) findViewById(R.id.textView32);

        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {
            i=(int)extras.get("pos");
        }

        List<UsuariosPokemones> datos;

        datos = new Select().from(UsuariosPokemones.class).queryList();


        i1.setText(datos.get(i).Name);
        i2.setText(datos.get(i).Type);
        i3.setText(datos.get(i).Total);
        i4.setText(datos.get(i).HP);
        i5.setText(datos.get(i).Attack);
        i6.setText(datos.get(i).Defense);
        i7.setText(datos.get(i).SpAtk);
        i8.setText(datos.get(i).SpDef);
        i9.setText(datos.get(i).Speed);


        switch (datos.get(i).DI) {
            case "2":
                logo.setImageResource(R.drawable.mms02);
                break;
            case "1":
                logo.setImageResource(R.drawable.mms01);
                break;
            case "3":
                logo.setImageResource(R.drawable.mms03);
                break;
            case "4":
                logo.setImageResource(R.drawable.mms04);
                break;
            case "5":
                logo.setImageResource(R.drawable.mms05);
                break;
            case "6":
                logo.setImageResource(R.drawable.mms06);
                break;
            case "7":
                logo.setImageResource(R.drawable.mms07);
                break;
            case "8":
                logo.setImageResource(R.drawable.mms08);
                break;
            case "9":
                logo.setImageResource(R.drawable.mms09);
                break;
            case "10":
                logo.setImageResource(R.drawable.mms10);
                break;
            case "11":
                logo.setImageResource(R.drawable.mms11);
                break;
            case "12":
                logo.setImageResource(R.drawable.mms12);
                break;
            case "13":
                logo.setImageResource(R.drawable.mms13);
                break;
            case "14":
                logo.setImageResource(R.drawable.mms14);
                break;
            case "15":
                logo.setImageResource(R.drawable.mms15);
                break;
            case "16":
                logo.setImageResource(R.drawable.mms16);
                break;
            case "17":
                logo.setImageResource(R.drawable.mms17);
                break;
            case "18":
                logo.setImageResource(R.drawable.mms18);
                break;
            case "19":
                logo.setImageResource(R.drawable.mms19);
                break;
            case "20":
                logo.setImageResource(R.drawable.mms20);
                break;
            case "21":
                logo.setImageResource(R.drawable.mms21);
                break;
            case "22":
                logo.setImageResource(R.drawable.mms22);
                break;
            case "23":
                logo.setImageResource(R.drawable.mms23);
                break;
            case "24":
                logo.setImageResource(R.drawable.mms24);
                break;
        }


    }
}
