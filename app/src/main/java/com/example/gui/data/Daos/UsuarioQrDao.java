package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gui.data.Relations.UsuarioQr;

import java.util.List;

@Dao
public interface UsuarioQrDao {
    @Transaction
    @Query("SELECT * FROM QR WHERE id = id")
    public List<UsuarioQr> getUsuarioQr();
}
