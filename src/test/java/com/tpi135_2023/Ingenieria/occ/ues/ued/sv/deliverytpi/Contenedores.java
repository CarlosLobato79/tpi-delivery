package com.tpi135_2023.Ingenieria.occ.ues.ued.sv.deliverytpi;

import java.nio.file.Paths;
import java.time.Duration;
import org.junit.ClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

public class Contenedores {

     static final Network network = Network.newNetwork();
     static final MountableFile WAR_FILE = MountableFile
               .forHostPath(Paths.get("target/deliverytpi.war").toAbsolutePath(), 0777);
     static final String JDBC_CONECTOR_DRIVER = Paths.get("postgresql-42.5.4.jar").toAbsolutePath().toString();
     static final MountableFile JDNI_RESOURCE = MountableFile
               .forHostPath(Paths.get("src/main/resources/META-INF/glassfish-resources.xml").toAbsolutePath(), 0777);

     static final MountableFile INIT_COMMANDS = MountableFile
               .forHostPath(Paths.get("commands-server.sh").toAbsolutePath(), 0777);
     static final String SQL_INIT_FILE = Paths.get("DeliveryDDL.sql").toAbsolutePath().toString();
     // Para capturar la salida
     static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Contenedores.class);

     // Base de datos info
     private static final String NAME_DB = "postgres";
     private static final String PASSWORD_DB = "1234";
     private static final String USER_DB = "postgres";

     /**
      * Se implemento classrule para asegurase que se in inicien al principio y
      * se destruya al terminar todos los test
      */
     @ClassRule
     public static PostgreSQLContainer<?> postgreSql = new PostgreSQLContainer<>(
               DockerImageName.parse("postgres:latest"))
               .withUsername(USER_DB)
               .withPassword(PASSWORD_DB)
               .withDatabaseName(NAME_DB)
               .withInitScript("DeliveryDDL.sql")
               .withExposedPorts(5432)
               .withNetwork(network)
               .withNetworkAliases("mired")
               .withStartupTimeout(Duration.ofSeconds(60));

     @ClassRule
     public static GenericContainer<?> payara = new GenericContainer<>(
               DockerImageName.parse("payara/server-full:6.2023.3-jdk17"))
               .dependsOn(postgreSql)

               // Pasando el proyecto
               .withCopyFileToContainer(WAR_FILE, "/opt/payara/deployments/deliverytpi.war")
               // JDNI RECURSO
               .withCopyFileToContainer(JDNI_RESOURCE, "/opt/payara/appserver/bin/glassfish-resources.xml")
               // JDBC DRIVER
               .withFileSystemBind(JDBC_CONECTOR_DRIVER,
                         "/opt/payara/appserver/glassfish/domains/domain1/lib/postgresql-42.5.4.jar",
                         BindMode.READ_ONLY)
               // ARCHIVO DE EJECUCION DE COMANDO
               .withCopyFileToContainer(INIT_COMMANDS, "/opt/payara/appserver/bin/commands-server.sh")

               // EXPONGO PUERTOS
               .withExposedPorts(8080, 4848)
               .withNetwork(network)
               .withLogConsumer(new Slf4jLogConsumer(LOGGER))
               .waitingFor(Wait.forHttp("/deliverytpi/comercio").forStatusCode(204))
               .withStartupTimeout(Duration.ofSeconds(60));

}