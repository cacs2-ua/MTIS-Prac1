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
import java.util.NoSuchElementException;

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
            throw new SQLException(e.getMessage());
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
            	throw new NoSuchElementException("ADVERTENCIA: No existe ningun empleado en la BD con nifnie: " + nifnie);
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
	
	public void modificarEmpleado(EmpleadosType empleado) throws SQLException {
		Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
        	String nifnie = empleado.getNifnie();
        	String nombreApellidos = empleado.getNombreApellidos();
            String email = empleado.getEmail();
            String naf = empleado.getNaf();
            String iban = empleado.getIban();
            int idNivel = empleado.getIdNivel();
            String usuario = empleado.getUsuario();
            String password = empleado.getPassword();
            int valido = empleado.getValido();
            int id = empleado.getId();
            
            String sql = "UPDATE empleados "
            			+ "SET nifnie  = ?, "
            			+ "nombreapellidos = ?, "
            			+ "email = ?, "
            			+ "naf= ?, "
            			+ "iban = ?, "
            			+ "idNivel = ?, "
            			+ "usuario = ?, "
            			+ "password = ?, "
            			+ "valido = ? "
            			+ "WHERE id = ?;";
            
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
            stmt.setInt(10, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
            	throw new NoSuchElementException("ADVERTENCIA: No existe ningún empleado en la BD con id: " + id);
            }
            
        }  catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }  finally {
            // Cerrar recursos en el finally
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
	}
	
	public EmpleadosType consultarEmpleado(String nifnie) throws SQLException {
		Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
        	con = this.conexion.conectar();
        	String sql = "SELECT * FROM empleados WHERE nifnie = ?";
        	stmt = con.prepareStatement(sql);
        	stmt.setString(1, nifnie);
        	
        	rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new NoSuchElementException("ADVERTENCIA: No existe ningún empleado con NIF/NIE: " + nifnie);
            }
            EmpleadosType empleado =  new EmpleadosType();
            
            empleado.setId(rs.getInt("id"));
            empleado.setNifnie(rs.getString("nifnie"));
            empleado.setNombreApellidos(rs.getString("nombreApellidos"));
            empleado.setEmail(rs.getString("email"));
            empleado.setNaf(rs.getString("naf"));
            empleado.setIban(rs.getString("iban"));
            empleado.setIdNivel(rs.getInt("idNivel"));
            empleado.setUsuario(rs.getString("usuario"));
            empleado.setPassword(rs.getString("password"));
            empleado.setValido(rs.getInt("valido"));
            
            return empleado;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
	}
	
}
