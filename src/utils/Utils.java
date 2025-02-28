package utils;

import ConexionDB.Conexion;
import exception.*;
import org.example.www.validaciones.*;

public final class Utils {
	
    private static final ValidacionesSkeleton validaciones = new ValidacionesSkeleton();
	
    private Utils() {
        throw new UnsupportedOperationException("No se puede instanciar esta clase.");
    }
    
    public static void verificarWSKey(String WSKey) throws WSKeyNoValidaException {
        Conexion cn = new Conexion();
        
        String WSKeyDB = cn.obtenerWSKey();
        
		if (!WSKeyDB.equals(WSKey)){
			throw new WSKeyNoValidaException("Acceso no autorizado: WSKey no válida.");
		}
		
    }
    
    public static boolean validarNIF (String NIF) {
    	ValidarNIF validarNIFRequest = new ValidarNIF();
        validarNIFRequest.setNIF(NIF);
        
        ValidarNIFResponse response = validaciones.validarNIF(validarNIFRequest);
        
        return response.getValido();
    	
    }
    
    public static boolean validarNIE (String NIE) {
    	ValidarNIE validarNIERequest = new ValidarNIE();
        validarNIERequest.setNIE(NIE);
        
        ValidarNIEResponse response = validaciones.validarNIE(validarNIERequest);
        
        return response.getValido();
    	
    }
    
    public static void validarNIFNIE (String nifnie) {
    	if (!validarNIF(nifnie) && !validarNIE(nifnie)) {
    		throw new InvalidNIFNIEException("ERROR: el nif/nie introducido no es válido. ");
    	}
    }
    
    public static boolean validarNAF (String NAF) {
    	ValidarNAF validarNAFRequest = new ValidarNAF();
        validarNAFRequest.setNAF(NAF);
        
        ValidarNAFResponse response = validaciones.validarNAF(validarNAFRequest);
        
        return response.getValido();
    	
    }
    
    public static boolean validarIBAN (String IBAN) {
    	ValidarIBAN validarIBANRequest = new ValidarIBAN();
        validarIBANRequest.setIBAN(IBAN);
        
        ValidarIBANResponse response = validaciones.validarIBAN(validarIBANRequest);
        
        return response.getValido();
    	
    }
    
    public static boolean esFormatoNIF(String nifnie) {
        if (nifnie == null || nifnie.isEmpty()) {
            return false;
        }
        return Character.isDigit(nifnie.charAt(0));
    	
    }
    
    public static boolean esFormatoNIE(String nifnie) {
        if (nifnie == null || nifnie.isEmpty()) {
            return false;
        }
        return Character.isLetter(nifnie.charAt(0));
    	
    }
    
    
    public static void validarEmpleado (String nifnie,
    											String NAF,
    											String IBAN) {
    	
    	if (!esFormatoNIF(nifnie) && !esFormatoNIE(nifnie)) {
    		throw new InvalidNIFNIEException("ERROR: el nif/nie introducido no es válido. ");
    	}
    	
    	if (esFormatoNIF(nifnie) && !validarNIF(nifnie)) {
    		throw new InvalidNIFException("El NIF proporcionado no es válido: " + nifnie);
    	}
    	
    	if (esFormatoNIE(nifnie) && !validarNIE(nifnie)) {
    		throw new InvalidNIEException("El NIE proporcionado no es válido: " + nifnie);
    	}
    	
    	if (!validarNAF(NAF)) {
    		throw new InvalidNAFException("El NAF proporcionado no es válido: " + NAF);
    	}
    	
    	if (!validarIBAN(IBAN)) {
    		throw new InvalidIBANException("El IBAN proporcionado no es válido: " + IBAN);
    	}
    	
    }

}
