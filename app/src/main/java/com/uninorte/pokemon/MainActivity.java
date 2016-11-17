package com.uninorte.pokemon;


/*/Para obtener la llave
C:\Program Files\Java\jdk1.8.0_101\bin>keytool -list -v -keystore
 "C:\Users\aimer\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
/*/


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult>, OnMapReadyCallback {

    String ds="0";

    public final static int MI_REQUEST_CODE = 111;
    private MediaPlayer reproductor;
    private String TAG = "SampleGoogleApi";
    private GoogleApiClient mgoogleApiClient;
    private LocationRequest mlocationRequest; //para pedir localizacion
    private LocationSettingsRequest mlocationSettingsRequest;//para ver si el telefono soporta priority hig accuracy

    LatLng UbicacionActual,U1[]=new LatLng[10],U2[]=new LatLng[10];
    Marker M[]=new Marker[10],Pp[]=new Marker[10];
    public static final long UPDATE_INTERVAL_IN_MILISECONDS = 15000; //-------Actualiza la ubicacion cada 5 segundos
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILISECONDS = UPDATE_INTERVAL_IN_MILISECONDS;
    public int REQUEST_CHECK_SETTINGS = 1;
    private final int MY_PERMISSIONS_REQUEST = 1;
    private GoogleMap Mapa;// este es el mapa de google

    private ProgressDialog Pdialog;
    String U;
    String Latitudes[]=new String[10];
    String Longitudes[]=new String[10];
    double X,Y, distanciaMinima=50.0;
    int sw=0,w=0;
    Handler mhandler;
    PokeInformation date;
    UsuarioObjetos date1;
    UsuariosPokemones date3;
    String[] Xx =new String[10];
    String[] Xxx =new String[10];
    double[] Xxy =new double[10];
    double[] Xxy2 =new double[10];


    int swparapedirdistancias=0;
    int alerts=0;

    FloatingActionButton floatingActionButton;
    ImageButton imageButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        imageButton=(ImageButton) findViewById(R.id.fab_add);
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

        llamarPokemonesaleatorios();//llama del 1 al 24 para saber que pokemones saldran
        LLenarBaseDePokemones();//llena la base de datos de los pokemones hasta ahora 24

        LLenarBaseDeUsuarioPokemones();
        LLenarBaseDeUsuarioObjetos();

        List<DataUbicaciones> datos = null;
        List<DataUbicacionesPokeparadas> dat = null;
        datos = new Select().from(DataUbicaciones.class).queryList();
        dat = new Select().from(DataUbicacionesPokeparadas.class).queryList();

        while (dat.size() > 0) {
            DataUbicacionesPokeparadas datoBorrar1 = dat.get(0);
            datoBorrar1.delete();
            dat = new Select().from(DataUbicacionesPokeparadas.class).queryList();
        }

        while (datos.size() > 0) {
            DataUbicaciones datoBorrar = datos.get(0);
            datoBorrar.delete();
            datos = new Select().from(DataUbicaciones.class).queryList();

        }////me borra las ubicaciones anteriores de la base de datos
        LocalizacionEmpezar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                U="http://190.144.171.172/function3.php?lat="+X+"&lng="+Y;
                //U="http://190.144.171.172/function3.php?lat=10.9217&lng=-74.795";
                if (verificacion() == true) {
                    new GetData().execute();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new GetData1().execute();
                                }

                            },3000);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Distancias(X,Y);
                        Distancias2(X,Y);
                        swparapedirdistancias=1;
                    }
                },5000);

            }
        },8000);
        reproductor = MediaPlayer.create(this, R.raw.music_pokemon);
        reproductor.setLooping(true);
        reproductor.start();
    }
    //---------------------------------------------------------------------------------------------------------------
