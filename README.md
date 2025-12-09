#Estructura de Carpetas:

.
.
├── auth-service-project/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── infra/
│   ├── prometheus/
│   │   └── prometheus.yml
│   └── ... (otros archivos de infra)
├── docker-compose.yml
└── Jenkinsfile


#Construir y Desplegar (Escalabilidad Básica):

Ejecuta el siguiente comando para construir el servicio Spring Boot y desplegar todos los contenedores:
Bash

    # Correr la infraestructura completa
    docker compose up -d --build

    # Para probar la escalabilidad (ejecutar 3 instancias del servicio 'auth-service')
    # Nota: Asegúrate de que el archivo nginx.conf apunte a múltiples instancias.
    docker compose up -d --build --scale auth-service=3


# Acceso a las Herramientas
Herramienta	URL Local	Credenciales por Defecto
Aplicación (vía LB)	http://localhost:80	N/A
Jenkins	http://localhost:8090	Primer login requiere el código inicial del log
Prometheus	http://localhost:9090	N/A
Grafana	http://localhost:3000	admin/admin
Kibana	http://localhost:5601	N/A
Vault	http://localhost:8200	Token: myroottoken (Modo Dev)

