package com.example.gui.seguridad

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EliminarSeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val telefono = intent.getStringExtra("telefono") ?: ""
        val correo = intent.getStringExtra("correo") ?: ""
        setContentView(R.layout.eliminar_usuario_admin_y_seguridad)

        //ACA YA DBEN DE IMPLEMENTAR SU LOGICA SI VA A ELIMINAR O NO
        //boton si
        findViewById<Button>(R.id.botonEliminar).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val adminDB = AdministradorDataBase(this@EliminarSeguridadActivity)
                var idUsuario: Long = -1
                withContext(Dispatchers.IO) {
                    idUsuario = adminDB.db.usuarioDao().getIdPorNombre(nombre, password, telefono, correo)
                    Log.d("DEBUG", "Intentando encontrar usuario con: Nombre: $nombre, Password: $password, Teléfono: $telefono, Correo: $correo")
                    Log.d("DEBUG", "ID obtenido para eliminar: $idUsuario")
                    if ( idUsuario > 0) {
                        adminDB.eliminarUsuario(nombre, password, telefono, correo)
                        Log.d("DEBUG", "Usuario eliminado con ID: $idUsuario")
                    } else {
                        Log.e("ERROR", "No se encontró el usuario para eliminar")
                    }
                }

                if ( idUsuario > 0) {
                    Toast.makeText(this@EliminarSeguridadActivity, "Usuario $nombre eliminado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                } else {
                    Toast.makeText(this@EliminarSeguridadActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                }

                finish()
            }
        }

        //boton para no
        findViewById<Button>(R.id.botonRechazar).setOnClickListener {
            finish() // Cierra esta actividad y regresa
        }
    }
}

