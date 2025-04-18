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
import com.example.gui.administrador.SolicitudesUsuariosAdministradorAdapter

class SolicitudesUsuariosSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_usuarios_seguridad)

        // Datos de ejemplo
        val solicitudesEjemplo = listOf(
            SolicitudesUsuariosSeguridadAdapter.Solicitud("Marian Garcia",22010983,
                "marian@gmail.com","IR","Pendiente"
            ),
            SolicitudesUsuariosSeguridadAdapter.Solicitud("Marian Garcia",22010983,
                "marian@gmail.com","IR","Pendiente")
        )

        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvSolicitudesSeguridad).apply {
            layoutManager = LinearLayoutManager(this@SolicitudesUsuariosSeguridadActivity)
            adapter = SolicitudesUsuariosSeguridadAdapter(
                solicitudesEjemplo,
                onAceptarClick = { solicitud ->
                    // Lógica para aceptar solicitud
                    Toast.makeText(this@SolicitudesUsuariosSeguridadActivity,
                        "Aceptado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                },
                onDenegarClick = { solicitud ->
                    // Lógica para denegar solicitud
                    Toast.makeText(this@SolicitudesUsuariosSeguridadActivity,
                        "Denegado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Botones inferiores
        findViewById<Button>(R.id.btnHomeSeguridad).setOnClickListener {
            // Navegar a la pantalla donde esta el qr
            finish()
        }

        findViewById<Button>(R.id.btnSalirSeguridad).setOnClickListener {
            // Cierra toda la pila de actividades y regresa a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}