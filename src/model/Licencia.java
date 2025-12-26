package model;

import java.time.LocalDate; //Para las fechas

public class Licencia {
    private int idLicencia;
    private Tramite tramite;
    private String numeroLicencia;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    public Licencia() {
        this.fechaEmision=LocalDate.now(); //Se crea automaticamente
        //plusYears para sumar años a una fecha
        this.fechaVencimiento=fechaEmision.plusYears(5); //La licencia vence en 5 años
    }

    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
