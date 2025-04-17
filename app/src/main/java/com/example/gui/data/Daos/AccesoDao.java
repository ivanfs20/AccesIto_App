package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Acceso;

import java.util.List;
@Dao
public interface AccesoDao{
    @Insert
    void insert(Acceso acceso);
    @Update
    void update(Acceso acceso);
    @Delete
    void delete(Acceso acceso);
    @Query("SELECT * FROM ACCESO")
    List<Acceso> AllAcceso();
}
