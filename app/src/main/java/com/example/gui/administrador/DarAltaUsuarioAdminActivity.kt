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

        //Botón para salir
        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}