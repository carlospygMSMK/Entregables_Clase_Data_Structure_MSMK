package com.Carpeta_Programas_Java.Archivos_Java;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class run implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(run.class);

    public static void main(String[] args) {SpringApplication.run(run.class, args);}

    @Override
    public void run(String... arg){
        int maximo = 20;
        ADT_Fila fila = new ADT_Fila(maximo);
        log.info("----------------------------------------------------------------------------------------------");
        log.info("---------------Abrimos el Parque de atracciones con un máximo de {} personas------------------", maximo);
        log.info("Abrimos paso a los visitantes..........");
        log.info("Entran varías personas....");
        fila.encolarVisitante("Carlos");
        fila.encolarVisitante("José");
        fila.encolarVisitante("María");
        fila.encolarVisitante("Tamara");
        fila.encolarVisitante("Jorge");
        fila.encolarVisitante("Guillen");
        fila.encolarVisitante("Hernesto");
        log.info("Tenemos en total {} personas en la cola", fila.tamañoTotal());
        log.info("Veamos quienes de estas personas son VIP o Standard....");
        log.info("---------------------------------------------------------------------------");
        fila.esVIP("Carlos");
        fila.esVIP("José");
        fila.esVIP("María");
        fila.esVIP("Tamara");
        fila.esVIP("Jorge");
        fila.esVIP("Guillen");
        fila.esVIP("Hernesto");
        log.info("---------------------------------------------------------------------------");
        log.info("Entonces tenemos de fila vip y no vip a: ");
        log.info("---------------------------------------------------------------------------");
        fila.mostrarFilas();
        log.info("------------------Que suban a la atracción----------------");
        fila.llenarAtraccion();
        log.info("...........................................................");
        log.info("Ahora en la fila hay: {}", fila.tamañoTotal());
    }

}