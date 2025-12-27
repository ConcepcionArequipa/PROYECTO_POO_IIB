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
        try{
            Connection com = new Conexion().getConexion();
            PreparedStatement ps = com.prepareStatement(sql);

            ps.setInt(1,l.getTramiteId());
            ps.setString(2, l.getNumeroLicencia());
            ps.setDate(3,Date.valueOf(l.getFechaVencimiento()));

            return ps.executeUpdate() > 0;

        }catch (Exception e){
            System.out.printf("Error "+e.getMessage());
            return false;
        }
    }
}
