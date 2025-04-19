package com.example.gui.data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
@Entity(tableName = "USUARIO")
public class Usuario {
    //Id del usuario
    @PrimaryKey(autoGenerate = true)
    private Long id;
    //Primer nombre
    @ColumnInfo(name = "nombreC")
    private String nombreC;
    /*
        //Segundo nombre
    @ColumnInfo(name = "segundo_nombre")
    private String nombre2;
    //Apellido Paterno
    @ColumnInfo(name = "apellido_paterno")
    private String apellidoPaterno;
    //Apellido Materno
    @ColumnInfo(name = "apellido_materno")
    private String apellidoMaterno;
    * */
    //Estatus del usuario (activo o inactivo)
    @ColumnInfo(name = "estatus")
    private boolean estatus;

    @ColumnInfo(name = "correo")
    private String correo;
    //Contraseña del usuario
    @ColumnInfo(name = "password")
    private String contraseña;
    //Telefono de contacto
    @ColumnInfo(name = "telefono")
    private String telefono;
    //Foto en bytes, para almacenar imagenes
    @ColumnInfo(name = "foto_bytes")
    private byte[] foto;
    //El tipo de usuario (alumno, docente, seguridad, administrador, empleadoadm, otrosempleados, familiares, invitados)
    @ColumnInfo(name = "tipo_usuario")
    private String tipo_usuario;

    @ColumnInfo(name = "asunto")
    private String asunto;

    public Usuario() {
    }

    public Usuario(String nombreC, boolean estatus, String correo, String contraseña, String telefono, byte[] foto, String tipo_usuario) {
        this.nombreC = nombreC;
        this.estatus = estatus;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.foto = foto;
        this.tipo_usuario = tipo_usuario;
    }

    public Usuario(String nombreC, boolean estatus, String correo, String contraseña, String telefono, byte[] foto, String tipo_usuario, String asunto) {
        this.nombreC = nombreC;
        this.estatus = estatus;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.foto = foto;
        this.tipo_usuario = tipo_usuario;
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public boolean isEstatus() {
        return estatus;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreC='" + nombreC + '\'' +
                ", estatus=" + estatus +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", telefono='" + telefono + '\'' +
                ", foto=" + Arrays.toString(foto) +
                ", tipo_usuario='" + tipo_usuario + '\'' +
                ", asunto='" + asunto + '\'' +
                '}';
    }
}
