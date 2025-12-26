package dao;

import model.Requisito;
import java.sql.*;

public class RequisitosDao {

    public boolean crear(int tramiteId){
        String sql = "insert into requisitos(tramite_id) values(?)";

        try{
            Connection com = new Conexion().getConexion();
            PreparedStatement ps = com.prepareStatement(sql);

            ps.setInt(1,tramiteId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.printf("Erro: "+e.getCause().getMessage());
            return false;

        }
    }


    public boolean actualizar (Requisito r){
        String sql = """
                update requisitos set
                certificado_medico = ?,
                pago = ?,
                multas =?,
                observaciones = ?
                where tramite_id =?
                """;

        try{
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1,r.isCertificadoMedico());
            ps.setBoolean(2,r.isPago());
            ps.setBoolean(3,r.isMultas());
            ps.setString(4,r.getObservaciones());
            ps.setInt(5,r.getTramiteId());

            return ps.executeUpdate() >0;

        } catch (Exception e) {
            System.out.printf("error "+e.getMessage());
            return false;
        }

    }

}
