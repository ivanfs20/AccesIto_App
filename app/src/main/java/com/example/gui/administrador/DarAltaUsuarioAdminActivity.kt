package com.example.gui.administrador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.gui.R
import com.example.gui.visitante.IdentificacionVisitanteActivity

class DarAltaUsuarioAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dar_alta_usuario_admin_y_seguridad)

        // Botón para guardar
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            finish()
        }

        //Botón para eliminar
        findViewById<Button>(R.id.btnEliminar).setOnClickListener {
            val intent = Intent(this, EliminarAdminActivity::class.java)
            startActivity(intent)
        }

        //Botón para HOME, regresa a donde muestra el qr del usuario, es decir a la administrador activity
        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            // Crear intent con flags para limpiar la pila
            val intent = Intent(this, AdministradorActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()  // Cierra la actividad actual
        }
    }
}