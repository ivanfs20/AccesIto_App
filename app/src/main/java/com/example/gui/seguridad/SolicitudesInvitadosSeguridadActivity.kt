package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.gui.MainActivity
import com.example.gui.R
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SolicitudesInvitadosSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_invitados_seguridad)

        val recyclerView = findViewById<RecyclerView>(R.id.rvSolicitudesSeguridadInv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()


            //para cambiar el tipo de usuario
            val listaUsuarios = db.usuarioDao().AllUsuario().filter {
                it.getTipo_usuario().equals("invitado", ignoreCase = true) && !it.isEstatus()
            }

            val solicitudesReales = listaUsuarios.map {
                SolicitudesInvitadosSeguridadAdapter.Solicitud(
                    nombre = it.getNombreC(),
                    numeroControl = it.getTelefono()?.toLongOrNull() ?: 0,
                    correo = it.getCorreo() ?: "",
                    solicitud = it.getAsunto() ?: "Solicitud",
                    estado = "Pendiente"
                )
            }
            withContext(Dispatchers.Main) {
                recyclerView.adapter = SolicitudesInvitadosSeguridadAdapter(
                    solicitudesReales,
                    onAceptarClick = { solicitud ->
                        GlobalScope.launch(Dispatchers.IO) {
                            val usuario = listaUsuarios.find { it.getNombreC() == solicitud.nombre && it.getTelefono() == solicitud.numeroControl.toString() }
                            if (usuario != null) {
                                usuario.setEstatus(true)
                                db.usuarioDao().update(usuario)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@SolicitudesInvitadosSeguridadActivity, "Solicitud aceptada: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    onDenegarClick = { solicitud ->
                        GlobalScope.launch(Dispatchers.IO) {
                            val usuario = listaUsuarios.find { it.getNombreC() == solicitud.nombre && it.getTelefono() == solicitud.numeroControl.toString() }
                            if (usuario != null) {
                                db.usuarioDao().delete(usuario)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@SolicitudesInvitadosSeguridadActivity, "Solicitud denegado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                )
            }
        }

        // Botones inferiores
        findViewById<Button>(R.id.btnHomeSeguridadInv).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSalirSeguridadInv).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
        }
}

