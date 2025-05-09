package com.example.gui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.gui.data.Daos.UsuarioDao
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.NameDataBase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementacion de los escuchadores de los botones

        //boton estudiante
        var botonEstudiante = findViewById<Button>(R.id.botonEstudiante).setOnClickListener {
            openLoginActivity("ESTUDIANTE")
        }

        //boton docente
        var botonDocente = findViewById<Button>(R.id.botonDocente).setOnClickListener {
            openLoginActivity("DOCENTE")
        }

        //boton administrador
        var botonAdministrador = findViewById<Button>(R.id.botonAdministrador).setOnClickListener {
            openLoginActivity("ADMINISTRADOR")
        }

        //boton seguridad
        var botonSeguridad = findViewById<Button>(R.id.botonSeguridad).setOnClickListener {
            openLoginActivity("SEGURIDAD")
        }

        //boton visitante
        var botonVisitante = findViewById<Button>(R.id.botonVisitante).setOnClickListener {
            openLoginActivity("VISITANTE")
        }

        //boton familiar
        var botonFamiliar = findViewById<Button>(R.id.botonFamiliar).setOnClickListener {
            openLoginActivity("FAMILIAR")
        }

        //boton empleado de administracion
        var botonEmpleadoADM = findViewById<Button>(R.id.botonEmpleadoADM).setOnClickListener {
            openLoginActivity("EMPLEADOADM")
        }

        //boton otros empleados
        var botonOtrosEmpleados =
            findViewById<Button>(R.id.botonOtrosEmpleados).setOnClickListener {
                openLoginActivity("OTROS")
            }

        /*
                var db : DataBase
        db = Room.databaseBuilder(this,DataBase::class.java,"PruebasAcces").build();
        val usuario : UsuarioDao = db.usuarioDao()
        Thread{
            usuario.insert(Usuario())
            var listaUsuarios : List<Usuario> = usuario.AllUsuario()
            for (i in listaUsuarios){
                Log.d("id",i.id.toString()+" "+i.nombre1)
            }
        }.start()
         */

        //En segundo plano

        //PRUEBA PARA ASIGNAR QR A ADMINISTRADORES
        val nuevoUsuario = Usuario(
            "Saul Lima Gonzalez",
            true,
            "slg@gmail.com",
            "Saul Lima Gonzalez",
            "limaSa",
            null,
            "administrador",
            ""
        )

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                var db: DataBase

                db = Room.databaseBuilder(
                    applicationContext,
                    DataBase::class.java,
                    NameDataBase.nameDB
                ).build();
                val usuario: UsuarioDao = db.usuarioDao()
                var listaUsers: List<Usuario> = usuario.AllUsuario()

                for (i in listaUsers) {
                    Log.d("usuarios", i.toString())
                }
            }
        }