public void Distancias(double la,double lo)
{
    List<DataUbicaciones> datos = null;
    datos = new Select().from(DataUbicaciones.class).queryList();
    double PI = 3.14159265358979323846;
    double R = 6378.137;
    for(int i=0;i<10;i++) {
        Double la1 = Double.parseDouble(datos.get(i).Latitud);
        Double lo1 = Double.parseDouble(datos.get(i).Longitud);
        double dLat = PI * (la - la1) / 180;
        double dLong = PI * (lo - lo1) / 180;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(PI * (la) / 180) * Math.cos(PI * (la1) / 180) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Xxy[i] = R * c*1000;
       // Toast.makeText(this, "Mtros "+i, Toast.LENGTH_LONG).show();
    }

}
    public void Distancias2(double la,double lo)
    {
        List<DataUbicacionesPokeparadas> datos = null;
        datos = new Select().from(DataUbicacionesPokeparadas.class).queryList();
        double PI = 3.14159265358979323846;
        double R = 6378.137;
        for(int i=0;i<10;i++) {
            Double la1 = Double.parseDouble(datos.get(i).Latitud);
            Double lo1 = Double.parseDouble(datos.get(i).Longitud);
            double dLat = PI * (la - la1) / 180;
            double dLong = PI * (lo - lo1) / 180;

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(PI * (la) / 180) * Math.cos(PI * (la1) / 180) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Xxy2[i] = R * c*1000;
            // Toast.makeText(this, "Mtros "+i, Toast.LENGTH_LONG).show();
        }

    }
    public void llamarApeleaPokemon(String namePokemon,String pokemon){
        Intent s = new Intent(this,PeleaPokemon.class);
        s.putExtra("POKEMON",pokemon);
        s.putExtra("NamePokemon",namePokemon);
        startActivityForResult(s, MI_REQUEST_CODE);
    }

    public void Alerta1(final String pokemon, final String namePokemon) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Pokemon Cerca :D");
            dialogo1.setMessage("¿ Quiere Pelear con el Pokemon "+pokemon+" ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Toast.makeText(getApplicationContext(), "Pelea Pelea  :p", Toast.LENGTH_LONG).show();
                    alerts=0;//iniciar actividad de pelea
                   llamarApeleaPokemon(namePokemon,pokemon);
                    dialogo1.cancel();
                }
            });
            dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Toast.makeText(getApplicationContext(), "No hubo Pelea Pelea  :p", Toast.LENGTH_LONG).show();
                    alerts=0;
                }
            });
            dialogo1.show();
    }
    public void Alerta2() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Pokeparada cerca :D");
        dialogo1.setMessage("¿ Quiere ir a la pokeparada?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                alerts=0;//iniciar actividad de pelea
                Toast.makeText(getApplicationContext(),"3 objetos recividos",Toast.LENGTH_SHORT).show();
                List<UsuarioObjetos> POKES;
                POKES=new Select().from(UsuarioObjetos.class).queryList();
                    date1 = new UsuarioObjetos("Aimer","1","12","1","6","5","0","0","0","0","0","0","0");
                    date1.save();
                dialogo1.cancel();
            }
        });
        dialogo1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                alerts=0;
            }
        });
        dialogo1.show();
    }
    public void Alerta() {


        if (Xxy2[0] < distanciaMinima){ Alerta2();}
        if (Xxy2[9] <  distanciaMinima){ Alerta2();}
        if (Xxy[0] <  distanciaMinima){ Alerta1(Xxx[0],Xx[0]);}
        if (Xxy[1] <  distanciaMinima){ Alerta1(Xxx[1],Xx[1]);}
        if (Xxy[2] <  distanciaMinima){ Alerta1(Xxx[2],Xx[2]);}
        if (Xxy[3] <  distanciaMinima){ Alerta1(Xxx[3],Xx[3]);}
        if (Xxy[4] <  distanciaMinima){ Alerta1(Xxx[4],Xx[4]);}
        if (Xxy[5] <  distanciaMinima){ Alerta1(Xxx[5],Xx[5]);}
        if (Xxy[6] <  distanciaMinima){ Alerta1(Xxx[6],Xx[6]);}
        if (Xxy[7] <  distanciaMinima){ Alerta1(Xxx[7],Xx[7]);}
        if (Xxy[8] <  distanciaMinima){ Alerta1(Xxx[8],Xx[8]);}
        if (Xxy[9] <  distanciaMinima){ Alerta1(Xxx[9],Xx[9]);}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reproductor.isPlaying()) {
            reproductor.stop();
            reproductor.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reproductor.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        reproductor.pause();
    }


    //---------------------------------------------------------------------------------------------------------------
