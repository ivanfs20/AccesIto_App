package com.example.gui.seguridad

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class generarReporteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte)

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerar).setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.botonSalir).setOnClickListener{
            finish()
        }

    }
}