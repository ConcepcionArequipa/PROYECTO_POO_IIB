package model;

import java.time.LocalDate; //Para las fechas
public class Tramite {
    private int idTramite;
    //Cada trámite pertenece a un solo solicitante
    private Solicitante solicitante;
    private String estado; //pendiente, en examenes, aprobado, etc..
    private LocalDate fechaCreacion; //fecha del tramite

    public Tramite() {
    }

    //getters y setters


    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
