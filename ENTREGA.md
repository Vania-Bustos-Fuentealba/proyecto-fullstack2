# Online Library - integración de la entrega III

Revisión del 26 de noviembre de 2026. Frontend vigente: `front-react`. Backend de entrega: `Vania-Bustos-Fuentealba/proyecto-fullstack2`, rama `feature/react-tienda`.

## Procedencia y alcance

Se conserva el contenido publicado por Ale en main (backend `4eabf98`, frontend `60e2630`). La administración procede del trabajo `ae0b10d` de `Quiti-1556/online-library-microservices`. Se adaptan el formulario y la lógica de pedidos publicados por Vale en `feature/backend-tienda` del frontend (`8d28587`). Las copias originales con cambios locales del equipo se mantienen intactas.

La aplicación React llama a `/api` con un cliente común basado en `fetch`. Axios no es necesario para esta arquitectura: el cliente agrega JWT, trata respuestas HTTP, errores y tiempos de espera. La elección de librería no sustituye ninguna prueba de integración.

## Iniciar desde una instalación limpia

Requisitos: Docker Desktop iniciado; Node compatible con Vite 8 y npm. Para ejecutar Maven fuera de Docker, Java 21 o superior. Los Dockerfile construyen los servicios con su Java correspondiente.

Backend:

```powershell
Copy-Item .env.example .env
# Editar .env con claves nuevas: MYSQL_ROOT_PASSWORD, JWT_SECRET y ADMIN_INITIAL_PASSWORD.
# JWT_SECRET debe ser una clave aleatoria Base64 de al menos 32 bytes.
docker compose -f docker-compose.entrega.yml up -d --build
```

Nunca publicar `.env`. La cuenta inicial es `admin@library.cl`; su contraseña procede de `ADMIN_INITIAL_PASSWORD`. Se crea únicamente si no existe. Para generar JWT_SECRET localmente puede usarse `node -e "console.log(require('node:crypto').randomBytes(32).toString('base64'))"` y copiar el resultado a .env, sin subirlo a Git.

Frontend, desde su repositorio:

```powershell
cd front-react
npm ci
npm run dev -- --host 127.0.0.1 --port 5173 --strictPort
```

Abrir http://127.0.0.1:5173. Swagger: http://127.0.0.1:8080/swagger-ui/index.html.

Si 8080 está ocupado, establecer `GATEWAY_PORT=18080` en .env del backend y `$env:GATEWAY_URL='http://127.0.0.1:18080'` antes de iniciar Vite. Si cambia el origen/puerto de Vite, agregar su URL exacta a `WEB_ALLOWED_ORIGINS` en .env y recrear el Gateway. No usar una lista abierta de orígenes.

La revisión local usa 5177 para React y 18080 para Gateway, proyecto Docker `library-entrega-check`, con una base aislada. El MySQL ajeno en 3306 no se modificó. Para detener esta revisión sin borrar datos: `docker compose -p library-entrega-check -f docker-compose.entrega.yml stop`. No ejecutar `down -v` sobre datos que se deban conservar.

## Arquitectura y persistencia

Navegador → React/Vite `/api` → API Gateway → Auth, User, Book o Content → MySQL.

El compose de entrega levanta cinco aplicaciones y MySQL. Solo el Gateway publica un puerto en 127.0.0.1. Auth y Gateway verifican la sesión; los demás servicios confían en la red privada y en las cabeceras que sustituye el Gateway. No se debe publicar Book/User/Content directamente porque sus controladores no validan JWT por sí solos.

Auth guarda identidad, hash BCrypt de contraseña, rol e historial de versión de credenciales. User guarda el perfil; sus IDs son independientes de Auth. Los cambios administrativos sincronizan el perfil por correo. Son transacciones locales, no una transacción distribuida entre las dos bases.

Book es la única fuente de libros y existencias que usa esta entrega. Los pedidos se guardan en Book para descontar stock y crear el pedido en una misma transacción. Esto adapta el trabajo de Vale al catálogo existente. Inventory y el antiguo Order no se sincronizan ni se usan en el recorrido de entrega; sus fuentes se conservan como legado. No se ha validado el funcionamiento conjunto de los doce microservicios históricos.

Content guarda publicaciones y mensajes. La lectura del blog y el envío de contacto son públicos; administrar publicaciones o leer mensajes requiere ADMIN.

## Contratos y permisos

| Recurso real | Lectura / escritura | Permiso |
|---|---|---|
| `/auth/login`, `/auth/register` | POST | Público; el registro siempre crea CLIENT |
| `/auth/session` | GET | Sesión válida |
| `/auth/users` y `/auth/users/{id}` | GET, POST, PUT, DELETE según ruta | ADMIN |
| `/users` y subrutas | Perfil y sincronización | ADMIN por Gateway; uso interno de Auth |
| `/books` y `/books/{id}` | GET público; POST/PUT/DELETE administrativos | ADMIN para modificar |
| `/pedidos` | POST | ADMIN o CLIENT autenticado |
| `/pedidos` | GET listado | ADMIN |
| `/pedidos/{id}` | GET detalle | Dueño por ID de Auth o ADMIN |
| `/v1/posts` y `/v1/posts/{id}` | GET público; CRUD protegido | ADMIN para modificar |
| `/v1/contact` | POST público; GET protegido | ADMIN para consultar |
| `/v1/contact/{id}/read` | PATCH | ADMIN |
| `/openapi/auth`, `/openapi/users`, `/openapi/books`, `/openapi/content` | GET de esquemas | Público, sin exponer puertos internos |

