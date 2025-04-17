package com.example.gui.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gui.data.Entities.Administracion;
import com.example.gui.data.Entities.Usuario;

import java.util.List;

public class UsuarioAdministracion {
    @Embedded
    public Usuario usuario;
    @Relation(parentColumn = "id",entityColumn = "idUsuario",entity = Administracion.class)
    public List<Administracion> listAdm;
}
