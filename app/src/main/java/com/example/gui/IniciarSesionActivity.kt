package com.example.gui

import UsuarioDataBase
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.gui.administrador.AdministradorActivity
import com.example.gui.data.Daos.UsuarioDao
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Usuario
import com.example.gui.docente.DocenteActivity
import com.example.gui.empleadoADM.EmpleadoADMActivity
import com.example.gui.estudiante.EstudianteActivity
import com.example.gui.familiar.FamiliarActivity
import com.example.gui.otrosEmpleados.OtrosEmpleadosActivity
import com.example.gui.seguridad.SeguridadActivity
import com.example.gui.visitante.VisitanteActivity
import com.example.gui.data.actions.SaveDateUser;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


            val uDatabase = UsuarioDataBase()
            val auxiliar = uDatabase.getUser(this, usuario, password, rol)
            Log.d("CREDENCIALES APROBADAS? ",rol)





                if (auxiliar!="") {

                    SaveDateUser.nombreC = usuario
                    SaveDateUser.contraseña = password


                    if(auxiliar == "alumno"){
                        when(rol){
                            "ESTUDIANTE" -> redirectTo(EstudianteActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(auxiliar == "docente"){
                        when(rol){
                            "DOCENTE" -> redirectTo(DocenteActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }
                    /*Agregue esto para obtener el id del administrador, en dado caso
                    * de que tengan alguna falla me avisan y le cambio
                    * No afecta en los demas usuarios */
                    if(auxiliar=="administrador"){
                        when(rol){
                            "ADMINISTRADOR"->{
                                val idAdmin=uDatabase.consultarId(this,usuario)
                                GestionarId(this).guardarIdAdmin(idAdmin)
                                redirectTo(AdministradorActivity::class.java,usuario)

                            }
                            else-> Toast.makeText(this,"Rol no valido",Toast.LENGTH_SHORT).show()
                        }
                    }




                   /* if(auxiliar == "administrador"){
                        when(rol){
                            "ADMINISTRADOR" -> redirectTo(AdministradorActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }*/

                    if(auxiliar == "seguridad"){
                        when(rol){
                            "SEGURIDAD"->{
                                val idSeguridad=uDatabase.consultarId(this,usuario)
                                GestionarId(this).guardarIdAdmin(idSeguridad)
                                redirectTo(SeguridadActivity::class.java,usuario)

                            }
                            else-> Toast.makeText(this,"Rol no valido",Toast.LENGTH_SHORT).show()
                        }
                    }
                 /*   if(auxiliar == "seguridad"){
                        when(rol){
                            "SEGURIDAD" -> redirectTo(SeguridadActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }*/

                    if(auxiliar == "visitante"){
                        when(rol){
                            "VISITANTE" -> redirectTo(VisitanteActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(auxiliar == "familiar"){
                        when(rol){
                            "FAMILIAR" -> redirectTo(FamiliarActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(auxiliar == "empleados de adm"){
                        when(rol){
                            "EMPLEADOADM" -> redirectTo(EmpleadoADMActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(auxiliar == "otros empleados"){
                        when(rol){
                            "OTROS" -> redirectTo(OtrosEmpleadosActivity::class.java, usuario)
                            else -> Toast.makeText(this, "Rol no válido", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

   /* private fun validarCredenciales(usuario: String, password: String): Boolean {
        // Lógica de autenticación genérica (ej: consultar una API o base de datos)
        return usuario == "admin" && password == "12345" // Ejemplo básico
    }
*/
    private fun <T : Activity> redirectTo(activityClass: Class<T>, usuario: String) {
        val intent = Intent(this, activityClass)
        intent.putExtra("USUARIO", usuario)
        startActivity(intent)
        finish()
    }


    }
