// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.
package com.library.reviewservice;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.boot.SpringApplication;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@SpringBootApplication
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@EnableDiscoveryClient
// Nota de lectura: Declara el tipo ReviewServiceApplication y su contrato.
public class ReviewServiceApplication {

	// Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
	public static void main(String[] args) {
		// Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
		SpringApplication.run(ReviewServiceApplication.class, args);
	// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
	}

// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
