/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.Comercio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qwerty
 */
@Stateless
public class ComercioBean extends AbstractDataAccess<Comercio>  implements Serializable{
    
    public ComercioBean() {
        super(Comercio.class, "DeliveryTpiUnitPersistence");
    }
    
    public Comercio traerPorIdComercio(int id) throws Exception {
        List<Comercio> p = new ArrayList<>();
        EntityManager em = this.getEntityManager();
        
        if (em != null) {
            TypedQuery<Comercio> query = em.createNamedQuery("Comercio.findByIdComercio", Comercio.class);
            query.setParameter("idComercio",id);
            
            return query.getSingleResult();
        }
        return null;
    }

    
}
