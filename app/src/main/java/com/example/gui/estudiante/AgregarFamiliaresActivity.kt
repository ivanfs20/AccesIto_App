package com.example.gui.estudiante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class AgregarFamiliaresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregarfamiliares)

        // Configurar botones para agregar familiares
        listOf(R.id.btnAgregarFam, R.id.btnAgregarFam2, R.id.btnAgregarFam3).forEach { btnId ->
            findViewById<Button>(btnId).setOnClickListener {
                val intent = Intent(this, DarAltaFamiliarActivity::class.java)
                startActivity(intent)
            }
        }

        // Botón para regresar
        findViewById<Button>(R.id.botonHome).setOnClickListener {
            finish() // Cierra esta actividad y regresa a EstudianteActivity
        }
    }
}