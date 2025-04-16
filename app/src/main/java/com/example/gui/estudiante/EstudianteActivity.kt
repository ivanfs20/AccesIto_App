package com.example.gui.estudiante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.gui.R


class EstudianteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrcompuesto)

        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido estudiante: $usuario", Toast.LENGTH_SHORT).show()


        //Dar de alta
        var botonAlta = findViewById<Button>(R.id.botonAlta).setOnClickListener {
            val intent = Intent(this, AgregarFamiliaresActivity::class.java)
            intent.putExtra("USUARIO", usuario) // Si necesitas pasar datos
            startActivity(intent)
        }

        //boton salir
        var botonSalir = findViewById<Button>(R.id.botonSalir).setOnClickListener{
            finish()
        }

    }
}