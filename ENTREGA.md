# Online Library - integración de la entrega III

Revisión del 27 de noviembre de 2026. Frontend vigente: `front-react`. Backend de entrega: `Vania-Bustos-Fuentealba/proyecto-fullstack2`, rama de entrega `main`; desarrollo en `feature/react-tienda`.

## Procedencia y alcance

Se conserva el contenido publicado por Ale en main (backend `4eabf98`, frontend `60e2630`). La administración procede del trabajo `ae0b10d` de `Quiti-1556/online-library-microservices`. Se adaptan el formulario y la lógica de pedidos publicados por Vale en `feature/backend-tienda` del frontend (`8d28587`). Las copias originales con cambios locales del equipo se mantienen intactas.

La aplicación React llama a `/api` con un cliente común basado en `fetch`. Axios no es necesario para esta arquitectura: el cliente agrega JWT, trata respuestas HTTP, errores y tiempos de espera, y coordina la renovación de JWT. La elección de librería no sustituye ninguna prueba de integración.

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

Abrir http://127.0.0.1:5173. Swagger: http://127.0.0.1:8080/swagger-ui/index.html. Esperar a que todos los servicios terminen de iniciar; un contenedor iniciado todavía puede estar cargando Spring y sus migraciones. Si aparece 502 durante el arranque, revisar los servicios y reintentar después de comprobar sus rutas de salud.

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
| `/auth/refresh` | POST, sin cuerpo | JWT vigente de ADMIN, CLIENT o SELLER; conserva el límite absoluto |
| `/auth/users` y `/auth/users/{id}` | GET, POST, PUT, DELETE según ruta | ADMIN |
| `/users` y subrutas | Perfil y sincronización | ADMIN por Gateway; uso interno de Auth |
| `/books` y `/books/{id}` | GET público; POST/PUT/DELETE administrativos | ADMIN para modificar |
| `/pedidos` | POST | ADMIN o CLIENT autenticado |
| `/pedidos` | GET listado | ADMIN o SELLER |
| `/pedidos/{id}` | GET detalle | Dueño por ID de Auth, ADMIN o SELLER |
| `/v1/posts` y `/v1/posts/{id}` | GET público; CRUD protegido | ADMIN para modificar |
| `/v1/contact` | POST público; GET protegido | ADMIN para consultar |
| `/v1/contact/{id}/read` | PATCH | ADMIN |
| `/openapi/auth`, `/openapi/users`, `/openapi/books`, `/openapi/content` | GET de esquemas | Público, sin exponer puertos internos |

Libro: `id, code, title, author, description, category, imageUrl, price, stock, criticalStock`. Cuenta: `id, run, name, lastName, email, birthDate, region, commune, address, role` y contraseña solo al crear/cambiar. Pedido: `cliente, correo, direccion, comuna, metodoPago, cupon, items[{libroId,cantidad}]`; exige cabecera `Idempotency-Key`. El precio y los totales se recalculan en el servidor.

## Reglas verificadas

JWT firmado HS256: acceso de 15 minutos y sesión con límite absoluto de 24 horas, configurables. `POST /auth/refresh` exige un JWT aún vigente y la identidad actual; emite otro identificador de token y conserva `sessionExpiresAt`. No permite renovar un token vencido ni prolongar indefinidamente la sesión. React intenta renovar cuando faltan dos minutos, al hacer peticiones, al recuperar el foco y cada 30 segundos mientras la página está visible. Una pestaña inactiva que deja vencer el token debe iniciar sesión nuevamente. Las respuestas tardías no reabren una sesión cerrada ni sustituyen otra cuenta. Gateway consulta el rol vigente en Auth; los tokens de cuentas eliminadas, identidades reemplazadas y credenciales modificadas se rechazan. Los pedidos pertenecen al ID inmutable de la cuenta. Las cabeceras de identidad enviadas por el cliente se reemplazan por las verificadas.

ADMIN: resumen, búsqueda y CRUD de cuentas/libros; protección de la cuenta principal y de la identidad que administra. SELLER: listado y detalle de productos y órdenes, sin crear/editar/eliminar ni administrar cuentas. CLIENT: catálogo y checkout; no puede administrar usuarios, stock, publicaciones ni mensajes. En React se restringen rutas y acciones y se conserva la sesión al recargar; un 401 cierra la sesión correspondiente. El cierre local borra el token del navegador; no es una lista de revocación global de tokens ya copiados. Un token copiado sigue sujeto a su expiración y versión de credenciales. La renovación no revoca por sí sola el token anterior. localStorage permite persistencia, pero es accesible a JavaScript: no proporciona la protección HttpOnly contra robo por XSS. El código no inserta contenido remoto como HTML. Una versión pública necesitaría HTTPS y revisar CSP/cookies HttpOnly y revocación según su modelo de amenazas.

