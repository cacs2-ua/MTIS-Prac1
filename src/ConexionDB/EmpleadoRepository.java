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
	
	public boolean borrarEmpleado (String nifnie) {
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
	
	/*
	
	public void modificarEmpleado (EmpleadosType datosEmpleado) {
		String nifnie = datosEmpleado.getNifnie();
		if (!validarNIF(nifnie))
		
	}
	
	*/

}
