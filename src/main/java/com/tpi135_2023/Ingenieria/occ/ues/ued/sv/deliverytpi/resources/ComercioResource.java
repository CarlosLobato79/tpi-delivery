/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import java.io.Serializable;
import java.util.List;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.boundary.RestResourcePattern;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.*;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.*;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 *
 * @author qwerty
 */
@Path("/comercio")
public class ComercioResource extends AbstracRestResources<Comercio>  {
    @Inject
    ComercioBean cb;
    

    @Override
    protected AbstractDataAccess<Comercio> getDataAccesBean() {
        return this.cb;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response ingresar(Comercio entidad) {

        Response.ResponseBuilder rb;
        if (entidad == null) {
            rb = Response.status(Response.Status.BAD_GATEWAY);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("deny", MediaType.APPLICATION_JSON);
            rb.entity(entidad);
            return rb.build();
        }
        try {
            Comercio entidadCreada = this.getDataAccesBean().Ingresar(entidad);
            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header("location", "comercio/" + entidadCreada.getIdComercio());
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
    @Path("nombre/{nombre}")
    public Response buscarPorNombre(@PathParam("nombre") String nombre){
        List<Comercio> lp = null;
        Response.ResponseBuilder rb;
        try{
            lp = cb.traerPorNombre(nombre);
            rb = Response.ok();
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.entity(lp);
            return rb.build();
        }catch(Exception e){
            e.printStackTrace();
        }
        rb = Response.status(Response.Status.NO_CONTENT);
        rb.header("Content-Type", MediaType.TEXT_HTML);
        return rb.build();
    }

    @POST
    @Path("/{idC}/tipocomercio/{idT}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response asociarTipoComercio(String json, @PathParam("idC") int idComercio,
            @PathParam("idT") int idTipoComercio) {
     
     
        Comercio comercio;
        TipoComercio tipoComercio;
        Response.ResponseBuilder rb;
        TipoComercioBean tcb = new TipoComercioBean();
        try {

            comercio = cb.traerPorIdComercio(idComercio);
            tipoComercio = tcb.traerPorIdTipoComercio(idTipoComercio);

            comercio.getComercioTipoComercioList().add(new ComercioTipoComercio(idComercio, idTipoComercio));

            tipoComercio.getComercioTipoComercioList().add(new ComercioTipoComercio(idComercio,idTipoComercio));

            cb.Actualizar(comercio);
            tcb.Actualizar(tipoComercio);

            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);

            return rb.build();

        } catch (Exception e) {
            rb = Response.status(Status.BAD_REQUEST);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            // rb.header("deny", MediaType.APPLICATION_JSON);
        }
        return rb.build();
    }

    @POST
    @Path("/{idC}/tipoproducto/{idP}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response asociarTipoProducto(String json, @PathParam("idC") int idComercio, @PathParam("idP") int idTipoProducto) {

        Comercio comercio;
        Producto productoComercio;
        Response.ResponseBuilder rb;
        ProductoBean tcb = new ProductoBean();
        try {

            comercio = cb.traerPorIdComercio(idComercio);
            productoComercio = tcb.traerPorIdProducto(idTipoProducto);

            comercio.getProductoComercioList().add(new ProductoComercio(idTipoProducto,idComercio));
            cb.Actualizar(comercio);
            rb = Response.status(Response.Status.CREATED);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);

            return rb.build();

        } catch (Exception e) {
            rb = Response.status(Status.BAD_REQUEST);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            // rb.header("deny", MediaType.APPLICATION_JSON);
        }
        return rb.build();
    }

    @POST
    @Path("/{idC}/sucursal/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response asociaraSucursal(Sucursal sucursal, @PathParam("idC") int idComercio) {


        Comercio comercio;
        Response.ResponseBuilder rb;

        try {

            comercio = cb.traerPorIdComercio(idComercio);
            comercio.getSucursalList().add(sucursal);
            cb.Actualizar(comercio);

            rb = Response.status(Response.Status.CREATED);
            rb.header("location", "sucursal/"+comercio.getIdComercio());
            rb.header("Content-Type", MediaType.APPLICATION_JSON);

            return rb.build();

        } catch (Exception e) {
            rb = Response.status(Status.BAD_REQUEST);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            // rb.header("deny", MediaType.APPLICATION_JSON);
        }
        return rb.build();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response buscarPorId(@PathParam("id") int id) {
        Comercio cEntity = null;
        Response.ResponseBuilder rb;
        try {
            cEntity = cb.traerPorIdComercio(id);
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

    @GET
    @Path("/{id}/tipocomercio")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response traerIdTipoComercio(@PathParam("id") int id) {
        TipoComercioBean tcb = new TipoComercioBean();
        List<ComercioTipoComercio> tipoComercioList;
        Comercio cm;
        Response.ResponseBuilder rb;
        try {
            cm= cb.traerPorIdComercio(id);
            rb = Response.ok();
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header(RestResourcePattern.CONTAR_REGISTROS,cm.getComercioTipoComercioList().size());

            return rb.build();
        } catch (Exception e) {
            rb = Response.status(Response.Status.NOT_FOUND);
            rb.header("Content-Type", MediaType.APPLICATION_JSON);
            rb.header(RestResourcePattern.ID_NOT_FOUND, id);
            return rb.build();
        }
    }



    
}
