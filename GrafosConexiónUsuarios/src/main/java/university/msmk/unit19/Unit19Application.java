package university.msmk.unit19;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class Unit19Application implements CommandLineRunner {

    @Autowired
    private final GraphLoader graphLoader;

    @Autowired
    private final GraphAnalytics graphAnalytics;

    public static void main(String[] args) {
		SpringApplication.run(Unit19Application.class, args);
	}


    @Override
    public void run(String... args) {
        ///  GRAFOS
        Graph<Usuario, SiguiendoArista> graph = graphLoader.loadUserGraph();

        GraphViewer.show(
                graph,
                u -> u.email(),
                e -> "" // o e.getSince() si quieres etiqueta
        );

        GraphViewer.pintar(graph);

        log.info("=== Grafo de usuarios de X ===");
        log.info("Vértices: {}", graph.vertexSet().size());
        log.info("Aristas: {}", graph.edgeSet().size());

        log.info("=== Análisis de Amistades Mutuas ===");
        List<String> amigosMutuos = graphAnalytics.encontrarAmistadesMutuas(graph);
        if (amigosMutuos.isEmpty()) {
            log.info("No hay amistades mutuas.");
        } else {
            amigosMutuos.forEach(pareja -> log.info("Amistad mutua detectada: {}", pareja));
        }
    }

}
