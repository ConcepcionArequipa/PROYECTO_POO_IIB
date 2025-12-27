package model;

import java.time.LocalDate;
public class Examen {
    private int idExamen;
    private int tramiteId;
    private double notaTeorica;
    private double notaPractica;
    private LocalDate fechaExamen;

    public Examen() {}

    //getters y setters


    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public int getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(int tramiteId) {
        this.tramiteId = tramiteId;
    }

    public double getNotaTeorica() {
        return notaTeorica;
    }

    public void setNotaTeorica(double notaTeorica) {
        this.notaTeorica = notaTeorica;
    }

    public double getNotaPractica() {
        return notaPractica;
    }

    public void setNotaPractica(double notaPractica) {
        this.notaPractica = notaPractica;
    }

    public LocalDate getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(LocalDate fechaExamen) {
        this.fechaExamen = fechaExamen;
    }
}
