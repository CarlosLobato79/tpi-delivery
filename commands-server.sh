#!/bin/bash
echo "==================================================="
echo "Ejecutando script"
echo "==================================================="
asadmin start-domain
echo "-------------------------------------------------------"
echo "CREANDO EL POOL DE CONEXIONES DESDE GLASSFISH RESOURCE"
echo "-------------------------------------------------------"
#hago el pool de conexiones y el recurso desde el archivo
asadmin --passwordfile=/opt/payara/passwordFile  add-resources /opt/payara/appserver/bin/glassfish-resources.xml
echo "-------------------------------------------------------"
echo "VIENDO LOS POOL DE CONEXIONEs"
echo "-------------------------------------------------------"
asadmin --passwordfile=/opt/payara/passwordFile list-jdbc-connection-pools

echo "-------------------------------------------------------"
echo "PROBANDO LA CONEXION"
echo "-------------------------------------------------------"
asadmin --passwordfile=/opt/payara/passwordFile ping-connection-pool tpi-pool

echo "-------------------------------------------------------"
echo "DESPLEGANDO EL .war EN EL SERVIDOR"
echo "-------------------------------------------------------"
#hago deploy de la app
asadmin --passwordfile=/opt/payara/passwordFile deploy /opt/payara/deployments/deliverytpi.war

echo "-------------------------------------------------------"
echo "PROBANDO LA CONEXION MODIFICADA CON EL PUERTO"
echo "-------------------------------------------------------"
asadmin --passwordfile=/opt/payara/passwordFile ping-connection-pool tpi-pool
echo "-------------------------------------------------------"
echo "LISTA DE ENDPOINTS MAPEADOS EN EL PROYECTO"
echo "-------------------------------------------------------"
asadmin --passwordfile=/opt/payara/passwordFile list-rest-endpoints deliverytpi
echo "-----------------SERVIDOR CORRIENDO INICIAR TEST-----------------"
tail -f /dev/null

