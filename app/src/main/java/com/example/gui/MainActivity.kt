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
import com.example.gui.data.Daos.UsuarioDao
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Usuario
import com.example.gui.ui.theme.GUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var db : DataBase
        db = Room.databaseBuilder(this,DataBase::class.java,"base").build();
        val usuario : UsuarioDao = db.usuarioDao()
        Thread{
            usuario.insert(Usuario())
            var listaUsuarios : List<Usuario> = usuario.AllUsuario()
            for (i in listaUsuarios){
                Log.d("id",i.id.toString()+" "+i.nombre1)
            }
        }.start()

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