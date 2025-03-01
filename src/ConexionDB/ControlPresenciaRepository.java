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

import org.example.www.controlpresencia.ControlPresenciaOperationType;
import org.example.www.controlpresencia.EmpleadosType;


public class ControlPresenciaRepository {
	private Conexion conexion;
	
    public ControlPresenciaRepository() {
        this.conexion = new Conexion();
    }
    
    public int obtenerIdEmpleadoAPartirDeNifNieEmpleado(String nifnie) throws SQLException {
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
            	throw new NoSuchElementException("ERROR: El empleado con NIF/NIE: " + nifnie + 
						" no existe en la BD");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
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
    
    public int obtenerIdSalaAPartirDeCodigoSala(int codigoSala) throws SQLException {
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
            	throw new NoSuchElementException("ERROR: La sala con codigo: " + codigoSala + 
						" no existe en la BD");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
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
    
    public void registrarControlPresencia(ControlPresenciaOperationType input) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
        	String nifnie = input.getNifnie();
        	int codigoSala = input.getCodigoSala();
        	
        	int idEmpleado = obtenerIdEmpleadoAPartirDeNifNieEmpleado(nifnie);
        	int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
        	Timestamp fechaHora = Timestamp.valueOf(LocalDateTime.now());
        	
        	String sql = "INSERT INTO controlpresencia "
        				+ "(idEmpleado, idSala, fechaHora) "
        				+ "VALUES (?, ?, ?);";
        	
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, idEmpleado);
        	stmt.setInt(2, idSala);
        	stmt.setTimestamp(3, fechaHora);
        	
        	stmt.executeUpdate();
        	
        } catch (SQLException e) {
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
    
    public void eliminarControlPresencia(ControlPresenciaOperationType input) throws SQLException {
    	Connection con = null;
        PreparedStatement stmt = null;
        
        try {
        	con = this.conexion.conectar();
        	
        	String nifnie = input.getNifnie();
        	int codigoSala = input.getCodigoSala();
        	
        	int idEmpleado = obtenerIdEmpleadoAPartirDeNifNieEmpleado(nifnie);
        	int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
        	
        	String sql = "DELETE FROM controlpresencia WHERE "
        				+ "idEmpleado = ? and "
        				+ "idSala = ? ";
        	stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEmpleado); 
            stmt.setInt(2, idSala); 
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
            	throw new NoSuchElementException("ADVERTENCIA: No existe ningun registro de control de presencia"
            									+ " que satisfaga las condicions de entrada. ");
            }
        } catch (SQLException e) {
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
    
    public List<EmpleadosType> controlEmpleadosSala(int codigoSala) throws SQLException {
        List<EmpleadosType> registros = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
        	con = this.conexion.conectar();
        	
        	int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
        	
        	String sql = "SELECT DISTINCT e.* FROM empleados e "
        				+ "JOIN registroaccesos r on r.idEmpleado = e.id "
        				+ "JOIN salas s on r.idSala = s.id "
        				+ "WHERE s.id = ? ;";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, idSala);
        	rs = stmt.executeQuery();

            while (rs.next()) {
            	EmpleadosType registro = new EmpleadosType();
                registro.setId(rs.getInt("id"));
                registro.setNifnie(rs.getString("nifnie"));
                registro.setNombreApellidos(rs.getString("nombreApellidos"));
                registro.setEmail(rs.getString("email"));
                registro.setNaf(rs.getString("naf"));
                registro.setIban(rs.getString("iban"));
                registro.setIdNivel(rs.getInt("idNivel"));
                registro.setUsuario(rs.getString("usuario"));
                registro.setPassword(rs.getString("password"));
                registro.setValido(rs.getInt("valido"));

                registros.add(registro);
            }
            
            if (registros.isEmpty()) {
                throw new NoSuchElementException("ADVERTENCIA: No existen usuarios asignados a dicha "
                								+ "combinaci�n de par�metros de entrada. ");
            }
            
            return registros;
            
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
