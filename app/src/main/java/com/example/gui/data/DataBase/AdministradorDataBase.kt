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
   /* @SuppressLint("Range")
    fun getAllReportes(context: Context): List<String> {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        var cursor = db.rawQuery(
            "SELECT id, idAdministracion, fecha_creacion FROM REPORTE", null
        )

        var lista = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                val fechaCreacion = cursor.getString(cursor.getColumnIndex("fecha_creacion"))
                val idAdmin = cursor.getString(cursor.getColumnIndex("idAdministracion"))

                // Agregar reporte como texto
                lista.add("$id.$nombre - $fechaCreacion")

                // Aquí generamos el PDF por cada reporte
                //val mes = fecha_c.substring(3, 5) // Extraer el mes
                //val año = fecha_c.substring(6, 10) // Extraer el año
                generatePdf(
                    context,
                    nombre,
                    descripcion,
                    fechaCreacion,
                    idAdmin,
                    null
                )  // Generar el PDF para el reporte

            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista

    }*/


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
   /* suspend fun obtenerAccesosPorMes (mes :Int, año:Int):List<AccesoConUsuario>{
        val fechaInicio=String.format("%02d-%02d-%d", 1, mes, año)
        val fechaFin= if(mes==12){
            String.format("%02d-%02d-%d", 1, 1, año + 1)
        }else{
            String.format("%02d-%02d-%d", 1, mes + 1, año)
        }
        Log.d("DEBUG", "Filtrando accesos entre: '$fechaInicio' y '$fechaFin'")
        return db.accesoDao().getAccesosPorMes(fechaInicio,fechaFin)
    }*/


  /* fun insertReporte( nombre: String, descripcion: String, mes: String, año: String, documento: ByteArray, idAdministracion: String) {

        val fecha = "01-$mes-$año"
        val nuevoReporte = Reporte( nombre, descripcion, fecha, documento, idAdministracion.toLong())

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("DEBUG", "Insertando reporte: Nombre: $nombre, Descripción: $descripcion, Fecha: $fecha, ID Administración: $idAdministracion, Documento bytes: $documento")
            db.reporteDao().insert(nuevoReporte)

            val reporteGuardado=db.reporteDao().AllReporte().lastOrNull()
            if (reporteGuardado!=null){
                Log.d("DEBUG", "Reporte insertado correctamente: ID ${reporteGuardado.getId()}, Nombre: ${reporteGuardado.getNombre()}, Documento size: ${reporteGuardado.getDocumento()?.size ?: 0}")
            }else{
                Log.d("DEBUG", "Error: El reporte no se insertó correctamente en la BD")
            }
           // Log.d("DEBUG", "Reporte insertado correctamente")
        }

        //db.close()
    }*/

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










        /*version 2 para generar pdfs


    fun generatePdf(
        nombre: String,
        descripcion: String,
        fechaCreacion: String,
        idAdministracion: String,
        reporteId: String,
        adminDB: AdministradorDataBase
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
            document.add(Paragraph("\nAccesos Registrados").setBold().setFontSize(14f))

            if (reporteId != null) {
                val accesos = getAccesosForReporte(context, reporteId)


                val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 3f, 3f, 2f))).useAllAvailableWidth()


                table.addCell(Cell().add(Paragraph("ID Acceso").setBold()))
                table.addCell(Cell().add(Paragraph("Fecha Entrada").setBold()))
                table.addCell(Cell().add(Paragraph("Fecha Salida").setBold()))
                table.addCell(Cell().add(Paragraph("Nombre Usuario").setBold()))


                for (acceso in accesos) {
                    val qr = getQrForAcceso(context, acceso.idQr)
                    val usuario = getUsuarioForQr(context, qr.idUsuario)

                    table.addCell(acceso.id.toString())
                    table.addCell(acceso.fechaEntrada)
                    table.addCell(acceso.fechaSalida)
                    table.addCell(usuario.nombreC)
                }

                document.add(table)
            }

            Log.d("DEBUG", "PDF generado correctamente con tamaño: ${arregloBytes.size()} bytes")
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR", "Error al generar el PDF", e)
            return null
        }

        return arregloBytes.toByteArray()
    }*/
    //VERSION 1 Generar el PDF del reporte en Bytes
    /*fun generatePdf(
        //context: Context,
        nombre: String,
        descripcion: String,
        fechaCreacion: String,
        idAdministracion: String,
        reporteId: String,
        adminDB: AdministradorDataBase
    ): ByteArray? {
        val arregloBytes = ByteArrayOutputStream()

        try {
            val writer = PdfWriter(arregloBytes)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)

            document.add(Paragraph("Reporte: $nombre"))
            document.add(Paragraph("Descripción: $descripcion"))
            document.add(Paragraph("Fecha de Creación: $fechaCreacion"))
            document.add(Paragraph("ID Administración: $idAdministracion"))


            if (reporteId != null) {
                val accesos = getAccesosForReporte(context, reporteId)
                for (acceso in accesos) {
                    val qr = getQrForAcceso(context, acceso.idQr)
                    val usuario = getUsuarioForQr(context, qr.idUsuario)

                    document.add(Paragraph("ID Acceso: ${acceso.id}"))
                    document.add(Paragraph("Fecha Entrada: ${acceso.fechaEntrada}"))
                    document.add(Paragraph("Fecha Salida: ${acceso.fechaSalida}"))
                    document.add(Paragraph("ID QR: ${qr.id}"))
                    document.add(Paragraph("ID Usuario: ${usuario.id}"))
                    document.add(Paragraph("Nombre Usuario: ${usuario.nombreC}"))
                    document.add(Paragraph("Tipo Usuario: ${usuario.tipo_usuario}"))
                }
            }
            Log.d("DEBUG", "PDF generado correctamente con tamaño: ${arregloBytes.size()} bytes")
            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR", "Error al generar el PDF", e)
            return null
        }
        return arregloBytes.toByteArray()
    }*/




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




















