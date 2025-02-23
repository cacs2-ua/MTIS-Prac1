package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RegistroAccesosRepository {
    private Conexion conexion;

    // Constructor que inicializa la conexión
    public RegistroAccesosRepository() {
        this.conexion = new Conexion();
    }
    
    public int obtenerIdEmpleadoAPartirDeNifNieEmpleado(String nifnie) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = this.conexion.conectar();
            
            String sql = "SELECT id FROM empleados WHERE nifnie = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nifnie);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
    
    public int obtenerIdSalaAPartirDeCodigoSala(int codigoSala) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = this.conexion.conectar();
            
            String sql = "SELECT id FROM salas WHERE codigoSala = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigoSala);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
    
    public int obtenerIdDispositivoAPartirDeCodigoDispositivo
    (int codigo) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = this.conexion.conectar();
            
            String sql = "SELECT id FROM dispositivo WHERE codigo = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);  
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
    
    public boolean consultarRegistroAcceso (
    		String nifnie,
    		int codigoSala,
    		int codigoDispositivo,
    		Timestamp fechaDesde,
    		Timestamp fechaHasta
    		) {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
        	int idEmpleado = obtenerIdEmpleadoAPartirDeNifNieEmpleado(nifnie);
            int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
            int idDispositivo = obtenerIdDispositivoAPartirDeCodigoDispositivo(codigoDispositivo);
            
            String sql =  "SELECT * FROM registroaccesos where "
            		    + "idEmpleado = ? and "
            		    + "idSala = ? and "
            		    + "idDispositivo = ? and "
            		    + "fechaHora >= ? and "
            		    + "fechaHora <= ?";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, idSala);
            stmt.setInt(3, idDispositivo);
            stmt.setTimestamp(4, fechaDesde);
            stmt.setTimestamp(5, fechaHasta);
            
            int filasAfectadas = stmt.executeUpdate();
            
            return (filasAfectadas > 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

}
