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

import org.example.www.controlaccesos.InstanciaRegistroAccesosType;

public class RegistroAccesosRepository {
    private Conexion conexion;

    // Constructor que inicializa la conexión
    public RegistroAccesosRepository() {
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
                return -1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ERROR: El empleado con NIF/NIE: " + nifnie + 
            						"no existe en la BD");
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
                return -1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ERROR: La sala con codigo: " + codigoSala + 
            						"no existe en la BD");
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
    (int codigo) throws SQLException {
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
            throw new SQLException("ERROR: El dispositivo con codigo: " + codigo + 
            						"no existe en la BD");
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
    
    public void insertarRegistroAcceso(
    		String nifnie,
    		int codigoSala,
    		int codigoDispositivo

    ) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
        	con = this.conexion.conectar();
            
            int idEmpleado = obtenerIdEmpleadoAPartirDeNifNieEmpleado(nifnie);
            int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
            int idDispositivo = obtenerIdDispositivoAPartirDeCodigoDispositivo(codigoDispositivo);
            Timestamp fechaHora = Timestamp.valueOf(LocalDateTime.now());

            String sql = "INSERT INTO registroaccesos "
                       + "(idEmpleado, idSala, idDispositivo, fechaHora) "
                       + "VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, idSala);
            stmt.setInt(3, idDispositivo);
            stmt.setTimestamp(4, fechaHora);
            
            int filasAfectadas = stmt.executeUpdate();

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
    
    public List<InstanciaRegistroAccesosType> consultarRegistrosAcceso(
            String nifnie, int codigoSala, int codigoDispositivo, Timestamp fechaDesde, Timestamp fechaHasta) throws SQLException {

        List<InstanciaRegistroAccesosType> registros = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = this.conexion.conectar();

            int idEmpleado = obtenerIdEmpleadoAPartirDeNifNieEmpleado(nifnie);
            int idSala = obtenerIdSalaAPartirDeCodigoSala(codigoSala);
            int idDispositivo = obtenerIdDispositivoAPartirDeCodigoDispositivo(codigoDispositivo);

            String sql = "SELECT * FROM registroaccesos WHERE idEmpleado = ? AND idSala = ? AND idDispositivo = ? AND fechaHora BETWEEN ? AND ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, idSala);
            stmt.setInt(3, idDispositivo);
            stmt.setTimestamp(4, fechaDesde);
            stmt.setTimestamp(5, fechaHasta);

            rs = stmt.executeQuery();

            while (rs.next()) {
                InstanciaRegistroAccesosType registro = new InstanciaRegistroAccesosType();
                registro.setId(rs.getInt("id"));
                registro.setIdEmpleado(rs.getInt("idEmpleado"));
                registro.setIdSala(rs.getInt("idSala"));
                registro.setIdDispositivo(rs.getInt("idDispositivo"));
                
                Calendar fechaHora = Calendar.getInstance();
                fechaHora.setTimeInMillis(rs.getTimestamp("fechaHora").getTime());
                registro.setFechaHora(fechaHora);

                registros.add(registro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return registros;
    }
}
