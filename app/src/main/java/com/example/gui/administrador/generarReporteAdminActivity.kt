package com.example.gui.administrador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase

class generarReporteAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte_admin)

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerarAdmin).setOnClickListener{
            var mes = findViewById<EditText>(R.id.mesEditTextAdmin)
            var mesString = mes.text.toString()

            var año = findViewById<EditText>(R.id.añoEditTextAdmin)
            var añoString = mes.text.toString()

            AdministradorDataBase().insertReporte(this, mesString, añoString)

            finish()
        }

        findViewById<Button>(R.id.botonSalirAdmin).setOnClickListener{
            finish()
        }
    }
}