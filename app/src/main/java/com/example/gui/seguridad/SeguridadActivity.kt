package com.example.gui.seguridad

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.gui.GestionarId
import com.example.gui.R
import com.example.gui.administrador.ListaReportesAdministradorActivity
import com.example.gui.administrador.SolicitudesInvitadosAdministradorActivity
import com.example.gui.administrador.SolicitudesUsuariosAdministradorActivity
import com.example.gui.administrador.usuariosAdministradorActivity
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import com.example.gui.visitante.DarAltaVisitanteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeguridadActivity : AppCompatActivity() {
    private val adminDB by lazy { AdministradorDataBase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrseguridad)
        val botonMenu = findViewById<ImageButton>(R.id.botonOpcionesExtrasSeguridad)
        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido guardia: $usuario", Toast.LENGTH_SHORT).show()
        //variable para obtenerel id del admin --Lima
        val idObtenida= GestionarId(this).obtenerIdAdmin()
        Toast.makeText(this,"Id recuperado: $idObtenida",Toast.LENGTH_SHORT).show()
        val ivQr=findViewById<ImageView>(R.id.qrImagenSeguridad)
        val nombreUsuario=findViewById<TextView>(R.id.mostNombreUsuarioSeguridad)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuarioData = adminDB.db.usuarioDao().getUser(usuario)
                if (usuarioData != null) {
                    withContext(Dispatchers.Main) {
                        nombreUsuario.text = usuarioData.nombreC
                    }

                    val qr = adminDB.db.qrDao().getQrById(usuarioData.id!!)
                    if (qr != null) {
                        val bitmap = BitmapFactory.decodeByteArray(qr.codigo, 0, qr.codigo.size)
                        withContext(Dispatchers.Main) {
                            ivQr.setImageBitmap(bitmap)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SeguridadActivity, "QR no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SeguridadActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } finally {
                adminDB.closeDataBase() //
            }
        }
        botonMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.menu_opciones_seguridad, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.menu_usuarios_seguridad -> {
                        val intent = Intent(this, UsuariosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de SOLICITUDES
                    R.id.menu_solicitudes_usuarios_seguridad -> {
                        val intent = Intent(this, SolicitudesUsuariosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_solicitudes_invitados_seguridad -> {
                        val intent = Intent(this, SolicitudesInvitadosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de REPORTES
                    R.id.menu_reportes_ver_segurida -> {
                        val intent = Intent(this, ListaReportesSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_reportes_generar_seguridad -> {
                        val intent = Intent(this, generarReporteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu_escanear_seguridad -> {
                        val intent = Intent(this, EscanearSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }//fin del metodo


        //bton salir que regresa al activityMain
        var botonSalir = findViewById<Button>(R.id.botonSalir_seguridad).setOnClickListener{
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG", "Cerrando la base de datos desde onDestroy()")
        // job.cancel() //
        adminDB.closeDataBase()
    }
}