package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.boundary.RestResourcePattern;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.AbstractDataAccess;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.TerritorioBean;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Territorio;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/territorio")
public class TerritorioResource extends AbstracRestResources<Territorio>{

    @Inject
    TerritorioBean tb;
    @Override
    protected AbstractDataAccess<Territorio> getDataAccesBean() {
        return this.tb;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response ingresar(Territorio entidad){
        Response.ResponseBuilder rb;
        if (entidad == null) {
            rb = Response.status(Response.Status.BAD_GATEWAY);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("deny", MediaType.APPLICATION_JSON);
            rb.entity(entidad);
            return rb.build();
        }
        try {
            Territorio entidadCreada = this.getDataAccesBean().Ingresar(entidad);
            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("location", "territorio/" + entidadCreada.getIdTerritorio());
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
    @Produces({ MediaType.APPLICATION_JSON })
    public Response buscarPorId(@PathParam("id") int id) {
        Territorio cEntity = null;
        Response.ResponseBuilder rb;
        try {
            cEntity = tb.traerPorIdTerritorio(id);
            rb = Response.ok();
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.entity(cEntity);
            return rb.build();
        } catch (Exception e) {
            rb = Response.status(Response.Status.NOT_FOUND);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header(RestResourcePattern.ID_NOT_FOUND, id);
            return rb.build();
        }
    }
}
