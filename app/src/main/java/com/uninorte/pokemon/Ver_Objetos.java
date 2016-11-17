package com.uninorte.pokemon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class Ver_Objetos extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver__objetos);

        i1=(ImageView) findViewById(R.id.imageView5); i2=(ImageView) findViewById(R.id.imageView6);
        i3=(ImageView) findViewById(R.id.imageView7); i4=(ImageView) findViewById(R.id.imageView8);
        i5=(ImageView) findViewById(R.id.imageView9); i6=(ImageView) findViewById(R.id.imageView10);
        i7=(ImageView) findViewById(R.id.imageView11); i8=(ImageView) findViewById(R.id.imageView12);
        i9=(ImageView) findViewById(R.id.imageView13); i10=(ImageView) findViewById(R.id.imageView14);
        i11=(ImageView) findViewById(R.id.imageView15); i12=(ImageView) findViewById(R.id.imageView16);

        t1=(TextView)findViewById(R.id.textView3);t2=(TextView)findViewById(R.id.textView4);
        t3=(TextView)findViewById(R.id.textView5);t4=(TextView)findViewById(R.id.textView6);
        t5=(TextView)findViewById(R.id.textView7);t6=(TextView)findViewById(R.id.textView8);
        t7=(TextView)findViewById(R.id.textView9);t8=(TextView)findViewById(R.id.textView10);
        t9=(TextView)findViewById(R.id.textView11);t10=(TextView)findViewById(R.id.textView12);
        t11=(TextView)findViewById(R.id.textView13);t12=(TextView)findViewById(R.id.textView14);


        List<UsuarioObjetos> datos=null;
        datos = new Select().from(UsuarioObjetos.class).queryList();
        int x=datos.size()-1;

        t1.setText(datos.get(x).Pokebola);t2.setText(datos.get(x).SuperBall);t3.setText(datos.get(x).UltraBall);
        t4.setText(datos.get(x).Pocion);t5.setText(datos.get(x).SuperPocion);t6.setText(datos.get(x).HiperPocion);
        t7.setText(datos.get(x).PocionMaxima);t8.setText(datos.get(x).Revivir);t9.setText(datos.get(x).RevivirMaximo);
        t10.setText(datos.get(x).Baya);t11.setText(datos.get(x).Huevo);t12.setText("∞");


    }
}
