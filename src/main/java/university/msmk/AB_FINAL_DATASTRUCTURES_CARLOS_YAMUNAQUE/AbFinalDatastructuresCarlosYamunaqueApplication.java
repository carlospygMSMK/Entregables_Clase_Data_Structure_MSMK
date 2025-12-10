package university.msmk.AB_FINAL_DATASTRUCTURES_CARLOS_YAMUNAQUE;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
@Slf4j
public class AbFinalDatastructuresCarlosYamunaqueApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AbFinalDatastructuresCarlosYamunaqueApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(AbFinalDatastructuresCarlosYamunaqueApplication.class, args);
	}

    @Override
    public void run(String... args){

        log.info("Hola");
        HashMap<Integer, String > jugadoresDeDender = new HashMap<>();
        JugadorNBA jugador15 = new JugadorNBA("Jokic", 20, 15);
        jugadoresDeDender.put(11,"Jokic");
        String NombreJugadorEncontrado = jugadoresDeDender.get(11);
        log.info("El jugador numero 11 es {}", NombreJugadorEncontrado);
    }
}
