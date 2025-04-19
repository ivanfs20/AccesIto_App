package com.example.gui.estudiante

import UsuarioDataBase
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.data.actions.PhotoBytesAl

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
            //OBTENEMOS LOS DATOS INGRESADOS, Y LA FOTO LA TENEMOS EN UNA CLASE APARTE
            //DONDE OBTENEMOS LA FOTO EN ARRAY Y PODER VERLA DESPUES
            val nombreET = findViewById<EditText>(R.id.nombreEditText)
            val apellidoPaternoET = findViewById<EditText>(R.id.apePaternoEditText)
            val apellidoMaternoET = findViewById<EditText>(R.id.apeMaternoEditText)
            val parentescoET = findViewById<EditText>(R.id.parentescoEditText)
            val photoBytes = PhotoBytesAl.arrayPhoto
            var nombreC = nombreET.text.toString()+apellidoPaternoET.text.toString()+apellidoMaternoET.text.toString()
            var parentesco = parentescoET.text.toString()
            UsuarioDataBase().createUserFamiliar(this, nombreC, nombreC, photoBytes,"familiar", parentesco)
            
            finish() // Cierra esta actividad y regresa a AgregarFamiliares
        }

    }
}