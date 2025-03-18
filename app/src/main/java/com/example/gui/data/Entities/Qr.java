package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "QR")
public class Qr {
    //Id del Qr
    @PrimaryKey(autoGenerate = true)
    private Long id;
    //Foto - Codigo - Qr
    @ColumnInfo(name = "codigo")
    private byte[] codigo;
    @ColumnInfo(name = "fecha_creacion")
    //Fecha de creacion del Qr
    private String fecha;
    @ColumnInfo(name = "estado")
    //Estado del Qr (Expirado, Denegado o Activo)
    private String estado;

    public Qr() {
    }

    public Qr(byte[] codigo, String fecha, String estado) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.estado = estado;
    }

    public void setCodigo(byte[] codigo) {
        this.codigo = codigo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public byte[] getCodigo() {
        return codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Qr{" +
                "id=" + id +
                ", codigo=" + Arrays.toString(codigo) +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
