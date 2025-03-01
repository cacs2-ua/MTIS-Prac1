package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import exception.*;
import org.example.www.validaciones.*;
import org.example.www.controlaccesos.InstanciaRegistroAccesosType;
import org.example.www.empleados.EmpleadosType;

public class EmpleadoRepository {
	private Conexion conexion;
	private ValidacionesSkeleton validaciones;
	
	public EmpleadoRepository() {
		this.conexion = new Conexion();
		this.validaciones = new ValidacionesSkeleton();
	}
	
    public void insertarEmpleado(
            String nifnie,
            String nombreApellidos,
            String email,
            String naf,
            String iban,
            int idNivel,
            String usuario,
            String password,
            int valido
    ) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
        	con = this.conexion.conectar();

            String sql = "INSERT INTO empleados "
                       + "(nifnie, nombreApellidos, email, naf, iban, idNivel, usuario, password, valido) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, nifnie);
            stmt.setString(2, nombreApellidos);
            stmt.setString(3, email);
            stmt.setString(4, naf);
            stmt.setString(5, iban);
            stmt.setInt(6, idNivel);
            stmt.setString(7, usuario);
            stmt.setString(8, password);
            stmt.setInt(9, valido);

            // 4. Ejecutar la inserción
            int filasAfectadas = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ERROR: El empleado que se ha tratado de insertar ya existía en la BD");
        } finally {
            // Cerrar recursos en el finally
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
	
	public void borrarEmpleado (String nifnie) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
            String sql = "DELETE FROM empleados WHERE nifnie = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nifnie); 
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
            	throw new SQLException("ERROR: No existe ningún empleado en la BD con nifnie: " + nifnie);
            }
        }
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
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
