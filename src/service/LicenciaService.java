package service;

import model.Licencia;
import dao.Conexion;
import dao.LicenciaDao;
import dao.TramiteDao;
import model.Usuario;

import java.sql.Connection;

public class LicenciaService {

    public void emitirLicencia(Licencia l, Usuario usuario) throws Exception {

        if (l==null) {
            throw new Exception("La licencia no puede ser nula");
        }

        if (l.getTramiteId()<= 0){
            throw new Exception("ID de tramite invalido");
        }

        if (l.getNumeroLicencia()==null || l.getNumeroLicencia().isEmpty()){
            throw new Exception("El numero de licencia es obligatorio");
        }

        l.setCreatedBy(usuario.getIdUsuario());
        l.setCreatedAt(java.time.LocalDateTime.now());

        Connection con = null;

        try {
            con = new Conexion().getConexion();
            con.setAutoCommit(false);

            LicenciaDao licenciaDao = new LicenciaDao();
            TramiteDao tramiteDao = new TramiteDao();

            String estado= tramiteDao.obtenerEstado(l.getTramiteId(),con);

            if(!"APROBADO".equalsIgnoreCase(estado)){
                throw new Exception("Solo se puede emitir licencia si el trámite está APROBADO");
            }

            if (licenciaDao.existeLicencia(l.getTramiteId(),con)) {
                throw new Exception("Este trámite ya tiene una licencia emitida");
            }

            if (!licenciaDao.emitir(l,con)) {
                throw new Exception("No se puedo emitir la licencia");
            }

            if (!tramiteDao.actualizarEstado(l.getTramiteId(),"EMITIDA",con)) {
                throw new Exception("No se puedo actualizar el estado del tramite");
            }

            con.commit();

        }catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        }finally {
            if (con != null) {
                con.close();
            }
        }


    }
}
