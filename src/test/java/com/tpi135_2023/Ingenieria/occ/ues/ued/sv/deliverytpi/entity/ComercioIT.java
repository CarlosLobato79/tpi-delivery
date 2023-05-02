/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.entity;

import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.Contenedores;
import com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi.boundary.RestResourcePattern;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * @author qwerty
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComercioIT extends Contenedores {

        static String endpoint;
        static Client cliente;
        static WebTarget target;
        static Long idComercioCreado;
        static Integer idTipoCreado;

        /**
         * Se encarga de inicializar los contenedores para realizar las pruebas
         */
        @BeforeAll
        public static void lanzarPayaraTest() {
                postgreSql.start();

                System.out.println("Comercio - lanzarPayara");
                payara.start();

                // Inicializando las entradas
                endpoint = "http://" + payara.getHost() + ":" + payara.getMappedPort(8080) + "/deliverytpi/";
                cliente = ClientBuilder.newClient();
                target = cliente.target(endpoint);

        }

        /**
         * Realiza la prueba de creacion de Comercio
         *
         * @see Comercio
         */
        @Order(1)
        @Test
        public void crearTest() {

                System.out.println("Comercio - crear");
                Assertions.assertTrue(postgreSql.isRunning());
                int esperado = Response.Status.CREATED.getStatusCode();
                Comercio creado = new Comercio();
                creado.setActivo(Boolean.TRUE);
                creado.setNombre("Farmacia Santa Maria");
                Response respuesta = target.path("comercio").request(MediaType.APPLICATION_JSON)
                                .post(Entity.entity(creado, MediaType.APPLICATION_JSON));
                Assertions.assertEquals(esperado, respuesta.getStatus());
                Assertions.assertTrue(respuesta.getHeaders().containsKey("location"));
                idComercioCreado = Long.valueOf(respuesta.getHeaderString("location").split("comercio/")[1]);
                Assertions.assertNotNull(idComercioCreado);
                // validar excepciones
                respuesta = target.path("comercio").request(MediaType.APPLICATION_JSON)
                                .post(Entity.json(null));
                Assertions.assertEquals(400, respuesta.getStatus());
        }

        /**
         * Busca un comercio por su Identificador
         */
        @Order(2)
        @Test
        public void findByIdTest() {
                System.out.println("Comercio - findById");
                Assertions.assertTrue(payara.isRunning());
                Assertions.assertNotNull(idComercioCreado);
                int esperado = 200;
                Response respuesta = target.path("/comercio/{id}").resolveTemplate("id", idComercioCreado)
                                .request(MediaType.APPLICATION_JSON).get();
                Assertions.assertEquals(esperado, respuesta.getStatus());
                Comercio encontrado = respuesta.readEntity(Comercio.class);
                Assertions.assertEquals(idComercioCreado, encontrado.getIdComercio());
                // excepciones
                respuesta = target.path("/comercio/{id}").resolveTemplate("id", 999)
                                .request(MediaType.APPLICATION_JSON).get();
                Assertions.assertEquals(404, respuesta.getStatus());
                Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.ID_NOT_FOUND));
        }

        /**
        * Crea un tipo de comercio
        *
        * @see TipoComercio
        */
        @Order(3)
        @Test
        public void crearTipoComercioTest() {
        System.out.println("Comercio - crearTipoComercio");
        Assertions.assertTrue(payara.isRunning());
        int esperado = Response.Status.CREATED.getStatusCode();
        TipoComercio creado = new TipoComercio();
        creado.setActivo(Boolean.TRUE);
        creado.setNombre("Farmacia");
        Response respuesta =
        target.path("tipocomercio").request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(creado, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("location"));
        idTipoCreado =
        Integer.valueOf(respuesta.getHeaderString("location").split("tipocomercio/")[1]);
        Assertions.assertNotNull(idTipoCreado);
        respuesta = target.path("tipocomercio").request(MediaType.APPLICATION_JSON)
        .post(Entity.json(null));
        Assertions.assertEquals(400, respuesta.getStatus());
        }
        
        /**
        * Valida que un comercio creado previamente no posea un Tipo asociado
        *
        * @see ComercioTipoComercio
        */
        @Order(4)
        @Test
        public void validarTipoVacioTest() {
        System.out.println("Comercio - validarTipoVacio");
        Assertions.assertTrue(payara.isRunning());
        int esperado = 200;
        Response respuesta =
        target.path("/comercio/{id}/tipocomercio").resolveTemplate("id",
        idComercioCreado)
        .request(MediaType.APPLICATION_JSON).get();
        Assertions.assertEquals(esperado, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.CONTAR_REGISTROS));
        Assertions.assertEquals(0,
        Integer.valueOf(respuesta.getHeaderString(RestResourcePattern.CONTAR_REGISTROS)));
        //excepciones
        respuesta = target.path("/comercio/{id}/tipocomercio").resolveTemplate("id",
        999)
        .request(MediaType.APPLICATION_JSON).get();
        Assertions.assertEquals(404, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.ID_NOT_FOUND));
        }
        
        /**
        * Responsable de asociar un Tipo con un Comercio que han sido creados
        * previamente
        *
        * @see ComercioTipoComercio
        */
        @Order(5)
        @Test
        public void agregarTipoAComercio() {
        System.out.println("Comercio - agregarTipoAComercio");
        Assertions.assertTrue(payara.isRunning());
        int esperado = Response.Status.CREATED.getStatusCode();
        Response respuesta =
        target.path("comercio/{idComercio}/tipocomercio/{idTipoComercio}")
        .resolveTemplate("idComercio", idComercioCreado)
        .resolveTemplate("idTipoComercio", idTipoCreado)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity("", MediaType.APPLICATION_JSON));
        if (respuesta.getStatus() == 400) {
        System.out.println(respuesta.getHeaderString(RestResourcePattern.WRONG_PARAMETER));
        }
        Assertions.assertEquals(esperado, respuesta.getStatus());
        //excepciones
        respuesta =
        target.path("comercio/{idComercio}/tipocomercio/{idTipoComercio}")
        .resolveTemplate("idComercio", 9999)
        .resolveTemplate("idTipoComercio", 9999)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity("", MediaType.APPLICATION_JSON));
        Assertions.assertEquals(400, respuesta.getStatus());
        }

         /**
         * Valida que un Comercio posea tipos asociados
         *
         * @see ComercioTipoComercio
         */
         @Order(6)
         @Test
         public void validarTipoLlenoTest() {
         System.out.println("Comercio - validarTipoLleno");
         Assertions.assertTrue(payara.isRunning());
         int esperado = 200;
         Response respuesta =
         target.path("/comercio/{id}/tipocomercio").resolveTemplate("id",
         idComercioCreado)
         .request(MediaType.APPLICATION_JSON).get();
         Assertions.assertEquals(esperado, respuesta.getStatus());
         Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourcePattern.CONTAR_REGISTROS));
         Assertions.assertEquals(1,
         Integer.valueOf(respuesta.getHeaderString(RestResourcePattern.CONTAR_REGISTROS)));
         }

         /**
         * Crea Tereritorios, Direccion y Sucursal, para luego asociarlos a un
         * Comercio creado previamente
         *
         * @see Territorio
         * @see Direccion
         * @see Sucursal
         */
         @Order(7)
         @Test
         public void crearSucursalTest() {
         System.out.println("Comercio - crearSucursal");
         Assertions.assertTrue(payara.isRunning());
         int esperado = 200;
         //crear territorios
         Territorio sv = new Territorio();
         sv.setHijosObligatorios(14);
         sv.setIdTerritorioPadre(null);
         sv.setNombre("El Salvador");
         sv.setTextoVisible("pais");
         Response respuestaSv =
         target.path("territorio").request(MediaType.APPLICATION_JSON)
         .post(Entity.entity(sv, MediaType.APPLICATION_JSON));
         Assertions.assertEquals(Response.Status.CREATED.getStatusCode(),
         respuestaSv.getStatus());
         Assertions.assertTrue(respuestaSv.getHeaders().containsKey("location"));
         sv.setIdTerritorio(Integer.valueOf(respuestaSv.getHeaderString("location").split("territorio/")[1]));
         Assertions.assertNotNull(sv.getIdTerritorio());
         Territorio santaAna = new Territorio();
         santaAna.setIdTerritorioPadre(sv);
         santaAna.setNombre("Santa Ana");
         santaAna.setTextoVisible("departamento");
         santaAna.setHijosObligatorios(13);
         Response respuestaSantaAna =
         target.path("territorio").request(MediaType.APPLICATION_JSON)
         .post(Entity.entity(santaAna, MediaType.APPLICATION_JSON));
         Assertions.assertEquals(Response.Status.CREATED.getStatusCode(),
         respuestaSantaAna.getStatus());
         Assertions.assertTrue(respuestaSantaAna.getHeaders().containsKey("location"));
         santaAna.setIdTerritorio(Integer.valueOf(respuestaSantaAna.getHeaderString("location").split("territorio/")[1]));
         //excepciones territorio
         respuestaSv = target.path("territorio").request(MediaType.APPLICATION_JSON)
         .post(Entity.json(null));
         Assertions.assertEquals(400, respuestaSv.getStatus());
         //crear direccion
         Direccion direccion = new Direccion();
         direccion.setIdTerritorio(santaAna);
         direccion.setDireccion("Final 1a Av Nte y 1a C pte. No 32");
         direccion.setLatitud(BigDecimal.TEN);
         direccion.setLongitud(BigDecimal.ONE);
         direccion.setReferencias("50 mts. al sur del palo de mango");
         Response respuestaDireccion =
         target.path("direccion").request(MediaType.APPLICATION_JSON)
         .post(Entity.entity(direccion, MediaType.APPLICATION_JSON));
         Assertions.assertEquals(Response.Status.CREATED.getStatusCode(),
         respuestaDireccion.getStatus());
         Assertions.assertTrue(respuestaDireccion.getHeaders().containsKey("location"));
         direccion.setIdDireccion(Long.valueOf(respuestaDireccion.getHeaderString("location").split("direccion/")[1]));
         // excepciones direccion
         respuestaDireccion =
         target.path("direccion").request(MediaType.APPLICATION_JSON)
         .post(Entity.json(null));
         Assertions.assertEquals(400, respuestaDireccion.getStatus());

//         asociar direccion a sucursal
         Sucursal s = new Sucursal();
         s.setIdComercio(new Comercio(idComercioCreado));
         s.setIdDireccion(BigInteger.valueOf(direccion.getIdDireccion()));
         s.setNombre("La Rotonda");
         Response respuestaSucursal = target.path("comercio/{idComercio}/sucursal")
         .resolveTemplate("idComercio", idComercioCreado)
         .request(MediaType.APPLICATION_JSON)
         .post(Entity.entity(s, MediaType.APPLICATION_JSON));
         Assertions.assertEquals(Response.Status.CREATED.getStatusCode(),
         respuestaSucursal.getStatus());
         Assertions.assertTrue(respuestaSucursal.getHeaders().containsKey("location"));
         direccion.setIdDireccion(Long.valueOf(respuestaSucursal.getHeaderString("location").split("sucursal/")[1]));
         //excepciones
         respuestaSucursal = target.path("comercio/{idComercio}/sucursal")
         .resolveTemplate("idComercio", 9999)
         .request(MediaType.APPLICATION_JSON)
         .post(Entity.entity(s, MediaType.APPLICATION_JSON));
         Assertions.assertEquals(400, respuestaSucursal.getStatus());
         }
}
