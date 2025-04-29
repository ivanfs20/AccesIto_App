package com.example.gui.administrador

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SolicitudesUsuariosAdministradorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes_usuarios_admin)
        val recyclerView = findViewById<RecyclerView>(R.id.rvSolicitudesAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val listaSolicitudesFamiliare = mutableListOf<Usuario>()


        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(applicationContext, DataBase::class.java, NameDataBase.nameDB).build()
            //para cambiar el tipo de usuario
            val listaUsuarios = db.usuarioDao().getSolicitudFamiliar()


            for(f in listaUsuarios){
                listaSolicitudesFamiliare.add(
                    Usuario(f.nombreC, f.isEstatus, f.correo, f.contraseña, f.telefono, f.foto, f.tipo_usuario)
                )
            }


            for (i in listaUsuarios){
                Log.d("user: ", i.nombreC)
            }



            val solicitudesReales = listaUsuarios.map {
                SolicitudesUsuariosAdministradorAdapter.Solicitud(
                    nombre = it.getNombreC(),
                    numeroControl = it.getTelefono()?.toLongOrNull() ?: 0,
                    correo = it.getCorreo() ?: "",
                    solicitud = it.getAsunto() ?: "Solicitud",
                    estado = "Pendiente"
                )
            }

            withContext(Dispatchers.Main) {
                recyclerView.adapter = SolicitudesUsuariosAdministradorAdapter(
                    solicitudesReales,
                    onAceptarClick = { solicitud ->
                        GlobalScope.launch(Dispatchers.IO) {
                            val usuario = listaUsuarios.find { it.getNombreC() == solicitud.nombre && it.getTelefono() == solicitud.numeroControl.toString() }
                            if (usuario != null) {
                                usuario.setEstatus(true)
                                db.usuarioDao().update(usuario)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(this@SolicitudesUsuariosAdministradorActivity, "Solicitud aceptada: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
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
                                    Toast.makeText(this@SolicitudesUsuariosAdministradorActivity, "Solicitud denegado: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                )
            }
        }
        // Botones inferiores
        findViewById<Button>(R.id.btnHomeAdmin).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSalirAdmin).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
            }
        }
}
