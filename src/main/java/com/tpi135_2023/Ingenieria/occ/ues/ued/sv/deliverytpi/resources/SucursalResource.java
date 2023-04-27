package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.boundary.RestResourcePattern;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.AbstractDataAccess;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.SucursalBean;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Direccion;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Sucursal;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/sucursal")
public class SucursalResource extends AbstracRestResources<Sucursal> {
    @Inject
    SucursalBean sb;

    @Override
    protected AbstractDataAccess<Sucursal> getDataAccesBean() {
        return null;
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response ingresar(Sucursal entidad){

        Response.ResponseBuilder rb;
        if (entidad == null) {
            rb = Response.status(Response.Status.BAD_GATEWAY);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("deny", MediaType.APPLICATION_JSON);
            rb.entity(entidad);
            return rb.build();
        }
        try {
            Sucursal entidadCreada = this.getDataAccesBean().Ingresar(entidad);
            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("location", "sucursal/" + entidadCreada.getIdDireccion());
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
        Sucursal cEntity = null;

        Response.ResponseBuilder rb;
        try {
            cEntity = sb.traerPorIdSucursal(id);
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
