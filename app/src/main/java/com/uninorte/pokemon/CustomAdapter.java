package com.uninorte.pokemon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    List<UsuariosPokemones> data0;
    //private Vector<String> data0;
   // private Vector<String> data1;
    //private Vector<String> data2;
    public static int f;


    public CustomAdapter(Context context, List<UsuariosPokemones> values)
    {
        this.context=context;
        this.data0=values;
    }

    @Override
    public int getCount() {
        return data0.size();
    }

    @Override
    public Object getItem(int i) {
        return data0.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String text = data0.get(i).toString();
    //    String text1 =data1.elementAt(i);
      //  String text2 =data2.elementAt(i);

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row,null);
        }

        TextView tv3row = (TextView) view.findViewById(R.id.t3);
        TextView tv4row = (TextView) view.findViewById(R.id.t4);
        TextView tv5row = (TextView) view.findViewById(R.id.t5);
        ImageView logo= (ImageView) view.findViewById(R.id.ima);


        tv3row.setText(data0.get(i).Name);
        tv4row.setText("Atk: "+data0.get(i).Attack);
        tv5row.setText("Def: "+data0.get(i).Defense);

        switch (data0.get(i).DI) {
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




        return view;
    }

    public void setData(List<UsuariosPokemones> values) {

        this.data0=values;


    }

}
