///ME SOBRA ESTA CLASE NO HACE NADA

package com.uninorte.pokemon;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)

public class AppDatabase {

    public static final String NAME = "DBUsers";

    public static final int VERSION = 1;
}