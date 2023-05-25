package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.resources;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.AbstractDataAccess;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.control.ComercioTipoComercioBean;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity.ComercioTipoComercio;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

@Path("/comerciotipocomercio")
public class ComercioTipoComercioResources extends AbstracRestResources<ComercioTipoComercio>{


    @Inject
    ComercioTipoComercioBean ctcb;

    @Override
    protected AbstractDataAccess<ComercioTipoComercio> getDataAccesBean() {
        return this.ctcb;
    }

}
