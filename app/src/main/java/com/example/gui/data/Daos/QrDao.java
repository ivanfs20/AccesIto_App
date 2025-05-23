package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Qr;

import java.util.List;
@Dao
public interface QrDao {
    @Insert
    void insert(Qr qr);
    @Update
    void update(Qr qr);
    @Delete
    void delete(Qr qr);
    @Query("SELECT * FROM QR")
    List<Qr> AllQr();

    @Query("SELECT * FROM QR WHERE idUsuario = :idUsuario LIMIT 1")
    Qr getQrByUsuarioId(Long idUsuario);

    @Query("SELECT * FROM QR WHERE id= :idQr LIMIT 1")
    Qr getQrById(Long idQr);

}
