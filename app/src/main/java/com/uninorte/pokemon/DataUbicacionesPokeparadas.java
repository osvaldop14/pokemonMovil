package com.uninorte.pokemon;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by aimer on 20/09/2016.
 */
@Table(database = AppDatabase.class)
public class DataUbicacionesPokeparadas extends BaseModel {


    @PrimaryKey(autoincrement = true)
    long id;


    @Column
    public String Latitud;
    @Column
    public String Longitud;


    public DataUbicacionesPokeparadas(){}

    public DataUbicacionesPokeparadas(String latitud, String longitud) {
        this.Latitud = latitud;
        this.Longitud = longitud;
    }

    @Override
    public String toString()
    {
        return id+" "+Latitud+" "+Longitud;
    }



}
