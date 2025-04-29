package com.example.gui.visitante

import UsuarioDataBase
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.data.actions.PhotoBytesAl
import com.example.gui.estudiante.IdentificacionFamiliarActivity

class DarAltaVisitanteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dar_alta_visitante)


        // Botón para seleccionar la foto
        findViewById<Button>(R.id.btnSeleccionarVisitante).setOnClickListener {
            val intent = Intent(this, IdentificacionVisitanteActivity::class.java)
            startActivity(intent)
        }

        //Botón para solicitar acceso, es decir para regresar
        findViewById<Button>(R.id.btnSolicitarVisitante).setOnClickListener {
            val nombre = findViewById<EditText>(R.id.nombreEditText)
            val apPaterno =findViewById<EditText>(R.id.apePaternoEditText)
            val apMaterno = findViewById<EditText>(R.id.apeMaternoEditText)
            val asunto = findViewById<EditText>(R.id.parentescoEditText)
            val photo = PhotoBytesAl.photoVisitanteIdentificacion
            val nombreC = nombre.text.toString()+apPaterno.text.toString()+apMaterno.text.toString()
            UsuarioDataBase().createVisante(this, nombreC, asunto.text.toString(), photo)
            finish() // Cierra esta actividad y regresa a solicitudes_acceso_visitante
        }
    }
}