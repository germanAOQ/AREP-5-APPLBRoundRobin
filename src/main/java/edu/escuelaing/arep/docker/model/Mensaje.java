package edu.escuelaing.arep.docker.model;

import java.util.Date;

/**
 * Esta clase permite mapear los mensajes obtenidos a partir de solicitudes post al API
 * 
 * @author German
 *
 */
public class Mensaje {

    private String descripcion;
    private String date;

    public Mensaje(String descripcion, String date) {
        this.descripcion = descripcion;
        this.date = date;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDate() {
        return date;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