Carrito persistido en `carrito-api-v1`; cupón en `cupon-api-v1`. Las claves anteriores se preservan pero no se migran: sus IDs podían provenir de productos simulados. Se descartan datos corruptos, cantidades imposibles y duplicados. El carrito no reserva stock. Al confirmar, el backend valida disponibilidad nuevamente.

Cupones DUOC10 (10%) y LIBROS5 (5%). Descuento redondeado a pesos. Envío $3.990 o gratis desde $30.000 después del descuento. Los importes de pedidos usan BigDecimal. Un bloqueo de fila evita vender dos veces la última unidad; una versión optimista detecta actualizaciones concurrentes del libro.

Reintentar el mismo contenido con la misma clave devuelve el mismo pedido; contenido distinto con la misma clave produce 409. La clave del formulario se mantiene durante los reintentos de esa pantalla; recargar o abandonar el formulario inicia un nuevo intento. El carrito solo se vacía después de una confirmación válida. Libros asociados a pedidos no pueden eliminarse: se preserva el historial y se devuelve 409.

## Requisitos de la ERS incorporados

Registro y mantenedor de usuarios: RUN obligatorio de 7 a 9 caracteres sin puntos ni guion, verificado con módulo 11 y único; nombre hasta 50; apellidos hasta 100; correo hasta 100 con dominios duoc.cl, profesor.duoc.cl o gmail.com; fecha de nacimiento opcional y no futura; región/comuna dependientes; dirección obligatoria hasta 300. Las mismas relaciones región/comuna se validan en Auth usando un recurso generado desde el arreglo existente en React. El registro público crea siempre CLIENT; solo ADMIN asigna roles.

Productos: código único de al menos 3 caracteres; título hasta 100; descripción opcional hasta 500; categoría desde las seis categorías existentes; imagen opcional como URL HTTP/HTTPS o ruta local; precio no negativo, incluido cero; existencias enteras no negativas; stock crítico opcional y no negativo. Se marca stock bajo cuando stock es menor o igual al umbral configurado en ese libro. Sin umbral no se inventa un límite. El servidor rechaza cantidades fraccionarias.

Flyway V3 agrega los campos conservando registros anteriores. Los datos antiguos pueden mostrar “Pendiente de completar”; no se les inventan RUN, categorías ni direcciones. Crear o editar requiere completar los campos obligatorios. La cuenta técnica inicial también queda sin datos personales hasta que la responsable ingrese los suyos. La base aislada de revisión conserva ejemplos antiguos y nuevos para comprobar esta transición. En una instalación nueva se aplican V1, V2 y V3.

La ERS de Entrega I pide contraseñas de 4 a 10 caracteres. Se aplica a nuevas cuentas y cambios de contraseña. Excepción técnica documentada: el bootstrap ADMIN mantiene mínimo 12 caracteres y el login admite las claves anteriores para no bloquear cuentas existentes. El límite BCrypt de 72 bytes se valida aparte. Esta compatibilidad no equivale a un cumplimiento literal del máximo de 10 en todos los inicios de sesión; tampoco se recomienda limitar a 10 caracteres en producción.

## Rúbrica, evidencia y límites

La pauta EP3 suministrada confirma seis áreas: modelo y BD/lógica, CRUD Spring/Swagger, integración REST, JWT con roles y renovación, persistencia de sesión y restricciones frontend. La revisión incluye implementación y pruebas en estas áreas; el resultado académico lo determina el docente y la defensa individual, no un conteo de pruebas.

Obligatorios del encargo: enlaces públicos GitHub de frontend/backend, ambos proyectos comprimidos, manual con capturas y documento de integración. Se entregan en esta revisión. La guía de presentación es material de apoyo adicional: el encargo representa 40% y la presentación individual 60% de esta evaluación. Cada integrante debe explicar los seis indicadores y reconocer la procedencia del trabajo de sus compañeras.

Correcciones necesarias realizadas: controles JaCoCo conservando 80%, permisos en servidor, coordinación del stock en Book con pedidos transaccionales, nuevos campos ERS, rol Vendedor, consulta de órdenes, renovación con límite y pruebas de errores reales. Una respuesta 502 interrumpió el primer intento de la prueba conjunta; al repetir el recorrido completo se verificaron 78 respuestas sin fallos. No se cambió el resultado esperado para ocultar ese fallo inicial.

