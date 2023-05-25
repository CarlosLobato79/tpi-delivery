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
        EntityManager em = this.getEntityManager();
            TypedQuery<Comercio> query = em.createNamedQuery("Comercio.findByIdComercio", Comercio.class);
            query.setParameter("idComercio",id);
            Comercio comercio = query.getSingleResult();
            if(comercio.getIdComercio()  != id){
                throw new Exception("No se encontro");
            }
        return  query.getSingleResult();
    }

    public List<Comercio> traerPorNombre(String nombre) throws Exception {
        List<Comercio> listComercio = new ArrayList<>();

        EntityManager em = this.getEntityManager();
        TypedQuery<Comercio> query = em.createNamedQuery("Comercio.findByNombre", Comercio.class);
        query.setParameter("nombre",nombre);
        listComercio = query.getResultList();
        if(listComercio.size() == 0){
            throw new Exception("No se encontro");
        }
        return listComercio;
    }



    
}
