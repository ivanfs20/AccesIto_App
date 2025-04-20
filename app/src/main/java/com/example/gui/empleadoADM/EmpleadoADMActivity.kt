package com.example.gui.empleadoADM

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmpleadoADMActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrsimple)

        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido empleado de ADM: $usuario", Toast.LENGTH_SHORT).show()
        val ivQr=findViewById<ImageView>(R.id.qrImagen)
        val nombreUsuario=findViewById<TextView>(R.id.mostNombreUsuario)
        //obtener qr y mostrarlos (Lima)
        GlobalScope.launch(Dispatchers.IO){
            var db :DataBase
               db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB)
                    .build()
            val usuario=db.usuarioDao().getUser(usuario)

            if(usuario!=null){
                nombreUsuario.text=usuario.nombreC
                val qr= db.qrDao().getQrByUsuarioId(usuario.id!!)
                if(qr!=null){
                    val bitmap= BitmapFactory.decodeByteArray(qr.codigo,0,qr.codigo.size)
                    runOnUiThread {
                        ivQr.setImageBitmap(bitmap)
                    }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@EmpleadoADMActivity,"Qr no encontrado",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                runOnUiThread {
                    Toast.makeText(this@EmpleadoADMActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //boton salir
        var botonSalir = findViewById<Button>(R.id.botonSalir).setOnClickListener{
            finish()
        }
    }
}