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
import com.example.gui.R
import com.example.gui.estudiante.AgregarFamiliaresActivity

class VisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solicitudes_acceso_visitante)

        // Datos de ejemplo
        val solicitudesEjemplo = listOf(
            SolicitudAdapter.Solicitud("ACENTRARABRIR", "2023-08-15", "ABIERTO",true),
            SolicitudAdapter.Solicitud("ADRIGARARABRIR", "2023-08-16", "CERRADO", false),
            SolicitudAdapter.Solicitud("CARLOSIVANCIFS", "2023-08-16", "NEGADO", false),
            SolicitudAdapter.Solicitud("ADRIRABRIR", "2023-08-17", "ABIERTO",true)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rvSolicitudes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SolicitudAdapter(solicitudesEjemplo) { solicitud ->
            // Lógica para abrir/editar
            if (solicitud.estatus == "ABIERTO") {
                mostrarQR(solicitud.id)
            }
        }


        //funcionamiento de los botones solicitar y salir

        findViewById<Button>(R.id.btnSolicitar).setOnClickListener{
            val intent = Intent(this, DarAltaVisitanteActivity::class.java)
            startActivity(intent)
        }

        //boton salir
        var botonSalir = findViewById<Button>(R.id.btnSalir).setOnClickListener{
            finish()
        }
    }

    private fun mostrarQR(solicitudId: String) {
        val intent = Intent(this, qrVisitanteActivity::class.java).apply {
            putExtra("SOLICITUD_ID", solicitudId)
        }
        startActivity(intent)
    }
}