package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Comercio;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Territorio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Stateless
public class TerritorioBean extends AbstractDataAccess<Territorio>{

    public TerritorioBean() {
        super(Territorio.class, "DeliveryTpiUnitPersistence");
    }


    public Territorio traerPorIdTerritorio(int id) throws Exception {
        EntityManager em = this.getEntityManager();
        TypedQuery<Territorio> query = em.createNamedQuery("Territorio.findByIdTerritorio", Territorio.class);
        query.setParameter("idTerritorio",id);
        Territorio territorio = query.getSingleResult();
        if(territorio.getIdTerritorio()  != id){
            throw new Exception("No se encontro");
        }
        return  query.getSingleResult();
    }
}
