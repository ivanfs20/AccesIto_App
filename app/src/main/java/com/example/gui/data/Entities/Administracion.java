package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ADMINISTRACION")
public class Administracion {
    //Id de administracion
    @PrimaryKey
    private Long id;
    //Fecha de inicio de ser de administracion
    @ColumnInfo(name = "fecha_inicio")
    private String fecha_inicio;
    //Fecha de final de ser administracion
    @ColumnInfo(name = "fecha_final")
    private String fecha_final;

    //Id del usuario que administrara la generacion de reportes
    @ColumnInfo(name = "idUsuario")
    private Long idUsuario;
    public Administracion() {
    }

    public Administracion(String fecha_inicio, String fecha_final, Long idUsuario) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    @Override
    public String toString() {
        return "Administracion{" +
                "id=" + id +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_final='" + fecha_final + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
