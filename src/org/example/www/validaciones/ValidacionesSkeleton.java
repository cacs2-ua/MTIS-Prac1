
/**
 * ValidacionesSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.validaciones;
    /**
     *  ValidacionesSkeleton java skeleton for the axisService
     */
    public class ValidacionesSkeleton{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param validarIBAN 
             * @return validarIBANResponse 
         */
        
    	public org.example.www.validaciones.ValidarIBANResponse validarIBAN(
    	        org.example.www.validaciones.ValidarIBAN validarIBAN) {

    	    // Se obtiene el IBAN desde el objeto de entrada.
    	    String iban = validarIBAN.getIBAN();
    	    org.example.www.validaciones.ValidarIBANResponse response = new org.example.www.validaciones.ValidarIBANResponse();

    	    // Comprobamos que el IBAN no sea nulo y eliminamos posibles espacios en blanco.
    	    if (iban == null) {
    	        response.setValido(false);
    	        return response;
    	    }
    	    iban = iban.replaceAll("\\s+", "");

    	    // Verificamos que tenga exactamente 24 caracteres.
    	    if (iban.length() != 24) {
    	        response.setValido(false);
    	        return response;
    	    }

    	    // Comprobamos que el formato sea: dos letras seguidas de 22 d�gitos.
    	    if (!iban.matches("^[A-Za-z]{2}[0-9]{22}$")) {
    	        response.setValido(false);
    	        return response;
    	    }

    	    try {
    	        // Algoritmo est�ndar de validaci�n de IBAN:
    	        // 1. Mover los cuatro primeros caracteres al final.
    	        String rearranged = iban.substring(4) + iban.substring(0, 4);

    	        // 2. Convertir cada letra a dos d�gitos (A=10, B=11, ..., Z=35).
    	        StringBuilder numericIban = new StringBuilder();
    	        for (int i = 0; i < rearranged.length(); i++) {
    	            char ch = rearranged.charAt(i);
    	            if (Character.isLetter(ch)) {
    	                int value = Character.toUpperCase(ch) - 'A' + 10;
    	                numericIban.append(value);
    	            } else {
    	                numericIban.append(ch);
    	            }
    	        }

    	        // 3. Convertir el string resultante en un BigInteger y calcular el m�dulo 97.
    	        java.math.BigInteger ibanNumber = new java.math.BigInteger(numericIban.toString());
    	        boolean valid = ibanNumber.mod(java.math.BigInteger.valueOf(97)).intValue() == 1;

    	        response.setValido(valid);
    	    } catch (Exception e) {
    	        // Si ocurre cualquier excepci�n, el IBAN se considera inv�lido.
    	        response.setValido(false);
    	    }

    	    return response;
    	}

     
         
        /**
         * Auto generated method signature
         * 
                                     * @param validarNAF 
             * @return validarNAFResponse 
         */
        
                 public org.example.www.validaciones.ValidarNAFResponse validarNAF(
                	        org.example.www.validaciones.ValidarNAF validarNAF) {

                	    // Obtenemos el NAF a validar
                	    String naf = validarNAF.getNAF();
                	    
                	    // Creamos el objeto respuesta
                	    ValidarNAFResponse response = new ValidarNAFResponse();

                	    // Comprobamos que el NAF no sea nulo y cumpla el formato de 12 d�gitos
                	    if (naf == null || !naf.matches("^[0-9]{12}$")) {
                	        response.setValido(false);
                	        return response;
                	    }
                	    
                	    try {
                	        // Extraemos la parte num�rica base (primeros 10 d�gitos)
                	        String baseStr = naf.substring(0, 10);
                	        long baseNumber = Long.parseLong(baseStr);
                	        
                	        // Calculamos el d�gito de control: control = 97 - (baseNumber mod 97)
                	        int controlComputed = (int)(97 - (baseNumber % 97));
                	        
                	        // Formateamos el control a dos d�gitos (con posible cero delante)
                	        String controlStr = String.format("%02d", controlComputed);
                	        
                	        // Extraemos los dos d�gitos de control proporcionados (�ltimos 2 d�gitos del NAF)
                	        String controlProvided = naf.substring(10, 12);
                	        
                	        // Comparamos la letra de control calculada con la proporcionada
                	        boolean valid = controlStr.equals(controlProvided);
                	        response.setValido(valid);
                	    } catch (NumberFormatException e) {
                	        // Si ocurre un error al parsear la parte num�rica, el NAF se considera inv�lido.
                	        response.setValido(false);
                	    }
                	    
                	    return response;
                	}

     
         
        /**
         * Auto generated method signature
         * 
                                     * @param validarNIF 
             * @return validarNIFResponse 
         */
        
                 public org.example.www.validaciones.ValidarNIFResponse validarNIF(
                	        org.example.www.validaciones.ValidarNIF validarNIF) {

                	    String nif = validarNIF.getNIF();
                	    ValidarNIFResponse response = new ValidarNIFResponse();

                	    // Primero comprobamos que el NIF no sea nulo y cumpla el formato: 8 d�gitos seguidos de una letra.
                	    if (nif == null || !nif.matches("^[0-9]{8}[A-Za-z]$")) {
                	        response.setValido(false);
                	        return response;
                	    }

                	    try {
                	        // Extraemos la parte num�rica y convertimos a entero.
                	        int numero = Integer.parseInt(nif.substring(0, 8));
                	        // Tabla de letras para el c�lculo del NIF.
                	        String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
                	        // Calculamos el �ndice: el resto de dividir el n�mero entre 23.
                	        char letraEsperada = tablaLetras.charAt(numero % 23);
                	        // Obtenemos la letra proporcionada (en may�sculas para comparar).
                	        char letraNIF = Character.toUpperCase(nif.charAt(8));

                	        // Comparamos la letra calculada con la letra del NIF.
                	        boolean valido = (letraEsperada == letraNIF);
                	        response.setValido(valido);
                	    } catch (NumberFormatException e) {
                	        // En caso de error al parsear el n�mero, el NIF no es v�lido.
                	        response.setValido(false);
                	    }

                	    return response;
                	}

     
         
        /**
         * Auto generated method signature
         * 
                                     * @param validarNIE 
             * @return validarNIEResponse 
         */
        
                 public org.example.www.validaciones.ValidarNIEResponse validarNIE(
                	        org.example.www.validaciones.ValidarNIE validarNIE) {

                	    String nie = validarNIE.getNIE();
                	    // Creamos la respuesta
                	    ValidarNIEResponse response = new ValidarNIEResponse();

                	    // Comprobar que el NIE no sea nulo y cumpla con el formato: [XYZ] seguido de 7 d�gitos y una letra final.
                	    if (nie == null || !nie.matches("^[XYZxyz][0-9]{7}[A-Za-z]$")) {
                	        response.setValido(false);
                	        return response;
                	    }

                	    try {
                	        // Convertir la primera letra a may�sculas para trabajar de forma uniforme
                	        char firstChar = Character.toUpperCase(nie.charAt(0));
                	        String numericPrefix;
                	        // Convertir X, Y, Z a 0, 1, 2 respectivamente
                	        if (firstChar == 'X') {
                	            numericPrefix = "0";
                	        } else if (firstChar == 'Y') {
                	            numericPrefix = "1";
                	        } else if (firstChar == 'Z') {
                	            numericPrefix = "2";
                	        } else {
                	            // Aunque la expresi�n regular garantiza solo X, Y o Z, se a�ade por seguridad.
                	            response.setValido(false);
                	            return response;
                	        }

                	        // Concatenar el prefijo num�rico con los 7 d�gitos (posiciones 1 a 7)
                	        String numberStr = numericPrefix + nie.substring(1, 8);
                	        int number = Integer.parseInt(numberStr);

                	        // Tabla de letras para el c�lculo del NIE (igual que para el NIF)
                	        String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
                	        // El �ndice se obtiene con el resto de dividir el n�mero entre 23
                	        char letraEsperada = tablaLetras.charAt(number % 23);
                	        // La letra proporcionada se encuentra en la posici�n 8 y se pasa a may�sculas
                	        char letraNIE = Character.toUpperCase(nie.charAt(8));

                	        // La validaci�n es correcta si la letra calculada coincide con la letra del NIE
                	        response.setValido(letraEsperada == letraNIE);

                	    } catch (NumberFormatException e) {
                	        // Si se produce alg�n error al convertir la parte num�rica, el NIE no es v�lido
                	        response.setValido(false);
                	    }

                	    return response;
                	}
    }
    