public void LLenarBaseDeUsuarioObjetos()
{
    List<UsuarioObjetos> POKES;
    POKES=new Select().from(UsuarioObjetos.class).queryList();

    if(POKES.size()<1)
    {
        date1 = new UsuarioObjetos("Aimer","1","10","1","5","5","0","0","0","0","0","0","0");
        date1.save();
    }
}

    public void LLenarBaseDeUsuarioPokemones()
    {
        int x;Random generador = new Random();
        x = Math.abs(generador.nextInt() % 24);

        List<PokeInformation> POK;
        POK=new Select().from(PokeInformation.class).queryList();

        List<UsuariosPokemones> POKES;
        POKES=new Select().from(UsuariosPokemones.class).queryList();

        if(POKES.size()<1)
        {
            date3 = new UsuariosPokemones("Aimer",POK.get(x).DI,POK.get(x).Name,POK.get(x).Type,POK.get(x).Total,
                    POK.get(x).HP,POK.get(x).Attack,POK.get(x).Defense,POK.get(x).SpAtk,POK.get(x).SpDef,
                    POK.get(x).Speed,POK.get(x).UrlImaFront,POK.get(x).UrlImaBack,POK.get(x).UrlGifFront,
                    POK.get(x).UrlGifBack,POK.get(x).ImaUrl,POK.get(x).ev_id);
            date3.save();
        }
    }
