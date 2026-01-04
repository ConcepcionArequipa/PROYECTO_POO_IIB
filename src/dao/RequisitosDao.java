package dao;

import model.Requisito;
import java.sql.*;

public class RequisitosDao {

    public boolean crear(int tramiteId){
        String sql = "insert into requisitos(tramite_id) values(?)";

        //Try-with-resources para cerrar los recursos automaticamente
        try(
                Connection com = new Conexion().getConexion();
                PreparedStatement ps = com.prepareStatement(sql)
        ) {
            ps.setInt(1,tramiteId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error al crear requisito: " + e.getMessage());
            return false;

        }
    }



    //Sobrecarga para transacciones, requerido para las reglas de negocio

    public boolean actualizar (Requisito r, Connection con){

        String sql = """
                update requisitos set
                certificado_medico = ?,
                pago = ?,
                multas =?,
                observaciones = ?
                where tramite_id =?
                """;

        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setBoolean(1,r.isCertificadoMedico());
            ps.setBoolean(2,r.isPago());
            ps.setBoolean(3,r.isMultas());
            ps.setString(4,r.getObservaciones());
            ps.setInt(5,r.getTramiteId());

            return ps.executeUpdate() >0;

        } catch (SQLException e) {
            //Lanzamos la excepcion para que Service pueda hacer Rollback
            throw new RuntimeException("Error al actualizar requisitos en transacción",e);
        }

    }

    //Metodo simple, Reutiliza la logica del metodo anterior

    public boolean actualizar (Requisito r){

        try(
                Connection con = new Conexion().getConexion();

        ) {
            //No se inicia la transaccion aqui
            return actualizar(r,con);

        } catch (RuntimeException e) {
            //Captura el error lanzado por el metodo transaccional
            System.err.println("Error logico: " + e.getMessage());
            return false;
        }catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            return false;
        }catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }

    }
}
