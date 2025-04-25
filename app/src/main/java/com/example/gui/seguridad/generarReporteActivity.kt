package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.GestionarId
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class generarReporteActivity : AppCompatActivity() {
    private val adminDB by lazy { AdministradorDataBase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_reporte)

        val idGuardado = GestionarId(this).obtenerIdAdmin()

        //debe de ir a la lista de reportes
        findViewById<Button>(R.id.botonGenerar).setOnClickListener {
            val mes = findViewById<EditText>(R.id.mesEditText).text.toString()
            val año = findViewById<EditText>(R.id.añoEditText).text.toString()
            val nombre = findViewById<EditText>(R.id.nombreEditText).text.toString()
            val descripcion = findViewById<EditText>(R.id.descripcionEditText).text.toString()
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
                                //mostrarReportesEnLogcat()
                                //mostrarAccesos()
                            }

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@generarReporteActivity, "Reporte $nombre generado correctamente", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@generarReporteActivity, "Error al generar el reporte", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("ERROR", "Error en la generación del reporte", e)
                    } finally {
                        adminDB.closeDataBase()
                    }
                }


            } else {
                Toast.makeText(this, "No se pudo obtener el ID", Toast.LENGTH_SHORT).show()
            }

        }

        findViewById<Button>(R.id.botonSalir).setOnClickListener{
            // Cierra toda la pila de actividades y regresa a MainActivity
                val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

    }
}