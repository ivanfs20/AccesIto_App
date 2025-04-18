package com.example.gui.administrador

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
import com.example.gui.seguridad.DarAltaUsuarioActivity

class usuariosAdministradorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_administrador)

        // Usuarios de ejemplo
        val usuariosEjemplo = listOf(
            usuariosAdministradorAdapter.Usuarios("Dato1", 22010983, "Valor3", 2721813047,true),
            usuariosAdministradorAdapter.Usuarios("DatoA", 22010910, "ValorC", 2725659491, true)
        )

        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvDatosAdministrador).apply {
            layoutManager = LinearLayoutManager(this@usuariosAdministradorActivity)
            adapter = usuariosAdministradorAdapter(usuariosEjemplo) { usuario ->
                // Recordar implementar accion para el boton editar, dado que debe de mostrar la pantalla para editar al usuario
                Toast.makeText(this@usuariosAdministradorActivity, "Acción: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar botones inferiores
        findViewById<Button>(R.id.btnHomeAdministrador).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDarAltaAdministrador).setOnClickListener {
            val intent = Intent(this, DarAltaUsuarioAdminActivity::class.java)
            startActivity(intent)
        }

        //se ajusto para que salga a la MainActivity
        findViewById<Button>(R.id.btnSalirAdministrador).setOnClickListener {
            // Cierra toda la pila de actividades y regresa a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}