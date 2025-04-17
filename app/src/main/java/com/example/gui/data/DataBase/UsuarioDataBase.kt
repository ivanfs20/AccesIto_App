import android.annotation.SuppressLint
import android.content.Context

class UsuarioDataBase {

    @SuppressLint("Range")
    fun getUser(context: Context, usuario: String, contraseña: String, rol:String): String {
        val db = context.openOrCreateDatabase("AccesItoPrueba", Context.MODE_PRIVATE, null)
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
