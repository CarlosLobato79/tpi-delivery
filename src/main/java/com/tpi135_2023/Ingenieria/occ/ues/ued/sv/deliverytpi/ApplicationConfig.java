/********************************************************************************
 * Copyright (c) 10/19/2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ********************************************************************************/
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;






@ApplicationPath("/")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
          final Set<Class<?>> classes = new HashSet<>();
          classes.add(CorsFiltro.class);
          
        return super.getClasses(); 
    }
    
    
}
