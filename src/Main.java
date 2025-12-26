import dao.Conexion;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        Connection con = conexion.getConexion();

        if (con != null) {
            System.out.println("La base de datos responde correctamente");
        } else {
            System.out.println("No se pudo establecer la conexión");
        }
    }
}