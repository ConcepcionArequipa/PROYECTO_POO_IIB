package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String Url ="jdbc:mysql://ugy8hvqbtqckzd5e:hXanAUQAhRULp5r9fQJu@b9ga2qwoxdhztatd3zbd-mysql.services.clever-cloud.com:3306/b9ga2qwoxdhztatd3zbd";
    private static final String User ="ugy8hvqbtqckzd5e";
    private static final String Pass="hXanAUQAhRULp5r9fQJu";

    public Connection getConexion() {
        try {
            Connection con = DriverManager.getConnection(Url, User, Pass);
            System.out.println("CONEXIÓN EXITOSA A MYSQL");
            return con;
        } catch (SQLException e) {
            System.out.println("ERROR DE CONEXIÓN");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Código SQL: " + e.getSQLState());
            return null;
        }
    }

}