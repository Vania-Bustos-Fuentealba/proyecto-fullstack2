> Actualización 26/11/2026: este documento conserva la revisión inicial. El estado integrado y las pruebas vigentes están en [ENTREGA.md](ENTREGA.md).

# Administración: preparación y comprobación

Rama local: feature/react-tienda, desde 0c28557 de Quiti-1556/online-library-microservices. El cambio previo de .idea/workspace.xml se conserva. Esta revisión no modifica carrito, compras ni otros servicios del equipo.

## Instalación limpia

La usuaria confirmó una base nueva. Las correcciones de V1 de Auth y V2 de perfiles no son una estrategia para una base con historial previo de Flyway. No borrar una base compartida para ejecutar esta revisión.

1. Conservar el archivo .env local. Para otra instalación, copiar .env.example a .env y reemplazar los tres secretos. JWT_SECRET debe contener al menos 32 bytes aleatorios en Base64. ADMIN_INITIAL_PASSWORD debe tener al menos 12 caracteres.
2. Iniciar Docker Desktop. Ejecutar desde este repositorio:

```powershell
docker compose -p online-library-admin -f docker-compose.admin.yml config --quiet
docker compose -p online-library-admin -f docker-compose.admin.yml up -d --build
```

Esta configuración levanta MySQL, Auth, perfiles, libros y Gateway. Publica únicamente el Gateway en 127.0.0.1:18080. Sus rutas usan los nombres internos de Docker y no necesitan Eureka en esta comprobación. El docker-compose.yml grupal mantiene Eureka.

3. Desde front-react del frontend:

```powershell
$env:GATEWAY_URL = 'http://127.0.0.1:18080'
npm run dev
```

La cuenta inicial es admin@library.cl; su contraseña es el valor local ADMIN_INITIAL_PASSWORD. No hay contraseña predeterminada pública. Si la cuenta ya existe, cambiar esa variable no cambia su contraseña.

Para detener esta instalación sin borrar sus datos: `docker compose -p online-library-admin -f docker-compose.admin.yml stop`.

## Contratos y permisos

- POST /auth/login y /auth/register son públicos; el registro siempre crea CLIENT.
- /auth/users: GET lista y POST crea. /auth/users/{id}: GET consulta, PUT edita y DELETE elimina. Solo ADMIN; las respuestas no incluyen contraseña ni hash.
- /auth/session permite al Gateway comprobar la identidad y rol vigentes. Firma y vencimiento del JWT se verifican antes de consultar la cuenta.
- /books: lectura autenticada; creación, edición y eliminación solo ADMIN por el Gateway.
- /users contiene perfiles. Sus IDs no son IDs de cuentas de Auth. Las ediciones y eliminaciones administrativas sincronizan por correo.
- La cuenta principal conserva su correo y rol; no se permite eliminarla ni eliminar la cuenta propia. La cuenta propia tampoco cambia su rol/correo desde este mantenedor.

Auth aplica Spring Security directamente. Libros y perfiles dependen de la autorización del Gateway y de permanecer privados en la red Docker. No publicar sus puertos sin incorporar una protección equivalente.

La cuenta y el perfil se guardan en bases distintas: no hay transacción distribuida. Un fallo HTTP no se anuncia como éxito; un fallo entre los commits de ambas bases puede requerir reconciliación.

El alta sincroniza mediante PUT /users/email/{email}; puede reintentarse si un timeout dejó solo el perfil, conservando teléfono/dirección. Se retiró la eliminación antigua que ocultaba errores del servicio de perfiles; el controlador usa exclusivamente AdminAccountService.

## Migraciones y documentación API

Se usa spring-boot-starter-flyway para activar Flyway en Spring Boot 4, junto con flyway-mysql. Hibernate valida el esquema después de migrarlo. V1 de Auth crea auth_users, que es la tabla mapeada por la entidad; V2 de perfiles no presupone una columna password inexistente.

Springdoc 3.0.3 corresponde a Spring Boot 4.0.x según la [matriz oficial](https://springdoc.org/#what-is-the-compatibility-matrix-of-springdoc-openapi-with-spring-boot). La documentación de cada servicio está en /v3/api-docs y /swagger-ui/index.html dentro de la red interna. Para consultar el JSON sin abrir un puerto:

```powershell
docker compose -p online-library-admin -f docker-compose.admin.yml exec -T auth-service wget -qO- http://localhost:8080/v3/api-docs
```

Repetir con book-service o user-service. Este comando obtiene documentación, no tokens. Para exponer Swagger en una entrega compartida, acordar un proxy de documentación con el equipo, conservando el aislamiento de las API.

## Pruebas

Con Java 17 o superior, desde cada módulo:

Para una ejecución nueva, anteponer clean a la fase (por ejemplo, ./mvnw.cmd clean test). El repositorio contiene binarios target versionados; los cambios generados por esta revisión se retiraron del diff y conviene recompilar desde cero.

- Auth: `./mvnw.cmd -Dtest=UserServiceImplTest,AdminAccountServiceTest,AdminSecurityTest,PasswordRulesTest test`.
- Perfiles: `./mvnw.cmd test`.
- Libros: `./mvnw.cmd -Dtest=BookServiceImplTest,BookValidationTest test`.
- Gateway: configurar JWT_SECRET de prueba; `./mvnw.cmd -Deureka.client.enabled=false -Dspring.cloud.discovery.enabled=false verify`.

La comprobación real está en front-react/scripts/admin-integration.mjs del frontend. Requiere BASE_URL, ADMIN_EMAIL y ADMIN_PASSWORD en el entorno y crea/elimina solo sus propios datos. Puede atravesar Vite con BASE_URL terminado en /api.

Las pruebas selectivas de Auth/Book no incluyen sus contextos que requieren BD externa. No equivalen a ejecutar todas las pruebas de todos los microservicios.

Resultado: 25 pruebas de Auth, 10 de perfiles (más 1 deshabilitada previamente), 9 de libros y 17 de Gateway aprobadas. La integración real por Vite/Gateway/MySQL comprobó 42 respuestas del recorrido, además de la limpieza. OpenAPI y Swagger UI respondieron en Auth, User y Book.

La fase verify de Auth/Book/User sigue fallando por el mínimo JaCoCo de 80 % por clase; las pruebas funcionales seleccionadas pasan. El informe de Maven no recoge la ejecución HTTP de Docker. No se alteró el umbral: falta instrumentar esas pruebas y cubrir las ramas restantes. Gateway verify sí termina correctamente.

## Entrega y límites

Pendientes de definición compartida: continuidad de los campos de la ERS de Entrega I y rol Vendedor; sincronización book-service/inventory-service; despliegue y manual final grupal. La interfaz actual utiliza ADMIN/CLIENT y title/author/price/stock, no valores simulados para campos inexistentes.

.env estaba versionado pese al ignore: se retiró del seguimiento conservando la copia local. La clave JWT local se rotó; no se publica en documentación. El historial remoto previo no fue reescrito. No agregar .idea/workspace.xml ni archivos target a este cambio. La publicación de esta revisión fue solicitada por la usuaria en feature/react-tienda, sin merge a main ni push forzado.
