
/**
 * ControlAccesosSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.controlaccesos;

import java.sql.Timestamp;
import java.util.List;

import org.example.www.empleados.NuevoResponse;

import ConexionDB.Conexion;
import ConexionDB.RegistroAccesosRepository;

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
        
    	public org.example.www.controlaccesos.ConsultarResponse consultar(
                org.example.www.controlaccesos.Consultar consultar) {

            ConsultarResponse response = new ConsultarResponse();

            String nifNie = consultar.getIn().getNifnie();
            int codigoSala = consultar.getIn().getCodigoSala();
            int codigoDispositivo = consultar.getIn().getCodigoDispositivo();
            Timestamp fechaDesde = new Timestamp(consultar.getIn().getFechaDesde().getTimeInMillis());
            Timestamp fechaHasta = new Timestamp(consultar.getIn().getFechaHasta().getTimeInMillis());

            RegistroAccesosRepository registroAccesosRepository = new RegistroAccesosRepository();

            List<InstanciaRegistroAccesosType> registros = registroAccesosRepository
                    .consultarRegistrosAcceso(nifNie, codigoSala, codigoDispositivo, fechaDesde, fechaHasta);

            if (!registros.isEmpty()) {
                response.setOut(registros.toArray(new InstanciaRegistroAccesosType[0]));
            } else {
                response.setMensaje("No existe ningún registro en la BD con los criterios aplicados");
            }

            return response;
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
                    	 response.setOut("Ha ocurrido algún error a la hora de insertar el RegistroAcceso");
                     }

                     return response;
        }
    }
    