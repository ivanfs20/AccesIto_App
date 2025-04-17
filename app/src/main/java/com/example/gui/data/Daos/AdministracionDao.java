package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Acceso;
import com.example.gui.data.Entities.Administracion;

import java.util.List;

@Dao
public interface AdministracionDao {
    @Insert
    void insert(Administracion adm);
    @Update
    void update(Administracion adm);
    @Delete
    void delete(Administracion adm);
    @Query("SELECT * FROM ADMINISTRACION")
    List<Administracion> AllAdm();
}
