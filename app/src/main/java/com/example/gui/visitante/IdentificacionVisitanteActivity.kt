package com.example.gui.visitante

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class IdentificacionVisitanteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.identificacion_visitante)


        // Botón para regresar
        findViewById<Button>(R.id.botonGuardarVisitante).setOnClickListener {
            finish() // Cierra esta actividad y regresa a DarAltaVisitanteActivity
        }
    }
}