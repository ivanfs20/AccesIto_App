package com.example.gui.administrador

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
import com.example.gui.seguridad.DarAltaUsuarioActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class usuariosAdministradorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_administrador)
        val recyclerView = findViewById<RecyclerView>(R.id.rvDatosAdministrador)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //Otener usuarios
        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                DataBase::class.java,
                NameDataBase.nameDB
            ).build()

            val listaUsuarios = db.usuarioDao().AllUsuario()

            runOnUiThread {

                recyclerView.adapter = usuariosAdministradorAdapter(
                    listaUsuarios.map { usuario ->
                        usuariosAdministradorAdapter.Usuarios(
                            nombre = usuario.nombreC,
                            nControl = usuario.telefono?.toLongOrNull() ?: 0,
                            correo = usuario.correo ?: "",
                            telefono = usuario.telefono?.toLongOrNull() ?: 0,
                            accionHabilitada = true
                        )
                    },
                    onAccionClick = { usuario ->
                        Toast.makeText(
                            this@usuariosAdministradorActivity,
                            "Usuario seleccionado: ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@usuariosAdministradorActivity, EditarAdminActivity::class.java).apply {

                        }
                        startActivity(intent)
                    },
                    onEliminarClick = { usuario ->
                        Toast.makeText(
                            this@usuariosAdministradorActivity,
                            "Usuario seleccionado: ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@usuariosAdministradorActivity, EliminarAdminActivity::class.java).apply {

                        }
                        startActivity(intent)
                    }
                ) /*{ usuario ->
                    Toast.makeText(
                        this@usuariosAdministradorActivity,
                        "Usuario seleccionado: ${usuario.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                */
            }
        }




        // Configurar botones inferiores
        findViewById<Button>(R.id.btnHomeAdministrador).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDarAltaAdministrador).setOnClickListener {
            val intent = Intent(this, DarAltaUsuarioAdminActivity::class.java)
            startActivity(intent)
        }

        //se ajusto para que salga a la MainActivity
        findViewById<Button>(R.id.btnSalirAdministrador).setOnClickListener {
            // Cierra toda la pila de actividades y regresa a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
