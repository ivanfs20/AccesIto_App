package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.visitante.IdentificacionVisitanteActivity

class DarAltaUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dar_alta_usuario_admin_y_seguridad)


        // Botón para guardar
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            finish()
        }

        //Botón para eliminar
        findViewById<Button>(R.id.btnEliminar).setOnClickListener {
            val intent = Intent(this, EliminarSeguridadActivity::class.java)
            startActivity(intent)
        }

        //Botón para salir
        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}