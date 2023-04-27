package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Direccion;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Territorio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class DireccionBean extends AbstractDataAccess<Direccion> {


    public DireccionBean() {
        super(Direccion.class, "DeliveryTpiUnitPersistence");
    }

    public Direccion traerIdPorDireccion(int id) throws Exception {
        EntityManager em = this.getEntityManager();
        TypedQuery<Direccion> query = em.createNamedQuery("Direccion.findByIdDireccion", Direccion.class);
        query.setParameter("idDireccion",id);
        Direccion direccion = query.getSingleResult();
        if( direccion.getIdDireccion() != id){
            throw new Exception("No se encontro");
        }
        return  query.getSingleResult();
    }
}
