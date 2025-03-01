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
	
    public boolean insertarEmpleado(
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

            // 4. Ejecutar la inserci�n
            int filasAfectadas = stmt.executeUpdate();

            // 5. Devolver true si se insert� al menos 1 fila
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ERROR: El empleado que se ha tratado de insertar ya exist�a en la BD");
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
	
	public boolean borrarEmpleado (String nifnie) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
            String sql = "DELETE FROM empleados WHERE nifnie = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nifnie); 
            
            int filasAfectadas = stmt.executeUpdate();
            
            return (filasAfectadas > 0);
        }
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ERROR: El empleado que se ha tratado "
            		+ "de borrar no existe en la BD");
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        
	}
	
	/*
	
	public void modificarEmpleado (EmpleadosType datosEmpleado) {
		String nifnie = datosEmpleado.getNifnie();
		if (!validarNIF(nifnie))
		
	}
	
	*/

}
