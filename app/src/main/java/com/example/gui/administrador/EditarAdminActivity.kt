package com.example.gui.administrador

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R



 class EditarAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_usuario_admin_y_seguridad)
        //variables que tendran lo que se ingresa para actualizar
        val nombretext=findViewById<EditText>(R.id.nombreEdit).text.toString()
        val apellidop=findViewById<EditText>(R.id.apePaternoEdit).text.toString()
        val apellidom=findViewById<EditText>(R.id.apeMaternoEdit).text.toString()
        val correotext=findViewById<EditText>(R.id.correoEdit).text.toString().trim()
        val telefonotext=findViewById<EditText>(R.id.telefonoEdit).text.toString().trim()
        val usuariotext=findViewById<EditText>(R.id.usuarioEditText).text.toString().trim()
        val asuntotext=findViewById<EditText>(R.id.asuntoEdit).text.toString().trim()

        val nombreCompleto = nombretext.trim() + " " +apellidop.trim() + " " + apellidom.trim()



        //variables que se usaran en consulta de editar --LIMA
        val nombre = intent.getStringExtra("nombre") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val telefono = intent.getStringExtra("telefono") ?: ""
        val correo = intent.getStringExtra("correo") ?: ""


        //botones de Guardar y Home

        //Botón guardar
        findViewById<Button>(R.id.btnGuardarEdit).setOnClickListener {
           CoroutineScope(Dispatchers.Main).launch {
                val adminDB = AdministradorDataBase(this@EditarAdminActivity)

                var idUsuario: Long = -1
                withContext(Dispatchers.IO) {
                    idUsuario =
                        adminDB.db.usuarioDao().getIdPorNombre(nombre, password, telefono, correo)


                    if (idUsuario > 0) {
                        adminDB.actualizarUser(
                            nombreCompleto.takeIf { it.isNotBlank() }?.takeIf { it != " " },
                            correotext.takeIf { it.isNotBlank() }?.takeIf { it != " " },
                            telefonotext.takeIf { it.isNotBlank() }?.takeIf { it != " " },
                            usuariotext.takeIf { it.isNotBlank() }?.takeIf { it != " " },
                            asuntotext.takeIf { it.isNotBlank() }?.takeIf { it != " " },
                            idUsuario
                        )
                       /* adminDB.actualizarUser(
                            nombreCompleto.takeIf { it.isNotBlank() } ?: nombreCompleto,
                            correotext.takeIf { it.isNotBlank() } ?: correotext,
                            telefonotext.takeIf { it.isNotBlank() } ?: telefonotext,
                            usuariotext.takeIf { it.isNotBlank() } ?: usuariotext,
                            asuntotext.takeIf { it.isNotBlank() } ?: asuntotext,
                            idUsuario
                        )*/
                    }
                }
                runOnUiThread {
                    if (idUsuario > 0) {
                        Toast.makeText(this@EditarAdminActivity, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditarAdminActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                    }

                }




               // if (idUsuario > 0) {
                   /* adminDB.actualizarUsuario(
                        nombreCompleto.takeIf { it.isNotEmpty() } ?: nombreCompleto,
                        correotext.takeIf { it.isNotEmpty() } ?: correotext,
                        telefonotext.takeIf { it.isNotEmpty() } ?: telefonotext,
                        usuariotext.takeIf { it.isNotEmpty() } ?: usuariotext,
                        asuntotext.takeIf { it.isNotEmpty() } ?: asuntotext,
                        idUsuario
                    )*/

                    //Toast.makeText(this@EditarAdminActivity, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                //} else {
                    //Toast.makeText(this@EditarAdminActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
               // }

                //finish()
            }



            /*CoroutineScope(Dispatchers.Main).launch {
                val adminDB = AdministradorDataBase(this@EditarAdminActivity)
                var idUsuario: Long = -1
                withContext(Dispatchers.IO) {
                    idUsuario =
                        adminDB.db.usuarioDao().getIdPorNombre(nombre, password, telefono, correo)
                    val asunto = adminDB.db.usuarioDao().getAsunto(nombre, nombre, correo, telefono)
                    val tipoUsuario =
                        adminDB.db.usuarioDao().getTipoUser(nombre, password, telefono, correo)
                    Log.d(
                        "DEBUG",
                        "Intentando encontrar usuario con: Nombre: $nombre, Password: $password, Teléfono: $telefono, Correo: $correo"
                    )
                    Log.d("DEBUG", "ID obtenido para eliminar: $idUsuario")
                    if (idUsuario > 0) {


                    if ( nombreCompleto.isNotEmpty() && correotext.isNotEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizarUsuario(
                            nombreCompleto,
                            correotext,
                            telefonotext,
                            usuariotext,
                            asuntotext
                        )

                    } else if ( nombreCompleto.isNotEmpty() && correotext.isNotEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizaUno(
                            nombreCompleto,
                            correotext,
                            telefonotext,
                            usuariotext,
                            idUsuario
                        )
                        //adminDB.actualizarUsuario(nombreCompleto,correotext,telefonotext,usuariotext,asunto)

                    } else if ( nombreCompleto.isNotEmpty() && correotext.isNotEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizaDos(nombreCompleto, correotext, telefonotext, idUsuario)
                        //adminDB.actualizarUsuario(nombreCompleto,correotext,telefonotext,tipoUsuario,asunto)

                    } else if ( nombreCompleto.isNotEmpty() && correotext.isNotEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizaTres(nombreCompleto, correotext, idUsuario)

                        //adminDB.actualizarUsuario(nombreCompleto,correotext,telefono,tipoUsuario,asunto)

                    } else if (nombreCompleto.isNotEmpty() && correotext.isEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizarNombre(nombreCompleto, idUsuario)
                        //adminDB.actualizarUsuario(nombreCompleto,correo,telefono,tipoUsuario,asunto)

                    } /*else if (nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {
                        Toast.makeText(
                            this@EditarAdminActivity,
                            "Actualiza por lo menos un campo",
                            Toast.LENGTH_SHORT
                        ).show()
                        //adminDB.actualizarUsuario(nombreCompleto,correo,telefono,tipoUsuario,asunto)

                    }*/ else if (nombreCompleto.isEmpty() && correotext.isNotEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizaCuatro(
                            asuntotext,
                            usuariotext,
                            telefonotext,
                            correotext,
                            idUsuario
                        )
                        //adminDB.actualizarUsuario(nombre,correotext,telefonotext,usuariotext,asuntotext)

                    } else if ( nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizaCinco(asuntotext, usuariotext, telefonotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefonotext,usuariotext,asuntotext)

                    } else if (idUsuario > 0 && nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizaSeis(asuntotext, usuariotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,usuariotext,asuntotext)

                    } else if (nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizarAsunto(asuntotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    } else if ( nombreCompleto.isEmpty() && correotext.isNotEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizarCorreo(correotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    } else if (nombreCompleto.isEmpty() && correotext.isNotEmpty() && telefonotext.isEmpty()
                        && usuariotext.isEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizaSiete(correotext, asuntotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    } else if (nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizarTelefono(telefonotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    } else if ( nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isNotEmpty()
                        && usuariotext.isEmpty() && asuntotext.isNotEmpty()
                    ) {

                        adminDB.actualizaOcho(telefonotext, asuntotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    } else if (nombreCompleto.isEmpty() && correotext.isEmpty() && telefonotext.isEmpty()
                        && usuariotext.isNotEmpty() && asuntotext.isEmpty()
                    ) {

                        adminDB.actualizarTipo(usuariotext, idUsuario)
                        //adminDB.actualizarUsuario(nombre,correo,telefono,tipoUsuario,asuntotext)

                    }else{
                        Toast.makeText(
                            this@EditarAdminActivity,
                            "Actualiza por lo menos un campo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                        Log.e("ERROR", "No se encontró el usuario para eliminar")
                    }
                    /* if ( idUsuario > 0) {
                        adminDB.eliminarUsuario(nombre, password, telefono, correo)
                        Log.d("DEBUG", "Usuario eliminado con ID: $idUsuario")
                    } else {
                        Log.e("ERROR", "No se encontró el usuario para eliminar")
                    }*/
                }

                if ( idUsuario > 0) {
                    Toast.makeText(this@EditarAdminActivity, "Usuario $nombre eliminado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                } else {
                    Toast.makeText(this@EditarAdminActivity, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                }

                finish()
            }*/
            //finish()//Agregar logica para guardar los cambios
        }

        //Botón salir (Home)
        findViewById<Button>(R.id.btnSalirEdit).setOnClickListener {
            finish()
        }
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                var db: DataBase

                db = Room.databaseBuilder(
                    applicationContext,
                    DataBase::class.java,
                    NameDataBase.nameDB
                ).build();
                val usuario : UsuarioDao = db.usuarioDao()
                var listaUsers : List<Usuario> = usuario.AllUsuario()

                for (i in listaUsers){
                    Log.d("usuarios",i.toString())
                }
            }
        }

    }

}
