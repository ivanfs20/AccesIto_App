package com.example.gui.familiar

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.gui.R

class FamiliarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrsimple)

        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido familiar: $usuario", Toast.LENGTH_SHORT).show()

        //boton salir
        var botonSalir = findViewById<Button>(R.id.botonSalir).setOnClickListener{
            finish()
        }
    }
}