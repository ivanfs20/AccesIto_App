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
    @ColumnInfo(name = "fecha_salida")
    private String fechaSalida;
    //Id del Qr del acceso (usuario)
    @ColumnInfo(name = "idQr")
    private Long idQr;
    public Acceso() {
    }

    public Acceso(String fechaEntrada, String fechaSalida, Long idQr) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.idQr = idQr;
    }

    public Long getIdQr() {
        return idQr;
    }

    public void setIdQr(Long idQr) {
        this.idQr = idQr;
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
                ", idQr=" + idQr +
                '}';
    }
}
