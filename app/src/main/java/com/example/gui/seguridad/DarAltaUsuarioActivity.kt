package com.example.gui.seguridad

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Qr
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.NameDataBase
import com.example.gui.visitante.IdentificacionVisitanteActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DarAltaUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dar_alta_usuario_admin_y_seguridad)

        //variables que contienen los valores a dar de alta
        val etNombre = findViewById<EditText>(R.id.nombreEditText)
        val etApellidoP = findViewById<EditText>(R.id.apePaternoEditText)
        val etApellidoM = findViewById<EditText>(R.id.apeMaternoEditText)
        val etCorreo = findViewById<EditText>(R.id.correoEditText)
        val etTelefono = findViewById<EditText>(R.id.telefonoEditText)
        val etUsuario = findViewById<EditText>(R.id.usuarioEditText)
        //boton para guardar
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nombreCompleto = etNombre.text.toString().trim() + " " + etApellidoP.text.toString()
                .trim() + " " + etApellidoM.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val usuario = etUsuario.text.toString().trim()
            val contra = nombreCompleto


            val rolesValidos= listOf(
                "docente",
                "seguridad",
                "administrador",
                "empleados de adm",
                "familiar",
                "visitante",
                "alumno",
                "otros empleados"
            )



            //para forzar a que se ingresen todos los datos
            if (nombreCompleto.isEmpty() || usuario.isEmpty() || contra.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos requeridos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            //Checar que se ingrese un rol valido --Lima
            if (usuario !in rolesValidos){
                Toast.makeText(this,"Tipo de usuario no valido",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoUsuario = Usuario(nombreCompleto, true,correo, contra, telefono, null, usuario," ")

            //se guarda en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                var db: DataBase
                db =
                    Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()

                //insertar usuario y obtener su id
                val idUsuario = db.usuarioDao().insert(nuevoUsuario)
                //crear qr en Bytes
                val contenidoQr = "Usuario: $usuario\nNombre: $nombreCompleto"
                val qrenBytes = generarCodigoQR(contenidoQr)

                //crear la fecha actual
                val fechaActual=
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                val qr= Qr (qrenBytes,fechaActual,"Activo",idUsuario)
                db.qrDao().insert(qr)




                //db.usuarioDao().insert(nuevoUsuario)
                runOnUiThread {

                    //bASE 64 PARA PODER GUARDAR , ENVIAR O VER EL qR
                    val qrEnBase64= Base64.encodeToString(qrenBytes, Base64.DEFAULT)
                    Toast.makeText(
                        this@DarAltaUsuarioActivity, "Usuario y qr guardado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("DATABASE_CHECK", "Usuario insertado: $nombreCompleto")
                    Log.d("DATABASE_CHECK", "QR generado en Base64: $qrEnBase64")

                    /*le agregue para mostrar el codigo Qr
                    val intent=Intent(this@DarAltaUsuarioAdminActivity,EstudianteActivity::class.java)
                    intent.putExtra("USUARIO",usuario)
                    intent.putExtra("qrBytes",qrenBytes)
                    startActivity(intent)*/
                    finish()
                }
            }

        }



        //Botón para salir
        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            // Navega a la MainActivity, cerrando las actividades en la pila
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
    fun generarCodigoQR(contenido: String, tamaño: Int = 300): ByteArray {
        val bitMatrix = MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, tamaño, tamaño)
        val bmp = Bitmap.createBitmap(tamaño, tamaño, Bitmap.Config.RGB_565)
        for (x in 0 until tamaño) {
            for (y in 0 until tamaño) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
           