El checkout registra pedidos PENDIENTE_PAGO. No hay cobro Webpay, verificación de transferencias, correo de confirmación ni despacho real. No se encontraron exigencias explícitas de una pasarela bancaria real o despliegue público en la pauta EP3. Si el docente los exige por otra especificación, se necesitan contrato/credenciales y definición del flujo. Las pantallas de listado y detalle de órdenes existen para ADMIN y SELLER; el cliente conserva la confirmación y puede consultar su pedido por API. La administración de publicaciones y mensajes está disponible en API/Swagger, sin un nuevo panel editorial.

Mejoras opcionales: historial de compras en la interfaz de CLIENT, panel editorial, migración de sesión a cookies HttpOnly con protección CSRF, paginación al crecer los datos y despliegue con HTTPS. No se presentan como requisitos nuevos de esta entrega.

La aplicación se probó localmente. Una publicación de producción necesita HTTPS, proxy `/api` y fallback de rutas React. No usar `vite preview` como servidor de producción ni suponer que el proxy de desarrollo está dentro de `dist`.

## Pruebas reproducibles

Frontend: `npm run coverage`, `npm run build`, `npm run lint`. La cobertura reportada corresponde a los archivos incluidos expresamente en vite.config.js, no a toda la aplicación.

Backend: `./mvnw verify` (o `mvnw.cmd verify`) dentro de auth-service, user-service, book-service, content-service y api-gateway. Gateway necesita JWT_SECRET de pruebas y `-Deureka.client.enabled=false`. Auth/User/Book incluyen HTTP real y H2 en pruebas; Flyway/MySQL se verifican adicionalmente con Docker. Auth/Book/User mantienen JaCoCo mínimo 80% de líneas por clase, sin bajar el umbral. Los módulos históricos quedan fuera de esta afirmación.

Contra una base exclusiva de pruebas, configurar BASE_URL hacia el Gateway y ADMIN_PASSWORD, y ejecutar `node scripts/admin-integration.mjs` y `node scripts/team-integration.mjs` desde front-react. El segundo conserva pedidos/libros de prueba como evidencia. Nunca ejecutar estos scripts sobre datos de producción. Los datos de prueba no son correos enviados ni cobros.

## Notas para estudiar el código

Los fuentes Java, JavaScript, JSX y CSS contienen notas de lectura sobre las instrucciones, incluidos los de Ale. Se omiten comentarios dentro de literales o entre atributos JSX donde modificarían la sintaxis. Las notas nuevas se agregaron a 34 archivos JS/JSX/CSS (2.079 notas) y 34 archivos Java sobre la anotación anterior de toda la aplicación. La salida compilada JS/JSX/CSS se comparó antes y después y fue idéntica. Java conserva exactamente sus instrucciones al retirar las notas añadidas.

JSON y archivos lock no admiten comentarios; no se alteran. Tampoco se reescriben migraciones Flyway ya versionadas para añadir notas, porque cambiaría su checksum. Las migraciones y configuración se explican en este documento. Las notas ayudan a leer, pero cada integrante debe poder explicar las reglas y límites de su parte.

## Trazabilidad para la entrega y defensa

| Indicador EP3 | Evidencia principal | Comprobación |
|---|---|---|
| 1. BD y lógica de negocio | Entidades, Flyway V3, Book/PedidoService | MySQL real, stock, totales e idempotencia |
| 2. REST Spring y Swagger | Controladores y /swagger-ui/index.html | CRUD y esquemas de los cuatro servicios |
| 3. Integración | src/services/api.js y servicios de dominio | 43 comprobaciones admin y 78 de equipo |
| 4. JWT y roles | JwtService, JwtWebMvcFilter, /auth/refresh | Rechazo 401/403, revocación por versión y renovación |
| 5. Sesión persistente | usuarios.js, SessionRenewal.jsx | Recarga, expiración y respuesta tardía |
| 6. Acceso frontend | AdminLayout, rutas /admin y /vendedor | Vendedor sin edición ni acceso a usuarios |

Resultados detallados: docs/entrega/resultados-pruebas.txt. Material para estudiar: docs/entrega/guia-presentacion-online-library.pdf. La rama de entrega es main en ambos repositorios. El trabajo se desarrolló en feature/react-tienda y se revisa mediante frontend PR #11 y backend PR #4. Los ZIP corresponden a los commits indicados en estado-entrega.json externo al repositorio. La fusión de estos PR a main fue autorizada expresamente por la responsable del proyecto.
