package dao;

import java.sql.*;
import javax.swing.JLabel;

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

    public void cargarConteosAutomaticos(JLabel pend, JLabel exam, JLabel aprob, JLabel reprob, JLabel emit) {
        // Reiniciamos a 0 por si la consulta no devuelve algún estado
        pend.setText("Pendientes: 0");
        exam.setText("En examen: 0");
        aprob.setText("Aprobadas: 0");
        reprob.setText("Reprobadas: 0");
        emit.setText("Emitidas: 0");

        String sql = "SELECT estado, COUNT(*) as total FROM tramite GROUP BY estado";

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estado = rs.getString("estado");
                int total = rs.getInt("total");

                // Asignación directa según el nombre del estado
                switch (estado.toUpperCase()) {
                    case "PENDIENTE" -> pend.setText("Pendientes: " + total);
                    case "EXAMENES"  -> exam.setText("En examen: " + total);
                    case "APROBADO"  -> aprob.setText("Aprobadas: " + total);
                    case "REPROBADO" -> reprob.setText("Reprobadas: " + total);
                    case "EMITIDA"   -> emit.setText("Emitidas: " + total);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al contar estados: " + e.getMessage());
        }
    }

}
