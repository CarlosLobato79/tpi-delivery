/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.AbstractDataAccess;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author qwerty
 * @param <T>
 */
public abstract class AbstracRestResources <T> implements Serializable{
    /**
     * Definimos una funcion para obtener acceso al bean
     *
     * @return
     */
    protected abstract AbstractDataAccess<T> getDataAccesBean();

    //Implementare las 2 primeras funciones comunes de cada entity traer todo y recibir 
    /**
     *
     * @return Response una respuesta ala peticion indicando si fue aprovada o
     * no
     */
    @GET
    public Response traerTodos() {
        List<T> clista = this.getDataAccesBean().traerTodos();
        Response.ResponseBuilder rb;
        if (clista.isEmpty()) {
            rb = Response.status(Response.Status.NO_CONTENT);
            rb.header("Content-Length", "0");
            return rb.build();
        }
        
        rb = Response.ok();
        rb.header("Content-Type", MediaType.APPLICATION_JSON);
        rb.header("Accept", MediaType.APPLICATION_JSON);
        rb.entity(clista);
        return rb.build();
    }
    
    


}
