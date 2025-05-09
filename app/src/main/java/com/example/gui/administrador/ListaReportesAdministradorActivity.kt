package com.example.gui.administrador

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.room.Database
import androidx.room.Room
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ListaReportesAdministradorActivity : AppCompatActivity() {
    //instancia unica de la base de datos --Lima
    private val adminDB by lazy { AdministradorDataBase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reportes_administrador)
        val listaEjemplo = mutableListOf<Reporte>()
        //val listaEjemplo = mutableListOf<ListaReportesAdministradorAdapter.Reporte>()
        //val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()

        CoroutineScope(Dispatchers.IO).launch {
            val reportes = adminDB.db.reporteDao().AllReporte()

            for (reporte in reportes) {
                //
                val nombreAdmin =
                    adminDB.db.usuarioDao().getNombreAdministrador(reporte.idAdministracion ?: -1L)
                if(nombreAdmin !=null) {


                    listaEjemplo.add(
                        //ListaReportesAdministradorAdapter.Reporte(
                        Reporte(
                            reporte.id.toString(),
                            nombreAdmin,
                            //reporte.nombre,
                            reporte.fecha_creacion
                        )
                    )
                }else{
                    Log.e("Error", "No se encontró un administrador para el ID: ${reporte.idAdministracion}")
                }
            }

            withContext(Dispatchers.Main) {
                //
                findViewById<RecyclerView>(R.id.rvListaReportesAdmin).apply {
                    layoutManager = LinearLayoutManager(this@ListaReportesAdministradorActivity)
                    adapter = ListaReportesAdministradorAdapter(
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
        findViewById<Button>(R.id.btnHomeReportAdmin).setOnClickListener {
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
        /*fun abrirPdf(file: File, context :Context){
            val intent=Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(file),"application/pdf")
        intent.flags=Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.startActivity(intent)
    }*/




/*   super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_lista_reportes_administrador)
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
       findViewById<RecyclerView>(R.id.rvListaReportesAdmin).apply {
           layoutManager = LinearLayoutManager(this@ListaReportesAdministradorActivity)
           adapter = ListaReportesAdministradorAdapter(listaEjemplo) {
               //accion vacia
           }
       }

       // Botón Home
       findViewById<Button>(R.id.btnHomeReportAdmin).setOnClickListener {
           finish()
       }*/