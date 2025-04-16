package com.example.gui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.gui.administrador.AdministradorActivity
import com.example.gui.docente.DocenteActivity
import com.example.gui.empleadoADM.EmpleadoADMActivity
import com.example.gui.estudiante.EstudianteActivity
import com.example.gui.familiar.FamiliarActivity
import com.example.gui.otrosEmpleados.OtrosEmpleadosActivity
import com.example.gui.seguridad.SeguridadActivity
import com.example.gui.visitante.VisitanteActivity

class IniciarSesionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.iniciarsesion)

        val etUsuario = findViewById<EditText>(R.id.usuarioEditText)
        val etPassword = findViewById<EditText>(R.id.contrasenaEditText)
        val btnLogin = findViewById<Button>(R.id.ingresarButton)
        val rol = intent.getStringExtra("ROL") ?: ""

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            if (validarCredenciales(usuario, password)) {
                when (rol) {
                    "ESTUDIANTE" -> redirectTo(EstudianteActivity::class.java, usuario)
                    "DOCENTE" -> redirectTo(DocenteActivity::class.java, usuario)
                    "ADMINISTRADOR" -> redirectTo(AdministradorActivity::class.java, usuario)
                    "SEGURIDAD" -> redirectTo(SeguridadActivity::class.java, usuario)
                    "VISITANTE" -> redirectTo(VisitanteActivity::class.java, usuario)
                    "FAMILIAR" -> redirectTo(FamiliarActivity::class.java, usuario)
                    "EMPLEADOADM" -> redirectTo(EmpleadoADMActivity::class.java, usuario)
                    "OTROS" -> redirectTo(OtrosEmpleadosActivity::class.java, usuario)
                    else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCredenciales(usuario: String, password: String): Boolean {
        // Lógica de autenticación genérica (ej: consultar una API o base de datos)
        return usuario == "admin" && password == "12345" // Ejemplo básico
    }

    private fun <T : Activity> redirectTo(activityClass: Class<T>, usuario: String) {
        val intent = Intent(this, activityClass)
        intent.putExtra("USUARIO", usuario)
        startActivity(intent)
        finish()
    }


    }
