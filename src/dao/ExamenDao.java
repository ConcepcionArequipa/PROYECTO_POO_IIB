package dao;

import model.Examen;
import java.sql.*;

public class ExamenDao {

    public boolean registrar(Examen e, Connection con) {

        String sql = """
                insert into examen 
                (tramite_id,nota_teorica , nota_practica, fecha_registro)
                values(?,?,?,curdate())
                ON DUPLICATE KEY UPDATE
                                 nota_teorica =VALUES(nota_teorica),
                                                   nota_practica=VALUES(nota_practica)
                """;

        try(PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setInt(1,e.getTramiteId());
            ps.setDouble(2,e.getNotaTeorica());
            ps.setDouble(3,e.getNotaPractica());

            return ps.executeUpdate() >0;

        }catch (Exception ex){
            System.out.printf("Error: "+ex.getMessage());
            return false;
        }


    }

}
