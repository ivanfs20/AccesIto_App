package com.example.gui.seguridad

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class EditarSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_usuario_admin_y_seguridad)

        //Botones de Guaradar y Home (Salir)

        //Botón Guardar
        findViewById<Button>(R.id.btnGuardarEdit).setOnClickListener {
            finish()//agregar logica para guardar los cambios en la base de datos
        }

        //Boton de Salir
        findViewById<Button>(R.id.btnSalirEdit).setOnClickListener {
            finish()
        }
    }
}
