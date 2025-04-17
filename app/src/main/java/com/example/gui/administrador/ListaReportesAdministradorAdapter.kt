package com.example.gui.administrador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R
import com.example.gui.visitante.SolicitudAdapter

class ListaReportesAdministradorAdapter (
    private val reportes: List<Reporte>,
    private val onAccionClick: (Reporte) -> Unit
) : RecyclerView.Adapter<ListaReportesAdministradorAdapter.ViewHolder>() {

    // Modelo de datos
    data class Reporte(
        val id_reporte: String,
        val usuario_administrador: String,
        val fecha_reporte: String,
        val necesitaAccion: Boolean = true
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdReporte: TextView = itemView.findViewById(R.id.tvIdReporte)
        val tvUsuarioAdministracion: TextView = itemView.findViewById(R.id.tvUsuarioAdministracion)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val btnAccionAbrir: Button = itemView.findViewById(R.id.btnAccionAbrir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista_reportes_administrador, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reporte = reportes[position]

        with(holder) {
            tvIdReporte.text = reporte.id_reporte
            tvUsuarioAdministracion.text = reporte.usuario_administrador
            tvFecha.text = reporte.fecha_reporte


            // Botón siempre visible si el reporte existe
            btnAccionAbrir.visibility = View.VISIBLE
            btnAccionAbrir.setOnClickListener { onAccionClick(reporte) }
        }
    }

    override fun getItemCount() = reportes.size
}