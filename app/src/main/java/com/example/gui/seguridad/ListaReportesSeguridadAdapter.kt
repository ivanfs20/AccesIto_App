package com.example.gui.seguridad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R
import com.example.gui.administrador.ListaReportesAdministradorActivity
import com.example.gui.administrador.ListaReportesAdministradorAdapter
import com.example.gui.seguridad.Reporte
import com.example.gui.data.DataBase.AdministradorDataBase

class ListaReportesSeguridadAdapter (
    private val reportes: List<Reporte>,
    private val adminDB: AdministradorDataBase,
    private val onDescargarClick: (Long, Context) -> Unit //
) : RecyclerView.Adapter<ListaReportesSeguridadAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdReporte: TextView = itemView.findViewById(R.id.tvIdReporteSeguridad)
        val tvUsuarioSeguridad: TextView = itemView.findViewById(R.id.tvUsuarioSeguridad)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaSeguridad)
        val btnAccionAbrir: Button = itemView.findViewById(R.id.btnAccionAbrirSeguridad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista_reportes_seguridad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reporte = reportes[position]

        with(holder) {
            tvIdReporte.text = reporte.id_reporte
            tvUsuarioSeguridad.text = reporte.usuario_seguridad
            tvFecha.text = reporte.fecha_reporte

            btnAccionAbrir.visibility = View.VISIBLE
            btnAccionAbrir.setOnClickListener {
                (holder.itemView.context as ListaReportesSeguridadActivity).descargarPdf(
                    reporte.id_reporte.toLong(),holder.itemView.context)
                //onDescargarClick(reporte.id_reporte.toLong(), holder.itemView.context) //
            }
        }
    }

    override fun getItemCount() = reportes.size
}
   /* private val reportes: List<Reporte>,
    private val onAccionClick: (Reporte) -> Unit
) : RecyclerView.Adapter<ListaReportesSeguridadAdapter.ViewHolder>() {

    // Modelo de datos
    data class Reporte(
        val id_reporte: String,
        val usuario_seguridad: String,
        val fecha_reporte: String,
        val necesitaAccion: Boolean = true
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdReporte: TextView = itemView.findViewById(R.id.tvIdReporteSeguridad)
        val tvUsuarioSeguridad: TextView = itemView.findViewById(R.id.tvUsuarioSeguridad)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaSeguridad)
        val btnAccionAbrir: Button = itemView.findViewById(R.id.btnAccionAbrirSeguridad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista_reportes_seguridad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reporte = reportes[position]

        with(holder) {
            tvIdReporte.text = reporte.id_reporte
            tvUsuarioSeguridad.text = reporte.usuario_seguridad
            tvFecha.text = reporte.fecha_reporte


            // Botón siempre visible si el reporte existe
            btnAccionAbrir.visibility = View.VISIBLE
            btnAccionAbrir.setOnClickListener { onAccionClick(reporte) }
        }
    }

    override fun getItemCount() = reportes.size
}*/
   // Modelo de datos
   data class Reporte(
       val id_reporte: String,
       val usuario_seguridad: String,
       val fecha_reporte: String,
       val necesitaAccion: Boolean = true
   )