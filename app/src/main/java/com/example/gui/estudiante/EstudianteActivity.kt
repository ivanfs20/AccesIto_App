package com.example.gui.estudiante

import android.content.Intent
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


class EstudianteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrcompuesto)

        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido estudiante: $usuario", Toast.LENGTH_SHORT).show()
        val ivQr=findViewById<ImageView>(R.id.qrImagen)
        val nombreUsuario=findViewById<TextView>(R.id.mostNombreUsuario)

        //obtener qr y mostrarlo en imageview (Lima)
        GlobalScope.launch(Dispatchers.IO){
            var db: DataBase
            db =
                Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB)
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
                        Toast.makeText(this@EstudianteActivity,"Qr no encontrado",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                runOnUiThread {
                    Toast.makeText(this@EstudianteActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Dar de alta
        var botonAlta = findViewById<Button>(R.id.botonAlta).setOnClickListener {
            val intent = Intent(this, AgregarFamiliaresActivity::class.java)
            intent.putExtra("USUARIO", usuario) // Si necesitas pasar datos
            startActivity(intent)
        }

        //boton salir
        var botonSalir = findViewById<Button>(R.id.botonSalir).setOnClickListener{
            finish()
        }

    }
}