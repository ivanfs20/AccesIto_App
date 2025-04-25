package com.example.gui.seguridad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.administrador.ListaReportesAdministradorAdapter
//import com.example.gui.seguridad.Reporte
import com.example.gui.data.DataBase.AdministradorDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ListaReportesSeguridadActivity : AppCompatActivity() {
    private val adminDB by lazy { AdministradorDataBase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reportes_seguridad)

        val listaEjemplo = mutableListOf<Reporte>()
        //val listaEjemplo = mutableListOf<ListaReportesAdministradorAdapter.Reporte>()
        //val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()

        CoroutineScope(Dispatchers.IO).launch {
            val reportes = adminDB.db.reporteDao().AllReporte()

            for (reporte in reportes) {
                //
                val nombreAdmin =
                    adminDB.db.usuarioDao().getNombreAdministrador(reporte.idAdministracion ?: -1L)

                listaEjemplo.add(
                    //ListaReportesAdministradorAdapter.Reporte(
                    Reporte(
                        reporte.id.toString(),
                        nombreAdmin,
                        //reporte.nombre,
                        reporte.fecha_creacion
                    )
                )
            }

            withContext(Dispatchers.Main) {
                //
                findViewById<RecyclerView>(R.id.rvListaReportesSeguridad).apply {
                    layoutManager = LinearLayoutManager(this@ListaReportesSeguridadActivity)
                    adapter = ListaReportesSeguridadAdapter(
                        listaEjemplo,
                        adminDB
                    ) { reporteId, context ->
                        Toast.makeText(context, "Abriendo reporte $reporteId", Toast.LENGTH_SHORT)
                            .show()
                    }
                    /* adapter = ListaReportesAdministradorAdapter(listaEjemplo) {
                        //Toast.makeText(this@ListaReportesAdministradorActivity, "Abriendo reporte ${it.id_reporte}", Toast.LENGTH_SHORT).show()
                    }*/
                }
            }


        }


        // Botón Home
        findViewById<Button>(R.id.btnHomeReportSeguridad).setOnClickListener {
            finish()
        }

    }
    //Funcion para descargar pdf -Lima
    fun descargarPdf(reporteId: Long, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            //val db=Room.databaseBuilder(context, Database::class.java,NameDataBase.nameDB).build()
            val documento = adminDB.db.reporteDao().getPdfByReporteId(reporteId)
            if (documento != null && documento.isNotEmpty()) {
                val nombreArchivo = "Reporte_$reporteId.pdf"
                // val file= File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),nombreArchivo)
                val file = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    nombreArchivo
                )
                try {
                    FileOutputStream(file).use { fos -> fos.write(documento) }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Reporte descargado", Toast.LENGTH_SHORT).show()
                        abrirPdf(file, context)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al descargar el archivo", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

            } else {
                Log.d("DEBUG", "Error: Documento no encontrado en la base de datos")
                Toast.makeText(context, "Error: Documento no existe", Toast.LENGTH_SHORT).show()
                //return@withContext
            }
        }
    }

    fun abrirPdf(file: File, context: Context) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    }


}