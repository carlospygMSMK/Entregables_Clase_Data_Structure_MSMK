package edu.msmk.clases.service;

import edu.msmk.clases.CoberturaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class TramoService {

    /// Leemos los tramos que damos covertura
    public CoberturaServicio leerTramos() throws IOException {
        CoberturaServicio miCobertura = new CoberturaServicio();
        ClassPathResource resource = new ClassPathResource("TRAM.D250101.A250630");

        // Si no existiera el archivo
        if (!resource.exists()) {
            throw new IOException("El archivo TRAM.D250101.A250630 no existe en los recursos");
        }

        boolean isEmpty = true; // Se inicializa fuera del bloque de lectura

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.ISO_8859_1))) {

            String linea;

            while ((linea = reader.readLine()) != null) {
                // Validación estricta para asegurar que la línea tiene datos completos
                if (linea.length() < 20 || linea.trim().isEmpty()) {
                    continue; // Pasa a la siguiente línea si es muy corta o está vacía
                }

                isEmpty = false; // Si llegamos aquí, el archivo no está vacío

                /// TROCEAR UNA LINEA POR POSICIÓN Y LONGITUD FIJA
                String provincia = linea.substring(0, 2).trim();  /// Longitud 2 Provincia
                String municipio = linea.substring(2, 5).trim();  /// Longitud 3 Municipio
                String unidadPoblacional = linea.substring(5, 12).trim();  ///Longitud 7 Unidad Poblacional
                String via = linea.substring(12, 20).trim();  ///Longitud 8 Vía

                // Evitar errores de parseo si los campos están vacíos
                if (provincia.isEmpty() || municipio.isEmpty() || unidadPoblacional.isEmpty() || via.isEmpty()) {
                    log.warn("Línea omitida por campos vacíos: {}", linea);
                    continue;
                }

                /// GUARDAR EN LA NUEVA ESTRUCTURA JERÁRQUICA
                try {
                    miCobertura.addTramo(
                            Integer.parseInt(provincia),
                            Integer.parseInt(municipio),
                            Integer.parseInt(unidadPoblacional),
                            Integer.parseInt(via)
                    );
                } catch (NumberFormatException e) {
                    log.error("Error al parsear un código de dirección: {}. Error: {}", linea, e.getMessage());
                    /// Continúa con la siguiente línea
                }
            }
        } // Cierre del try-with-resources

        if (isEmpty) {
            throw new IOException("El archivo está vacío o no contiene líneas válidas.");
        }

        return miCobertura;
    }
}