package dao;

import java.sql.*;

import model.Reporte;
import java.util.ArrayList;
import java.util.List;


public class ReporteDao {

    public List<Object[]> filtrarReporteCompleto(String estado, String tipo, String cedula, java.sql.Date fechaInicio, java.sql.Date fechaFin) {
        List<Object[]> lista = new ArrayList<>();

        String sql = """
        SELECT t.id, s.cedula, s.nombre, s.tipo_licencia, t.fecha_creacion, t.estado
        FROM tramite t
        JOIN solicitante s ON s.id = t.solicitante_id
        WHERE (t.estado = ? OR ? = 'TODOS')
          AND (s.tipo_licencia = ? OR ? = 'TODOS')
          AND (s.cedula LIKE ?)
          AND (t.fecha_creacion BETWEEN ? AND ?)
    """;

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, estado);
            ps.setString(3, tipo);
            ps.setString(4, tipo);
            ps.setString(5, "%" + cedula + "%");
            ps.setDate(6, fechaInicio);
            ps.setDate(7, fechaFin);

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

}
