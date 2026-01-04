package dao;

import java.sql.*;

import model.Reporte;
import java.util.ArrayList;
import java.util.List;


public class ReporteDao {


    public List<Object[]> llenarTabla() {
        List<Object[]> lista = new ArrayList<>();

        String sql = """
        SELECT t.id, s.cedula, s.nombre, s.tipo_licencia, t.fecha_creacion, t.estado
        FROM tramite t
        JOIN solicitante s ON s.id = t.solicitante_id
    """;

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"), rs.getString("cedula"), rs.getString("nombre"),
                        rs.getString("tipo_licencia"), rs.getDate("fecha_creacion"), rs.getString("estado")
                });
            }
        } catch (Exception e) {
            System.out.println("Error en filtro avanzado: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> filtrarFlexible(
            String estado,
            String tipo,
            String cedula,
            Date desde,
            Date hasta
    ) {

        List<Object[]> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT t.id, s.cedula, s.nombre, s.tipo_licencia, t.fecha_creacion, t.estado
        FROM tramite t
        JOIN solicitante s ON s.id = t.solicitante_id
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (!estado.equals("TODOS")) {
            sql.append(" AND t.estado = ?");
            params.add(estado);
        }

        if (!tipo.equals("TODOS")) {
            sql.append(" AND s.tipo_licencia = ?");
            params.add(tipo);
        }

        if (!cedula.isEmpty()) {
            sql.append(" AND s.cedula LIKE ?");
            params.add("%" + cedula + "%");
        }

        if (desde != null && hasta != null) {
            sql.append(" AND t.fecha_creacion BETWEEN ? AND ?");
            params.add(desde);
            params.add(hasta);
        }

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("tipo_licencia"),
                        rs.getDate("fecha_creacion"),
                        rs.getString("estado")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}
