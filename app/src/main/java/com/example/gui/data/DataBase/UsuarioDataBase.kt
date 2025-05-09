import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.gui.data.DataBase.DataBase
import com.example.gui.data.Entities.Familiar
import com.example.gui.data.Entities.Usuario
import com.example.gui.data.actions.NameDataBase
import com.example.gui.data.actions.PhotoBytesAl
import com.example.gui.data.actions.SaveDateUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioDataBase {

    @SuppressLint("Range")
    fun getUser(context: Context, usuario: String, contraseña: String, rol:String): String {
        val db = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)
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

                    GlobalScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.IO) {
                            var db: DataBase

                            db = Room.databaseBuilder(
                                context,
                                DataBase::class.java,
                                NameDataBase.nameDB
                            ).build();

                            val i = Usuario(
                                usuario,
                                false,
                                usuario+"g@gmail.com",
                                usuario,
                                null,
                                null,
                                "visitante",
                                ""
                            )


                            db.usuarioDao().insert(i)

                        }}

                    return "visitante";
                }
            }
        }finally {
            cursor.close()
            db.close()
        }


        return ""
    }



    //Consulta para obtener el id del usuario (cualquier usuario) --Lima
    @SuppressLint("Range")
    fun consultarId(context:Context,usuario:String):Long{
        val db=context.openOrCreateDatabase(NameDataBase.nameDB,Context.MODE_PRIVATE,null)
        val cursor=db.rawQuery(
            "SELECT id FROM USUARIO WHERE nombreC = ? AND password= ?",
            arrayOf(usuario,usuario)
        )
        var id=-1L
        if(cursor.moveToFirst()){
            id=cursor.getLong(cursor.getColumnIndex("id"))
        }
        cursor.close()

        return id
        db.close()
    }
    //PARA UN FAMILIAR -> PASA A ACTIVARLO
    //->TANTO USUARIO COMO FAMILIAR SE ACTIVA
    // CREATE USER //ID
    // CREATE FAMILIAR //ID(AUTOMATICAMENTE) -> ID USER CREADO -> ID DE FAM -> PARENTESCO
    @SuppressLint("Range")
    fun createUserFamiliar(context: Context, usuario: String, contraseña: String, photo: ByteArray, rol: String, parentesco: String) {

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {

                val db: DataBase = Room.databaseBuilder(
                    context,
                    DataBase::class.java,
                    NameDataBase.nameDB
                ).build();

                val nuevoUsuario =
                    Usuario(usuario, false, "$usuario@gmail.com", contraseña, "", photo, "familiar")

                db.usuarioDao().insert(nuevoUsuario)

                val db2 = context.openOrCreateDatabase(NameDataBase.nameDB, Context.MODE_PRIVATE, null)

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



    @SuppressLint("Range")
    fun createVisante(context: Context, usuario: String, asunto: String, photo: ByteArray){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val db =
                    Room.databaseBuilder(context, DataBase::class.java, NameDataBase.nameDB).build()
                db.usuarioDao().setData(usuario, asunto, photo, SaveDateUser.contraseña)
            }
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
=======


    @SuppressLint("Range")
    fun createVisante(context: Context, usuario: String, asunto: String, photo: ByteArray){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val db =
                    Room.databaseBuilder(context, DataBase::class.java, NameDataBase.nameDB).build()
                db.usuarioDao().setData(usuario, asunto, photo, SaveDateUser.contraseña)
            }
        }
    }

*/



}
