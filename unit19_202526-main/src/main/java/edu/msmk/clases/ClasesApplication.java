package edu.msmk.clases;

import edu.msmk.clases.exchange.PeticionCliente;
import edu.msmk.clases.service.TramoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.Timestamp;

@SpringBootApplication
@Slf4j
public class ClasesApplication implements CommandLineRunner {

    private final TramoService tramoService;

    public ClasesApplication(TramoService tramoService) {
        this.tramoService = tramoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClasesApplication.class, args);
    }

    @Override
    public void run(String... args) {
        /// Crear pila
        PilaBasica miPila = new PilaBasica();
        probarPila (miPila);

        /// VALIDAR DIRECCIONES
        try {
            CoberturaServicio coberturaServicio = tramoService.leerTramos();  // Leemos los tramos y lo guardamos
            probarEntregas (coberturaServicio); // Iniciamos la función
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            // Si el archivo falla, la aplicación no puede probar la cobertura
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    /// // Función para probar la Pila
    private void probarPila(PilaBasica pila) {
        pila.push(1);
        pila.push(3);
        pila.push(5);

        log.info("El elemento que esta arriba debería ser un 5 y es {}", pila.pop());
        log.info("el siguiente elemento debería ser un 3 y es {}", pila.top());
        log.info("El elemento que esta arriba debería ser un 3 y es {}", pila.pop());
    }

    /// Probamos si damos servicio o no
    private void probarEntregas(CoberturaServicio miCobertura) {
        log.info("----------------------------------------------------------------------------------------");
        log.info("Provincias cubiertas cargadas: {}", miCobertura.numeroProvinciasCubiertas());

        // NOTA: Estos códigos (1001000, 38010) DEBEN COINCIDIR con algún tramo cargado en TRAM.

        // Petición 1 -> TRUE
        PeticionCliente peticion1 = new PeticionCliente(10, 16, 1001000, 38010);
        log.info("--- Petición 1 (Esperando: TRUE) ---");
        cronometrarDireccion(miCobertura, peticion1);

        // Petición Falsa: -> FALSE
        PeticionCliente peticionFalse = new PeticionCliente(10, 16, 1001000, 99999);
        log.info("--- Petición Control (Esperado: FALSE) ---");
        cronometrarDireccion(miCobertura, peticionFalse);
        log.info("----------------------------------------------------------------------------------------");
    }

    // Mide el tiempo y muestra el resultado de la búsqueda
    private void cronometrarDireccion(CoberturaServicio miCobertura, PeticionCliente direccion) {
        Timestamp inicio = new Timestamp(System.currentTimeMillis());
        boolean resultado = miCobertura.damosServicio(direccion); // Llamada clave
        Timestamp acabo = new Timestamp(System.currentTimeMillis());

        log.info("Damos servicio a {}/{}/{} (Vía {}): {}",
                direccion.getProvincia(),
                direccion.getMunicipio(),
                direccion.getUnidadPoblacional(),
                direccion.getVia(),
                resultado);
        log.info("Tiempo de ejecución: {} ms", acabo.getTime() - inicio.getTime());
    }
}