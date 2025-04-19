import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Familiar
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.PhotoBytesAl
import com.example.gui.data.actions.SaveDateUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioDataBase {

    @SuppressLint("Range")
    fun getUser(context: Context, usuario: String, contraseña: String, rol:String): String {
        val db = context.openOrCreateDatabase("Access", Context.MODE_PRIVATE, null)
        var cursor = db.rawQuery(
            "SELECT * FROM USUARIO WHERE nombreC = ? AND password = ?",
            arrayOf(usuario, contraseña)
        )

        try {
            val encontrado = cursor.moveToFirst()

            if(encontrado){
                return cursor.getString(cursor.getColumnIndex("tipo_usuario"))
            }else{
                if(rol=="VISITANTE"){
                    cursor = db.rawQuery(
                        "INSERT INTO USUARIO (nombreC, password, tipo_usuario) VALUES (?,?,?)",
                        arrayOf(usuario, contraseña, "visitante")
                    )
                    return "visitante";
                }
            }
        }finally {
            cursor.close()
            db.close()
        }


        return ""
    }


    //PARA UN FAMILIAR -> PASA A ACTIVARLO
    //->TANTO USUARIO COMO FAMILIAR SE ACTIVA
    // CREATE USER //ID
    // CREATE FAMILIAR //ID(AUTOMATICAMENTE) -> ID USER CREADO -> ID DE FAM -> PARENTESCO
    @SuppressLint("Range")
    fun createUserFamiliar(context: Context, usuario: String, contraseña: String, photo: ByteArray, rol: String, parentesco: String) {

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(context, DataBase::class.java, "Access").build()
                val nuevoUsuario =
                    Usuario(usuario, false, "$usuario@gmail.com", contraseña, "", photo, rol)

                val idUsuario = db.usuarioDao().insert(nuevoUsuario)
                Log.d("InsertUsuario", "Usuario creado con ID: $idUsuario")

                val db2 = context.openOrCreateDatabase("Access", Context.MODE_PRIVATE, null)

                var cursor = db2.rawQuery(
                    "SELECT * FROM USUARIO WHERE nombreC = ? AND password = ?",
                    arrayOf(usuario, contraseña)
                )

                var cursor2 = db2.rawQuery(
                    "SELECT * FROM USUARIO WHERE nombreC = ? AND password = ?",
                    arrayOf(SaveDateUser.nombreC, SaveDateUser.contraseña)
                )

                if(cursor.moveToFirst() && cursor2.moveToFirst()){
                    val id_user_fam = cursor.getString(cursor.getColumnIndex("id"))
                    val id_user_student = cursor2.getString(cursor.getColumnIndex("id"))

                    val fam = Familiar(id_user_fam.toLong(), id_user_student.toLong(), parentesco)
                    val dbFam = db.familiarDao().insert(fam)

                    Log.d("InsertUsuario", "Usuario creado con ID: $dbFam")

                }


                db.close()
            }
        }
    }

/*
    @SuppressLint("Range")
    fun createUser(context: Context, nombre1: String, nombre2: String): String {
        val db = context.openOrCreateDatabase("PruebasAcces", Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery(
            "SELECT * FROM USUARIO WHERE id = ? AND password = ?",
            arrayOf(usuario, contraseña)
        )

        val encontrado = cursor.moveToFirst()


        if(encontrado){
            return cursor.getString(cursor.getColumnIndex("tipo_usuario"))
        }

        cursor.close()
        db.close()

        return ""
    }

*/


}
