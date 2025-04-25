package com.example.gui

import android.content.Context
import android.content.SharedPreferences

class GestionarId(private val context:Context) {
    private val nombrearchivo="Sesion"

    private val id_admin="idAdministracion"

    private val sharedPreferences: SharedPreferences =context.getSharedPreferences(nombrearchivo,Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor=sharedPreferences.edit()

    //Funcion para guardar el id de quien este iniciando sesion--Lima
    fun guardarIdAdmin(idAdmin:Long){
        editor.putLong(id_admin,idAdmin)
        editor.apply()
    }

    //Funcion para obtener el Id del admin
    fun obtenerIdAdmin():Long{
        return sharedPreferences.getLong(id_admin,-1)
    }

    //Funcion para eliminar el id al cerrar sesion
    fun cerarSesion(){
        editor.remove(id_admin)
        editor.apply()
    }

}