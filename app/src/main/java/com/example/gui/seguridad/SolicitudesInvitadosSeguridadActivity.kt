package com.example.gui.seguridad

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
import com.example.gui.MainActivity
import com.example.gui.R

class SolicitudesInvitadosSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_invitados_seguridad)

        // Datos de ejemplo
        val solicitudesEjemplo = listOf(
            SolicitudesInvitadosSeguridadAdapter.Solicitud("Marian Garcia",22010983,
                "marian@gmail.com","IR","Pendiente"
            ),
            SolicitudesInvitadosSeguridadAdapter.Solicitud("Marian Garcia",22010983,
                "marian@gmail.com","IR","Pendiente")
        )

        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvSolicitudesSeguridadInv).apply {
            layoutManager = LinearLayoutManager(this@SolicitudesInvitadosSeguridadActivity)
            adapter = SolicitudesInvitadosSeguridadAdapter(
                solicitudesEjemplo,
                onAceptarClick = { solicitud ->
                    // Lógica para aceptar solicitud
                    Toast.makeText(this@SolicitudesInvitadosSeguridadActivity,
                        "Aceptado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                },
                onDenegarClick = { solicitud ->
                    // Lógica para denegar solicitud
                    Toast.makeText(this@SolicitudesInvitadosSeguridadActivity,
                        "Denegado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Botones inferiores
        findViewById<Button>(R.id.btnHomeSeguridadInv).setOnClickListener {
            // Navegar a Home
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.btnSalirSeguridadInv).setOnClickListener {
            finish()
        }
    }
}