public void LLenarBaseDePokemones()
{
    List<PokeInformation> POKES;
    POKES=new Select().from(PokeInformation.class).queryList();

    if(POKES.size()<1)
    {
        date = new PokeInformation("1","Bulbasaur","GRASS-POISON",
                "318","45","49","49","65","65","45","https://img.pokemondb.net/sprites/black-white/normal/bulbasaur.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/bulbasaur.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/bulbasaur.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/bulbasaur.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//001Bulbasaur.png",
                "2");
        date.save();
        date = new PokeInformation("2","Ivysaur","GRASS-POISON",
                "405","60","62","63","80",
                "80",
                "60",
                "https://img.pokemondb.net/sprites/black-white/normal/ivysaur.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/ivysaur.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/ivysaur.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/ivysaur.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//002Ivysaur.png",
                "3");
        date.save();
        date = new PokeInformation("3", "Venusaur", "GRASS-POISON",
                "625","80","100","123","122","120","80",
                "https://img.pokemondb.net/sprites/black-white/normal/venusaur.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/venusaur.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/venusaur.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/venusaur.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//003Venusaur.png",
                "-1");
        date.save();
        date = new PokeInformation("4","Charmander","FIRE","309",
                "39","52","43","60","50","65",
                "https://img.pokemondb.net/sprites/black-white/normal/charmander.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/charmander.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/charmander.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/charmander.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//004Charmander.png",
                "5");
        date.save();
        date = new PokeInformation("5","Charmeleon","FIRE","405","58","64","58","80","65","80",
                "https://img.pokemondb.net/sprites/black-white/normal/charmeleon.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/charmeleon.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/charmeleon.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/charmeleon.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//005Charmaleon.png",
                "6");
        date.save();
        date = new PokeInformation("6","Charizard","FIRE-DRAGON","634","78","130","111","130","85","100",
                "https://img.pokemondb.net/sprites/black-white/normal/charizard.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/charizard.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/charizard.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/charizard.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//006Charizar.png",
                "-1");
        date.save();
        date = new PokeInformation("7","Squirtle","WATER","314","44","48","65","50","64","43",
                "https://img.pokemondb.net/sprites/black-white/normal/squirtle.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/squirtle.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/squirtle.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/squirtle.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//007Squirtle.png",
                "8");
        date.save();
        date = new PokeInformation("8","Wartortle","WATER","405","59","63","80","65","80","58",
                "https://img.pokemondb.net/sprites/black-white/normal/wartortle.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/wartortle.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/wartortle.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/wartortle.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//008Wartortle.png",
                "9");
        date.save();
        date = new PokeInformation("9","Blastoise","WATER","630","79","103","120","135","115","78",
                "https://img.pokemondb.net/sprites/black-white/normal/blastoise.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/blastoise.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/blastoise.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/blastoise.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//009Blastoise.png",
                "-1");
        date.save();
        date = new PokeInformation("10","Caterpie","BUG", "195","45","30","35","20","20","45",
                "https://img.pokemondb.net/sprites/black-white/normal/caterpie.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/caterpie.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/caterpie.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/caterpie.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//010Caterpie.png",
                "11");
        date.save();
        date = new PokeInformation("11","Metapod","BUG","205","50","20","55","25","25","30",
                "https://img.pokemondb.net/sprites/black-white/normal/metapod.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/metapod.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/metapod.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/metapod.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//011Metapod.png",
                "12");
        date.save();
        date = new PokeInformation("12","Butterfree","BUG-FLYING","395","60","45","50","90","80","70",
                "https://img.pokemondb.net/sprites/black-white/normal/butterfree.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/butterfree.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/butterfree.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/butterfree.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//012Butterfree.png",
                "-1");
        date.save();
        date = new PokeInformation("13","Weedle","BUG-POISON","195","40","35","30","20","20","50",
                "https://img.pokemondb.net/sprites/black-white/normal/weedle.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/weedle.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/weedle.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/weedle.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//013Weedle.png",
                "14");
        date.save();
        date = new PokeInformation("14","Kakuna","BUG-POISON","205","45","25","50","25","25","35",
                "https://img.pokemondb.net/sprites/black-white/normal/kakuna.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/kakuna.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/kakuna.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/kakuna.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//014Kakuna.png",
                "15");
        date.save();
        date = new PokeInformation("15","Beedrill","BUG-POISON","495","65","150","40","15","80","145",
                "https://img.pokemondb.net/sprites/black-white/normal/beedrill.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/beedrill.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/beedrill.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/beedrill.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//015Beedrill.png",
                "-1");
        date.save();
        date = new PokeInformation("16","Pidgey","NORMAL-FLYING","251","40","45","40","35","35","56",
                "https://img.pokemondb.net/sprites/black-white/normal/pidgey.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/pidgey.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/pidgey.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/pidgey.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//016Pidgey.png",
                "17");
        date.save();
        date = new PokeInformation("17","Pidgeotto","NORMAL-FLYING","349","63","60","55","50","50","71",
                "https://img.pokemondb.net/sprites/black-white/normal/pidgeotto.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/pidgeotto.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/pidgeotto.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/pidgeotto.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//017Pidgeotto.png",
                "18");
        date.save();
        date = new PokeInformation("18","Pidgeot","NORMAL-FLYING","579","83","80","80","135","80","121",
                "https://img.pokemondb.net/sprites/black-white/normal/pidgeot.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/pidgeot.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/pidgeot.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/pidgeot.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//018Pidgeot.png",
                "-1");
        date.save();
        date = new PokeInformation("19","Rattata","NORMAL","253","30","56","35","25","35","72",
                "https://img.pokemondb.net/sprites/black-white/normal/rattata.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/rattata.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/rattata.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/rattata.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//019Rattata.png",
                "20");
        date.save();
        date = new PokeInformation("20","Raticate","NORMAL","413","55","81","60","50","70","97",
                "https://img.pokemondb.net/sprites/black-white/normal/raticate.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/raticate.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/raticate.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/raticate.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//020Raticate.png",
                "-1");
        date.save();
        date = new PokeInformation("21","Spearow","NORMAL-FLYING","262","40","60", "30", "31","31","70",
                "https://img.pokemondb.net/sprites/black-white/normal/spearow.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/spearow.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/spearow.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/spearow.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//021Spearow.png",
                "22");
        date.save();
        date = new PokeInformation("22","Fearow","NORMAL-FLYING","442","65","90","65","61","61","100",
                "https://img.pokemondb.net/sprites/black-white/normal/fearow.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/fearow.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/fearow.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/fearow.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//022Fearow.png",
                "-1");
        date.save();
        date = new PokeInformation("23","Ekans","POISON","288","35","60","44","40","54","55",
                "https://img.pokemondb.net/sprites/black-white/normal/ekans.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/ekans.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/ekans.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/ekans.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//023Ekans.png",
                "24");
        date.save();
        date = new PokeInformation("24","Arbok","POISON", "438","60","85","69","65","79","80",
                "https://img.pokemondb.net/sprites/black-white/normal/arbok.png",
                "https://img.pokemondb.net/sprites/black-white/back-normal/arbok.png",
                "https://img.pokemondb.net/sprites/black-white/anim/normal/arbok.gif",
                "https://img.pokemondb.net/sprites/black-white/anim/back-normal/arbok.gif",
                "http://190.144.171.172//proyectoMovil//Pokemon-DB//img//024Arbok.png",
                "-1");
        date.save();
      /*      date = new PokeInformation();
            date.save();
            date = new PokeInformation();
            date.save();
       */
    }
}

