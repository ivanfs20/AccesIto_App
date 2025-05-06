package com.example.gui.seguridad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R

class usuariosSeguridadAdapter (
    private val usuarios: List<Usuarios>,
    private val onAccionClick: (Usuarios) -> Unit,
    private val onDeleteClick: (Usuarios)-> Unit
) : RecyclerView.Adapter<usuariosSeguridadAdapter.ViewHolder>() {

    data class Usuarios(
        val nombre: String,
        val nControl: Long,
        val correo: String,
        val telefono: Long,
        val accionHabilitada: Boolean = true
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreSeguridad)
        val tvNcontrol: TextView = itemView.findViewById(R.id.tvNoControlSeguridad)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreoElectronicoSeguridad)
        val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefonoSeguridad)
        val tvAccionHabilitada: Button = itemView.findViewById(R.id.btnAccionSeguridad)
        val btnDeleteUsuario: Button = itemView.findViewById(R.id.btnEliminarSeguridad)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuarios_seguridad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuarios = usuarios[position]

        with(holder) {
            tvNombre.text = usuarios.nombre
            tvNcontrol.text = usuarios.nControl.toString()
            tvCorreo.text = usuarios.correo
            tvTelefono.text = usuarios.telefono.toString()

            tvAccionHabilitada.visibility = if (usuarios.accionHabilitada) View.VISIBLE else View.GONE
            tvAccionHabilitada.setOnClickListener { onAccionClick(usuarios) }

            btnDeleteUsuario.setOnClickListener { onDeleteClick(usuarios) }
        }
    }

    override fun getItemCount()=usuarios.size
}