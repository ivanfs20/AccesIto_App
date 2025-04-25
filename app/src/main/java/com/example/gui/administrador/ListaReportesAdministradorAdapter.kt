package com.example.gui.administrador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.visitante.SolicitudAdapter

class ListaReportesAdministradorAdapter (
    private val reportes: List<Reporte>,
    private val adminDB: AdministradorDataBase,
    private val onDescargarClick: (Long, Context) -> Unit //
) : RecyclerView.Adapter<ListaReportesAdministradorAdapter.ViewHolder>() {

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

            btnAccionAbrir.visibility = View.VISIBLE
            btnAccionAbrir.setOnClickListener {
                (holder.itemView.context as ListaReportesAdministradorActivity).descargarPdf(
                reporte.id_reporte.toLong(),holder.itemView.context)
                //onDescargarClick(reporte.id_reporte.toLong(), holder.itemView.context) //
            }
        }
    }

    override fun getItemCount() = reportes.size
}


// Modelo de datos
data class Reporte(
    val id_reporte: String,
    val usuario_administrador: String,
    //val nombre_reporte:String,
    //val descripcion_reporte:String,
    val fecha_reporte: String,
    val necesitaAccion: Boolean = true
)




/*class ListaReportesAdministradorAdapter (
    private val reportes: List<Reporte>,
    private val onAccionClick: (Reporte) -> Unit
) : RecyclerView.Adapter<ListaReportesAdministradorAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdReporte: TextView = itemView.findViewById(R.id.tvIdReporte)
        val tvUsuarioAdministracion: TextView = itemView.findViewById(R.id.tvUsuarioAdministracion)

        //val tv_nombre_reporte:TextView=itemView.findViewById(R.id.tvNombre)
        //val tv_descripcion_reporte:TextView=itemView.findViewById(R.id.tv)
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
            //tv_nombre_reporte.text=reporte.nombre_reporte

            tvFecha.text = reporte.fecha_reporte


            // Botón siempre visible si el reporte existe
            btnAccionAbrir.visibility = View.VISIBLE
            btnAccionAbrir.setOnClickListener {
                (holder.itemView.context as ListaReportesAdministradorActivity).descargarPdf(
                    reporte.id_reporte.toLong(),
                    holder.itemView.context
                )
                //descargarPdf(reporte.id_reporte.toLong(),holder.itemView.context as ListaReportesAdministradorActivity)
                /*onAccionClick(reporte)*/
            }
        }
    }


    override fun getItemCount() = reportes.size
}*/
