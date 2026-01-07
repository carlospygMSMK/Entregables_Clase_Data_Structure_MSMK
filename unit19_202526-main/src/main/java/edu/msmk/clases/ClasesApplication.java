package edu.msmk.clases;

import edu.msmk.clases.exchange.PeticionCliente;
import edu.msmk.clases.grafo.GestorRutas;
import edu.msmk.clases.service.TramoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
@Slf4j

public class ClasesApplication implements CommandLineRunner {

    private final TramoService tramoService;
    private final GestorRutas gestorRutas;

    public ClasesApplication(TramoService tramoService, GestorRutas gestorRutas) {
        this.tramoService = tramoService;
        this.gestorRutas = gestorRutas;
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

        /// Definimos dos puntos que sabemos que existen en el archivo
        PeticionCliente origen = new PeticionCliente(10, 116, 1001000, 38010123);
        PeticionCliente destino = new PeticionCliente(10, 116, 1001000, 38010125);
        log.info("Verificando Cobertura y Ruta....");

        // 1. Validar cobertura con tu código original
        boolean origenOk = miCobertura.damosServicio(origen);
        boolean destinoOk = miCobertura.damosServicio(destino);

        if (origenOk && destinoOk) {
            log.info("Ambos puntos tienen cobertura. Calculando camino...");

            // 2. Usar el gestor de rutas
            List<String> camino = gestorRutas.obtenerCaminoRapido(
                    String.valueOf(origen.getVia()),
                    String.valueOf(destino.getVia())
            );

            if (camino != null) {
                log.info("¡RUTA ENCONTRADA!: {}", String.join(" -> ", camino));
            } else {
                log.warn("No se encontró conexión directa entre estas vías.");
            }
        } else {
            log.error("No se puede calcular ruta: uno de los puntos no tiene cobertura.");
        }
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