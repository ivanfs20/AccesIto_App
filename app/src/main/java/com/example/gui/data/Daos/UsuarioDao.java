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


}
