package com.example.gui.data.DataBase

import android.annotation.SuppressLint
import android.content.Context
import com.example.gui.data.actions.SaveDateUser

class AdministradorDataBase {

    @SuppressLint("Range")
    fun getAllReportes(context: Context): List<String>{
        val db = context.openOrCreateDatabase("AccesItoPrueba", Context.MODE_PRIVATE, null)
        var cursor = db.rawQuery(
            "SELECT id, idAdministracion, fecha_creacion FROM REPORTE", null
        )

        var lista = mutableListOf<String>()

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val id_admin = cursor.getString(cursor.getColumnIndex("idAdministracion"))
                val fecha_c = cursor.getString(cursor.getColumnIndex("fecha_creacion"))
                lista.add(id+"."+id_admin+"."+fecha_c)
            }while (cursor.moveToNext())
        }

        return lista;
    }

    fun insertReporte(context : Context, mes : String, año : String){
        val db = context.openOrCreateDatabase("AccesItoPrueba", Context.MODE_PRIVATE, null)
        var fecha = "01-"+mes+"-"+año
        var cursor = db.rawQuery(
            "INSERT INTO REPORTE (fecha_creacion, idAdministracion) VALUES(?,?)", arrayOf(fecha,)
        )
    }


    fun getIdUser(context: Context, nombre : String, password : String): String{
        return "";
    }

}