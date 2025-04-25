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
    long insert(Reporte reporte);
    @Update
    void update(Reporte reporte);
    @Delete
    void delete(Reporte reporte);
    @Query("SELECT * FROM REPORTE")
    List<Reporte> AllReporte();
   // @Query("SELECT id,nombre,descripcion,fecha_creacion,documento,idAdministracion FROM REPORTE")
    //List <Reporte> AllReporte();

    //Lo agregue para poder descargar el pdf
    @Query("SELECT documento FROM REPORTE WHERE id = :reporteId")
    byte[] getPdfByReporteId(Long reporteId);

    //para agregarle el documento al reporte
    @Query("UPDATE REPORTE SET documento = :documento WHERE id = :reporteId")
    void updateReporte (Long reporteId,byte[] documento);
}
