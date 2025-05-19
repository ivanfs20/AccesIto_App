package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Usuario;

import java.util.List;
@Dao
    public interface UsuarioDao {
        @Insert
            //void insert(Usuario usuario);
            //Le agregue esto para que obtenga el id del usuario
        long insert(Usuario usuario);

        @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM USUARIO")
    List<Usuario> AllUsuario();

    @Query("SELECT * FROM USUARIO WHERE password =:password")
    Usuario getUser(String password);

    @Query("SELECT * FROM USUARIO WHERE id = :idUsuario LIMIT 1")
    Usuario getUsuarioById(Long idUsuario);

    @Query ("SELECT nombreC FROM USUARIO WHERE id =:idAdmin")
    String getNombreAdministrador(Long idAdmin);

    @Query("SELECT * FROM USUARIO WHERE tipo_usuario=:tipo")
    List<Usuario> getUserType(String tipo);

    @Query("SELECT * FROM USUARIO WHERE tipo_usuario='familiar' AND estatus=0")
    List<Usuario> getSolicitudFamiliar();


    @Query("SELECT * FROM USUARIO WHERE tipo_usuario='visitante' AND estatus=0")
    List<Usuario> getSolicitudVisitante();



    @Query("SELECT * FROM usuario WHERE nombreC = :nombre AND password = :contraseña LIMIT 1")
    Usuario getUsuario(String nombre, String contraseña);

    @Query("UPDATE USUARIO SET nombreC=:usuario, asunto=:asun, foto_bytes=:photo WHERE nombreC=:usuario and password =:password")
    void setData(String usuario, String asun, byte[] photo, String password);


    //Query para eliminar el usuario segun todas
   // @Query("DELETE  FROM USUARIO WHERE nombreC=:nombre AND password=:password AND telefono=:telefono AND correo=:correo")
    @Query ("DELETE FROM USUARIO WHERE id=:id")
    void deletePorId(long id);

    @Query("SELECT id FROM USUARIO WHERE nombreC=:nombre AND password=:password AND telefono=:telefono AND correo=:correo")

    long getIdPorNombre(String nombre,String password,String telefono,String correo);

    @Query("SELECT COUNT(*) FROM USUARIO WHERE nombreC = :nombre AND password=:nombre")
    int existeUsuario(String nombre);

    @Query("UPDATE USUARIO SET nombreC=:nombre,password=:nombre,correo=:correo, telefono=:telefono, tipo_usuario=:usuario, asunto=:asunto WHERE id=:id")
    void actualizarUsuario(String nombre,String correo,String telefono,String usuario,String asunto,long id);

    //para obtener el tipo de usuario
    @Query("SELECT tipo_usuario FROM USUARIO WHERE  nombreC=:nombre AND password=:password AND telefono=:telefono AND correo=:correo")
    String getTipoUser(String nombre,String password,String telefono,String correo);

    //para obtener el asunto
    @Query("SELECT asunto FROM USUARIO WHERE  nombreC=:nombre AND password=:password AND telefono=:telefono AND correo=:correo")
    String getAsunto(String nombre,String password,String telefono,String correo);

    @Query("UPDATE USUARIO SET nombreC=:nombre,password=:nombre WHERE id=:id")
    void actualizarNombrePassword(String nombre,long id);

    @Query("UPDATE USUARIO SET correo=:correo WHERE id=:id")
    void actualizarCorreo(String correo,long id);

    @Query("UPDATE USUARIO SET telefono=:telefono WHERE id=:id")
    void actualizarTelefono(String telefono,long id);

    @Query("UPDATE USUARIO SET tipo_usuario=:tipo WHERE id=:id")
    void actualizarTipo(String tipo,long id);

    @Query("UPDATE USUARIO SET asunto=:asunto WHERE id=:id")
    void actualizarAsunto(String asunto,long id);

    @Query("UPDATE USUARIO SET nombreC=:nombre,password=:nombre,correo=:correo,telefono=:telefono,tipo_usuario=:tipo WHERE id=:id")
    void actualizaUno(String nombre,String correo,String telefono,String tipo,long id);

    @Query("UPDATE USUARIO SET nombreC=:nombre,password=:nombre,correo=:correo,telefono=:telefono WHERE id=:id")
    void actualizaDos(String nombre,String correo,String telefono,long id);

    @Query("UPDATE USUARIO SET nombreC=:nombre,password=:nombre,correo=:correo WHERE id=:id")
    void actualizaTres(String nombre,String correo,long id);

    @Query("UPDATE USUARIO SET correo=:correo,telefono=:telefono,tipo_usuario=:tipo,asunto=:asunto WHERE id=:id")
    void actualizaCuatro(String correo,String telefono,String tipo,String asunto,long id);

    @Query("UPDATE USUARIO SET telefono=:telefono,tipo_usuario=:tipo,asunto=:asunto WHERE id=:id")
    void actualizaCinco(String telefono,String tipo,String asunto,long id);

    @Query("UPDATE USUARIO SET tipo_usuario=:tipo,asunto=:asunto WHERE id=:id")
    void actualizaSeis(String tipo,String asunto,long id);

    @Query("UPDATE USUARIO SET correo=:correo,asunto=:asunto WHERE id=:id")
    void actualizaSiete(String correo,String asunto,long id);

    @Query("UPDATE USUARIO SET telefono=:telefono,asunto=:asunto WHERE id=:id")
    void actualizaOcho(String telefono,String asunto,long id);

    @Query("UPDATE USUARIO " +
            "SET nombreC = CASE WHEN :nombre IS NOT NULL AND :nombre != '' THEN :nombre ELSE nombreC END, " +
            "password = CASE WHEN :nombre IS NOT NULL AND :nombre != '' THEN :nombre ELSE password END, " +
            "correo = CASE WHEN :correo IS NOT NULL AND :correo != '' THEN :correo ELSE correo END, " +
            "telefono = CASE WHEN :telefono IS NOT NULL AND :telefono != '' THEN :telefono ELSE telefono END, " +
            "tipo_usuario = CASE WHEN :tipo IS NOT NULL AND :tipo != '' THEN :tipo ELSE tipo_usuario END, " +
            "asunto = CASE WHEN :asunto IS NOT NULL AND :asunto != '' THEN :asunto ELSE asunto END " +
            "WHERE id = :id")
    void actualizarUser(String nombre,
                           String correo,
                           String telefono,
                           String tipo,
                           String asunto,
                           long id);









}
