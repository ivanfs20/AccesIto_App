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

    @Query("SELECT * FROM USUARIO  WHERE password =:password")
    Usuario getUser(String password);

    @Query("UPDATE USUARIO SET nombreC=:usuario, asunto=:asun, foto_bytes=:photo WHERE nombreC=:usuario and password =:password")
    void setData(String usuario, String asun, byte[] photo, String password);

    @Query("SELECT * FROM USUARIO WHERE id = :idUsuario LIMIT 1")
    Usuario getUsuarioById(Long idUsuario);

    @Query ("SELECT nombreC FROM USUARIO WHERE id =:idAdmin")
    String getNombreAdministrador(Long idAdmin);

    @Query("SELECT * FROM USUARIO WHERE tipo_usuario='familiar' AND estatus=0")
    List<Usuario> getSolicitudFamiliar();


    @Query("SELECT * FROM USUARIO WHERE tipo_usuario='visitante' AND estatus=0")
    List<Usuario> getSolicitudVisitante();


    @Query("SELECT * FROM usuario WHERE nombreC = :nombre AND password = :contraseña LIMIT 1")
    Usuario getUsuario(String nombre, String contraseña);

}
