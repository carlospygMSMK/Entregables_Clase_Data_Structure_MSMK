package university.msmk.unit19;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.springframework.stereotype.Component;

import java.util.*;
        import java.util.stream.Collectors;

@Component
public class GraphAnalytics {

    public List<Usuario> topPorSeguidores(Graph<Usuario, SiguiendoArista> graph, int topN) {
        return graph.vertexSet().stream()
                .sorted(Comparator.comparingInt((Usuario u) -> graph.inDegreeOf(u)).reversed())
                .limit(topN)
                .toList();
    }

    public List<List<Usuario>> ciclosSimples(Graph<Usuario, SiguiendoArista> graph) {
        var cycleFinder = new JohnsonSimpleCycles<>(graph);
        return cycleFinder.findSimpleCycles();
    }

    public GraphPath<Usuario, SiguiendoArista> caminoMasCorto(
            Graph<Usuario, SiguiendoArista> graph,
            Usuario origen,
            Usuario destino
    ) {
        var dijkstra = new DijkstraShortestPath<>(graph);
        return dijkstra.getPath(origen, destino);
    }

    public Set<Usuario> sugerenciasSeguir(Graph<Usuario, SiguiendoArista> graph, Usuario origen) {
        // usuarios ya seguidos + uno mismo
        Set<Usuario> yaSiguiendo = graph.outgoingEdgesOf(origen).stream()
                .map(graph::getEdgeTarget)
                .collect(Collectors.toSet());
        yaSiguiendo.add(origen);

        Set<Usuario> sugerencias = new HashSet<>();
        var bfs = new BreadthFirstIterator<>(graph, origen);

        Map<Usuario, Integer> distancia = new HashMap<>();
        distancia.put(origen, 0);

        while (bfs.hasNext()) {
            Usuario actual = bfs.next();
            if (actual.equals(origen)) {
                continue;
            }

            Usuario padre = bfs.getParent(actual);
            int dist = distancia.getOrDefault(padre, 0) + 1;
            distancia.put(actual, dist);

            if (dist == 2 && !yaSiguiendo.contains(actual)) {
                sugerencias.add(actual);
            }
        }

        return sugerencias;
    }

    public Usuario findByEmail(Graph<Usuario, SiguiendoArista> graph, String email) {
        return graph.vertexSet().stream()
                .filter(u -> Objects.equals(u.email(), email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
    }

    public List<String> encontrarAmistadesMutuas(Graph<Usuario, SiguiendoArista> graph) {
        List<String> mutuos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>(graph.vertexSet());

        for (int i = 0; i < usuarios.size(); i++) {
            for (int j = i + 1; j < usuarios.size(); j++) {
                Usuario u1 = usuarios.get(i);
                Usuario u2 = usuarios.get(j);

                // Si u1 sigue a u2 Y u2 sigue a u1
                if (graph.containsEdge(u1, u2) && graph.containsEdge(u2, u1)) {
                    mutuos.add(u1.handle() + " <--> " + u2.handle());
                }
            }
        }
        return mutuos;
    }
}