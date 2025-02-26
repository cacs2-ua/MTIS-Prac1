
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

    	    // Comprobamos que el formato sea: dos letras seguidas de 22 dígitos.
    	    if (!iban.matches("^[A-Za-z]{2}[0-9]{22}$")) {
    	        response.setValido(false);
    	        return response;
    	    }

    	    try {
    	        // Algoritmo estándar de validación de IBAN:
    	        // 1. Mover los cuatro primeros caracteres al final.
    	        String rearranged = iban.substring(4) + iban.substring(0, 4);

    	        // 2. Convertir cada letra a dos dígitos (A=10, B=11, ..., Z=35).
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

    	        // 3. Convertir el string resultante en un BigInteger y calcular el módulo 97.
    	        java.math.BigInteger ibanNumber = new java.math.BigInteger(numericIban.toString());
    	        boolean valid = ibanNumber.mod(java.math.BigInteger.valueOf(97)).intValue() == 1;

    	        response.setValido(valid);
    	    } catch (Exception e) {
    	        // Si ocurre cualquier excepción, el IBAN se considera inválido.
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

                	    // Comprobamos que el NAF no sea nulo y cumpla el formato de 12 dígitos
                	    if (naf == null || !naf.matches("^[0-9]{12}$")) {
                	        response.setValido(false);
                	        return response;
                	    }
                	    
                	    try {
                	        // Extraemos la parte numérica base (primeros 10 dígitos)
                	        String baseStr = naf.substring(0, 10);
                	        long baseNumber = Long.parseLong(baseStr);
                	        
                	        // Calculamos el dígito de control: control = 97 - (baseNumber mod 97)
                	        int controlComputed = (int)(97 - (baseNumber % 97));
                	        
                	        // Formateamos el control a dos dígitos (con posible cero delante)
                	        String controlStr = String.format("%02d", controlComputed);
                	        
                	        // Extraemos los dos dígitos de control proporcionados (últimos 2 dígitos del NAF)
                	        String controlProvided = naf.substring(10, 12);
                	        
                	        // Comparamos la letra de control calculada con la proporcionada
                	        boolean valid = controlStr.equals(controlProvided);
                	        response.setValido(valid);
                	    } catch (NumberFormatException e) {
                	        // Si ocurre un error al parsear la parte numérica, el NAF se considera inválido.
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

                	    // Primero comprobamos que el NIF no sea nulo y cumpla el formato: 8 dígitos seguidos de una letra.
                	    if (nif == null || !nif.matches("^[0-9]{8}[A-Za-z]$")) {
                	        response.setValido(false);
                	        return response;
                	    }

                	    try {
                	        // Extraemos la parte numérica y convertimos a entero.
                	        int numero = Integer.parseInt(nif.substring(0, 8));
                	        // Tabla de letras para el cálculo del NIF.
                	        String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
                	        // Calculamos el índice: el resto de dividir el número entre 23.
                	        char letraEsperada = tablaLetras.charAt(numero % 23);
                	        // Obtenemos la letra proporcionada (en mayúsculas para comparar).
                	        char letraNIF = Character.toUpperCase(nif.charAt(8));

                	        // Comparamos la letra calculada con la letra del NIF.
                	        boolean valido = (letraEsperada == letraNIF);
                	        response.setValido(valido);
                	    } catch (NumberFormatException e) {
                	        // En caso de error al parsear el número, el NIF no es válido.
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

                	    // Comprobar que el NIE no sea nulo y cumpla con el formato: [XYZ] seguido de 7 dígitos y una letra final.
                	    if (nie == null || !nie.matches("^[XYZxyz][0-9]{7}[A-Za-z]$")) {
                	        response.setValido(false);
                	        return response;
                	    }

                	    try {
                	        // Convertir la primera letra a mayúsculas para trabajar de forma uniforme
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
                	            // Aunque la expresión regular garantiza solo X, Y o Z, se añade por seguridad.
                	            response.setValido(false);
                	            return response;
                	        }

                	        // Concatenar el prefijo numérico con los 7 dígitos (posiciones 1 a 7)
                	        String numberStr = numericPrefix + nie.substring(1, 8);
                	        int number = Integer.parseInt(numberStr);

                	        // Tabla de letras para el cálculo del NIE (igual que para el NIF)
                	        String tablaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
                	        // El índice se obtiene con el resto de dividir el número entre 23
                	        char letraEsperada = tablaLetras.charAt(number % 23);
                	        // La letra proporcionada se encuentra en la posición 8 y se pasa a mayúsculas
                	        char letraNIE = Character.toUpperCase(nie.charAt(8));

                	        // La validación es correcta si la letra calculada coincide con la letra del NIE
                	        response.setValido(letraEsperada == letraNIE);

                	    } catch (NumberFormatException e) {
                	        // Si se produce algún error al convertir la parte numérica, el NIE no es válido
                	        response.setValido(false);
                	    }

                	    return response;
                	}
    }
    