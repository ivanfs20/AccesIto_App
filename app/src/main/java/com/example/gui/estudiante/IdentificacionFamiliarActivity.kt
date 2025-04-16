package com.example.gui.estudiante

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class IdentificacionFamiliarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.identificacionfamiliar)


        // Botón para regresar
        findViewById<Button>(R.id.botonGuardar).setOnClickListener {
            finish() // Cierra esta actividad y regresa a DarAltaFamiliarActivity
        }
    }
}