package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gui.data.Relations.UsuarioAdministracion;

import java.util.List;

@Dao
public interface UsuarioAdministracionDao {
    @Transaction
    @Query("SELECT * FROM ADMINISTRACION WHERE id = id")
    public List<UsuarioAdministracion> getUsuarioAdministracion();
}
