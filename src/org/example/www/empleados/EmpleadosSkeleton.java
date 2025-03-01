
/**
 * EmpleadosSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.3  Built on : Jun 27, 2015 (11:17:49 BST)
 */
    package org.example.www.empleados;

import ConexionDB.Conexion;
import ConexionDB.EmpleadoRepository;
import exception.WSKeyNoValidaException;
import utils.*;

/**
     *  EmpleadosSkeleton java skeleton for the axisService
     */
    public class EmpleadosSkeleton{
        
         
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
                //TODO : fill this with the necessary business logic
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#modificar");
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
                //TODO : fill this with the necessary business logic
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#consultar");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param nuevo 
             * @return nuevoResponse 
         */
        
                 public org.example.www.empleados.NuevoResponse nuevo
                  (
                  org.example.www.empleados.Nuevo nuevo
                  )
            {
                     org.example.www.empleados.NuevoResponse response = new org.example.www.empleados.NuevoResponse();
                     
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

                     Conexion conexion = new Conexion();
                     boolean exito = conexion.insertarEmpleado(
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
                     
                     if (exito == true) {
                         response.setOut("Empleado Insertado Correctamente");
                     }
                     
                     else  {
                    	 response.setOut("Ha ocurrido algún error a la hora de insertar el empleado");
                     }

                     return response;
                	 
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param borrar 
             * @return borrarResponse 
         * @throws WSKeyNoValidaException 
         */
        
                 public org.example.www.empleados.BorrarResponse borrar
                  (
                  org.example.www.empleados.Borrar borrar
                  ) throws WSKeyNoValidaException
            {
                	 
                	BorrarResponse response = new BorrarResponse();
                	 
                	String WSKey = borrar.getWSKey();
                	 
             		Utils.verificarWSKey(WSKey);
             		
             		String nifnie = borrar.getNifnie();
             		
             		Utils.validarNIFNIE(nifnie);
             		
             		EmpleadoRepository empleadoRepository = new EmpleadoRepository ();
             		
             		boolean exito = empleadoRepository.borrarEmpleado(nifnie);
             		
             		if (exito == true) {
             			response.setOut("El empleado con nifnie: " + nifnie + " ha sido borrado con éxito");
        
             		}
             		
             		else {
             			response.setOut("Ha ocurrido algún error al borrar el empleado con nifnie: " + nifnie);
             		}
             		
             		return response;
        }
     
    }
    