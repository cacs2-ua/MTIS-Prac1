
/**
 * ControlPresenciaSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.controlpresencia;
    
    import java.sql.SQLException;
    import java.sql.Timestamp;
    import java.util.List;
    import ConexionDB.Conexion;
    import ConexionDB.ControlPresenciaRepository;
    import exception.WSKeyNoValidaException;
    import utils.*;
    
    /**
     *  ControlPresenciaSkeleton java skeleton for the axisService
     */
    public class ControlPresenciaSkeleton{
    	private ControlPresenciaRepository controlPresenciaRepository;
    	
    	public ControlPresenciaSkeleton() {
    		this.controlPresenciaRepository = new ControlPresenciaRepository();
    	}
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param registrar 
             * @return registrarResponse 
         */
        
                 public org.example.www.controlpresencia.RegistrarResponse registrar
                  (
                  org.example.www.controlpresencia.Registrar registrar
                  )
            {
                	 RegistrarResponse response = new RegistrarResponse();
                	 
                	 try {
                		 String WSKey = registrar.getWSKey();
                		 
                		 Utils.verificarWSKey(WSKey);
                		 
                		 String nifNie = registrar.getIn().getNifnie();
                		 
                		 Utils.validarNIFNIE(nifNie);
                		 
                		 this.controlPresenciaRepository.registrarControlPresencia(registrar.getIn());
                		 response.setMensajeSalida("ControlPresencia Registrado Correctamente");
                		 
                		 return response;
                		 
                	 } catch (Exception e) {
              			response.setMensajeSalida(e.getMessage());
            			return response;
            		}
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param eliminar 
             * @return eliminarResponse 
         */
        
                 public org.example.www.controlpresencia.EliminarResponse eliminar
                  (
                  org.example.www.controlpresencia.Eliminar eliminar
                  )
            {
                	 EliminarResponse response = new EliminarResponse();
                	 try {
                		String WSKey = eliminar.getWSKey();
                      	
                   		Utils.verificarWSKey(WSKey);
                   		
                   		String nifnie = eliminar.getIn().getNifnie();
                   		
                   		Utils.validarNIFNIE(nifnie);
                   		
                   		this.controlPresenciaRepository.eliminarControlPresencia(eliminar.getIn());
                   		
                   		response.setMensajeSalida("Los Controles Presencia han sido eliminados correctamente. ");
                   		
                   		return response;
                	 } catch (Exception e) {
              			response.setMensajeSalida(e.getMessage());
             			return response;
             		}
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param controlEmpleadoSala 
             * @return controlEmpleadoSalaResponse 
         */
        
                 public org.example.www.controlpresencia.ControlEmpleadoSalaResponse controlEmpleadoSala
                  (
                  org.example.www.controlpresencia.ControlEmpleadoSala controlEmpleadoSala
                  )
            {
                	 ControlEmpleadoSalaResponse response = new ControlEmpleadoSalaResponse();
                	 
                	 try {
                		 String WSKey = controlEmpleadoSala.getWSKey();
                 		
                 		 Utils.verificarWSKey(WSKey);
                 		 
                 		 int codigoSala = controlEmpleadoSala.getCodigoSala();
                 		 
                 		List<EmpleadosType> registros = this.controlPresenciaRepository()
                 		
                	 }
        }
     
    }
    