package service;
import dao.Conexion;
import dao.TramiteDao;
import dao.LicenciaDao;
import model.Licencia;
import model.Usuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class TramiteService {
    private TramiteDao tramiteDao = new TramiteDao();

    //Crear tramite inicial

    public void crearTramiteInicial(int solicitanteId, Usuario usuario) throws Exception {
        if (solicitanteId <= 0) {
            throw new Exception("ID de solicitante invalido");
        }

        boolean ok = tramiteDao.crear(solicitanteId, usuario.getIdUsuario());
        if(!ok) {
            throw new Exception("No se pudo crear el tramite inicial");
        }
    }

    //Validar requisitos

    public void validarRequisitos(int tramiteId, boolean certificadoMedico, boolean pagoRealizado, boolean multasPagadas) throws Exception {
        if (tramiteId <=0) {
            throw new Exception("ID de tramite invalido");
        }

        //Consultar el estado actual

        String estadoActual= tramiteDao.obtenerEstado(tramiteId);

        if (estadoActual == null) {
            throw new Exception("Trámite no encontrado");
        }

        // Solo se permite si esta en estado = pendiente

        if (!estadoActual.equalsIgnoreCase("pendiente")) {
            throw new Exception("Los requisitos solo se pueden validar cuando el trámite está en estado pendiente");
        }

        //Validar los requisitos obligatorios

        if (!certificadoMedico || !pagoRealizado || !multasPagadas) {
            throw new Exception("No se puede avanzar: faltan requisitos obligatorios");
        }

        //Si cumple con los requisitos, cambia a estado en examenes

        boolean ok= tramiteDao.actualizarEstado(tramiteId,"EN_EXAMENES");
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

        String estadoActual= tramiteDao.obtenerEstado(tramiteId);

        if (estadoActual == null) {
            throw new Exception("Tramite no encontrado");
        }

        if (!estadoActual.equalsIgnoreCase("en_examenes")) {
            throw new Exception("El examen solo puede procesarse cuando el trámite está en estado en_examenes");
        }


        // Validar rango de notas
        if (notaTeorica < 0 || notaTeorica > 20 || notaPractica < 0 || notaPractica > 20) {
            throw new Exception("La nota debe estar entre 0 y 20");
        }

        // Regla de negocio: nota minima de 14 para aprobar
        String nuevoEstado;
        if (notaTeorica >= 14 && notaPractica >= 14) {
            nuevoEstado = "APROBADO";
        } else {
            nuevoEstado = "REPROBADO";
        }

        // Actualizar estado del trámite
        boolean ok = tramiteDao.actualizarEstado(tramiteId, nuevoEstado);

        if (!ok) {
            throw new Exception("No se pudo actualizar el estado del trámite");
        }
    }

    // Metodo para generar el numero de licencia

    private String generarNumLicencia(){
        return "LIC-"+ System.currentTimeMillis();
    }
    //Generar licencia solo si el estado del tramite es aprobado

    public void generarLicencia(int tramiteId) throws Exception {

        if (tramiteId <= 0) {
            throw new Exception("ID de tramite invalido");
        }
        Connection con= null;
        try{
            con= new Conexion().getConexion();
            con.setAutoCommit(false); //Inicia transaccion

            //Validaciones

            //Consultar el estado actual del tramite
            String estadoActual= tramiteDao.obtenerEstado(tramiteId,con);

            if (estadoActual == null) {
                throw new Exception("Tramite no encontrado");
            }

            if (!"aprobado".equalsIgnoreCase(estadoActual)) {
                throw new Exception("Tramite no aprobado");
            }

            //Emitir licencia
            LicenciaDao licenciaDao = new LicenciaDao();

            if (licenciaDao.existeLicencia(tramiteId,con)) {
                throw new Exception("Licencia duplicada");
            }

            //Crear el objeto Licencia

            Licencia licencia = new Licencia();
            licencia.setTramiteId(tramiteId);
            licencia.setNumeroLicencia(generarNumLicencia());
            licencia.setFechaVencimiento(LocalDate.now().plusYears(5));

            boolean licenciaExitosa=licenciaDao.emitir(licencia,con);
            if(!licenciaExitosa) {
                throw new Exception("Error al emitir la licencia");
            }

            //Actualizar el estado del tramite

            boolean okEstado=tramiteDao.actualizarEstado(tramiteId,"LICENCIA_EMITIDA",con);

            if(!okEstado) {
                throw new Exception("La licencia se emitió, pero no se pudo actualizar el estado del tramite");
            }

            con.commit(); //Se realizo con exito
        }

        catch (Exception e){
            if(con != null) {
                con.rollback(); //Deshace
            }
            throw e;

        } finally {
            if(con != null) {
                con.close();
            }
        }
    }


    //Listar tramites por estado

    public List<Object[]> listarPorEstado(String estado) throws Exception {
        if (!estadoValido(estado)) {
            throw new Exception("Estado inválido para búsqueda");

        }
        return tramiteDao.listarPorEstado(estado);
    }

    //Validacion de estados permitidos

    private boolean estadoValido(String estado) {
        return estado != null && (estado.equalsIgnoreCase("pendiente") || estado.equalsIgnoreCase("en_examenes") || estado.equalsIgnoreCase("aprobado") || estado.equalsIgnoreCase("reprobado")|| estado.equalsIgnoreCase("licencia_emitida"));
    }


}
