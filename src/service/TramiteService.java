package service;
import dao.TramiteDao;
import model.Tramite;

import java.util.List;

public class TramiteService {
    private TramiteDao tramiteDao = new TramiteDao();

    //Crear tramite inicial

    public void crearTramiteInicial(int solicitanteId) throws Exception {
        if (solicitanteId <= 0) {
            throw new Exception("ID de solicitante invalido");
        }

        boolean ok = tramiteDao.crear(solicitanteId);
        if(!ok) {
            throw new Exception("No se pudo crear el tramite inicial");
        }
    }

    //Validar requisitos

    public void validarRequisitos(int tramiteId, boolean certificadoMedico, boolean pagoRealizado, boolean multasPagadas) throws Exception {
        if (tramiteId <=0) {
            throw new Exception("ID de tramite invalido");
        }

        //Validar los requisitos obligatorios

        if (!certificadoMedico || !pagoRealizado || !multasPagadas) {
            throw new Exception("No se puede avanzar: faltan requisitos obligatorios");
        }

        //Si cumple con los requisitos, cambia a estado en examenes

        boolean ok= tramiteDao.actualizarEstado(tramiteId,"EXAMENES");
        if(!ok) {
            throw new Exception("No se pudo actualizar el estado del tramite");
        }
    }

    //Cambiar el estado del tramite

    public void cambiarEstado(int tramiteId, String nuevoEstado) throws Exception {
        if (tramiteId <= 0) {
            throw new Exception("ID de tramite invalido");
        }
        if (!estadoValido(nuevoEstado)) {
            throw new Exception("Estado no permitido");
        }

        boolean ok= tramiteDao.actualizarEstado(tramiteId, nuevoEstado);
        if(!ok) {
            throw new Exception("No se pudo actualizar el estado del tramite");
        }
    }

    //Para procesar la nota del examen

    public void procesarExamen(int tramiteId, double notaTeorica, double notaPractica) throws Exception {

        // Validar ID del trámite
        if (tramiteId <= 0) {
            throw new Exception("ID de trámite inválido");
        }

        // Validar rango de notas
        if (notaTeorica < 0 || notaTeorica > 20 || notaPractica < 0 || notaPractica > 20) {
            throw new Exception("La nota debe estar entre 0 y 20");
        }

        // Regla de negocio: nota minima de 14 para aprobar
        String nuevoEstado;
        if (notaTeorica >= 14 && notaPractica >= 14) {
            nuevoEstado = "aprobado";
        } else {
            nuevoEstado = "reprobado";
        }

        // Actualizar estado del trámite
        boolean ok = tramiteDao.actualizarEstado(tramiteId, nuevoEstado);

        if (!ok) {
            throw new Exception("No se pudo actualizar el estado del trámite");
        }
    }

    //Generar licencia solo si el estado del tramite es aprobado


    //Listar tramites por estado

    public List<Object[]> listarPorEstado(String estado) throws Exception {
        if (!estadoValido(estado)) {
            throw new Exception("Estado inválido para búsqueda");

        }
        return tramiteDao.listarPorEstado(estado);
    }

    //Validacion de estados permitidos

    private boolean estadoValido(String estado) {
        return estado != null && (estado.equalsIgnoreCase("pendiente") || estado.equalsIgnoreCase("aprobado") || estado.equalsIgnoreCase("rechazado"));
    }


}
