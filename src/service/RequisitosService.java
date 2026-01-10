package service;
import model.Requisito;
import dao.RequisitosDao;
import dao.TramiteDao;
import dao.Conexion;
import java.sql.Connection;

public class RequisitosService {

    public void verificarRequisitos(Requisito r) throws Exception {

        if (r == null) {
            throw new Exception("Requisito inválido");
        }

        if (r.getTramiteId() <=0) {
            throw new Exception("ID de tramite invalido");
        }

        Connection con = null;

        try {
            con = new Conexion().getConexion();
            con.setAutoCommit(false);

            RequisitosDao requisitosDao = new RequisitosDao();
            TramiteDao tramiteDao = new TramiteDao();

            //Consultar el estado actual

            String estadoActual= tramiteDao.obtenerEstado(r.getTramiteId(),con);

            if (estadoActual==null) {
                throw new Exception("Tramite no encontrado");
            }
            //Validar si el tramite = PENDIENTE
            if (!"PENDIENTE".equalsIgnoreCase(estadoActual)) {
                throw new Exception("Solo se puede verificar los requisitos si el tramite esta en estado PENDIENTE");
            }

            // Regla de negocio, validacion

            if (!r.isCertificadoMedico() || !r.isPago() || !r.isMultas()) {
                throw new Exception("Para aprobar, todos los requisitos deben estar marcados");



            }

            // Guardar requisitos + observaciones
            boolean ok = requisitosDao.actualizar(r, con);
            if (!ok) {
                throw new Exception("No se pudo guardar los requisitos");
            }

            //Cambiar el estado
            boolean estadoOk= tramiteDao.actualizarEstado(r.getTramiteId(),"EXAMENES",con);

            if (!estadoOk) {
                throw new Exception("No se pudo actualizar el estado del tramite");
            }

            con.commit(); //Commit final

        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    public void rechazarRequisitos(Requisito r) throws Exception {
        if (r == null) {
            throw new Exception("Requisito inválido");
        }

        if (r.getTramiteId() <=0) {
            throw new Exception("ID de tramite invalido");
        }

        if (r.getObservaciones() == null || r.getObservaciones().trim().isEmpty()) {
            throw new Exception("Es obligatorio ingresar observaciones");
        }

        //Validacion de requisitos al momento de rechazar

        if (r.isCertificadoMedico() && r.isPago() && r.isMultas()) {
            throw new Exception("No se puede rechazar si todos los requisitos estan completos");
        }
        Connection con = null;
        try {
            con = new Conexion().getConexion();
            con.setAutoCommit(false);
            RequisitosDao requisitosDao = new RequisitosDao();
            TramiteDao tramiteDao = new TramiteDao();

            //Consultar el estado actual

            String estadoActual= tramiteDao.obtenerEstado(r.getTramiteId(),con);

            if (estadoActual==null) {
                throw new Exception("Tramite no encontrado");
            }
            //Validar si el tramite = PENDIENTE
            if (!"PENDIENTE".equalsIgnoreCase(estadoActual)) {
                throw new Exception("Solo se puede rechazar si el tramite esta en estado PENDIENTE");
            }

            // Guardar requisitos + observaciones
            boolean okRequisitos = requisitosDao.actualizar(r, con);
            if (!okRequisitos) {
                throw new Exception("No se pudo guardar las observaciones");
            }
            //No se cambia el estado
            con.commit();

        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        }finally {
            if (con != null) con.close();
        }


    }

}