package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Acceso
import com.example.gui.data.actions.NameDataBase
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EscanearSeguridadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escanear_seguridad)

        findViewById<Button>(R.id.botonGuardarSeguridad).setOnClickListener {
            escanearQR()
        }
    }

    private fun escanearQR() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Escanea el código QR del visitante")
        integrator.setOrientationLocked(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                registrarAccesoDesdeQr(result.contents)
            } else {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun registrarAccesoDesdeQr(contenidoQr: String) {
        val lineas = contenidoQr.lines()
        val lineaNombre = lineas.find { it.startsWith("Nombre:", ignoreCase = true) }

        val nombreUsuario = lineaNombre?.substringAfter("Nombre:")?.trim()

        if (nombreUsuario.isNullOrBlank()) {
            Toast.makeText(this, "Nombre no válido en el QR", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            val db =
                Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB)
                    .build()
            val usuario = db.usuarioDao().getUser(nombreUsuario)

            if (usuario != null) {
                val qr = db.qrDao().getQrByUsuarioId(usuario.id!!)
                if (qr != null) {
                    val fechaEntrada =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                    val nuevoAcceso = Acceso(fechaEntrada, null, qr.id)
                    db.accesoDao().insert(nuevoAcceso)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@EscanearSeguridadActivity,
                            "Acceso registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@EscanearSeguridadActivity,
                            "QR no encontrado para el usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@EscanearSeguridadActivity,
                        "Usuario no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}