package dao;

import model.Solicitante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SolicitanteDao {

    public int insertarYRetornarId(Solicitante s) {

        String sql = "insert into solicitante "
                + "(cedula, nombre, fecha_nacimiento, tipo_licencia, fecha_solicitud) "
                + "values (?, ?, ?, ?, curdate())";

        //try-with-resources
        //Sirve para cerrar Connection, PreparedStament, ResultSet cuando termina el bloque try
        try (
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            ps.setString(1, s.getCedula());
            ps.setString(2, s.getNombre());
            ps.setDate(3, java.sql.Date.valueOf(s.getFechaNacimiento())); // se usa sql.date para cambiar el tipo de dato a SQL
            ps.setString(4, s.getTipoLicencia());

            int filas= ps.executeUpdate();

            if(filas == 0) {
                return -1; //No se inserto nada
            }

            //Obtiene la id de la bd que se genero automaticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    return rs.getInt(1); //Retorna el id generado
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al insertar el solicitante",e);
        }
        return -1;
    }

    //Validar que no exista cedulas duplicadas
    public boolean existeCedula(String cedula) {
        String sql = "select count(*) from solicitante where cedula = ?";
        try (
                Connection con= new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1,cedula);
            try(ResultSet rs=ps.executeQuery()){
                if (rs.next()){
                    return rs.getInt(1)> 0;
                }
            }

            return false;

        }
        catch (Exception e) {
            throw new RuntimeException("Error al verificar la cedula duplicada",e);
        }
    }

}
