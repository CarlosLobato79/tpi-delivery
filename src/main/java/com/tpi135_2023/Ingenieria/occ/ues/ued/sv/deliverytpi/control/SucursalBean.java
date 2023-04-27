package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Comercio;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Producto;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Sucursal;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;



@Stateless
public class SucursalBean extends AbstractDataAccess<Sucursal>{


    public SucursalBean() {
        super(Sucursal.class, "DeliveryTpiUnitPersistence");
    }

    public Sucursal traerPorIdSucursal(int id) throws Exception {
        EntityManager em = this.getEntityManager();
        TypedQuery<Sucursal> query = em.createNamedQuery("Sucursal.findByIdSucursal", Sucursal.class);
        query.setParameter("idSucursal",id);
        Sucursal sucursal = query.getSingleResult();
        if(sucursal.getIdSucursal()  != id){
            throw new Exception("No se encontro");
        }
        return  query.getSingleResult();
    }


}
