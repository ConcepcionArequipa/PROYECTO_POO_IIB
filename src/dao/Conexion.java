package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String Url ="jdbc:mysql://ugy8hvqbtqckzd5e:hXanAUQAhRULp5r9fQJu@b9ga2qwoxdhztatd3zbd-mysql.services.clever-cloud.com:3306/b9ga2qwoxdhztatd3zbd";
    private static final String User ="ugy8hvqbtqckzd5e";
    private static final String Pass="hXanAUQAhRULp5r9fQJu";

    public Connection getConexion(){

        try{
           return DriverManager.getConnection(Url,User,Pass);

        }catch(SQLException e){
            System.out.print("ERROR DE CONEXION EN : "+e.getSQLState());
            return null;
        }
    }
}
