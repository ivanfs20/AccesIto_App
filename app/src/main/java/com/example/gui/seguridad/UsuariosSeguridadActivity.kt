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
import com.example.gui.R
import com.example.gui.administrador.usuariosAdministradorAdapter
import com.example.gui.visitante.IdentificacionVisitanteActivity

class UsuariosSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_seguridad)

        // Usuarios de ejemplo
        val usuariosEjemploSe = listOf(
            usuariosSeguridadAdapter.Usuarios("Dato1", 22010983, "Valor3", 2721813047,true),
            usuariosSeguridadAdapter.Usuarios("DatoA", 22010910, "ValorC", 2725659491, true)
        )

        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvDatosSeguridad).apply {
            layoutManager = LinearLayoutManager(this@UsuariosSeguridadActivity)
            adapter = usuariosSeguridadAdapter(usuariosEjemploSe) { usuario ->
                // Lógica para el botón de acción
                Toast.makeText(this@UsuariosSeguridadActivity, "Acción: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar botones inferiores
        findViewById<Button>(R.id.btnHomeSeguridad).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDarAltaSeguridad).setOnClickListener {
            val intent = Intent(this, DarAltaUsuarioActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSalirSeguridad).setOnClickListener {
            finish()
        }
    }
}