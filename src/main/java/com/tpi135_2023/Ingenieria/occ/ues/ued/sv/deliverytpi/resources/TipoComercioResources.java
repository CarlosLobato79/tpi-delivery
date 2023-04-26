package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import java.io.Serializable;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.boundary.RestResourcePattern;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.AbstractDataAccess;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.TipoComercioBean;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.TipoComercio;


import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
@Path("/tipocomercio")
public class TipoComercioResources extends AbstracRestResources<TipoComercio>{

    @Inject TipoComercioBean tcb;

    @Override
    protected AbstractDataAccess<TipoComercio> getDataAccesBean() {
        return this.tcb;
    
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response ingresar(TipoComercio entidad) {
        
        Response.ResponseBuilder rb;
        if(entidad == null){
            rb = Response.status(Response.Status.BAD_GATEWAY);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("deny", MediaType.APPLICATION_JSON);
            rb.entity(entidad);
            return rb.build();   
        }
        try {
            TipoComercio entidadCreada = this.getDataAccesBean().Ingresar(entidad);
            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("location", "tipocomercio/"+entidadCreada.getIdTipoComercio());
            rb.entity(entidadCreada);
            return rb.build();

        } catch (Exception e) {
            rb = Response.noContent();
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("deny", MediaType.APPLICATION_JSON);
            rb.entity(entidad);
        } 
        return rb.build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id){
        TipoComercio cEntity = null;
        Response.ResponseBuilder rb;
        try{
            cEntity = tcb.traerPorIdTipoComercio(id);
            rb = Response.ok();
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.entity(cEntity);
            return rb.build();
        }catch(Exception e){
            rb = Response.status(Response.Status.NOT_FOUND);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header(RestResourcePattern.ID_NOT_FOUND, id);
            return rb.build();
        }
    }
    
    
}
