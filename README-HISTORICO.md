# 📚 Online Library Microservices

Sistema de biblioteca online desarrollado con arquitectura de microservicios utilizando Spring Boot, Spring Cloud y autenticación JWT.

## 👥 Integrantes

| Nombre | Rol |
|---|---|
| Brayan Quitian | Desarrollo de microservicios: cart, notification, review, recommendation, eureka-server |
| Vania Fernanda Bustos | Desarrollo de microservicios: auth, user, book, inventory, order, payment, api-gateway |

**Asignatura:** Desarrollo FullStack 1 — DSY1103
**Institución:** DuocUC

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Cloud | Latest |
| Spring Security | Incluido en Spring Boot |
| JWT | jjwt |
| Eureka Server | Spring Cloud Netflix |
| API Gateway | Spring Cloud Gateway |
| MySQL | 8.0 |
| JPA / Hibernate | Incluido en Spring Boot |
| Flyway | 11.14.1 |
| Swagger / OpenAPI | springdoc-openapi 2.8.9 |
| Maven | 3.x |
| Docker | Latest |

---

## 🏗️ Arquitectura del proyecto

```
Cliente
   │
   ▼
API Gateway (puerto 8080)
   │  ├── Valida JWT
   │  └── Enruta peticiones
   │
   ├──▶ auth-service        (8081)
   ├──▶ book-service        (8082)
   ├──▶ cart-service        (8083)
   ├──▶ order-service       (8085)
   ├──▶ inventory-service   (8086)
   ├──▶ review-service      (8087)
   ├──▶ payment-service     (8088)
   ├──▶ user-service        (8089)
   ├──▶ notification-service(8090)
   └──▶ recommendation-service (8091)
            │
            ▼
      Eureka Server (8761)
```

---

## 📦 Microservicios implementados

| Microservicio | Puerto | Base de datos | Descripción |
|---|---|---|---|
| eureka-server | 8761 | — | Registro y descubrimiento de servicios |
| api-gateway | 8080 | — | Enrutamiento centralizado y validación JWT |
| auth-service | 8081 | auth_db | Registro, login y generación de JWT |
| book-service | 8082 | book_db | CRUD de libros |
| cart-service | 8083 | cart_db | Gestión del carrito de compras |
| order-service | 8085 | order_db | Gestión de órdenes de compra |
| inventory-service | 8086 | inventory_db | Control de stock de libros |
| review-service | 8087 | review_db | Reseñas de libros por usuarios |
| payment-service | 8088 | payment_db | Gestión de pagos |
| user-service | 8089 | user_db | Gestión de perfiles de usuario |
| notification-service | 8090 | notification_db | Notificaciones a usuarios |
| recommendation-service | 8091 | recommendation_db | Recomendaciones de libros |

---

## 🔐 Seguridad

El proyecto implementa autenticación JWT centralizada a través del API Gateway.

**Flujo de autenticación:**

```
1. Usuario envía POST /auth/login con email y password
2. auth-service valida credenciales y genera token JWT
3. Cliente incluye token en header: Authorization: Bearer <token>
4. API Gateway intercepta la petición y valida el JWT
5. Si es válido, enruta al microservicio correspondiente
6. Si no es válido, retorna 401 Unauthorized
```

**Endpoints públicos (sin JWT):**
- `POST /auth/login`
- `POST /auth/register`

**Endpoints protegidos (requieren JWT):** todos los demás.

---

## 🛣️ Rutas principales del Gateway

| Método | Ruta Gateway | Microservicio destino |
|---|---|---|
| POST | /auth/login | auth-service |
| POST | /auth/register | auth-service |
| GET/POST/PUT/DELETE | /books/** | book-service |
| GET/POST/DELETE | /cart/** | cart-service |
| POST | /orders/** | order-service |
| GET/POST | /inventory/** | inventory-service |
| POST | /payments/** | payment-service |
| GET/POST/DELETE | /reviews/** | review-service |
| GET/POST/DELETE | /users/** | user-service |
| GET/POST | /notifications/** | notification-service |
| GET/POST/DELETE | /recommendations/** | recommendation-service |

---

## 📖 Documentación Swagger

Cada microservicio expone su documentación en `/swagger-ui/index.html`:

| Microservicio | URL Swagger (local) |
|---|---|
| auth-service | http://localhost:8081/swagger-ui/index.html |
| book-service | http://localhost:8082/swagger-ui/index.html |
| cart-service | http://localhost:8083/swagger-ui/index.html |
| order-service | http://localhost:8085/swagger-ui/index.html |
| inventory-service | http://localhost:8086/swagger-ui/index.html |
| review-service | http://localhost:8087/swagger-ui/index.html |
| payment-service | http://localhost:8088/swagger-ui/index.html |
| user-service | http://localhost:8089/swagger-ui/index.html |
| notification-service | http://localhost:8090/swagger-ui/index.html |
| recommendation-service | http://localhost:8091/swagger-ui/index.html |

---

## ▶️ Instrucciones de ejecución local

### Prerrequisitos
- Java 21 instalado
- MySQL 8 corriendo en localhost:3306
- Maven instalado
- IntelliJ IDEA (recomendado)

### 1. Crear bases de datos en MySQL

```sql
CREATE DATABASE auth_db;
CREATE DATABASE book_db;
CREATE DATABASE cart_db;
CREATE DATABASE order_db;
CREATE DATABASE inventory_db;
CREATE DATABASE payment_db;
CREATE DATABASE review_db;
CREATE DATABASE user_db;
CREATE DATABASE notification_db;
CREATE DATABASE recommendation_db;
```

### 2. Clonar el repositorio

```bash
git clone https://github.com/Quiti-1556/online-library-microservices.git
cd online-library-microservices
```

### 3. Orden de arranque (importante)

```
1. eureka-server        → esperar que levante en http://localhost:8761
2. api-gateway          → esperar que se registre en Eureka
3. auth-service         → base de autenticación
4. Los demás servicios  → en cualquier orden
```

### 4. Verificar que los servicios están activos

Abrí http://localhost:8761 — deberías ver todos los microservicios registrados en Eureka.

### 5. Probar con Postman

**Login:**
```
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

**Usar el token:**
```
Authorization: Bearer <token_recibido>
```

**Ejemplo crear libro:**
```
POST http://localhost:8080/books
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert Martin",
  "price": 29.99,
  "stock": 10
}
```

---

## 🐳 Instrucciones de ejecución con Docker

### Prerrequisitos
- Docker Desktop instalado y corriendo

### Construir y levantar todos los servicios

```bash
docker-compose up --build
```

### Verificar contenedores activos

```bash
docker ps
```

### Detener todos los servicios

```bash
docker-compose down
```

---

## 🧪 Pruebas unitarias

El proyecto incluye pruebas unitarias con JUnit 5 y Mockito en cada microservicio, ubicadas en `src/test/java`.

**Ejecutar pruebas desde IntelliJ:**
- Clic derecho sobre la carpeta `test` → `Run All Tests`

**Ejecutar desde terminal:**
```bash
mvn test
```

Las pruebas cubren la lógica de negocio de los ServiceImpl usando el patrón Given-When-Then con mocks de repositorios.

---

## 📁 Estructura del proyecto

```
online-library-microservices/
├── eureka-server/
├── api-gateway/
├── auth-service/
├── book-service/
├── cart-service/
├── order-service/
├── inventory-service/
├── payment-service/
├── review-service/
├── user-service/
├── notification-service/
├── recommendation-service/
├── docker-compose.yml
└── README.md
```