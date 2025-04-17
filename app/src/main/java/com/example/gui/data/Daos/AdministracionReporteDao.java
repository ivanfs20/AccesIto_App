package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gui.data.Relations.AdministracionReporte;

import java.util.List;

@Dao
public interface AdministracionReporteDao {
    @Transaction
    @Query("SELECT * FROM REPORTE WHERE id = id")
    public List<AdministracionReporte> getAdministracionReporte();
}
