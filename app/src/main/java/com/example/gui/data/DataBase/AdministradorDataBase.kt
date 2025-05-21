package com.example.gui.data.DataBase
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

    //funcion para eliminar --Lima
    suspend fun eliminarUsuario(nombre: String, password: String, telefono: String, correo: String) {
        withContext(Dispatchers.IO) {
          val id=  db.usuarioDao().getIdPorNombre(nombre, password, telefono, correo)
            if (id != null && id>0) {
                // Eliminar al usuario por su ID
                db.usuarioDao().deletePorId(id)

                Log.d("Eliminar usuario $nombre", "Usuario eliminado con ID: $id")
            } else {
                Log.d("Eliminar usuario $nombre", "No se encontró el usuario")
            }
           // Log.d("Eliminar usuario $nombre","Filas eliminadas:")
        }
    }

    suspend fun actualizarUsuario(
        nombre: String,
        correo: String,
        telefono: String,
        usuario: String,
        asunto: String,
        idUsuario: Long
    ){
        withContext(Dispatchers.IO){
            val id=db.usuarioDao().getIdPorNombre(nombre,nombre,telefono,correo)
            if(id!=null && id>0){
                db.usuarioDao().actualizarUsuario(nombre,correo,telefono,usuario,asunto,idUsuario)
                Log.d("Actualizar usuario","Su id es $id")
        }else{
                Log.d("Actualizar usuario $nombre", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizarNombre(nombre:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizarNombrePassword(nombre,id)

            }else{
                Log.d("Actualizar nombre $nombre", "No se encontró el usuario")
            }
        }
    }
    suspend fun actualizarCorreo(correo:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizarCorreo(correo,id)

            }else{
                Log.d("Actualizar correo $correo", "No se encontró el usuario")
            }
        }
    }
    suspend fun actualizarTelefono(telefono:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizarTelefono(telefono,id)

            }else{
                Log.d("Actualizar telefono $telefono", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizarAsunto(asunto:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizarAsunto(asunto,id)

            }else{
                Log.d("Actualizar asunto $asunto", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizarTipo(tipo:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizarTipo(tipo,id)

            }else{
                Log.d("Actualizar tipo $tipo", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaUno(nombre:String,correo:String,telefono:String,usuario:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaUno(nombre,correo,telefono,usuario,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaDos(nombre:String,correo:String,telefono:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaDos(nombre,correo,telefono,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaTres(nombre:String,correo:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaTres(nombre,correo,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaCuatro(asunto:String,usuario:String,telefono:String,correo:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaCuatro(asunto,usuario,telefono,correo,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaCinco(asunto:String,usuario:String,telefono:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaCinco(telefono,usuario,asunto,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaSeis(asunto:String,usuario:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaSeis(usuario,asunto,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaSiete(correo:String,asunto:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaSiete(correo,asunto,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizaOcho(telefono:String,asunto:String,id:Long){
        withContext(Dispatchers.IO){
            if (id!=null && id>0){
                db.usuarioDao().actualizaOcho(telefono,asunto,id)

            }else{
                Log.d("Actualizando", "No se encontró el usuario")
            }
        }
    }

    suspend fun actualizarUser(nombre: String?, correo: String?, telefono: String?, tipo: String?, asunto: String?, id: Long) {
        withContext(Dispatchers.IO) {
            if (id > 0) {
                db.usuarioDao().actualizarUser(nombre, correo, telefono, tipo, asunto, id)
                Log.d("Actualizar usuario", "Usuario actualizado con ID: $id")
            } else {
                Log.d("Actualizar usuario", "No se encontró el usuario con ID: $id")
            }
        }
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





            
    
       

   
  
