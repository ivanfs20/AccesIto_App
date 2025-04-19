package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gui.data.Entities.Familiar;
import com.example.gui.data.Relations.AdministracionReporte;
import com.example.gui.data.Relations.FamiliarConUsuario;

import java.util.List;

@Dao
public interface FamiliarConUsuarioDao {
    @Transaction
    @Query("SELECT * FROM FAMILIAR WHERE id = id")
    public List<FamiliarConUsuario> getFamiliarConUsuario();
}
