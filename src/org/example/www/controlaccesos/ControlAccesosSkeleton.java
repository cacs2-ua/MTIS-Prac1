
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
		
        ConsultarResponse response = new ConsultarResponse();
		try {
    		String WSKey = consultar.getWSKey();
    		
    		Utils.verificarWSKey(WSKey);
    		
            String nifNie = consultar.getIn().getNifnie();
            
            Utils.validarNIFNIE(nifNie);
    		
            int codigoSala = consultar.getIn().getCodigoSala();
            int codigoDispositivo = consultar.getIn().getCodigoDispositivo();
            Timestamp fechaDesde = new Timestamp(consultar.getIn().getFechaDesde().getTimeInMillis());
            Timestamp fechaHasta = new Timestamp(consultar.getIn().getFechaHasta().getTimeInMillis());
            
            List<InstanciaRegistroAccesosType> registros = this.registroAccesosRepository
                    .consultarRegistrosAcceso(nifNie, codigoSala, codigoDispositivo, fechaDesde, fechaHasta);

            response.setOut(registros.toArray(new InstanciaRegistroAccesosType[0]));
            response.setMensajeSalida("Consulta de registros de acceso realizada con éxito. ");
             
            return response;
            
		} catch (Exception e) {
			response.setMensajeSalida(e.getMessage());
			return response;
		}
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
         RegistrarResponse response = new RegistrarResponse();
    	 try {
     		 String WSKey = registrar.getWSKey();
     		 
     		 Utils.verificarWSKey(WSKey);
     		 
     		 String nifNie = registrar.getIn().getNifnie();
     		 
     		 Utils.validarNIFNIE(nifNie);
     		 
             int codigoSala = registrar.getIn().getCodigoSala();
             int codigoDispositivo = registrar.getIn().getCodigoDispositivo();
             
             this.registroAccesosRepository.insertarRegistroAcceso(
            		 nifNie, 
            		 codigoSala, 
            		 codigoDispositivo
            		 );
             response.setMensajeSalida("RegistroAcceso Insertado Correctamente");
             
             return response;
    		 
    	 } catch (Exception e) {
 			response.setMensajeSalida(e.getMessage());
			return response;
		}
     }
}
