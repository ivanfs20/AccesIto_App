package com.example.gui.data.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gui.data.Entities.Acceso;
import com.example.gui.data.Entities.Qr;

import java.util.List;

public class QrAccceso {
    //OneToMany
    @Embedded
    Qr qr;

    @Relation(parentColumn = "id", entityColumn = "idQr", entity = Acceso.class)
    List<Acceso> listAcceso;
}
