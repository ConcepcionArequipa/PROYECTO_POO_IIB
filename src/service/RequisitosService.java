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

            // Guardar requisitos + observaciones
            boolean ok = requisitosDao.actualizar(r, con);
            if (!ok) {
                throw new Exception("No se pudo guardar los requisitos");
            }

            // Regla de negocio

            if (r.isCertificadoMedico() && r.isPago() && r.isMultas()) {
                boolean estadoOk= tramiteDao.actualizarEstado(r.getTramiteId(),"EN_EXAMENES",con);

                if (!estadoOk) {
                    throw new Exception("No se pudo actualizar el estado del tramite");
                }
            }



            con.commit(); //Commit final

        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

}