/* FUNCIONES QUE ESTAN MAL (Lima)
    fun insertReporte(context: Context, nombre: String, descripcion: String, mes: String, año: String,documento:ByteArray, idAdministracion: String) {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        val fecha = "01-$mes-$año"
       // val documento=generatePdf(context, nombre, descripcion, fecha, idAdministracion, null)
        // Insertar el reporte en la base de datos
        db.execSQL("INSERT INTO REPORTE (nombre, descripcion, fecha_creacion,documento, idAdministracion) VALUES(?, ?, ?, ?,?)",
            arrayOf(nombre, descripcion, fecha,documento, idAdministracion))

        db.close()

    }

    /*fun getIdUser(context: Context, nombre: String, password: String): String {

        return ""
    }*/

    // Función para generar el PDF
     fun generatePdf(
        context: Context,
        nombre: String,
        descripcion: String,
        fechaCreacion: String,
        idAdministracion: String,
        reporteId: String?
    ):ByteArray? {
        // Crear un archivo temporal en la memoria
        val arregloBytes = ByteArrayOutputStream()

        /* Ruta para guardar el archivo PDF
        val filePath =
            context.getExternalFilesDir(null)?.absolutePath + "/reporte_${nombre}_${fechaCreacion}.pdf"
        val file = File(filePath)*/

        try {
            // Crear PdfWriter y PdfDocument
            val writer = PdfWriter(arregloBytes)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)

            // Agregar contenido al PDF
            document.add(Paragraph("Reporte: $nombre"))
            document.add(Paragraph("Descripción: $descripcion"))
            document.add(Paragraph("Fecha de Creación: $fechaCreacion"))
            document.add(Paragraph("ID Administracion: $idAdministracion"))

            // Agregar datos de accesos, QR y usuarios
            if (reporteId != null) {
                val accesos = getAccesosForReporte(context, reporteId)
                for (acceso in accesos) {
                    val qr = getQrForAcceso(context, acceso.idQr)
                    val usuario = getUsuarioForQr(context, qr.idUsuario)

                    document.add(Paragraph("ID Acceso: ${acceso.id}"))
                    document.add(Paragraph("Fecha Entrada: ${acceso.fechaEntrada}"))
                    document.add(Paragraph("Fecha Salida: ${acceso.fechaSalida}"))
                    document.add(Paragraph("ID QR: ${qr.id}"))
                    document.add(Paragraph("ID Usuario: ${usuario.id}"))
                    document.add(Paragraph("Nombre Usuario: ${usuario.nombreC}"))
                    document.add(Paragraph("Tipo Usuario: ${usuario.tipo_usuario}"))
                }
            }

            // Cerrar el documento
            document.close()

            //val pdfBytes=arregloBytes.toByteArray()


        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return arregloBytes.toByteArray()
    }
    // Funciones para obtener los accesos, QR y usuario

    @SuppressLint("Range")
    fun getAccesosForReporte(context: Context, reporteId: String): List<Acceso> {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT * FROM ACCESO WHERE reporte_id = ?", arrayOf(reporteId))

        val accesos = mutableListOf<Acceso>()
        if (cursor.moveToFirst()) {
            do {
                val acceso = Acceso().apply {
                    id = cursor.getLong(cursor.getColumnIndex("id"))
                    fechaEntrada = cursor.getString(cursor.getColumnIndex("fecha_entrada"))
                    fechaSalida = cursor.getString(cursor.getColumnIndex("fecha_salida"))
                    idQr = cursor.getLong(cursor.getColumnIndex("idQr"))
                }
                accesos.add(acceso)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return accesos

    }
    @SuppressLint("Range")
    fun getQrForAcceso(context: Context, idQr: Long): Qr {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT * FROM QR WHERE id = ?", arrayOf(idQr.toString()))

        val qr = Qr()
        if (cursor.moveToFirst()) {
            qr.id = cursor.getLong(cursor.getColumnIndex("id"))
            qr.codigo = cursor.getBlob(cursor.getColumnIndex("codigo"))
            qr.fecha = cursor.getString(cursor.getColumnIndex("fecha_creacion"))
            qr.estado = cursor.getString(cursor.getColumnIndex("estado"))
            qr.idUsuario = cursor.getLong(cursor.getColumnIndex("idUsuario"))
        }
        cursor.close()
        db.close()
        return qr
    }
    @SuppressLint("Range")
    fun getUsuarioForQr(context: Context, idUsuario: Long): Usuario {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT * FROM USUARIO WHERE id = ?", arrayOf(idUsuario.toString()))

        val usuario = Usuario()
        if (cursor.moveToFirst()) {
            usuario.id = cursor.getLong(cursor.getColumnIndex("id"))
            usuario.nombreC = cursor.getString(cursor.getColumnIndex("nombreC"))
            usuario.tipo_usuario = cursor.getString(cursor.getColumnIndex("tipo_usuario"))
        }
        cursor.close()
        db.close()
        return usuario
    }
}*/









//CODIGO DE CARLOS (LO COMENTE PORQUE LO MODIFIQUE ARRIBA)

   /* @SuppressLint("Range")
    fun getAllReportes(context: Context): List<String>{
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        var cursor = db.rawQuery(
            "SELECT id, idAdministracion, fecha_creacion FROM REPORTE", null
        )

        var lista = mutableListOf<String>()

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val id_admin = cursor.getString(cursor.getColumnIndex("idAdministracion"))
                val fecha_c = cursor.getString(cursor.getColumnIndex("fecha_creacion"))
                lista.add(id+"."+id_admin+"."+fecha_c)
            }while (cursor.moveToNext())
        }

        return lista;
    }

    fun insertReporte(context : Context, mes : String, año : String){
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
        var fecha = "01-"+mes+"-"+año
        var cursor = db.rawQuery(
            "INSERT INTO REPORTE (fecha_creacion, idAdministracion) VALUES(?,?)", arrayOf(fecha,)
        )
    }


    fun getIdUser(context: Context, nombre : String, password : String): String{
        //AUN NO AVANZO
        return "";
    }*/


