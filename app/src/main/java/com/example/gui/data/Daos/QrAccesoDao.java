package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.gui.data.Entities.Qr;
import com.example.gui.data.Relations.QrAccceso;

import java.util.List;

@Dao
public interface QrAccesoDao {
    @Transaction
    @Query("SELECT * FROM ACCESO WHERE id = id")
    public List<QrAccceso> getQrAcceso();

}
