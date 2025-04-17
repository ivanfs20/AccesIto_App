package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.administrador.ListaReportesAdministradorAdapter

class ListaReportesSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reportes_seguridad)
        // Datos de ejemplo
        val reportesEjemplo = listOf(
            ListaReportesAdministradorAdapter.Reporte(
                "REP-001",
                "Admin01",
                "2024-03-15"
            ),
            ListaReportesAdministradorAdapter.Reporte(
                "REP-002",
                "Admin02",
                "2024-03-16"
            )
        )

        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvListaReportesSeguridad).apply {
            layoutManager = LinearLayoutManager(this@ListaReportesSeguridadActivity)
            adapter = ListaReportesAdministradorAdapter(reportesEjemplo) {
                //accion vacia
            }
        }

        // Botón Home
        findViewById<Button>(R.id.btnHomeReportSeguridad).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}