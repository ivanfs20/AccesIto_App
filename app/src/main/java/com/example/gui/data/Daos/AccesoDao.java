package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Acceso;
import com.example.gui.data.actions.AccesoConUsuario;

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
    @Query("SELECT * FROM ACCESO WHERE id= :reporteId")
    List<Acceso> getAccesosByReporteId(Long reporteId);

    //Consulta con inner join para obtener todos los accesos-Lima
    @Query("SELECT ACCESO.id, ACCESO.fecha_entrada AS fechaEntrada, ACCESO.fecha_salida AS fechaSalida, USUARIO.nombreC AS nombreUsuario " +
            "FROM ACCESO " +
            "INNER JOIN QR ON ACCESO.idQr = QR.id " +
            "INNER JOIN USUARIO ON QR.idUsuario = USUARIO.id " +
            " WHERE ACCESO.fecha_entrada LIKE :fechaPatron")
    List<AccesoConUsuario> getAccesosPorMes(String fechaPatron);
}
