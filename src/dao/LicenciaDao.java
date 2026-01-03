package dao;
import model.Licencia;
import java.sql.*;

public class LicenciaDao {

    public boolean emitir(Licencia l){

        String sql = """
                insert into licencia
                (tramite_id,numero_licencia,fecha_emision,fecha_vencimiento)
                values(?,?,curdate(),?)
                """;
        try(
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1,l.getTramiteId());
            ps.setString(2, l.getNumeroLicencia());
            ps.setDate(3,Date.valueOf(l.getFechaVencimiento()));

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            throw new RuntimeException("Error al emitir licencia",e);
        }
    }

    //Metodo para verificar si ya existe una Licencia por tramite
    //Recibe la id del tramite y retorna true si ya existe

    public boolean existeLicencia(int tramiteId){

        //Cuenta cuantas licencias estan asociadas al tramite
        String sql= "select count(*) from licencia where tramite_id=?";
        try(
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setInt(1,tramiteId);

            //Ejecutar consulta

            ResultSet rs= ps.executeQuery(); //Devuelve el resultado de la consulta, como una fila por count

            //Lee el rsultado
            if (rs.next()){
                //Si es mayor que 0 es true, existe licencia
                return rs.getInt(1)> 0;
            }
            return false; //No exite licencia
        }catch (Exception e){
            //Excepcion no verificada para errores tecnicos
            throw new RuntimeException("Error al verificar licencia",e);
        }
    }
}
