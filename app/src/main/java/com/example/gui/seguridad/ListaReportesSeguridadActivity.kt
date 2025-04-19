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
import com.example.gui.data.DataBase.AdministradorDataBase

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

        val listaEjemplo = mutableListOf<ListaReportesAdministradorAdapter.Reporte>()

        var a  : List<String> =  AdministradorDataBase().getAllReportes(this)

        for (valor in a){
            var actual = valor.split(".")
            var index1 = actual[0]
            var index2 = actual[1]
            var index3 = actual[2]

            listaEjemplo.add(
                ListaReportesAdministradorAdapter.Reporte(
                    index1,
                    index2,
                    index3
                )
            )

        }



        // Configurar RecyclerView
        findViewById<RecyclerView>(R.id.rvListaReportesSeguridad).apply {
            layoutManager = LinearLayoutManager(this@ListaReportesSeguridadActivity)
            adapter = ListaReportesAdministradorAdapter(listaEjemplo) {
                //accion vacia
            }
        }

        // Botón Home
        findViewById<Button>(R.id.btnHomeReportSeguridad).setOnClickListener {
            finish()
        }
    }
}