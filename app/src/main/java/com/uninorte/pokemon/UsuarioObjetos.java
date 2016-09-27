package com.uninorte.pokemon;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class UsuarioObjetos extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    public String User;
    @Column
    public String UserLevel;
    @Column
    public String Pokebola;
    @Column
    public String Huevo;
    @Column
    public String Revivir;
    @Column
    public String Pocion;
    @Column
    public String Baya;
    @Column
    public String SuperPocion;
    @Column
    public String SuperBall;
    @Column
    public String HiperPocion;
    @Column
    public String UltraBall;
    @Column
    public String PocionMaxima;
    @Column
    public String RevivirMaximo;

    public UsuarioObjetos() {

    }

    public UsuarioObjetos(String user, String userLevel, String pokebola, String huevo, String revivir, String pocion, String baya, String superPocion, String superBall, String hiperPocion, String ultraBall, String pocionMaxima, String revivirMaximo) {
        User = user;
        UserLevel = userLevel;
        Pokebola = pokebola;
        Huevo = huevo;
        Revivir = revivir;
        Pocion = pocion;
        Baya = baya;
        SuperPocion = superPocion;
        SuperBall = superBall;
        HiperPocion = hiperPocion;
        UltraBall = ultraBall;
        PocionMaxima = pocionMaxima;
        RevivirMaximo = revivirMaximo;
    }
    @Override
    public String toString()
    {
        return id+" "+User+" "+UserLevel+" "+Pokebola+" "+Huevo+" "+Revivir+" "+Pocion+" "+Baya+" "+SuperPocion+" "+SuperBall
                +" "+HiperPocion+" "+UltraBall+" "+PocionMaxima+" "+RevivirMaximo;
    }
}
