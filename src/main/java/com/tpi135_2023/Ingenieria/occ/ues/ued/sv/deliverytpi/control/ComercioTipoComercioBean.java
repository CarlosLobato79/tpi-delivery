package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.ComercioTipoComercio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
@Stateless
public class ComercioTipoComercioBean extends AbstractDataAccess<ComercioTipoComercio>{

    public ComercioTipoComercioBean() {
        super(ComercioTipoComercio.class, "DeliveryTpiUnitPersistence");
        
    }

    public ComercioTipoComercio traerPorIdComercio(int id) throws Exception {
        List<ComercioTipoComercio> p = new ArrayList<>();
        EntityManager em = this.getEntityManager();

        if (em != null) {
            TypedQuery<ComercioTipoComercio> query = em.createNamedQuery("Producto.findByIdProducto", ComercioTipoComercio.class);
            query.setParameter("idComercio",id);

            return query.getSingleResult();
        }
        return null;
    }
//    public Producto traerPorIdTipoComercio(int id) throws Exception {
//        List<Producto> p = new ArrayList<>();
//        EntityManager em = this.getEntityManager();
//
//        if (em != null) {
//            TypedQuery<Producto> query = em.createNamedQuery("Producto.findByIdProducto", Producto.class);
//            query.setParameter("idProducto",id);
//
//            return query.getSingleResult();
//        }
//        return null;
//    }
//
//
//
//    public List<Producto> buscarPorActivo(boolean estado) throws Exception{
//        List<Producto> p = new ArrayList<>();
//        EntityManager em = this.getEntityManager();
//        if(em != null){
//            TypedQuery<Producto> query = em.createNamedQuery("Producto.findByActivo", Producto.class);
//            query.setParameter("activo", estado);
//            p = query.getResultList();
//            return p;
//        }
//        return p;
//    }


}
