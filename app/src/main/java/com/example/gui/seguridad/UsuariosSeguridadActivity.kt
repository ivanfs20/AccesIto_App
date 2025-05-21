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
import com.example.gui.administrador.EditarAdminActivity
import com.example.gui.administrador.usuariosAdministradorAdapter
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import com.example.gui.visitante.IdentificacionVisitanteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsuariosSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_seguridad)
        val recyclerView = findViewById<RecyclerView>(R.id.rvDatosSeguridad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                DataBase::class.java,
                NameDataBase.nameDB
            ).build()

            val listaUsuarios = db.usuarioDao().AllUsuario()

            runOnUiThread {
                recyclerView.adapter = usuariosSeguridadAdapter(
                    listaUsuarios.map { usuario ->
                        usuariosSeguridadAdapter.Usuarios(
                            nombre = usuario.nombreC,
                            nControl = usuario.telefono?.toLongOrNull() ?: 0,
                            correo = usuario.correo ?: "",
                            telefono = usuario.telefono?.toLongOrNull() ?: 0,
                            accionHabilitada = true
                        )
                    },
                    onAccionClick = { usuario ->
                        Toast.makeText(
                            this@UsuariosSeguridadActivity,
                            "Usuario seleccionado: ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@UsuariosSeguridadActivity, EditarSeguridadActivity::class.java).apply {

                        }
                        startActivity(intent)
                    },
                    onDeleteClick = { usuario ->
                        Toast.makeText(
                            this@UsuariosSeguridadActivity,
                            "Usuario seleccionado: ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@UsuariosSeguridadActivity, EliminarSeguridadActivity::class.java).apply {

                        }
                        startActivity(intent)
                    }
                ) /*{ usuario ->
                    Toast.makeText(
                        this@UsuariosSeguridadActivity,
                        "Usuario seleccionado: ${usuario.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
            }
        }


        // Configurar botones inferiores
        findViewById<Button>(R.id.btnHomeSeguridad).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDarAltaSeguridad).setOnClickListener {
            val intent = Intent(this, DarAltaUsuarioActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSalirSeguridad).setOnClickListener {
            // Cierra toda la pila de actividades y regresa a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
