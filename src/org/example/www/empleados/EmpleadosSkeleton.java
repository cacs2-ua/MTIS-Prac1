
/**
 * EmpleadosSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
package org.example.www.empleados;

import java.sql.SQLException;

import ConexionDB.EmpleadoRepository;
import exception.WSKeyNoValidaException;
import utils.*;

/**
     *  EmpleadosSkeleton java skeleton for the axisService
     */
    public class EmpleadosSkeleton{
    	private EmpleadoRepository empleadoRepository;
    	
    	public EmpleadosSkeleton() {
    		this.empleadoRepository = new EmpleadoRepository();
    	}
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param modificar 
             * @return modificarResponse 
         */
        
                 public org.example.www.empleados.ModificarResponse modificar
                  (
                  org.example.www.empleados.Modificar modificar
                  )
            {
                	 ModificarResponse response = new ModificarResponse();
                	 
                	 try {
                		 String WSKey = modificar.getWSKey();
                         
                         Utils.verificarWSKey(WSKey);
                         
                         String nifnie = modificar.getIn().getNifnie();
                         String naf = modificar.getIn().getNaf();
                         String iban = modificar.getIn().getIban();
                         
                         Utils.validarEmpleado(nifnie, naf, iban);
                         
                         this.empleadoRepository.modificarEmpleado(modificar.getIn());
                         
                         response.setMensajeSalida("Empleado con nif: " + nifnie + " modificado correctamente. ");
                         return response;
                	 } catch (Exception e) {
              			response.setMensajeSalida(e.getMessage());
             			return response;
             		}
            }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param consultar 
             * @return consultarResponse 
         */
        
                 public org.example.www.empleados.ConsultarResponse consultar
                  (
                  org.example.www.empleados.Consultar consultar
                  )
            {
                	 ConsultarResponse response = new ConsultarResponse();
                	 
                	 try {
                		 String WSKey = consultar.getWSKey();
                 		
                 		 Utils.verificarWSKey(WSKey);
                 		
                         String nifNie = consultar.getNifnie();
                         
                         Utils.validarNIFNIE(nifNie);
                         
                         EmpleadosType empleadoObtenido = this.empleadoRepository.consultarEmpleado(nifNie);
                         
                         response.setEmpleadoOut(empleadoObtenido);
                         response.setMensajeSalida("OK: Empleado con NIF/NIE: " + nifNie
                         		+ "ha sido consultado correctamente. ");
                         return response;
                	 } catch (Exception e) {
             			response.setMensajeSalida(e.getMessage());
            			return response;
            		}
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param nuevo 
             * @return nuevoResponse 
         * @throws WSKeyNoValidaException 
         * @throws SQLException 
         */
        
                 public org.example.www.empleados.NuevoResponse nuevo
                  (
                  org.example.www.empleados.Nuevo nuevo
                  ) throws WSKeyNoValidaException, SQLException
            {
                     NuevoResponse response = new NuevoResponse();
                	 try {
                         String WSKey = nuevo.getWSKey();
                         
                         Utils.verificarWSKey(WSKey);
                         
                         String nifnie = nuevo.getIn().getNifnie();
                         String naf = nuevo.getIn().getNaf();
                         String iban = nuevo.getIn().getIban();
                         
                         Utils.validarEmpleado(nifnie, naf, iban);
                         
                         String nombreApellidos = nuevo.getIn().getNombreApellidos();
                         String email = nuevo.getIn().getEmail();
                         int idNivel = nuevo.getIn().getIdNivel();
                         String usuario = nuevo.getIn().getUsuario();
                         String password = nuevo.getIn().getPassword();
                         int valido = nuevo.getIn().getValido();
                         
                         this.empleadoRepository.insertarEmpleado(
                             nifnie,
                             nombreApellidos,
                             email,
                             naf,
                             iban,
                             idNivel,
                             usuario,
                             password,
                             valido
                         );
                         
                         response.setMensajeSalida("Empleado Insertado Correctamente. ");
                         
                         return response;
                	 } catch (Exception e) {
             			response.setMensajeSalida(e.getMessage());
            			return response;
            		}
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param borrar 
             * @return borrarResponse 
         * @throws WSKeyNoValidaException 
         * @throws SQLException 
         */
        
                 public org.example.www.empleados.BorrarResponse borrar
                  (
                  org.example.www.empleados.Borrar borrar
                  ) throws WSKeyNoValidaException, SQLException
            {
                 	BorrarResponse response = new BorrarResponse();
                	try {
                     	String WSKey = borrar.getWSKey();
                     	
                  		Utils.verificarWSKey(WSKey);
                    	 
                 		String nifnie = borrar.getNifnie();
                 		
                 		Utils.validarNIFNIE(nifnie);
                 		
                 		EmpleadoRepository empleadoRepository = new EmpleadoRepository ();
                 		
                 		empleadoRepository.borrarEmpleado(nifnie);
                 		
                 		response.setMensajeSalida("El empleado con nifnie: " + nifnie + " ha sido borrado con exito. ");
            
                 		return response;
                		
                	} catch (Exception e) {
             			response.setMensajeSalida(e.getMessage());
            			return response;
            		}
        }
     
    }
    