
/**
 * ControlAccesosSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.controlaccesos;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import ConexionDB.Conexion;
import ConexionDB.RegistroAccesosRepository;
import exception.WSKeyNoValidaException;
import utils.*;

/**
     *  ControlAccesosSkeleton java skeleton for the axisService
     */
    public class ControlAccesosSkeleton{
    	private RegistroAccesosRepository registroAccesosRepository;
        
        public  ControlAccesosSkeleton() {
        	this.registroAccesosRepository = new RegistroAccesosRepository();
        }
    	
        /**
         * Auto generated method signature
         * 
                                     * @param consultar 
             * @return consultarResponse 
         * @throws SQLException 
         */
        
    	public org.example.www.controlaccesos.ConsultarResponse consultar(
                org.example.www.controlaccesos.Consultar consultar) throws WSKeyNoValidaException, SQLException {
    		
    		String WSKey = consultar.getIn().getWSKey();
    		
    		Utils.verificarWSKey(WSKey);
    		
            String nifNie = consultar.getIn().getNifnie();
            
            Utils.validarNIFNIE(nifNie);
    		
            int codigoSala = consultar.getIn().getCodigoSala();
            int codigoDispositivo = consultar.getIn().getCodigoDispositivo();
            Timestamp fechaDesde = new Timestamp(consultar.getIn().getFechaDesde().getTimeInMillis());
            Timestamp fechaHasta = new Timestamp(consultar.getIn().getFechaHasta().getTimeInMillis());

            ConsultarResponse response = new ConsultarResponse();
            
            List<InstanciaRegistroAccesosType> registros = this.registroAccesosRepository
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
         * @throws WSKeyNoValidaException   
         * @throws SQLException 
         */
        
                 public org.example.www.controlaccesos.RegistrarResponse registrar
                  (
                  org.example.www.controlaccesos.Registrar registrar
                  ) throws WSKeyNoValidaException, SQLException
            {
                	 
             		 String WSKey = registrar.getIn().getWSKey();
             		 
             		 Utils.verificarWSKey(WSKey);
             		 
             		 String nifNie = registrar.getIn().getNifnie();
             		 
             		 Utils.validarNIFNIE(nifNie);
             		 
                     int codigoSala = registrar.getIn().getCodigoSala();
                     int codigoDispositivo = registrar.getIn().getCodigoDispositivo();
                     
                     RegistrarResponse response = new RegistrarResponse();
                     
                     boolean exito = this.registroAccesosRepository.insertarRegistroAcceso(
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
    