Libro: `id, title, author, price, stock`. Cuenta: `id, name, email, role` y contraseña solo al crear/cambiar. Pedido: `cliente, correo, direccion, comuna, metodoPago, cupon, items[{libroId,cantidad}]`; exige cabecera `Idempotency-Key`. El precio y los totales se recalculan en el servidor.

## Reglas verificadas

JWT firmado y con expiración. Gateway consulta el rol vigente en Auth; los tokens de cuentas eliminadas, identidades reemplazadas y credenciales modificadas se rechazan. Los pedidos pertenecen al ID inmutable de la cuenta. Las cabeceras de identidad enviadas por el cliente se reemplazan por las verificadas.

ADMIN: resumen, búsqueda y CRUD de cuentas/libros; protección de la cuenta principal y de la identidad que administra. CLIENT: catálogo y checkout; no puede administrar usuarios, stock, publicaciones ni mensajes. En React se restringen rutas y acciones y se conserva la sesión al recargar; un 401 cierra la sesión correspondiente. El cierre local borra el token del navegador; no es una lista de revocación global de tokens ya copiados.

Carrito persistido en `carrito-api-v1`; cupón en `cupon-api-v1`. Las claves anteriores se preservan pero no se migran: sus IDs podían provenir de productos simulados. Se descartan datos corruptos, cantidades imposibles y duplicados. El carrito no reserva stock. Al confirmar, el backend valida disponibilidad nuevamente.

Cupones DUOC10 (10%) y LIBROS5 (5%). Descuento redondeado a pesos. Envío $3.990 o gratis desde $30.000 después del descuento. Los importes de pedidos usan BigDecimal. Un bloqueo de fila evita vender dos veces la última unidad; una versión optimista detecta actualizaciones concurrentes del libro.

Reintentar el mismo contenido con la misma clave devuelve el mismo pedido; contenido distinto con la misma clave produce 409. La clave del formulario se mantiene durante los reintentos de esa pantalla; recargar o abandonar el formulario inicia un nuevo intento. El carrito solo se vacía después de una confirmación válida. Libros asociados a pedidos no pueden eliminarse: se preserva el historial y se devuelve 409.

## Rúbrica y pendientes reales

La pauta EP3 suministrada confirma: backend con BD y lógica, CRUD Spring/Swagger, integración REST, JWT/roles, persistencia de sesión, restricciones frontend, pruebas, enlaces GitHub, ZIP frontend/backend, manual con capturas y documento de integración. Estas áreas tienen implementación y evidencia en esta revisión.

La ERS de la primera entrega exige además campos que siguen sin estar en el modelo actual: RUN, apellidos, región/comuna de usuario, categoría, imagen y stock crítico configurable por libro, además de rol Vendedor. La rúbrica EP3 remite a los requisitos del cliente; no confirma que esos requisitos anteriores se hayan retirado. Se mantienen como pendiente de confirmación y desarrollo si el docente los exige. No se declaran cumplidos por usar datos vacíos o una portada genérica.

El checkout registra pedidos PENDIENTE_PAGO. No hay cobro Webpay, verificación de transferencias, correo de confirmación ni despacho real. Hace falta contrato/credenciales y definición del flujo si se requiere pago bancario real. Tampoco existe una pantalla de historial de pedidos o administración de contenido: sus consultas/operaciones protegidas están disponibles en API y Swagger.

La aplicación se probó en un entorno local. Una publicación de producción necesita HTTPS, proxy `/api` y fallback de rutas React. No usar `vite preview` como servidor de producción ni suponer que el proxy de desarrollo está dentro de `dist`.

## Pruebas reproducibles

Frontend: `npm run coverage`, `npm run build`, `npm run lint`. La cobertura reportada corresponde a los archivos incluidos expresamente en vite.config.js, no a toda la aplicación.

Backend: `./mvnw verify` (o `mvnw.cmd verify`) dentro de auth-service, user-service, book-service, content-service y api-gateway. Gateway necesita JWT_SECRET de pruebas y `-Deureka.client.enabled=false`. Auth/User/Book incluyen HTTP real y H2 en pruebas; Flyway/MySQL se verifican adicionalmente con Docker. Auth/Book/User mantienen JaCoCo mínimo 80% de líneas por clase, sin bajar el umbral. Los módulos históricos quedan fuera de esta afirmación.

Contra una base exclusiva de pruebas, configurar BASE_URL hacia el Gateway y ADMIN_PASSWORD, y ejecutar `node scripts/admin-integration.mjs` y `node scripts/team-integration.mjs` desde front-react. El segundo conserva pedidos/libros de prueba como evidencia. Nunca ejecutar estos scripts sobre datos de producción. Los datos de prueba no son correos enviados ni cobros.

## Notas para estudiar el código

Los fuentes Java, JavaScript, JSX y CSS contienen notas de lectura sobre las instrucciones, incluidos los de Ale. Se omiten comentarios dentro de literales o entre atributos JSX donde modificarían la sintaxis. La salida compilada JS/JSX/CSS se comparó antes y después y fue idéntica. Java conserva exactamente sus instrucciones al retirar las notas añadidas.

JSON y archivos lock no admiten comentarios; no se alteran. Tampoco se reescriben migraciones Flyway ya versionadas para añadir notas, porque cambiaría su checksum. Las migraciones y configuración se explican en este documento. Las notas ayudan a leer, pero cada integrante debe poder explicar las reglas y límites de su parte.
