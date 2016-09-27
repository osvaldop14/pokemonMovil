///ME SOBRA ESTA CLASE NO HACE NADA

package com.uninorte.pokemon;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class Data extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;


    @Column
    public String name;
    @Column
    public String password;
    @Column
    public String pokeId;


    public Data(){}


    public Data(String name, String pass, String pokeId) {
        this.name = name;
        this.password = pass;
        this.pokeId = pokeId;

    }

    @Override
    public String toString()
    {
        return id+" "+name+" "+password+" "+pokeId;
    }


}