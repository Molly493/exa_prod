# 🌟 Proyecto Final: Plataforma Local de CI/CD, Observabilidad y Seguridad (DevSecOps)

## 🎯 Objetivo del Proyecto

Diseñar e implementar una infraestructura local y auto-contenida para el ciclo de vida de un servicio **Spring Boot (Auth Service)**. La plataforma simula un entorno de producción, integrando Continuous Integration/Continuous Delivery (CI/CD), Observabilidad (Logs y Métricas), Seguridad (DevSecOps) y operaciones básicas de alta disponibilidad/escalabilidad.

## 1. 🏗️ Arquitectura de la Solución

La solución se basa completamente en **Docker Compose** para orquestar la aplicación y todos los servicios de infraestructura.

### 1.1. Componentes del Stack

| Categoría | Servicio | Herramienta | Puerto Expuesto (Host) |
| :--- | :--- | :--- | :--- |
| **Aplicación** | `auth-service` | Spring Boot (Maven) | 8080 (Interno) |
| **Balanceo** | `load-balancer` | Nginx | **80** |
| **CI/CD** | `jenkins` | Jenkins | **8090** |
| **Seguridad** | `vault` | HashiCorp Vault | **8200** |
| **Métricas** | `prometheus` | Prometheus | **9090** |
| **Visualización** | `grafana` | Grafana | **3000** |
| **Logs** | `elasticsearch` | ELK Stack | **9200** |
| **Visualización Logs** | `kibana` | ELK Stack | **5601** |

### 1.2. Estructura del Repositorio

 ```
.
├── auth-service-project/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile      
├── infra/
│   ├── prometheus/prometheus.yml
│   ├── nginx/nginx.conf
│   └── filebeat/filebeat.yml
├── Jenkinsfile
└── docker-compose.yml
 ```

## 2. 📝 Instrucciones de Despliegue

### 2.1. Prerrequisitos

* Docker y Docker Compose (o Docker Engine con Compose CLI).
* Maven (para la construcción local si se requiere, pero el pipeline usa Maven dentro del contenedor Jenkins).

### 2.2. Inicialización de Vault (SETUP)

Para que el *pipeline* pueda acceder a los secretos, primero hay que escribirlos en Vault:

1.  Levantar el servicio Vault:
    ```bash
    docker compose up -d vault
    ```
2.  Acceder al contenedor de Vault e inicializar el secreto (usando el token de desarrollo `myroottoken`):
    ```bash
    docker exec -it vault sh
    vault login myroottoken
    vault secrets enable -version=2 secret 
    vault kv put secret/app/config DB_PASSWORD=SecretParaJenkins123
    exit
    ```

### 2.3. Ejecución Completa de la Infraestructura

Ejecuta este comando desde el directorio raíz para construir la aplicación (`auth-service`) y levantar todos los servicios:

```bash
docker compose up -d --build
```
### 2.3. Acceso a Interfaces

Una vez levantada la infraestructura, puedes acceder a los siguientes servicios clave:

| Interfaz | URL Local | Credenciales por Defecto |
| :--- | :--- | :--- |
| **Aplicación (LB)** | `http://localhost:80` | N/A |
| **Jenkins** | `http://localhost:8090` | Primer login requiere el código del log |
| **Grafana** | `http://localhost:3000` | admin/admin |
| **Kibana** | `http://localhost:5601` | N/A |



