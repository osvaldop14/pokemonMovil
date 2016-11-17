package com.uninorte.pokemon;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class PeleaPokemon extends AppCompatActivity {
    List<UsuariosPokemones> datos;
    UsuariosPokemones date3;
    String n,p;
    ImageView rival,logo;
    TextView rivalname,mipokemon;
    ProgressBar mipokemonPelea,pokemonSalvaje;
    int vida=120,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelea_pokemon);
        rival=(ImageView) findViewById(R.id.imageView19);
        logo=(ImageView) findViewById(R.id.imageView18);
        rivalname=(TextView)findViewById(R.id.textView34);
        mipokemon=(TextView)findViewById(R.id.textView33);

        mipokemonPelea = (ProgressBar)findViewById(R.id.progressBar3);
        pokemonSalvaje = (ProgressBar)findViewById(R.id.progressBar);
        pokemonSalvaje.setProgress(vida);

        Intent t = getIntent();
        n = t.getStringExtra("NamePokemon");
        p = t.getStringExtra("POKEMON");
        //Toast.makeText(getApplicationContext(),""+n+" "+p,Toast.LENGTH_SHORT).show();
        ponerPokemon(n,p);ponerMiPokemon();
        AtacarMiPokemon();



    }


    public void ponerPokemon(String pokemon,String nombre) {
        switch (pokemon) {
            case "2":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms02);
                break;
            case "1":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms01);
                break;
            case "3":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms03);
                break;
            case "4":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms04);
                break;
            case "5":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms05);
                break;
            case "6":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms06);
                break;
            case "7":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms07);
               break;
            case "8":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms08);
                break;
            case "9":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms09);
                break;
            case "10":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms10);
                break;
            case "11":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms11);
                break;
            case "12":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms12);
                break;
            case "13":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms13);
                break;
            case "14":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms14);
                break;
            case "15":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms15);
                break;
            case "16":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms16);
                break;
            case "17":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms17);
                break;
            case "18":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms18);
                break;
            case "19":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms19);
                break;
            case "20":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms20);
                break;
            case "21":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms21);
                break;
            case "22":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms22);
                break;
            case "23":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms23);
                break;
            case "24":
                rivalname.setText(nombre);
                rival.setImageResource(R.drawable.mms24);
                break;

        }

    }

    public void ponerMiPokemon()
    {
        int i=0;
        datos = new Select().from(UsuariosPokemones.class).queryList();

/*
        i1.setText(datos.get(i).Name);
        i2.setText(datos.get(i).Type);
        i3.setText(datos.get(i).Total);
        i4.setText(datos.get(i).HP);
        i5.setText(datos.get(i).Attack);
        i6.setText(datos.get(i).Defense);
        i7.setText(datos.get(i).SpAtk);
        i8.setText(datos.get(i).SpDef);
        i9.setText(datos.get(i).Speed);
*/
        y= 100+Integer.parseInt(datos.get(0).HP);
        mipokemonPelea.setProgress(y);
        switch (datos.get(i).DI) {
            case "2":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms02);
                break;
            case "1":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms01);
                break;
            case "3":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms03);
                break;
            case "4":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms04);
                break;
            case "5":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms05);
                break;
            case "6":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms06);
                break;
            case "7":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms07);
                break;
            case "8":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms08);
                break;
            case "9":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms09);
                break;
            case "10":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms10);
                break;
            case "11":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms11);
                break;
            case "12":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms12);
                break;
            case "13":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms13);
                break;
            case "14":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms14);
                break;
            case "15":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms15);
                break;
            case "16":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms16);
                break;
            case "17":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms17);
                break;
            case "18":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms18);
                break;
            case "19":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms19);
                break;
            case "20":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms20);
                break;
            case "21":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms21);
                break;
            case "22":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms22);
                break;
            case "23":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms23);
                break;
            case "24":
                mipokemon.setText(datos.get(0).Name);
                logo.setImageResource(R.drawable.mms24);
                break;
        }


    }

    public void atk(View view) {
        vida=vida-4;
        pokemonSalvaje.setProgress(vida);
        if(vida<0)
        {  Intent intent = new Intent();
            intent.putExtra("NOMBRES","0");
            setResult(RESULT_OK,intent);
            finish();
        }
        if(vida<8)
        {  Alerta2();
        }
    }

    public void AtacarMiPokemon()
    {
        if(y<0)
        {  Intent intent = new Intent();
            setResult(RESULT_OK,intent);
            intent.putExtra("NOMBRES","0");
            finish();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                y=y-10;
                mipokemonPelea.setProgress(y);
                AtacarMiPokemon();
            }
        },3000);

    }

    public void Alerta2() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Pokemon Debilitado");
        dialogo1.setMessage("¿ Quiere atrapar al pokemon?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Toast.makeText(getApplicationContext(),"Pokemon atrapado",Toast.LENGTH_SHORT).show();

                List<PokeInformation> POK;
                POK=new Select().from(PokeInformation.class).queryList();

                List<UsuariosPokemones> POKES;
                POKES=new Select().from(UsuariosPokemones.class).queryList();
                int x=Integer.parseInt(n)-1;

                   date3 = new UsuariosPokemones("Aimer",POK.get(x).DI,POK.get(x).Name,POK.get(x).Type,POK.get(x).Total,
                                    POK.get(x).HP,POK.get(x).Attack,POK.get(x).Defense,POK.get(x).SpAtk,POK.get(x).SpDef,
                                    POK.get(x).Speed,POK.get(x).UrlImaFront,POK.get(x).UrlImaBack,POK.get(x).UrlGifFront,
                                    POK.get(x).UrlGifBack,POK.get(x).ImaUrl,POK.get(x).ev_id);
                date3.save();
                dialogo1.cancel();
                Intent intent = new Intent();
                intent.putExtra("NOMBRES","1");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();
    }

}
