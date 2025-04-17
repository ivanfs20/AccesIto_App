package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Reporte;

import java.util.List;
@Dao
public interface ReporteDao {
    @Insert
    void insert(Reporte reporte);
    @Update
    void update(Reporte reporte);
    @Delete
    void delete(Reporte reporte);
    @Query("SELECT * FROM REPORTE")
    List<Reporte> AllReporte();
}
