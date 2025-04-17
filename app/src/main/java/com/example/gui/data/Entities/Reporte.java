package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
@Entity(tableName = "REPORTE")
public class Reporte {
    //Id
    @PrimaryKey(autoGenerate = true)
    private Long id;
    //Nombre
    @ColumnInfo(name = "nombre")
    private String nombre;
    //Descripcion
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    //Fecha de creacion del documento
    @ColumnInfo(name = "fecha_creacion")
    private String fecha_creacion;
    //PDF en Bytes
    @ColumnInfo(name = "documento")
    private byte[] documento;
    //Id del administraccion del usuario (tabla intermedia entre usuario-reporte)
    @ColumnInfo(name = "idAdministracion")
    private Long idAdministracion;

    public Reporte() {
    }

    public Reporte(String nombre, String descripcion, String fecha_creacion, byte[] documento, Long idAdministracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_creacion = fecha_creacion;
        this.documento = documento;
        this.idAdministracion = idAdministracion;
    }

    public Long getIdAdministracion() {
        return idAdministracion;
    }

    public void setIdAdministracion(Long idAdministracion) {
        this.idAdministracion = idAdministracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha_creacion='" + fecha_creacion + '\'' +
                ", documento=" + Arrays.toString(documento) +
                ", idAdministracion=" + idAdministracion +
                '}';
    }
}
