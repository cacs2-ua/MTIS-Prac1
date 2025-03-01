package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Conexion {
    // Datos de conexión a la base de datos
    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/edificio_inteligente";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root"; 

    /**
     * Método para establecer la conexión con la base de datos.
     */
    public Connection conectar() {
        Connection conc = null;
        try {
            // Cargar el driver
            Class.forName(CONTROLADOR);
            // Realizar la conexión
            conc = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión OK");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return conc;
    }
    
    public String obtenerWSKey() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = this.conectar();
            String sql = "SELECT * FROM soapkey where id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1);  
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("soap_key");
            } else {
                return "ERROR";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

}