public void llamarPokemonesaleatorios()
    {
        int x;Random generador = new Random();
        for(int j=0;j<10;j++) {
            x = Math.abs(generador.nextInt() % 24)+1;
            Xx[j]=x+"";
           // Log.d("TAG", Xx[j]);

        }
    }
public void LocalizacionEmpezar()
{
    mgoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    mlocationRequest = new LocationRequest();
    mlocationRequest.setInterval(UPDATE_INTERVAL_IN_MILISECONDS);
    mlocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILISECONDS);
    mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
}

//Aqui se pide que se conecte en el onstart

    @Override
    protected void onStart() {
        super.onStart();
        if (mgoogleApiClient != null) {
            mgoogleApiClient.connect();
        }
        empezar();
    }

    //-------------------ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d(TAG, "Se conecto con googleApis");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
//__________________tambien para permisos en android 6
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
//----------------------------
            Toast.makeText(this, "???", Toast.LENGTH_LONG).show();
            return;

        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mgoogleApiClient);

        if (lastLocation != null) {
            Toast.makeText(this, lastLocation.getLatitude() + " " + lastLocation.getLongitude(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Se Suspendio la conexion");
    }//------------------------

    /*/Para iniciar una conexión administrada de forma automática,
     se debe especificar una implementación de la OnConnectionFailedListener interfaz para recibir
     errores de conexión que no tienen solución.
     Cuando su auto-administrada GoogleApiClientinstancia intenta conectarse a las API de Google,
     se mostrará automáticamente la interfaz de usuario para intentar solucionar los fallos de conexión
     que se pueden resolver (por ejemplo, si los servicios de Google Play necesita ser actualizada).
     Si se produce un error que no puede ser resuelto,
     usted recibirá una llamada a onConnectionFailed().
     /*///---------------------addOnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Algo paso en la conexion", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Algo paso en la conexion");

    }//-----------------------------------------

    public void empezar() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mlocationRequest);
        mlocationSettingsRequest = builder.build();


        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.
                checkLocationSettings(mgoogleApiClient, mlocationSettingsRequest);
        result.setResultCallback(this);
///Aqui pide y dice si el celular soporta la configuracion que le estamos pidiendo Y SI ES EXITOSO hace lo de la localizacion
        //ir abajo y mirar onResult

    }

    public void parar() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mgoogleApiClient, this);
        Toast.makeText(this, "Algo paso en la conexion", Toast.LENGTH_LONG).show();
    }
    public String getUrl()
    {
        return U;
    }
    //--------------------------cambia las ubicaciones
    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        UbicacionActual = new LatLng(location.getLatitude(), location.getLongitude());

        this.X=location.getLatitude();
        this.Y=location.getLongitude();

        Mapa.moveCamera(CameraUpdateFactory.newLatLng(UbicacionActual));
        // Zoom in the Google Map
        Mapa.animateCamera(CameraUpdateFactory.zoomTo(18));
        Mapa.clear();
        Mapa.addMarker(new MarkerOptions().position(UbicacionActual)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ash));

        List<DataUbicaciones> datos = new Select().from(DataUbicaciones.class).queryList();

        if(datos.size()>1){
            Double[] la1=new Double[10];
            Double[] lng1=new Double[10];


            for(int i=0;i<10;i++){
                la1[i] = Double.parseDouble(datos.get(i).Latitud);
                lng1[i] = Double.parseDouble(datos.get(i).Longitud);
                U1[i] = new LatLng(la1[i],lng1[i]);
                }
                if(w==0) {
                    w=1;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llamarPokemonesaleatorios();
                            List<DataUbicaciones> datos = null;
                            datos = new Select().from(DataUbicaciones.class).queryList();

                            while (datos.size() > 0) {
                                DataUbicaciones datoBorrar = datos.get(0);
                                datoBorrar.delete();
                                datos = new Select().from(DataUbicaciones.class).queryList();
                            }

                            U = "http://190.144.171.172/function3.php?lat=" + X + "&lng=" + Y;
                            //U="http://190.144.171.172/function3.php?lat=10.9217&lng=-74.795";
                            if (verificacion() == true) {
                                new GetData().execute();
                            }
                            w=0;
                        }
                    }, 300000);
                }
            MarcarUbicacion();
            if(ds.compareTo("0")==0){
            if(swparapedirdistancias==1)
            {
                Distancias(X,Y);
                Distancias2(X,Y);
                if (alerts==0)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Alerta();alerts=1;    }
                    }, 5000);

                }
            }
            }else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ds="1";
                    }
                }, 300000);

            }

        }
    }
    //---------------------------------------
