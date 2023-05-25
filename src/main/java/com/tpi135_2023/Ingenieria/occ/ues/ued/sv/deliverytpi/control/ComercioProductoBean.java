package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control;


import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.ProductoComercio;

public class ComercioProductoBean extends AbstractDataAccess<ProductoComercio>{

    public ComercioProductoBean(Class entityClassPrincipal, String persistenUnitName) {
        super(ProductoComercio.class, "DeliveryTpiUnitPersistence");
    }
}
