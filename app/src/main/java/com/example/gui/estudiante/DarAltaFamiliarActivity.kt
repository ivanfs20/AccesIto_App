package com.example.gui.estudiante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class DarAltaFamiliarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daraltafamiliar)

        // Botón para seleccionar la foto
        findViewById<Button>(R.id.botonSeleccionar).setOnClickListener {
            val intent = Intent(this, IdentificacionFamiliarActivity::class.java)
            startActivity(intent)
        }

        /** Es importante identificar que los dos funcionan en conjunto

        val lanzamientoFoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                // Aquí manejas el bitmap de la foto
            }
        }


        findViewById<Button>(R.id.botonSeleccionar).setOnClickListener {
        lanzamientoFoto.launch(null)
        }
        */

        //Provisional, en lo que esta el funcionamiento de la camara
        //Botón para solicitar acceso, es decir para regresar a AgregarFamiliares
        findViewById<Button>(R.id.botonSolicitar).setOnClickListener {
            finish() // Cierra esta actividad y regresa a AgregarFamiliares
        }

    }
}