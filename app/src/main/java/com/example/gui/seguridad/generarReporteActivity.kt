package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.MainActivity
import com.example.gui.R

class generarReporteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte)

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerar).setOnClickListener{
            //regresa a ListaReportesSeguridadActivity
            val intent = Intent(this, ListaReportesSeguridadActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.botonSalir).setOnClickListener{
            // Cierra toda la pila de actividades y regresa a MainActivity
                val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

    }
}