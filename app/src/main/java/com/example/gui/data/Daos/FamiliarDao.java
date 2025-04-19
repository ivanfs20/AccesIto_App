package com.example.gui.data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gui.data.Entities.Familiar;
import com.example.gui.data.Entities.Usuario;

import java.util.List;

@Dao
public interface FamiliarDao {
    @Insert
    void insert(Familiar familiar);

    @Update
    void update(Familiar familiar);

    @Delete
    void delete(Familiar familiar);

    @Query("SELECT * FROM FAMILIAR")
    List<Familiar> AllFamiliar();


}
