package com.example.gui.seguridad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gui.R
import com.example.gui.administrador.ListaReportesAdministradorActivity
import com.example.gui.administrador.SolicitudesInvitadosAdministradorActivity
import com.example.gui.administrador.SolicitudesUsuariosAdministradorActivity
import com.example.gui.administrador.usuariosAdministradorActivity
import com.example.gui.visitante.DarAltaVisitanteActivity

class SeguridadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarqrseguridad)//Aca debo de hacer referencia a la carpeta que contiene los layouts del administrador
        val botonMenu = findViewById<ImageButton>(R.id.botonOpcionesExtrasSeguridad)

        botonMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.menuInflater.inflate(R.menu.menu_opciones_seguridad, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.menu_usuarios_seguridad -> {
                        val intent = Intent(this, UsuariosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de SOLICITUDES
                    R.id.menu_solicitudes_usuarios_seguridad -> {
                        val intent = Intent(this, SolicitudesUsuariosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_solicitudes_invitados_seguridad -> {
                        val intent = Intent(this, SolicitudesInvitadosSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    // Submenú de REPORTES
                    R.id.menu_reportes_ver_segurida -> {
                        val intent = Intent(this, ListaReportesSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_reportes_generar_seguridad -> {
                        val intent = Intent(this, generarReporteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu_escanear_seguridad -> {
                        val intent = Intent(this, EscanearSeguridadActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }//fin del metodo


        //bton salir que regresa al activityMain
        var botonSalir = findViewById<Button>(R.id.botonSalir_seguridad).setOnClickListener{
            finish()
        }
    }
}