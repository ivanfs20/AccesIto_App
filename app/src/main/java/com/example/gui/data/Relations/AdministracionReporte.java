package com.example.gui.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gui.data.Entities.Administracion;
import com.example.gui.data.Entities.Reporte;

import java.util.List;

public class AdministracionReporte {
    @Embedded
    public Administracion administracion;
    @Relation(parentColumn = "id",entityColumn = "idAdministracion",entity = Reporte.class)
    public List<Reporte> listReporte;
}
