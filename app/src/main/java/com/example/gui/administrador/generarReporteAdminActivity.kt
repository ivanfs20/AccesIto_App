package com.example.gui.administrador

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class generarReporteAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte_admin)

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerarAdmin).setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.botonSalirAdmin).setOnClickListener{
            finish()
        }
    }
}