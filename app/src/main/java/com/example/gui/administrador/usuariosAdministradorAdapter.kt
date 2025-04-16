package com.example.gui.administrador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R
import com.example.gui.seguridad.usuariosSeguridadAdapter

class usuariosAdministradorAdapter (
    private val usuariosAdministrador: List<Usuarios>,
    private val onAccionClick: (Usuarios) -> Unit
) : RecyclerView.Adapter<usuariosAdministradorAdapter.ViewHolder>() {

    data class Usuarios(
        val nombre: String,
        val nControl: Long,
        val correo: String,
        val telefono: Long,
        val accionHabilitada: Boolean = true
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreAdmin)
        val tvNcontrol: TextView = itemView.findViewById(R.id.tvNoControlAdmin)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreoElectronicoAdmin)
        val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefonoAdmin)
        val tvAccionHabilitada: Button = itemView.findViewById(R.id.btnAccionAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuarios_administrador, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuariosAdministrador = usuariosAdministrador[position]

        with(holder) {
            tvNombre.text = usuariosAdministrador.nombre
            tvNcontrol.text = usuariosAdministrador.nControl.toString()
            tvCorreo.text = usuariosAdministrador.correo
            tvTelefono.text = usuariosAdministrador.telefono.toString()

            tvAccionHabilitada.visibility = if (usuariosAdministrador.accionHabilitada) View.VISIBLE else View.GONE
            tvAccionHabilitada.setOnClickListener { onAccionClick(usuariosAdministrador) }
        }
    }

    override fun getItemCount() = usuariosAdministrador.size
}
