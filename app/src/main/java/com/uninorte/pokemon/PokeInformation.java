package com.uninorte.pokemon;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class PokeInformation  extends BaseModel {


    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    public String DI;
    @Column
    public String Name;
    @Column
    public String Type;
    @Column
    public String Total;
    @Column
    public String HP;
    @Column
    public String Attack;
    @Column
    public String Defense;
    @Column
    public String SpAtk;
    @Column
    public String SpDef;
    @Column
    public String Speed;
    @Column
    public String UrlImaFront;
    @Column
    public String UrlImaBack;
    @Column
    public String UrlGifFront;
    @Column
    public String UrlGifBack;
    @Column
    public String ImaUrl;
    @Column
    public String ev_id;




    public PokeInformation(){}

    public PokeInformation(String dI,String name, String type, String total, String HP, String attack, String defense, String spAtk, String spDef, String speed, String urlImaFront, String urlImaBack, String urlGifFront, String urlGifBack, String imaUrl, String ev_id) {
        this.DI=dI;
        this.Name = name;
        this.Type = type;
        this.Total = total;
        this.HP = HP;
        this.Attack = attack;
        this.Defense = defense;
        this.SpAtk = spAtk;
        this.SpDef = spDef;
        this.Speed = speed;
        this.UrlImaFront = urlImaFront;
        this.UrlImaBack = urlImaBack;
        this.UrlGifFront = urlGifFront;
        this.UrlGifBack = urlGifBack;
        this.ImaUrl = imaUrl;
        this.ev_id = ev_id;
    }

    @Override
    public String toString()
    {
        return id+" "+DI+" "+Name+" "+Type+" "+Total+" "+HP+" "+Attack+" "+Defense+" "+SpAtk+" "+SpDef+" "+Speed+" "+
                UrlImaFront+" "+UrlGifBack+" "+UrlGifFront+" "+UrlGifBack+" "+ImaUrl+" "+ev_id;
    }


}











