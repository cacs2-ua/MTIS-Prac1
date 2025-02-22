
/**
 * ControlAccesosSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.controlaccesos;

import org.example.www.empleados.NuevoResponse;

import ConexionDB.Conexion;

/**
     *  ControlAccesosSkeleton java skeleton for the axisService
     */
    public class ControlAccesosSkeleton{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param consultar 
             * @return consultarResponse 
         */
        
                 public org.example.www.controlaccesos.ConsultarResponse consultar
                  (
                  org.example.www.controlaccesos.Consultar consultar
                  )
            {
                //TODO : fill this with the necessary business logic
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#consultar");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param registrar 
             * @return registrarResponse 
         */
        
                 public org.example.www.controlaccesos.RegistrarResponse registrar
                  (
                  org.example.www.controlaccesos.Registrar registrar
                  )
            {
                     RegistrarResponse response = new RegistrarResponse();
                     
                     String nifNie = registrar.getIn().getNifnie();
                     int codigoSala = registrar.getIn().getCodigoSala();
                     int codigoDispositivo = registrar.getIn().getCodigoDispositivo();
                     
                     Conexion conexion = new Conexion();
                     
                     boolean exito = conexion.insertarRegistroAcceso(
                    		 nifNie, 
                    		 codigoSala, 
                    		 codigoDispositivo
                    		 );
                     if (exito == true) {
                         response.setOut("RegistroAcceso Insertado Correctamente");
                     }
                     
                     else  {
                    	 response.setOut("Ha ocurrido alg�n error a la hora de insertar el RegistroAcceso");
                     }

                     return response;
        }
    }
    