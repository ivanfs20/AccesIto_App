package com.example.gui.administrador

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R

class EditarAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_usuario_admin_y_seguridad)

        //botones de Guardar y Home

        //Botón guardar
        findViewById<Button>(R.id.btnGuardarEdit).setOnClickListener {
            finish()//Agregar logica para guardar los cambios
        }

        //Botón salir (Home)
        findViewById<Button>(R.id.btnSalirEdit).setOnClickListener {
            finish()
        }


    }
}
