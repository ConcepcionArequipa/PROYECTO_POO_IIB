package service;

import dao.Conexion;
import dao.RequisitosDao;
import dao.TramiteDao;
import model.Requisito;
import java.sql.Connection;

public class RequisitoService {

    private RequisitosDao requisitosDao = new RequisitosDao();
    private TramiteDao tramiteDao = new TramiteDao();

    public void validarRequisito(Requisito requisito) throws Exception {

        //Validacion previa

        if (requisito==null || requisito.getTramiteId()<= 0){
            throw new Exception("Error: El id del tramite no es valido");
        }

        //Reglas de negocio

        boolean requisitosCompletos= requisito.isCertificadoMedico()&&requisito.isPago()&&!requisito.isMultas();

        Connection con=null;

        try {
            con=new Conexion().getConexion();
            con.setAutoCommit(false); //Inicio de transaccion atomica

            //Se guarda los checks de los requisitos

            boolean okRequisitos= requisitosDao.actualizar(requisito,con);

            //Si los requisitos estan completos, se cambia el estado

            boolean okEstado= true;

            if (requisitosCompletos){
                //Si esta ok, el estado= en_examenes
                okEstado= tramiteDao.actualizarEstado(requisito.getTramiteId(),"en_examenes",con);
            }

            if (okRequisitos && okEstado){
                con.commit(); //Se guardan ambos cambios
            }
            else {
                con.rollback();
                throw new Exception("No se pudo actualizar el trámite. Verifique la conexión");
            }
        }catch(Exception e){
            if(con!=null){

            }
        }

    }



}
