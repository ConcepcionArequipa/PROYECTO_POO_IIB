package dao;

import model.Tramite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TramiteDao {

    public boolean crear(int solicitanteId){

        String sql = "insert into tramite (solicitante_id, estado, fecha_creacion) " +
                      "values (?, 'pendiente', curdate())";
        try(
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1,solicitanteId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.printf("Error : "+ e.getMessage());
            return false;
        }
    }

    public boolean crear(int solicitanteId, Connection con){

        String sql = "insert into tramite (solicitante_id, estado, fecha_creacion) " +
                "values (?, 'pendiente', curdate())";
        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1,solicitanteId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.printf("Error : "+ e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstado(int tramiteId , String estado){
        String sql = "update tramite set estado = ? where id =?";

        try(
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1,estado);
            ps.setInt(2,tramiteId);
            return ps.executeUpdate() > 0;

        }catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado del tramite: ", e);

        }
    }



    public boolean actualizarEstado(int tramiteId , String estado, Connection con){
        String sql = "update tramite set estado = ? where id =?";

        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1,estado);
            ps.setInt(2,tramiteId);
            return ps.executeUpdate() > 0;

        }catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado del tramite: ", e);

        }
    }


    public List<Object[]> listarTodos() {
        List<Object[]> lista = new ArrayList<>();

        String sql = """
        SELECT 
            t.id,
            s.cedula,
            s.nombre,
            s.tipo_licencia,
            t.fecha_creacion,
            t.estado
        FROM tramite t
        JOIN solicitante s ON s.id = t.solicitante_id
        ORDER BY t.fecha_creacion DESC
    """;

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("estado")
                };
                lista.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error listar todos: " + e.getMessage());
        }

        return lista;
    }

    public List<Object[]> listarPorEstado(String estado) {
        List<Object[]> lista = new ArrayList<>();

        String sql = """
        SELECT 
            t.id,
            s.cedula,
            s.nombre,
            s.tipo_licencia,
            t.fecha_creacion,
            t.estado
        FROM tramite t
        JOIN solicitante s ON s.id = t.solicitante_id
        WHERE t.estado = ?
    """;

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("estado")
                };
                lista.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error listar por estado: " + e.getMessage());
        }

        return lista;
    }

    //Metodo para obtener el estado actual del tramite
    public String obtenerEstado(int tramiteId) {
        String sql= "SELECT estado FROM tramite WHERE id = ?";
        try(
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1,tramiteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return rs.getString("estado");
                }
            }



        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el estado del tramite: ",e);
        }
        return null;
    }

    //Metodo para obtener el estado actual del tramite
    public String obtenerEstado(int tramiteId, Connection con) {
        String sql= "SELECT estado FROM tramite WHERE id = ?";
        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1,tramiteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return rs.getString("estado");
                }
            }



        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el estado del tramite: ",e);
        }
        return null;
    }

}