public void MarcarUbicacion()
{
    BitmapDescriptor imagen[]=new BitmapDescriptor[11];
    List<PokeInformation> datos = new Select().from(PokeInformation.class).queryList();
    int y=0;
    while(y<10) {
        switch (Xx[y]) {
            case "2":
                Xxx[y]=datos.get(1).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms002);y++;
                break;
            case "1":
                Xxx[y]=datos.get(0).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms001);y++;
                break;
            case "3":
                Xxx[y]=datos.get(2).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms003);y++;
                break;
            case "4":
                Xxx[y]=datos.get(3).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms004);y++;
                break;
            case "5":
                Xxx[y]=datos.get(4).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms005);y++;
                break;
            case "6":
                Xxx[y]=datos.get(5).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms006);y++;
                break;
            case "7":
                Xxx[y]=datos.get(6).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms007);y++;
                break;
            case "8":
                Xxx[y]=datos.get(7).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms008);y++;
                break;
            case "9":
                Xxx[y]=datos.get(8).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms009);y++;
                break;
            case "10":
                Xxx[y]=datos.get(9).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms010);y++;
                break;
            case "11":
                Xxx[y]=datos.get(10).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms011);y++;
                break;
            case "12":
                Xxx[y]=datos.get(11).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms012);y++;
                break;
            case "13":
                Xxx[y]=datos.get(12).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms013);y++;
                break;
            case "14":
                Xxx[y]=datos.get(13).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms014);y++;
                break;
            case "15":
                Xxx[y]=datos.get(14).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms015);y++;
                break;
            case "16":
                Xxx[y]=datos.get(15).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms016);y++;
                break;
            case "17":
                Xxx[y]=datos.get(16).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms017);y++;
                break;
            case "18":
                Xxx[y]=datos.get(17).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms018);y++;
                break;
            case "19":
                Xxx[y]=datos.get(18).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms019);y++;
                break;
            case "20":
                Xxx[y]=datos.get(19).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms020);y++;
                break;
            case "21":
                Xxx[y]=datos.get(20).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms021);y++;
                break;
            case "22":
                Xxx[y]=datos.get(21).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms022);y++;
                break;
            case "23":
                Xxx[y]=datos.get(22).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms023);y++;
                break;
            case "24":
                Xxx[y]=datos.get(23).Name;
                imagen[y] = BitmapDescriptorFactory.fromResource(R.drawable.ms024);y++;
                break;
        }
    }
    imagen[10] = BitmapDescriptorFactory.fromResource(R.drawable.pokeparada);
    M[0] = Mapa.addMarker(new MarkerOptions().position(U1[0]).title(Xxx[0]+" "+U1[0])); M[0].setIcon(imagen[0]);
    M[1] = Mapa.addMarker(new MarkerOptions().position(U1[1]).title(Xxx[1]+" "+U1[1]));M[1].setIcon(imagen[1]);
    M[2] = Mapa.addMarker(new MarkerOptions().position(U1[2]).title(Xxx[2]+" "+U1[2]));M[2].setIcon(imagen[2]);
    M[3] = Mapa.addMarker(new MarkerOptions().position(U1[3]).title(Xxx[3]+" "+U1[3]));M[3].setIcon(imagen[3]);
    M[4] = Mapa.addMarker(new MarkerOptions().position(U1[4]).title(Xxx[4]+" "+U1[4]));M[4].setIcon(imagen[4]);
    M[5] = Mapa.addMarker(new MarkerOptions().position(U1[5]).title(Xxx[5]+" "+U1[5]));M[5].setIcon(imagen[5]);
    M[6] = Mapa.addMarker(new MarkerOptions().position(U1[6]).title(Xxx[6]+" "+U1[6]));M[6].setIcon(imagen[6]);
    M[7] = Mapa.addMarker(new MarkerOptions().position(U1[7]).title(Xxx[7]+" "+U1[7]));M[7].setIcon(imagen[7]);
    M[8] = Mapa.addMarker(new MarkerOptions().position(U1[8]).title(Xxx[8]+" "+U1[8]));M[8].setIcon(imagen[8]);
    M[9] = Mapa.addMarker(new MarkerOptions().position(U1[9]).title(Xxx[9]+" "+U1[9]));M[9].setIcon(imagen[9]);


    List<DataUbicacionesPokeparadas> dat = new Select().from(DataUbicacionesPokeparadas.class).queryList();
    Double[] la11=new Double[10];
    Double[] lng11=new Double[10];

    for(int i=0;i<10;i++){
        la11[i] = Double.parseDouble(dat.get(i).Latitud);
        lng11[i] = Double.parseDouble(dat.get(i).Longitud);
        U2[i] = new LatLng(la11[i],lng11[i]);
    }

   Pp[0] = Mapa.addMarker(new MarkerOptions().position(U2[0]).title("PokeParada "+U2[0]));Pp[0].setIcon(imagen[10]);
   Pp[1] = Mapa.addMarker(new MarkerOptions().position(U2[9]).title("PokeParada "+U2[9]));Pp[1].setIcon(imagen[10]);

}


    //---------------setResultCallback
    ////-------para ver si el telefono soporta la configuracion que le pedimos  priority high accuracy
    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

        final Status status = locationSettingsResult.getStatus();///miramos que estado nos llego si fue exitoso o no

        Log.d(TAG,"onResult "+status.getStatusCode());
        switch (status.getStatusCode()){
            case LocationSettingsStatusCodes.SUCCESS:  //CASO EXITOSO
                if (mgoogleApiClient.isConnected()) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "¿?¿?¿?", Toast.LENGTH_LONG).show();
                        return;

                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiClient, mlocationRequest, this);

                }
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //Esto es el caso que el gps este desabilitado hay que pedir que se habilite
                try {
                    status.startResolutionForResult(MainActivity.this,REQUEST_CHECK_SETTINGS);
                    //esto inicia una actividad result que nos dira si la persona habilito el gps o no y
                    //mirar el onActivityresult
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Toast.makeText(this,"LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE",Toast.LENGTH_LONG).show();
                break;
        }
    }
