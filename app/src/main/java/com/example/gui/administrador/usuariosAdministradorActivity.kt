package com.example.gui.administrador

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var eliminarUsuarioLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarUsuarioLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuarios_administrador)

        // Inicializar el ActivityResultLauncher
        eliminarUsuarioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d("DEBUG", "Recargando usuarios después de eliminar")
                cargarUsuarios()
            }
        }

        editarUsuarioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d("DEBUG", "Recargando usuarios después de editar")
                cargarUsuarios()
            }
        }

        // Cargar usuarios cuando se crea la Activity
        cargarUsuarios()

        // Configurar botones inferiores
        findViewById<Button>(R.id.btnHomeAdministrador).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnDarAltaAdministrador).setOnClickListener {
            val intent = Intent(this, DarAltaUsuarioAdminActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSalirAdministrador).setOnClickListener {
            // Cierra toda la pila de actividades y regresa a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarUsuarios() // Recargar la lista cada vez que regreses a esta Activity
    }

    // Función para cargar usuarios
    private fun cargarUsuarios() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvDatosAdministrador)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                DataBase::class.java,
                NameDataBase.nameDB
            ).build()

            // Obtener la lista de usuarios
            val listaUsuarios = db.usuarioDao().AllUsuario()

            // Actualizar el RecyclerView en el hilo principal
            runOnUiThread {
                recyclerView.adapter = usuariosAdministradorAdapter(
                    listaUsuarios.map { usuario ->
                        usuariosAdministradorAdapter.Usuarios(
                            nombre = usuario.nombreC,
                            nControl = usuario.telefono?.toLongOrNull() ?: 0,
                            correo = usuario.correo ?: "",
                            telefono = usuario.telefono ?: "",
                            accionHabilitada = true
                        )
                    },
                    onAccionClick = { usuario ->
                        Toast.makeText(
                            this@usuariosAdministradorActivity,
                            "Usuario seleccionado: ${usuario.nombre} ${usuario.correo}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent=Intent(this@usuariosAdministradorActivity,EditarAdminActivity::class.java).apply{
                            putExtra("nombre",usuario.nombre)
                            putExtra("password",usuario.nombre)
                            putExtra("telefono",usuario.telefono)
                            putExtra("correo",usuario.correo)
                        }
                        //val intent = Intent(this@usuariosAdministradorActivity, EditarAdminActivity::class.java)
                        //startActivity(intent)
                        editarUsuarioLauncher.launch(intent)
                    },
                    onEliminarClick = { usuario ->
                        Toast.makeText(
                            this@usuariosAdministradorActivity,
                            "Usuario seleccionado: ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Llamada a EliminarAdminActivity pasando parámetros
                        val intent = Intent(this@usuariosAdministradorActivity, EliminarAdminActivity::class.java).apply {
                            putExtra("nombre", usuario.nombre)
                            putExtra("password", usuario.nombre) // Esto parece estar pasando el nombre como password
                            putExtra("telefono", usuario.telefono)
                            putExtra("correo", usuario.correo)
                        }
                        eliminarUsuarioLauncher.launch(intent) // Lanzar la actividad con el launcher
                    }
                )
            }
        }
    }
}
/*class usuariosAdministradorActivity : AppCompatActivity() {
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
                            "Usuario seleccionado: ${usuario.nombre} ${usuario.correo}",
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
                            //pasar parametros a la activity eliminar
                            putExtra("nombre", usuario.nombre)
                            putExtra("password", usuario.nombre)
                            putExtra("telefono", usuario.telefono)
                            putExtra("correo", usuario.correo)
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
}*/