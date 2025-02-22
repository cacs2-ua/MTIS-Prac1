package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    /**
     * Método para comprobar si un usuario existe en la tabla "usuario".
     * (Método ya existente, se deja tal cual).
     */
    public static boolean comprobarUsuario(String codigo) {
        Conexion cn = new Conexion();
        Connection con = cn.conectar();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario WHERE codigo = ?");
            stmt.setString(1, codigo);
            ResultSet res = stmt.executeQuery();

            if (res.first()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
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
    ) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = this.conectar();

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

            // 5. Devolver true si se insertó al menos 1 fila
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    
    public int obtenerIdEmpleadoAPartirDeNifNieEmpleado(String nifnie) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = this.conectar();
            
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
            con = this.conectar();
            
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
            con = this.conectar();
            
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

    
    public boolean insertarRegistroAcceso(
    		String nifnie,
    		int codigoSala,
    		int codigoDispositivo

    ) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = this.conectar();

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

            // 5. Devolver true si se insertó al menos 1 fila
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
}