///--------------------------------esto me toco para solicitar permisos en android 6
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e("El", "Permiso denegado");
            }
        }
    }
//---------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==MI_REQUEST_CODE && resultCode==RESULT_OK)
        {
            ds= data.getStringExtra("NOMBRES");
        }
        if(requestCode==1)
        {
                switch (resultCode)
                {case Activity.RESULT_OK:
                    if (mgoogleApiClient.isConnected()) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            Toast.makeText(this, "?", Toast.LENGTH_LONG).show();
                            return;

                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiClient, mlocationRequest, this);

                    }
                    break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this,"disabled??",Toast.LENGTH_LONG).show();
                        break;

                }
        }

    }
    //--------------------------------------------------

//-------------------------------Aqui Pedimos el mapa y el mapa nos llama cuando esta listo

    //aqui entra un mapa que es googleMap y pongo marcadeores si quiero
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.Mapa=googleMap;

        //Mapa.getUiSettings().setAllGesturesEnabled(false);

        //Mapa.getUiSettings().setZoomGesturesEnabled(false);//Modifica la disponibilidad de los gestos de zoom. Usa false para deshabilitarlos.
        //Mapa.getUiSettings().setScrollGesturesEnabled(false);// Realiza exactamente que el método anterior pero para los gestos de desplazamiento.
        //Mapa.getUiSettings().setTiltGesturesEnabled(false);//Igual pero para la inclinación.
        //setRotateGesturesEnabled(boolean);// La disponibilidad en gestos de rotación.

        // Marcadores

    }
