package com.example.gui.administrador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.gui.GestionarId
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.gui.data.Entities.Reporte

class generarReporteAdminActivity : AppCompatActivity() {
    private val adminDB by lazy { AdministradorDataBase(this) }

    //val db: DataBase = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()
    //val db = AdministradorDataBase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        // adminDB = AdministradorDataBase(this) // Inicializar solo una vez
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte_admin)
        val idGuardado = GestionarId(this).obtenerIdAdmin()

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerarAdmin).setOnClickListener {
            val mes = findViewById<EditText>(R.id.mesEditTextAdmin).text.toString()
            val año = findViewById<EditText>(R.id.añoEditTextAdmin).text.toString()
            val nombre = findViewById<EditText>(R.id.nombreEditTextAdmin).text.toString()
            val descripcion = findViewById<EditText>(R.id.descripcionEditTextAdmin).text.toString()
            val fecha = "01-$mes-$año"
            if (idGuardado != -1L && nombre.isNotEmpty() && descripcion.isNotEmpty() && mes.isNotEmpty()) {

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val reporteId = withContext(Dispatchers.IO) {
                            adminDB.insertReporte(nombre, descripcion, mes, año, idGuardado.toString())
                        }


                        val accesos = withContext(Dispatchers.IO) {
                            adminDB.obtenerAccesosPorMes(mes.toInt(), año.toInt())
                        }


                        val documento = adminDB.generatePdf(nombre, descripcion, fecha, idGuardado.toString(), accesos)

                        if (documento != null) {
                            withContext(Dispatchers.IO) {
                                adminDB.updateReporte(reporteId, documento)
                                mostrarReportesEnLogcat()
                                //mostrarAccesos()
                            }

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@generarReporteAdminActivity, "Reporte $nombre generado correctamente", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@generarReporteAdminActivity, "Error al generar el reporte", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("ERROR", "Error en la generación del reporte", e)
                    } finally {
                        adminDB.closeDataBase()
                    }
                }
                   // val admin= AdministradorDataBase(this)
                   /* CoroutineScope(Dispatchers.IO).launch{
                        try{
                            val reporteId = withContext(Dispatchers.IO) {
                                adminDB.insertReporte(nombre, descripcion, mes, año, idGuardado.toString())
                            }

                            val documento=adminDB.generatePdf(nombre,descripcion,fecha,idGuardado.toString(),reporteId.toString(),adminDB)

                            if(documento!=null){
                                withContext(Dispatchers.IO) {
                                    adminDB.updateReporte(reporteId, documento)
                                }
                                withContext(Dispatchers.IO) {
                                    mostrarReportesEnLogcat()
                                }

                                withContext(Dispatchers.Main){
                                    Toast.makeText(this@generarReporteAdminActivity,"Reporte $nombre generado correctamente",Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }else{
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@generarReporteAdminActivity, "Error al generar el reporte", Toast.LENGTH_SHORT).show()
                                }
                            }
                                //val reporteId=adminDB.insertReporte(nombre,descripcion,mes,año,null,idGuardado.toString())
                            //val nuevoReporte=Reporte(nombre,descripcion,fecha,null,idGuardado)
                            //val reporteId=adminDB.insertReporte(nombre,descripcion,fecha,idGuardado)
                            /*  PRIMERA PARTE DE LO QUE NO SIRVE val documento= withContext(Dispatchers.IO){

                                adminDB.generatePdf( this@generarReporteAdminActivity,
                                    nombre,
                                    descripcion,
                                    fecha,
                                    idGuardado.toString(),
                                    null)
                            }
                            if (documento!=null){
                                val nuevoReporte=Reporte(
                                    nombre,
                                    descripcion,
                                    fecha,
                                    documento,
                                    idGuardado)
                                withContext(Dispatchers.IO) {
                                    adminDB.db.reporteDao().insert(nuevoReporte)


                                }
                                Toast.makeText(
                                    this@generarReporteAdminActivity,
                                    "Reporte generado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mostrarReportesEnLogcat()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@generarReporteAdminActivity,
                                    "Error al generar el reporte",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }FIN DE LO QUE NO SIRVE */
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("ERROR", "Error en la generación del reporte", e)
                        }finally {

                            adminDB.closeDataBase()
                            //db.close()
                        }
                    }*/

            } else {
                Toast.makeText(this, "No se pudo obtener el ID", Toast.LENGTH_SHORT).show()
            }

                        }

                    //val db = AdministradorDataBase(this)
                    //db.open(this)

                    /*   CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val documento = withContext(Dispatchers.IO) {
                                db.generatePdf(
                                    this@generarReporteAdminActivity,
                                    nombre,
                                    descripcion,
                                    fecha,
                                    idGuardado.toString(),
                                    null
                                )
                            }

                            if (documento != null) {
                                withContext(Dispatchers.IO) {
                                    db.insertReporte(
                                        this@generarReporteAdminActivity,
                                        nombre,
                                        descripcion,
                                        mes,
                                        año,
                                        documento,
                                        idGuardado.toString()
                                    )
                                }
                                Toast.makeText(
                                    this@generarReporteAdminActivity,
                                    "Reporte generado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                mostrarReportesEnLogcat()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@generarReporteAdminActivity,
                                    "Error al generar el reporte",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } finally {
                            //db.close()
                        }
                    }
                } else {
                    Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se pudo obtener el ID", Toast.LENGTH_SHORT).show()
            }
        }*/

                    findViewById<Button>(R.id.botonSalirAdmin).setOnClickListener {
                        // Cierra toda la pila de actividades y regresa a MainActivity
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                        finish()

                    }

                }


    private fun mostrarReportesEnLogcat() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reportes = adminDB.db.reporteDao().AllReporte()

                for (reporte in reportes) {
                    Log.d(
                        "DEBUG",
                        "Reporte ID: ${reporte.id}, Nombre: ${reporte.nombre}, Descripción: ${reporte.descripcion}, Fecha: ${reporte.fecha_creacion}, ID Administración: ${reporte.idAdministracion}, Documento : ${reporte.documento} bytes"
                    )
                }
            } finally {
                adminDB.closeDataBase()
            }
        }
    }

   /* private fun mostrarAccesos(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
               val accesos=adminDB.db.accesoDao().AllAcceso()
                    Log.d("DEBUG","Tamaño ${accesos.size}")
                for (acceso in accesos){
                    Log.d("DEBUG","Acceso id: ${acceso.id} , fecha entrada ${acceso.fechaEntrada}, fecha salida: ${acceso.fechaSalida} ,idqr ${acceso.idQr}")
                }
            } finally {
                adminDB.closeDataBase()
            }
        }
    }*/





        // private val job = Job()
        // private val scope = CoroutineScope(Dispatchers.IO + job)
        override fun onDestroy() {
            super.onDestroy()
            Log.d("DEBUG", "Cerrando la base de datos desde onDestroy()")
            // job.cancel() //
            adminDB.closeDataBase()
        }
}

