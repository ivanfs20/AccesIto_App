package com.example.gui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.gui.data.Daos.AccesoDao
import com.example.gui.data.Daos.AdministracionDao
import com.example.gui.data.Daos.QrAccesoDao
import com.example.gui.data.Daos.QrDao
import com.example.gui.data.Daos.ReporteDao
import com.example.gui.data.Daos.UsuarioDao
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Acceso
import com.example.gui.data.Entities.Administracion
import com.example.gui.data.Entities.Qr
import com.example.gui.data.Entities.Reporte
import com.example.gui.data.Entities.Usuario
import com.example.gui.ui.theme.GUITheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
                var db : DataBase
        db = Room.databaseBuilder(this,DataBase::class.java,"PruebasAcces").build();
        val usuario : UsuarioDao = db.usuarioDao()
        Thread{
            usuario.insert(Usuario())
            var listaUsuarios : List<Usuario> = usuario.AllUsuario()
            for (i in listaUsuarios){
                Log.d("id",i.id.toString()+" "+i.nombre1)
            }
        }.start()
         */

        //En segundo plano
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var db : DataBase
                db = Room.databaseBuilder(applicationContext,DataBase::class.java,"PruebasAcces").build();


                val usuario : UsuarioDao = db.usuarioDao()
                usuario.insert(Usuario("Carlos","Ivan","Flores","Sanchez",true,"","carloscras15",null,"alumno"))
                usuario.insert(Usuario("Jesus","Antonio","Morales","Jesus",true,"","jesusMor",null,"docente"))
                usuario.insert(Usuario("Edwin","Ariel","Ramos","Alvares",true,"","edwinAri",null,"administrador"))
                usuario.insert(Usuario("Saul",null,"Lima","Gonzalez",true,"","limaSa",null,"seguridad"))
                usuario.insert(Usuario("Uriel",null,"Herrara","Hurtado",true,"","uriHerre",null,"visitante"))
                usuario.insert(Usuario("Mariana","Ariana","Martinez","Sanchez",true,"","marl",null,"familiar"))
                usuario.insert(Usuario("Jose","Unico","Vela","Alarcon",true,"","alarc",null,"empleados de adm"))
                usuario.insert(Usuario("Ricardo",null,"Rivera","Rivera",true,"","riveRic",null,"otros empleados"))

                var listaUsers : List<Usuario> = usuario.AllUsuario()

                for (i in listaUsers){
                    Log.d("usuarios",i.toString())
                }

                val qr : QrDao = db.qrDao()

                qr.insert(Qr(null,"20-01-2025","VIGENTE",1))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",2))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",3))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",4))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",5))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",6))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",7))
                qr.insert(Qr(null,"20-01-2025","VIGENTE",8))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",1))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",1))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",1))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",2))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",4))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",3))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",6))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",1))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",2))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",7))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",5))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",2))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",1))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",3))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",4))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",5))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",5))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",2))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",1))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",4))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",6))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",2))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",1))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",8))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",7))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",6))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",5))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",4))
                qr.insert(Qr(null,"20-01-2025","EXPIRADO",3))
                qr.insert(Qr(null,"20-01-2025","DENEGADO",2))

                var listaQr : List<Qr> = qr.AllQr()

                for (i in listaQr){
                    Log.d("Qr",i.toString())
                }

                var acceso : AccesoDao = db.accesoDao()
                acceso.insert(Acceso("20-01-2025","20-01-2025",1))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",3))
                acceso.insert(Acceso("20-01-2025","20-01-2025",4))
                acceso.insert(Acceso("20-01-2025","20-01-2025",5))
                acceso.insert(Acceso("20-01-2025","20-01-2025",1))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",3))
                acceso.insert(Acceso("20-01-2025","20-01-2025",4))
                acceso.insert(Acceso("20-01-2025","20-01-2025",5))
                acceso.insert(Acceso("20-01-2025","20-01-2025",6))
                acceso.insert(Acceso("20-01-2025","20-01-2025",1))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",3))
                acceso.insert(Acceso("20-01-2025","20-01-2025",4))
                acceso.insert(Acceso("20-01-2025","20-01-2025",1))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",3))
                acceso.insert(Acceso("20-01-2025","20-01-2025",4))
                acceso.insert(Acceso("20-01-2025","20-01-2025",5))
                acceso.insert(Acceso("20-01-2025","20-01-2025",6))
                acceso.insert(Acceso("20-01-2025","20-01-2025",7))
                acceso.insert(Acceso("20-01-2025","20-01-2025",2))
                acceso.insert(Acceso("20-01-2025","20-01-2025",1))

                val listaacceso : List<Acceso> = acceso.AllAcceso()

                for(i in listaacceso){
                    Log.d("Acceso: ",i.toString())
                }

                val administración : AdministracionDao = db.administracionDao()
                administración.insert(Administracion("20-01-2020","20-12-2020",3))
                administración.insert(Administracion("20-01-2020","01-11-2020",4))
                administración.insert(Administracion("20-01-2021","20-08-2021",4))
                administración.insert(Administracion("21-08-2021","01-01-2022",4))
                administración.insert(Administracion("20-01-2022",null,3))
                administración.insert(Administracion("20-01-2022",null,4))

                val listaadministracion : List<Administracion> = administración.AllAdm()

                for(i in listaadministracion){
                    Log.d("Administracion: ",i.toString())
                }


                val reporte : ReporteDao = db.reporteDao()

                reporte.insert(Reporte("Reporte 1","Este es el reporte 1","20-12-2020",null,3))
                reporte.insert(Reporte("Reporte 1","Este es el reporte 1","01-11-2020",null,4))
                reporte.insert(Reporte("Reporte 1","Este es el reporte 1","20-18-2021",null,4))
                reporte.insert(Reporte("Reporte 1","Este es el reporte 1","01-01-2022",null,4))

                val listareporte : List<Reporte> = reporte.AllReporte()

                for(i in listareporte){
                    Log.d("Reporte: ", i.toString())
                }
            }


        }

        enableEdgeToEdge()
        setContent {
            GUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GUITheme {
        Greeting("Android")
    }
}