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
    @ColumnInfo(name = "primer_nombre")
    private String nombre1;
    //Segundo nombre
    @ColumnInfo(name = "segundo_nombre")
    private String nombre2;
    //Apellido Paterno
    @ColumnInfo(name = "apellido_paterno")
    private String apellidoPaterno;
    //Apellido Materno
    @ColumnInfo(name = "apellido_materno")
    private String apellidoMaterno;
    //Estatus del usuario (activo o inactivo)
    @ColumnInfo(name = "estatus")
    private boolean estatus;
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

    public Usuario() {
    }

    public Usuario(String nombre1, String nombre2, String apellidoPaterno, String apellidoMaterno, boolean estatus, String contraseña, String telefono, byte[] foto, String tipo_usuario) {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.estatus = estatus;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.foto = foto;
        this.tipo_usuario = tipo_usuario;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
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

    public String getNombre1() {
        return nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre1='" + nombre1 + '\'' +
                ", nombre2='" + nombre2 + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", estatus=" + estatus +
                ", contraseña='" + contraseña + '\'' +
                ", telefono='" + telefono + '\'' +
                ", foto=" + Arrays.toString(foto) +
                ", tipo_usuario='" + tipo_usuario + '\'' +
                '}';
    }
}
