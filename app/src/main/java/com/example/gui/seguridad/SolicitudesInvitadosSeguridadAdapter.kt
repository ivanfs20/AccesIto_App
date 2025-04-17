package com.example.gui.seguridad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R

class SolicitudesInvitadosSeguridadAdapter (
    private val solicitudes: List<Solicitud>,
    private val onAceptarClick: (Solicitud) -> Unit,
    private val onDenegarClick: (Solicitud) -> Unit
) : RecyclerView.Adapter<SolicitudesInvitadosSeguridadAdapter.ViewHolder>() {

    data class Solicitud(
        val nombre: String,
        val numeroControl: Long,
        val correo: String,
        val solicitud: String,
        val estado: String = "Pendiente"
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreSeguridadInv)
        val tvNumeroControl: TextView = itemView.findViewById(R.id.tvNumeroControlSeguridadInv)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreoSeguridadInv)
        val tvSolicitud: TextView = itemView.findViewById(R.id.tvSolicitudSeguridadInv)
        val btnAceptar: Button = itemView.findViewById(R.id.btnAceptarSeguridadInv)
        val btnDenegar: Button = itemView.findViewById(R.id.btnDenegarSeguridadInv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitudes_invitados_seguridad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val solicitud = solicitudes[position]

        with(holder) {
            tvNombre.text = solicitud.nombre
            tvNumeroControl.text = solicitud.numeroControl.toString()
            tvCorreo.text = solicitud.correo
            tvSolicitud.text = solicitud.solicitud

            btnAceptar.setOnClickListener { onAceptarClick(solicitud) }
            btnDenegar.setOnClickListener { onDenegarClick(solicitud) }
        }
    }

    override fun getItemCount() = solicitudes.size
}