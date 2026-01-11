package dao;
import model.Licencia;
import java.sql.*;

public class LicenciaDao {

    //Reciben connection como parametro
    //Para que Service controle la transaccion
    //Garantizando que las operaciones criticas se ejectuten de forma atomica

    public boolean emitir(Licencia l, Connection con) {

        String sql = """
                INSERT INTO licencia
                        (tramite_id, numero_licencia, fecha_emision, fecha_vencimiento, created_by, created_at)
                        VALUES (?, ?, CURDATE(), ?, ?, ?)
                """;
        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1,l.getTramiteId());
            ps.setString(2, l.getNumeroLicencia());
            ps.setDate(3,Date.valueOf(l.getFechaVencimiento()));
            ps.setInt(4,l.getCreatedBy());
            ps.setTimestamp(5,Timestamp.valueOf(l.getCreatedAt()));

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al emitir licencia",e);
        }
    }

    //Metodo para verificar si ya existe una Licencia por tramite
    //Recibe la id del tramite y retorna true si ya existe

    public boolean existeLicencia(int tramiteId, Connection con) {

        //Cuenta cuantas licencias estan asociadas al tramite
        String sql= "select count(*) from licencia where tramite_id=?";
        try(
                PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setInt(1,tramiteId);

            //Ejecutar consulta
            //Devuelve el resultado de la consulta, como una fila por count
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return rs.getInt(1)> 0; //Si es mayor que 0 es true, existe licencia
                }

                return false;
            }

        }catch (Exception e){
            //Excepcion no verificada para errores tecnicos
            throw new RuntimeException("Error al verificar licencia",e);
        }
    }

    public boolean cambiarEstado(int tramiteId, String estado, Connection con) {
        String sql = "UPDATE tramite SET estado = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,estado);
            ps.setInt(2, tramiteId);
            return ps.executeUpdate() > 0; // retorna true si se actualizó al menos 1 fila
        } catch (Exception e) {
            throw new RuntimeException("Error al cambiar el estado del tramite",e);
        }
    }
}
