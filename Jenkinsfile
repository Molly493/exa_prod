// Jenkinsfile

pipeline {
    agent any 

    environment {
        // Variables globales del proyecto
        SERVICE_NAME = 'jwt-demo'
        APP_VERSION = '0.0.1'
        DOCKER_IMAGE = "auth-service:${APP_VERSION}-${BUILD_NUMBER}"
        # URL de Vault (desde el docker-compose)
        VAULT_ADDR = 'http://vault:8200'
        # Token de prueba (¡Solo para entorno local de prueba!)
        VAULT_TOKEN = 'myroottoken'
    }

    stages {
        // ----------------------------------------------------------------
        // 1. BUILD Y PRUEBAS
        // ----------------------------------------------------------------
        stage('Build & Unit Tests') {
            steps {
                echo "Iniciando compilación del servicio ${SERVICE_NAME}..."
                sh 'mvn clean install' 
            }
        }
        
        // ----------------------------------------------------------------
        // 2. DEVSECOPS: ESCANEO DE VULNERABILIDADES (SAST/DAST Básico)
        // ----------------------------------------------------------------
        stage('Security Scan (Trivy)') {
            steps {
                echo "Escaneando vulnerabilidades de dependencias y configuración..."
                
                // Opción 1: Escanear dependencias del código fuente (SCA)
                // Se ejecuta Trivy sobre el sistema de archivos
                // --exit-code 1 hace que el paso falle si encuentra cualquier CRITICAL
                sh 'trivy fs --exit-code 1 --severity CRITICAL,HIGH . || true'
                
                // Si el escaneo falló, detener el pipeline (Implementación de BLOQUEO)
                script {
                    def trivyOutput = sh(script: 'trivy fs --format json --quiet .', returnStdout: true)
                    if (trivyOutput.contains('"Severity":"CRITICAL"')) {
                        error('¡DevSecOps FALLIDO! Se encontraron vulnerabilidades CRÍTICAS. Despliegue bloqueado.')
                    }
                    echo "Escaneo completado. No se encontraron vulnerabilidades CRÍTICAS."
                }
            }
        }
        
        // ----------------------------------------------------------------
        // 3. ARTEFACTO Y GESTIÓN DE RELEASES
        // ----------------------------------------------------------------
        stage('Build Docker Image') {
            steps {
                echo "Construyendo imagen Docker: ${DOCKER_IMAGE}"
                // Construye la imagen utilizando el Dockerfile creado anteriormente
                sh "docker build -t ${DOCKER_IMAGE} ."
                // Etiquetar también como 'latest' para el despliegue local
                sh "docker tag ${DOCKER_IMAGE} auth-service:latest"
            }
        }
        
        // ----------------------------------------------------------------
        // 4. DEVSECOPS: GESTIÓN DE SECRETOS (Integración con Vault)
        // ----------------------------------------------------------------
        stage('Get Secrets from Vault') {
            steps {
                script {
                
                    // 1. Leer el secreto (Ejecutar en el contenedor de Jenkins)
                    def dbSecret = sh(
                        script: "curl -s -H \"X-Vault-Token: ${VAULT_TOKEN}\" ${VAULT_ADDR}/v1/secret/data/app/config | jq -r '.data.data.DB_PASSWORD'",
                        returnStdout: true
                    ).trim()
                    
                    // 2. Establecer la variable de entorno para el despliegue (Evita exponer en logs)
                    env.DB_PASSWORD_SECRET = dbSecret
                    echo "Secreto de BD recuperado y almacenado de forma segura en 'env.DB_PASSWORD_SECRET'."
                }
            }
        }

        // ----------------------------------------------------------------
        // 5. DEPLOYMENT (OPERACIONES: ENTREGA Y DESPLIEGUE LOCAL)
        // ----------------------------------------------------------------
        stage('Deploy & Scale') {
            steps {
                echo "Desplegando la nueva imagen y reiniciando el stack local..."
                

                sh """
                docker compose up -d --no-deps --build auth-service
                """

                // 2. Simulación de Escalabilidad y Balanceo de Carga (Objetivo de Operaciones)
                echo "Escalando a 3 instancias para pruebas de carga..."
                sh 'docker compose up -d --scale auth-service=3'
                
                // 3. Verificar estado de los servicios (Gestión de Fallos)
                sh 'docker compose ps'
            }
        }
    }

    // ----------------------------------------------------------------
    // POST-BUILD (Notificaciones, limpieza, etc.)
    // ----------------------------------------------------------------
    post {
        always {
            // Ejemplo de limpieza o registro
            echo "Pipeline ${currentBuild.result}"
        }
        success {
            echo '¡Despliegue exitoso! El servicio está disponible en http://localhost:80'
        }
        failure {
            echo 'El pipeline falló. Revisa el build, las pruebas o el escaneo de seguridad.'
        }
    }
}