package com.uninorte.pokemon;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLogin extends AppCompatActivity {


    String na,pass;
    private TextView tv1, tv2;
    private EditText ed1, ed2;
    private Button b1, b2, b3,b4;
    private RadioGroup radio;
    private RadioButton r1, r2, r3;
    private ImageView i1, i2, i3;
    private MediaPlayer reproductor;
    List<Data> datos = null;
    boolean sw=false;


    String xx[] = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_login);

        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        radio = (RadioGroup) findViewById(R.id.Elige_Un_Pokemon);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        i1 = (ImageView) findViewById(R.id.imageView);
        i2 = (ImageView) findViewById(R.id.imageView2);
        i3 = (ImageView) findViewById(R.id.imageView3);

        radio.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.INVISIBLE);
        r3.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);

        reproductor = MediaPlayer.create(this, R.raw.opening);
        reproductor.setLooping(true);
        reproductor.start();


        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        datos = new Select().from(Data.class).queryList();

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


    public void add_in_db(View view) {

        datos = new Select().from(Data.class).queryList();
        if (datos.size() > 0) {

        } else {

            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.VISIBLE);
            b4.setVisibility(View.VISIBLE);
//        radio.setVisibility(View.VISIBLE);
            //      i1.setVisibility(View.VISIBLE);
            //    i2.setVisibility(View.VISIBLE);
            //  i3.setVisibility(View.VISIBLE);
            //r1.setChecked(true);

        }
    }
    public void ok(View view) {

       /* radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radioButton) {
                    xx[2] = "Bulbasaur";
                } else if (checkedId == R.id.radioButton2) {
                    xx[2] = "Charmander";
                } else if (checkedId == R.id.radioButton3) {
                    xx[2] = "Squirtle";
                }

            }

        });*/
        xx[0] = ed1.getText().toString();
        xx[1] = ed2.getText().toString();

        if (xx[0].compareTo("")==0 || xx[1].compareTo("")==0){
        }else{
            if(Ya_esta_usuari(xx[0])==true){
                Toast.makeText(this,"Ya existe este usuario",Toast.LENGTH_SHORT).show();
        }else{
                    xx[2] = 2 + "";//NUMERO ALEATORIO
                    ed1.setText("");
                    ed2.setText("");
                    Data data = new Data(xx[0], xx[1], xx[2]);
                    data.save();
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    //radio.setVisibility(View.INVISIBLE);
                    //i1.setVisibility(View.INVISIBLE);
                    //i2.setVisibility(View.INVISIBLE);
                    //i3.setVisibility(View.INVISIBLE);
                }
        }
    }
public boolean Ya_esta_usuari(String nnn)
{
    int i;
    datos = new Select().from(Data.class).queryList();

    for(i=0;i<datos.size();i++){

        if(datos.get(i).name.compareTo(nnn)==0) {
            return true;
        }
    }
    return false;
}

    public boolean verificarEtrada()
    {
        int i;
        na = ed1.getText().toString();
        pass = ed2.getText().toString();

        datos = new Select().from(Data.class).queryList();

        for(i=0;i<datos.size();i++){

            if(datos.get(i).name.compareTo(na)== 0 && datos.get(i).password.compareTo(pass)==0) {
                return true;
            }
        }

        return false;
    }

    public void Entrar(View view) {

        sw=verificarEtrada();

        if(sw==true) {
            Intent e = new Intent(this, MainActivity.class);
            startActivity(e);
            reproductor.pause();
        }else{Toast.makeText(this,"Verifique Usuario O Contraseña e intente de nuevo",Toast.LENGTH_SHORT).show();}

    }

    public void Cancel(View view) {
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        radio.setVisibility(View.INVISIBLE);
        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);

    }
}
