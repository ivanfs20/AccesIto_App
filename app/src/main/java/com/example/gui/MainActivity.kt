package com.example.gui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.gui.data.Daos.UsuarioDao
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.NameDataBase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementacion de los escuchadores de los botones

        //boton estudiante
        var botonEstudiante = findViewById<Button>(R.id.botonEstudiante).setOnClickListener {
            openLoginActivity("ESTUDIANTE")
        }

        //boton docente
        var botonDocente = findViewById<Button>(R.id.botonDocente).setOnClickListener {
            openLoginActivity("DOCENTE")
        }

        //boton administrador
        var botonAdministrador = findViewById<Button>(R.id.botonAdministrador).setOnClickListener {
            openLoginActivity("ADMINISTRADOR")
        }

        //boton seguridad
        var botonSeguridad = findViewById<Button>(R.id.botonSeguridad).setOnClickListener {
            openLoginActivity("SEGURIDAD")
        }

        //boton visitante
        var botonVisitante = findViewById<Button>(R.id.botonVisitante).setOnClickListener {
            openLoginActivity("VISITANTE")
        }

        //boton familiar
        var botonFamiliar = findViewById<Button>(R.id.botonFamiliar).setOnClickListener {
            openLoginActivity("FAMILIAR")
        }

        //boton empleado de administracion
        var botonEmpleadoADM = findViewById<Button>(R.id.botonEmpleadoADM).setOnClickListener {
            openLoginActivity("EMPLEADOADM")
        }

        //boton otros empleados
        var botonOtrosEmpleados =
            findViewById<Button>(R.id.botonOtrosEmpleados).setOnClickListener {
                openLoginActivity("OTROS")
            }

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

        //PRUEBA PARA ASIGNAR QR A ADMINISTRADORES
        val nuevoUsuario = Usuario(
            "Saul Lima Gonzalez",
            true,
            "slg@gmail.com",
            "Saul Lima Gonzalez",
            "limaSa",
            null,
            "administrador",
            ""
        )


        val studiante = Usuario(
            "Carlos",
            true,
            "carg@gmail.com",
            "Carlos",
            "2722062180",
            null,
            "alumno",
            "Ingenieria Sistemas Computacionales"
        )

        val familiar = Usuario(
            "C",
            false,
            "carg@gmail.com",
            "C",
            "2722062180",
            null,
            "familiar",
            "Ingenieria Sistemas Computacionales"
        )


        val invitado = Usuario(
            "v1",
            false,
            "carg@gmail.com",
            "v1",
            "2722062180",
            null,
            "visitante",
            "Ingenieria Sistemas Computacionales"
        )


        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                var db: DataBase

                db = Room.databaseBuilder(
                    applicationContext,
                    DataBase::class.java,
                    NameDataBase.nameDB
                ).build();
 val usuario : UsuarioDao = db.usuarioDao()
usuario.insert(nuevoUsuario)
                usuario.insert(studiante)


                usuario.insert(familiar)

                usuario.insert(invitado)
}}




/*GlobalScope.launch(Dispatchers.Main) {
withContext(Dispatchers.IO) {
 var db: DataBase

 db = Room.databaseBuilder(
     applicationContext,
     DataBase::class.java,
     NameDataBase.nameDB
 ).build();


 /* val usuario : UsuarioDao = db.usuarioDao()
 usuario.insert(Usuario("Carlos Ivan Flores Sanchez",true,"cifs@gmail.com","1","carloscras15",null,"alumno",""))
 usuario.insert(Usuario("Jesus Antonio Morales Jesus",true,"jamj@gmail.com","2","jesusMor",null,"docente",""))
 usuario.insert(Usuario("Edwin Ariel Ramos Alvares",true,"eara@gmail.com","3","edwinAri",null,"administrador",""))
 usuario.insert(Usuario("Saul Lima Gonzalez",true,"slg@gmail.com","4","limaSa",null,"seguridad",""))
 usuario.insert(Usuario("Uriel Herrara Hurtado",true,"uhh@gmail.com","5","uriHerre",null,"visitante","Papeleo"))
 usuario.insert(Usuario("Mariana Ariana Martinez Sanchez",true,"mams@gmail.com","6","marl",null,"familiar",""))
 usuario.insert(Usuario("Jose Unico Vela Alarcon",true,"juva@gmail.com","7","alarc",null,"empleados de adm",""))
 usuario.insert(Usuario("Ricardo Rivera Rivera",true,"rrr@gmai.com","8","riveRic",null,"otros empleados",""))

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
 } */

 var acceso: AccesoDao = db.accesoDao()

 acceso.insert(Acceso("20-05-2025", "20-05-2025", 15))
 acceso.insert(Acceso("20-05-2025", "20-05-2025", 16))
 acceso.insert(Acceso("20-05-2025", "20-05-2025", 17))
 acceso.insert(Acceso("20-05-2025", "20-05-2025", 18))
 acceso.insert(Acceso("20-05-2025", "20-05-2025", 19))
 acceso.insert(Acceso("20-05-2025", "20-05-2025", 20))

 val listaacceso: List<Acceso> = acceso.AllAcceso()

 for (i in listaacceso) {
     Log.d("Acceso: ", i.toString())
 }

 /*    var familiar : FamiliarDao = db.familiarDao()
 familiar.insert(Familiar(6,1,"Madre"));

 val listaFamiliar : List<Familiar> = familiar.AllFamiliar()

 for(i in listaFamiliar){
     Log.d("Familiar: ",i.toString())
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
 }*/


 /*
*                val userQrDaos : UsuarioQrDao = db.usuarioQrDao()
 val listaQrUser = userQrDaos.usuarioQr

 for(i in listaQrUser){
     Log.d("RELATION LISTAUSERQR",i.toString())
 }

 val usuarioAdm : UsuarioAdministracionDao = db.usuarioAdministracionDao()
 val listaUserAdm = usuarioAdm.usuarioAdministracion

 for(i in listaUserAdm){
     Log.d("RELATION LISTAUSERADM",i.toString())
 }
* */
}


}*/


/**
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
*/

}
private fun openLoginActivity(rol: String) {
val intent = Intent(this, IniciarSesionActivity::class.java)
intent.putExtra("ROL", rol)
startActivity(intent)
}

fun generarCodigoQR(contenido: String, tamaño: Int = 300): ByteArray {
val bitMatrix = MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, tamaño, tamaño)
val bmp = Bitmap.createBitmap(tamaño, tamaño, Bitmap.Config.RGB_565)
for (x in 0 until tamaño) {
for (y in 0 until tamaño) {
 bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
}
}
val stream = ByteArrayOutputStream()
bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
return stream.toByteArray()
}
}

/**
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
*/