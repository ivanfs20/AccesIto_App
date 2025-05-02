package com.example.gui.data.DataBase

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room

import com.example.gui.data.Entities.Acceso
import com.example.gui.data.Entities.Qr
import com.example.gui.data.Entities.Reporte
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.AccesoConUsuario
import com.example.gui.data.actions.NameDataBase
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.UnitValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlinx.coroutines.withContext

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class AdministradorDataBase(private val context: Context) {
    val db: DataBase by lazy {
        Room.databaseBuilder(context.applicationContext, DataBase::class.java, NameDataBase.nameDB).build()
    }

    //  Insertar reporte usando Room en lugar de SQLite manual
    suspend fun insertReporte (nombre:String, descripcion:String,mes:String, año:String, idAdministrador:String):Long{
        val fecha="01-$mes-$año"
        val nuevoReporte=Reporte(nombre,descripcion,fecha,null,idAdministrador.toLong())
        return withContext(Dispatchers.IO){
            db.reporteDao().insert(nuevoReporte)
        }
    }

    //para actualizar el reporte y agregarle el pdf en bytes --Lima
    suspend fun updateReporte(reporteId:Long,documento:ByteArray){
        withContext(Dispatchers.IO){
            db.reporteDao().updateReporte(reporteId,documento)
        }
    }
    suspend fun obtenerAccesosPorMes(mes: Int, año: Int): List<AccesoConUsuario> {
        val fechaPatron = "%-${String.format("%02d", mes)}-${año}"
        Log.d("DEBUG", "Buscando accesos con patrón: '$fechaPatron'") //Verifica cómo se está filtrando
        return db.accesoDao().getAccesosPorMes(fechaPatron)
    }

    //  Obtener todos los reportes desde Room

    fun generarReportePDF(nombre: String, descripcion: String, fechaCreacion: String, idAdministracion: String, mes: Int, anio: Int, adminDB: AdministradorDataBase) {
        CoroutineScope(Dispatchers.IO).launch {
            val accesos = adminDB.obtenerAccesosPorMes(mes, anio)
            val pdfBytes = generatePdf(nombre, descripcion, fechaCreacion, idAdministracion, accesos)

            withContext(Dispatchers.Main) {
                if (pdfBytes != null) {
                    Log.d("DEBUG", "PDF generado correctamente con ${pdfBytes.size} bytes")
                    Toast.makeText(context, "Reporte generado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error al generar el PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    //VERSION 3 para generar pdfs
    fun generatePdf(
        nombre: String,
        descripcion: String,
        fechaCreacion: String,
        idAdministracion: String,
        accesos: List<AccesoConUsuario>
    ): ByteArray? {
        val arregloBytes = ByteArrayOutputStream()

        try {
            val writer = PdfWriter(arregloBytes)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)

            document.add(Paragraph("Reporte: $nombre").setBold().setFontSize(16f))
            document.add(Paragraph("Descripción: $descripcion"))
            document.add(Paragraph("Fecha de Creación: $fechaCreacion"))
            document.add(Paragraph("ID Administración: $idAdministracion"))
            document.add(Paragraph("\nAccesos Registrados en el Mes").setBold().setFontSize(14f))

            val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 3f, 3f, 2f))).useAllAvailableWidth()

            table.addCell(Cell().add(Paragraph("ID Acceso").setBold()))
            table.addCell(Cell().add(Paragraph("Fecha Entrada").setBold()))
            table.addCell(Cell().add(Paragraph("Fecha Salida").setBold()))
            table.addCell(Cell().add(Paragraph("Nombre Usuario").setBold()))
            Log.d("DEBUG", "Cantidad de accesos recibidos para el PDF: ${accesos.size}")
            for (acceso in accesos) {
                table.addCell(acceso.id.toString())
                table.addCell(acceso.fechaEntrada)
                table.addCell(acceso.fechaSalida)
                table.addCell(acceso.nombreUsuario)
            }

            document.add(table)

            document.close()
            return arregloBytes.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR", "Error al generar el PDF", e)
            return null
        }
    }

    //  Obtener accesos del reporte usando Room
    fun getAccesosForReporte(context: Context, reporteId: String): List<Acceso> {
        //val db =  Room.databaseBuilder(context, DataBase::class.java, NameDataBase.nameDB).build()

        return db.accesoDao().getAccesosByReporteId(reporteId.toLong()) //
    }

    //  Obtener QR del acceso usando Room
    fun getQrForAcceso(context: Context, idQr: Long): Qr {
       // val db = Room.databaseBuilder(context, DataBase::class.java, NameDataBase.nameDB).build()

        return db.qrDao().getQrById(idQr) //

    }

    //  Obtener usuario del QR usando Room
    fun getUsuarioForQr(context: Context, idUsuario: Long): Usuario {
       /*val db =  Room.databaseBuilder(context, DataBase::class.java, NameDataBase.nameDB).build()*/

        return db.usuarioDao().getUsuarioById(idUsuario) //
    }

    fun closeDataBase(){
        Log.d("DEBUG","Cerrando base de datos")
        db.close()
    }

}