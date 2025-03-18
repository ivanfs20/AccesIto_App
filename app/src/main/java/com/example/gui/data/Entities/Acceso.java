package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ACCESO")
public class Acceso {
    //Id del acceso
    @PrimaryKey(autoGenerate = true)
    private Long id;
    //Fecha de entrada por parte del usuario
    @ColumnInfo(name = "fecha_entrada")
    private String fechaEntrada;
    //Fecha de salida del usuario
    @ColumnInfo(name = "fehca_salida")
    private String fechaSalida;

    public Acceso() {
    }

    public Acceso(String fechaEntrada, String fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public String toString() {
        return "Acceso{" +
                "id=" + id +
                ", fechaEntrada='" + fechaEntrada + '\'' +
                ", fechaSalida='" + fechaSalida + '\'' +
                '}';
    }
}
