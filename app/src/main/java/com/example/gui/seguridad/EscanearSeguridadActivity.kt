package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class EscanearSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escanear_seguridad)

        //al dar el boton guardar se debe de implementar la logica
        findViewById<Button>(R.id.botonGuardarSeguridad).setOnClickListener {
            finish()
        }
    }
}