package com.example.gui.seguridad

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class EliminarSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eliminar_usuario_admin_y_seguridad)

        //ACA YA DBEN DE IMPLEMENTAR SU LOGICA SI VA A ELIMINAR O NO
        //boton si
        findViewById<Button>(R.id.botonEliminar).setOnClickListener {
            finish() // Cierra esta actividad y regresa
        }

        //boton para no
        findViewById<Button>(R.id.botonRechazar).setOnClickListener {
            finish() // Cierra esta actividad y regresa
        }
    }
}

