package service;

import model.Examen;
import dao.ExamenDao;
import dao.TramiteDao;
import dao.Conexion;

import java.sql.Connection;

public class ExamenService {

    public void registrarExamen(Examen examen) throws Exception {
        if (examen == null) {
            throw new Exception("Examen invalido");
        }

        // Validar ID del trámite
        if (examen.getTramiteId() <= 0) {
            throw new Exception("ID de trámite inválido");
        }

        // Validar rango de notas
        if (examen.getNotaTeorica() < 0 || examen.getNotaTeorica() > 20 || examen.getNotaPractica() < 0 || examen.getNotaPractica() > 20) {
            throw new Exception("La nota debe estar entre 0 y 20");
        }

        Connection con=null;

        try {
            con=new Conexion().getConexion();
            con.setAutoCommit(false);

            ExamenDao examenDao=new ExamenDao();
            TramiteDao tramiteDao=new TramiteDao();

            //Obtener el estado actual

            String estadoActual= tramiteDao.obtenerEstado(examen.getTramiteId(),con);

            if(estadoActual==null){
                throw new Exception("Tramite no encontrado");
            }

            if (!"EXAMENES".equalsIgnoreCase(estadoActual)) {
                throw new Exception("El tramite no se encuentra en este estado");
            }

            //Registrar la nota del examen

            boolean registroExamen= examenDao.registrar(examen,con);

            if (!registroExamen) {
                throw new Exception("No se pudo registrar el examen");
            }

            //Regla de negocio

            String nuevoEstado=(examen.getNotaTeorica() >= 14 && examen.getNotaPractica() >= 14)
                    ? "APROBADO"
                    : "REPROBADO";

            //CAMBIAR EL ESTADO DEL TRAMITE
            boolean okEstado= tramiteDao.actualizarEstado(examen.getTramiteId(),nuevoEstado,con);

            if (!okEstado) {
                throw new Exception("No se pudo actualizar el estado del tramite");
            }

            con.commit();

        }catch(Exception e){
            if(con!=null){
                con.rollback();
                throw e;
            }
        }finally{
            if(con!=null){
                con.close();
            }
        }
    }
}
