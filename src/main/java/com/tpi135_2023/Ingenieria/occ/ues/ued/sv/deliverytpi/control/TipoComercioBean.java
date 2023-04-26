/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;



import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.TipoComercio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.Serializable;
/**
 *
 * @author qwerty
 */
@Stateless
public class TipoComercioBean extends AbstractDataAccess<TipoComercio> implements Serializable {
    
    public TipoComercioBean() {
        super(TipoComercio.class, "DeliveryTpiUnitPersistence");
    }
    
    public TipoComercio traerPorIdTipoComercio(int id) throws Exception {
        EntityManager em = this.getEntityManager();
            TypedQuery<TipoComercio> query = em.createNamedQuery("TipoComercio.findByIdTipoComercio", TipoComercio.class);
            query.setParameter("idTipoComercio",id);
            TipoComercio tipoComercio = query.getSingleResult();
            if(tipoComercio.getIdTipoComercio()  != id){
                throw new Exception("No se encontro");
            }
        return  query.getSingleResult();
    }
    
}
