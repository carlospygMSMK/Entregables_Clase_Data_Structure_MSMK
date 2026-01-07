package university.msmk.unit19;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @Component
    public class GraphLoader {

        private final ObjectMapper mapper;

        public GraphLoader(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        public Graph<Usuario, SiguiendoArista> loadUserGraph() {
            try {
                List<Usuario> usuarios = mapper.readValue(
                        new ClassPathResource("users.json").getInputStream(),
                        new TypeReference<List<Usuario>>() {}
                );

                Map<String, Usuario> byEmail = usuarios.stream()
                        .collect(Collectors.toMap(Usuario::email, u -> u));


                List<SiguiendoDTO> follows = mapper.readValue(
                        new ClassPathResource("follows.json").getInputStream(),
                        new TypeReference<List<SiguiendoDTO>>() {}
                );

                Graph<Usuario, SiguiendoArista> graph =
                        new DefaultDirectedGraph<>(SiguiendoArista.class);

                // vértices
                usuarios.forEach(graph::addVertex);

                // aristas con propiedad "since"
                for (SiguiendoDTO f : follows) {
                    Usuario from = byEmail.get(f.from());
                    Usuario to = byEmail.get(f.to());
                    if (from != null && to != null) {
                        SiguiendoArista edge = new SiguiendoArista(f.since());
                        graph.addEdge(from, to, edge);
                    }
                }

                return graph;
            } catch (IOException e) {
                throw new RuntimeException("Error cargando grafo de usuarios", e);
            }
        }
    }
