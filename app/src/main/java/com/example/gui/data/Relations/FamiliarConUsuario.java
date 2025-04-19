package com.example.gui.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gui.data.Entities.Familiar;
import com.example.gui.data.Entities.Reporte;
import com.example.gui.data.Entities.Usuario;

import java.util.List;

public class FamiliarConUsuario {

            @Embedded
            public Usuario usuario;

    @Relation(parentColumn = "id",entityColumn = "idUsuario",entity = UsuarioQr.class)
    public List<Familiar> listFamiliar;

}
