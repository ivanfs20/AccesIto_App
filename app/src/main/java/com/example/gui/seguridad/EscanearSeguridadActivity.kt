package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Acceso
import com.example.gui.data.actions.NameDataBase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class EscanearSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escanear_seguridad)

        //al dar el boton guardar se debe de implementar la logica
        findViewById<Button>(R.id.botonGuardarSeguridad).setOnClickListener {
            finish()
        }
    }
    //metodo para ingresar los datos a accesos
    private fun registrarAccesoDesdeQr(contenidoQr: String) {

        val nombreQR = Regex("Usuario: (\\S+)")
        val Coincidencia = nombreQR.find(contenidoQr)
        val nombreUsuario = Coincidencia?.groupValues?.get(1)

        if (nombreUsuario == null) {
            Toast.makeText(this, "Usuario no válido en el QR", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()
            val usuario = db.usuarioDao().getUser(nombreUsuario)

            if (usuario != null) {
                val qr = db.qrDao().getQrByUsuarioId(usuario.id!!)
                if (qr != null) {
                    val fechaEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val nuevoAcceso = Acceso(fechaEntrada, null, qr.id)
                    db.accesoDao().insert(nuevoAcceso)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EscanearSeguridadActivity, "Acceso registrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EscanearSeguridadActivity, "Acceso denegado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EscanearSeguridadActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            }
        }
}
