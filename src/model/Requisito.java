package model;

public class Requisito {
    private int idRequisito;
    //Un requisito pertenece a un trámite
    //Mantiene una referencia al trámite asociado
    private Tramite tramite;
    private boolean certificadoMedico;
    private boolean pago;
    private boolean multas;
    private String observaciones;

    public Requisito() {}

    //getters y setters
    public int getIdRequisito() {
        return idRequisito;
    }

    public void setIdRequisito(int idRequisito) {
        this.idRequisito = idRequisito;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public boolean isCertificadoMedico() {
        return certificadoMedico;
    }

    public void setCertificadoMedico(boolean certificadoMedico) {
        this.certificadoMedico = certificadoMedico;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isMultas() {
        return multas;
    }

    public void setMultas(boolean multas) {
        this.multas = multas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
