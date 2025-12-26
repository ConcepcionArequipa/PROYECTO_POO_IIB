package dao;

import model.Solicitante;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class SolicitanteDao {

    public boolean insertar(Solicitante s) {

        String sql = "insert into solicitante "
                + "(cedula, nombre, fecha_nacimiento, tipo_licencia, fecha_solicitud) "
                + "values (?, ?, ?, ?, curdate())";

        try {
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, s.getCedula());
            ps.setString(2, s.getNombre());
            ps.setDate(3, s.getFechaNacimiento());
            ps.setString(4, s.getTipoLicencia());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.printf("Error : "+e.getMessage());
            return false;
        }
    }

}
