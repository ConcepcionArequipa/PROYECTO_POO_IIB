package dao;

import model.Tramite;
import java.sql.*;

public class TramiteDao {

    public boolean crear(int solicitanteId){

        String sql = "insert into tramite (solicitante_id, estado, fecha_creacion) " +
                      "values (?, 'pendiente', curdate())";
        try{
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,solicitanteId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.printf("Error : "+ e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int tramiteId , String estado){
        String sql = "update tramite set estado = ? where id =?";

        try{
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,estado);
            ps.setInt(2,tramiteId);

            return ps.executeUpdate() > 0;

        }catch (Exception e) {
            System.out.printf("Error : "+ e.getMessage());
            return false;
        }
    }


}
