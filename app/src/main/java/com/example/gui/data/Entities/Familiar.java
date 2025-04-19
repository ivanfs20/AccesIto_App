package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FAMILIAR")
public class Familiar {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "idUsuario")
    private Long idUsuario;

    @ColumnInfo(name = "idUsuarioFamiliarDe")
    private Long idUsuarioFamiliarDe;

    @ColumnInfo(name = "parentesco")
    private String parentesco;

    public Familiar() {
    }

    public Familiar(Long idUsuario, Long idUsuarioFamiliarDe, String parentesco) {
        this.idUsuario = idUsuario;
        this.idUsuarioFamiliarDe = idUsuarioFamiliarDe;
        this.parentesco = parentesco;
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

    public Long getIdUsuarioFamiliarDe() {
        return idUsuarioFamiliarDe;
    }

    public void setIdUsuarioFamiliarDe(Long idUsuarioFamiliarDe) {
        this.idUsuarioFamiliarDe = idUsuarioFamiliarDe;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return "Familiar{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idUsuarioFamiliarDe=" + idUsuarioFamiliarDe +
                ", parentesco='" + parentesco + '\'' +
                '}';
    }
}
