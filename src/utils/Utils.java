package utils;

import ConexionDB.Conexion;

public final class Utils {
	
    private Utils() {
        throw new UnsupportedOperationException("No se puede instanciar esta clase.");
    }
    
    public static boolean verificarWSKey(String WSKey) {
        Conexion cn = new Conexion();
        
        String WSKeyDB = cn.obtenerWSKey();
        
		if (WSKeyDB.equals(WSKey)){
			return true;
		}
		
		else {
			return false;
		}
    }

}
