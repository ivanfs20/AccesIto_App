package com.example.gui.administrador

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import androidx.appcompat.widget.PopupMenu
import androidx.room.Room
import com.example.gui.GestionarId
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import com.example.gui.seguridad.generarReporteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AdministradorActivity : AppCompatActivity() {
    private val adminDB by lazy { AdministradorDataBase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqradministrador)
        val botonMenu = findViewById<ImageButton>(R.id.botonOpcionesExtras)
        val usuario = intent.getStringExtra("USUARIO") ?: ""
        Toast.makeText(this, "Bienvenido administrador: $usuario", Toast.LENGTH_SHORT).show()
        //variable para obtenerel id del admin --Lima
        val idObtenida=GestionarId(this).obtenerIdAdmin()
        Toast.makeText(this,"Id recuperado: $idObtenida",Toast.LENGTH_SHORT).show()
        val ivQr=findViewById<ImageView>(R.id.qrImagenAdministrador)
        val nombreUsuario=findViewById<TextView>(R.id.mostNombreUsuarioAdministrador)

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
                            Toast.makeText(this@AdministradorActivity, "QR no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AdministradorActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } finally {
                adminDB.closeDataBase() //
            }
        }


      /*  GlobalScope.launch(Dispatchers.IO){
            var db: DataBase
            db =
                Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB)
                    .build()
            val usuario=db.usuarioDao().getUser(usuario)

            if(usuario!=null){
                nombreUsuario.text=usuario.nombreC
                val qr= db.qrDao().getQrByUsuarioId(usuario.id!!)
                if(qr!=null){
                    val bitmap= BitmapFactory.decodeByteArray(qr.codigo,0,qr.codigo.size)
                    runOnUiThread {
                        ivQr.setImageBitmap(bitmap)
                    }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@AdministradorActivity,"Qr no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                runOnUiThread {
                    Toast.makeText(this@AdministradorActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }*/





        botonMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.menu_opciones, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    /**
                     * Nota importante, dado que el flujo que va desde el submenu, al elegir la opcion de usuarios, se abre la activity usuariosAdministradorActivity
                     * y por consiguiente al presionar el boton btnDarAltaAdministrador se redirige a la activity DarAltaUsuarioAdminActivity se tienene 3 botnes, pero
                     * el que importa en este caso es el boton HOME (salir), al presionarlo se implemento unas banderas que eliminan las actividades intermedias de la pila
                     * y si esta actividad no esta en la pila (AdministradorActivity) la inicia normalmente o si esta en el top la reutiliza, peroooooooo
                     * puede haber inconvenientes a la hora de cargar el qr, se debe de tener en cuenta eso.
                     */
                    R.id.menu_usuarios -> {
                        val intent = Intent(this, usuariosAdministradorActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de SOLICITUDES
                    R.id.menu_solicitudes_usuarios -> {
                        //Se arreglo lo del boton home y salir
                        val intent = Intent(this, SolicitudesUsuariosAdministradorActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_solicitudes_invitados -> {
                        val intent = Intent(this, SolicitudesInvitadosAdministradorActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de REPORTES
                    R.id.menu_reportes_ver -> {
                        val intent = Intent(this, ListaReportesAdministradorActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_reportes_generar -> {
                        val intent = Intent(this, generarReporteAdminActivity::class.java)
                        startActivity(intent)
                        true

                    }
                    else -> false
                }
            }
            popup.show()
        }

        //bton salir que regresa al activityMain
        var botonSalir = findViewById<Button>(R.id.botonSalir).setOnClickListener{
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