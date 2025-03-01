package utils;

import ConexionDB.Conexion;
import exception.*;
import org.example.www.validaciones.*;

public final class Utils {
	
	private static final Conexion conexion = new Conexion();

    private static final ValidacionesSkeleton validaciones = new ValidacionesSkeleton();
	
    private Utils() {
        throw new UnsupportedOperationException("ERROR: No se puede instanciar esta clase.");
    }
    
    public static void verificarWSKey(String WSKey) throws WSKeyNoValidaException {
        String WSKeyDB = conexion.obtenerWSKey();
        
		if (!WSKeyDB.equals(WSKey)){
			throw new WSKeyNoValidaException("ERROR: Operacion no autorizada. ");
		}
		
    }
    
    public static boolean validarNIF (String NIF) throws WSKeyNoValidaException {
    	ValidarNIF validarNIFRequest = new ValidarNIF();
        validarNIFRequest.setNIF(NIF);
        validarNIFRequest.setWSKey(conexion.obtenerWSKey());
        
        ValidarNIFResponse response = validaciones.validarNIF(validarNIFRequest);
        
        return response.getValido();
    	
    }
    
    public static boolean validarNIE (String NIE) throws WSKeyNoValidaException {
    	ValidarNIE validarNIERequest = new ValidarNIE();
        validarNIERequest.setNIE(NIE);
        validarNIERequest.setNIE(NIE);
        validarNIERequest.setWSKey(conexion.obtenerWSKey());
        
        ValidarNIEResponse response = validaciones.validarNIE(validarNIERequest);
        
        return response.getValido();
    	
    }
    
    public static void validarNIFNIE (String nifnie) throws WSKeyNoValidaException {
    	if (!validarNIF(nifnie) && !validarNIE(nifnie)) {
    		throw new InvalidNIFNIEException("ERROR: el nif/nie introducido no es válido. ");
    	}
    }
    
    public static boolean validarNAF (String NAF) throws WSKeyNoValidaException {
    	ValidarNAF validarNAFRequest = new ValidarNAF();
        validarNAFRequest.setNAF(NAF);
        validarNAFRequest.setNAF(NAF);
        validarNAFRequest.setWSKey(conexion.obtenerWSKey());
        
        ValidarNAFResponse response = validaciones.validarNAF(validarNAFRequest);
        
        return response.getValido();
    	
    }
    
    public static boolean validarIBAN (String IBAN) throws WSKeyNoValidaException {
    	ValidarIBAN validarIBANRequest = new ValidarIBAN();
        validarIBANRequest.setIBAN(IBAN);
        validarIBANRequest.setIBAN(IBAN);
        validarIBANRequest.setWSKey(conexion.obtenerWSKey());
        
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
    											String IBAN) throws WSKeyNoValidaException {
    	
    	if (!esFormatoNIF(nifnie) && !esFormatoNIE(nifnie)) {
    		throw new InvalidNIFNIEException("ERROR: el nif/nie introducido no es válido. ");
    	}
    	
    	if (esFormatoNIF(nifnie) && !validarNIF(nifnie)) {
    		throw new InvalidNIFException("ERROR: El NIF proporcionado no es válido: " + nifnie);
    	}
    	
    	if (esFormatoNIE(nifnie) && !validarNIE(nifnie)) {
    		throw new InvalidNIEException("ERROR: El NIE proporcionado no es válido: " + nifnie);
    	}
    	
    	if (!validarNAF(NAF)) {
    		throw new InvalidNAFException("ERROR: El NAF proporcionado no es válido: " + NAF);
    	}
    	
    	if (!validarIBAN(IBAN)) {
    		throw new InvalidIBANException("ERROR: El IBAN proporcionado no es válido: " + IBAN);
    	}
    	
    }

}