//--------------------------------------------------------------------------------------------------

    /////aqui se verifica si hay red o no
    public boolean verificacion() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
           // Toast.makeText(this, "Is connect", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "No tiene internet, revise su conexion", Toast.LENGTH_LONG).show();

            return false;
        }
    }//--------------

    public void Bolsa(View view) {
        Intent e = new Intent(this, Ver_Objetos.class);
        startActivity(e);
    }

    public void Perfil(View view) {
        Intent e = new Intent(this, Perfil.class);
        startActivity(e);
    }

    public void PokeCercas(View view) {
        Toast.makeText(this, "Todavia no hace nada", Toast.LENGTH_LONG).show();
    }


    //---------------------------------------------------------------------------------------------------------
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Pdialog = new ProgressDialog(MainActivity.this);
            Pdialog.setMessage("Please Wait");
            Pdialog.setCancelable(false);
            Pdialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String Respuesta = getData();

            if (Respuesta != null) {

                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(Respuesta);

                    // Getting JSON Array node
                    JSONArray pers = jsonObj.getJSONArray("results");

                    //Log.d("este es",""+pers);

                    // looping through All Equipos

                    for (int i = 0; i < pers.length(); i++) {
                        JSONObject c = pers.getJSONObject(i);
                        //Log.d("este es",""+c);
                        //RECOJEMOS DATOS EN VARIABLES

                        Latitudes[i] = c.getString("lt");
                        Longitudes[i]= c.getString("lng");
                        DataUbicaciones data = new DataUbicaciones(Latitudes[i], Longitudes[i]);
                        data.save();
                        //Log.d("Lat",i+" "+Latitudes[i]);
                        //Log.d("Lng",i+" "+Longitudes[i]);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Servidor X", "Esta habiendo problemas para cargar el JSON");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Pdialog.isShowing())
            {
                Pdialog.dismiss();
            }
        }
    }

    protected  String getData() {
        Log.d("TAG", "Get");
        String rp = null;
        try {
            URL url = null;
            //url= new URL("http://api.randomuser.me/?result=2&format=json");
            String Ud = getUrl();
            url = new URL(Ud);
            URLConnection YT = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(YT.getInputStream()));
            String inputline;
            while ((inputline = in.readLine()) != null) {
                //Log.d("hola",inputline);
                rp = inputline;
            }
            in.close();
            return "{\"results\":" + rp + "}";

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//---------------------------PokeParadas
private class GetData1 extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Pdialog = new ProgressDialog(MainActivity.this);
        Pdialog.setMessage("Please Wait");
        Pdialog.setCancelable(false);
        Pdialog.show();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        String Respuesta = getData();

        if (Respuesta != null) {

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(Respuesta);

                // Getting JSON Array node
                JSONArray pers = jsonObj.getJSONArray("results");

                //Log.d("este es",""+pers);

                // looping through All Equipos

                for (int i = 0; i < pers.length(); i++) {
                    JSONObject c = pers.getJSONObject(i);
                    //Log.d("este es",""+c);
                    //RECOJEMOS DATOS EN VARIABLES

                    Latitudes[i] = c.getString("lt");
                    Longitudes[i]= c.getString("lng");
                    DataUbicacionesPokeparadas data = new DataUbicacionesPokeparadas(Latitudes[i], Longitudes[i]);
                    data.save();
                    //Log.d("Lat",i+" "+Latitudes[i]);
                    //Log.d("Lng",i+" "+Longitudes[i]);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Servidor X", "Esta habiendo problemas para cargar el JSON");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(Pdialog.isShowing())
        {
            Pdialog.dismiss();
        }
    }
}




}


