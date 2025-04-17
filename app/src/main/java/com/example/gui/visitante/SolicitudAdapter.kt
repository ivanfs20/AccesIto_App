package com.example.gui.visitante

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R

class SolicitudAdapter(
    private val solicitudes: List<Solicitud>,
    private val onAccionClick: (Solicitud) -> Unit
) : RecyclerView.Adapter<SolicitudAdapter.ViewHolder>() {

    // Modelo de datos
    data class Solicitud(
        val id: String,
        val fecha: String,
        val estatus: String,
        val necesitaAccion: Boolean = true
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdSolicitud: TextView = itemView.findViewById(R.id.tvIdSolicitud)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvEstatus: TextView = itemView.findViewById(R.id.tvEstatus)
        val btnAccion: Button = itemView.findViewById(R.id.btnAccion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val solicitud = solicitudes[position]

        with(holder) {
            tvIdSolicitud.text = solicitud.id
            tvFecha.text = solicitud.fecha
            tvEstatus.text = solicitud.estatus

            // Personalizar según estatus
            when (solicitud.estatus) {
                "ABIERTO" -> tvEstatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_500))
                "CERRADO" -> tvEstatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_500))
                "NEGADO" -> tvEstatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow_500))
            }

            btnAccion.visibility = if (solicitud.necesitaAccion && solicitud.estatus == "ABIERTO") {
                View.VISIBLE
            } else {
                View.GONE
            }

            btnAccion.setOnClickListener {
                onAccionClick(solicitud) // Disparar el callback
            }
        }
    }

    override fun getItemCount() = solicitudes.size
}