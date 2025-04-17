package com.example.gui.data.MigrationPostgresSQL;

import java.lang.reflect.Constructor;
import java.sql.Connection;

public class Instancia {
    public static Instancia instancia = null;
    private static String user = "postgres";
    private static String password = "carloscras15";
    private static String nameDataBase = "accesito";
    private static String jdbc = "jdbc:postgresql://localhost:5432/" + nameDataBase;
    private Instancia(){}
    public static Instancia getInstancia(){
        if(instancia !=null){
            instancia = new Instancia();
            return instancia;
        }
        return instancia;
    }
}
