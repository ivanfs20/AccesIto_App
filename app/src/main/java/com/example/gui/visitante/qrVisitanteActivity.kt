package com.example.gui.visitante

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class qrVisitanteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqr_visitante)

        //logica para implementar el qr del visitante, como tal ya envia el id, por si es necesario, sino necesitaria otra accion


        //boton para salir
        findViewById<Button>(R.id.botonSalirVisitante).setOnClickListener {
            finish() // Cierra esta actividad y regresa
        }
    }
}