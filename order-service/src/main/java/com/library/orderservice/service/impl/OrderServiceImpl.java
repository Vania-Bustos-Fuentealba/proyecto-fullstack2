// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.service.impl.
package com.library.orderservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.entity.OrderEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.repository.OrderRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.service.OrderService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo OrderServiceImpl y su contrato.
public class OrderServiceImpl implements OrderService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(OrderServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final OrderRepository orderRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private OrderResponseDTO mapToResponse(OrderEntity order) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO response = new OrderResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(order.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(order.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setTotal(order.getTotal());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStatus(order.getStatus());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando orden para userId {}", request.getUserId());

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseUser = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://localhost:8089/users/" + request.getUserId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde user-service: {}", responseUser);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseBook = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://localhost:8082/books/" + request.getBookId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde book-service: {}", responseBook);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseInventory = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://localhost:8086/inventory/book/" + request.getBookId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde inventory-service: {}", responseInventory);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Double total = request.getTotal() != null ? request.getTotal() : 0.0;
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String status = request.getStatus() != null ? request.getStatus() : "PENDING";

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderEntity order = new OrderEntity();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setUserId(request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setTotal(total);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setStatus(status);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        OrderEntity savedOrder = orderRepository.save(order);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Orden creada correctamente con id {}", savedOrder.getId());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        java.util.Map<String, Object> paymentRequest = new java.util.HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        paymentRequest.put("orderId", savedOrder.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        paymentRequest.put("amount", total);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        paymentRequest.put("paymentMethod", "CARD");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        paymentRequest.put("status", "PAID");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responsePayment = restTemplate.postForObject(
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "http://localhost:8088/payments",
                // Nota de lectura: Aporta el valor paymentRequest a la declaración o llamada abierta.
                paymentRequest,
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde payment-service: {}", responsePayment);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        java.util.Map<String, Object> notificationRequest = new java.util.HashMap<>();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notificationRequest.put("userId", request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notificationRequest.put("message", "Tu orden " + savedOrder.getId() + " fue creada y pagada correctamente");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notificationRequest.put("type", "ORDER_CREATED");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        org.springframework.http.HttpEntity<java.util.Map<String, Object>> entity =
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new org.springframework.http.HttpEntity<>(notificationRequest, headers);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        org.springframework.http.ResponseEntity<String> responseNotification = restTemplate.postForEntity(
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "http://localhost:9090/notifications",
                // Nota de lectura: Aporta el valor entity a la declaración o llamada abierta.
                entity,
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde notification-service: {}", responseNotification.getBody());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(savedOrder);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}