package com.example.gui.data.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gui.data.Daos.AccesoDao;
import com.example.gui.data.Daos.AdministracionDao;
import com.example.gui.data.Daos.FamiliarDao;
import com.example.gui.data.Daos.QrDao;
import com.example.gui.data.Daos.ReporteDao;
import com.example.gui.data.Daos.UsuarioDao;
import com.example.gui.data.Entities.Acceso;
import com.example.gui.data.Entities.Administracion;
import com.example.gui.data.Entities.Familiar;
import com.example.gui.data.Entities.Qr;
import com.example.gui.data.Entities.Reporte;
import com.example.gui.data.Entities.Usuario;

@Database(entities = {Usuario.class, Acceso.class, Qr.class, Reporte.class, Administracion.class, Familiar.class},version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract AccesoDao accesoDao();
    public abstract QrDao qrDao();
    public abstract ReporteDao reporteDao();
    public abstract UsuarioDao usuarioDao();
    public abstract AdministracionDao administracionDao();
    public abstract FamiliarDao familiarDao();
}
