package com.example.gui.visitante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import com.example.gui.estudiante.AgregarFamiliaresActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solicitudes_acceso_visitante)

        val recyclerView = findViewById<RecyclerView>(R.id.rvSolicitudes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()
            val listaQr = db.qrDao().AllQr()
            val listaUsuarios = db.usuarioDao().AllUsuario()

            val listaSolicitudes = listaQr.mapNotNull { qr ->
                val usuario = listaUsuarios.find { it.id == qr.idUsuario }
                if (usuario != null) {
                    val estatus = when (qr.estado.uppercase()) {
                        "ACTIVO" -> "ABIERTO"
                        "EXPIRADO" -> "CERRADO"
                        "DENEGADO" -> "NEGADO"
                        else -> null
                    }


                    estatus?.let {
                        SolicitudAdapter.Solicitud(
                            id = usuario.nombreC,
                            fecha = qr.fecha,
                            estatus = it,
                            necesitaAccion = it == "ABIERTO"
                        )
                    }
                } else null
            }
            withContext(Dispatchers.Main) {
                recyclerView.adapter = SolicitudAdapter(listaSolicitudes) { solicitud ->
                    if (solicitud.estatus == "ABIERTO") {
                        mostrarQR(solicitud.id)
                    }
                }
            }
        }

        findViewById<Button>(R.id.btnSolicitar).setOnClickListener {
            val intent = Intent(this, DarAltaVisitanteActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }

    private fun mostrarQR(nombreUsuario: String) {
        val intent = Intent(this, qrVisitanteActivity::class.java).apply {
            putExtra("SOLICITUD_ID", nombreUsuario)
        }
        startActivity(intent)
        }
}