package com.example.gui.administrador

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.gui.R
import com.example.gui.data.DataBase.AdministradorDataBase
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.actions.NameDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EliminarAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //variables que se usaran en consulta de eliminar --LIMA
        val nombre = intent.getStringExtra("nombre") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val telefono = intent.getStringExtra("telefono") ?: ""
        val correo = intent.getStringExtra("correo") ?: ""
        setContentView(R.layout.eliminar_usuario_admin_y_seguridad)

        //ACA YA DBEN DE IMPLEMENTAR SU LOGICA SI VA A ELIMINAR O NO
        //boton si
        findViewById<Button>(R.id.botonEliminar).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val adminDB = AdministradorDataBase(this@EliminarAdminActivity)
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
                    Toast.makeText(this@EliminarAdminActivity, "Usuario $nombre eliminado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                } else {
                    Toast.makeText(this@EliminarAdminActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
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