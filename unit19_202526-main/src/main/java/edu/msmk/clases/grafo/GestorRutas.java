package edu.msmk.clases.grafo;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GestorRutas {
    private final Graph<String, DefaultWeightedEdge> redVial = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    public void conectarVias(String viaA, String viaB, double peso) {
        redVial.addVertex(viaA);
        redVial.addVertex(viaB);
        if (!redVial.containsEdge(viaA, viaB)) {
            DefaultWeightedEdge arista = redVial.addEdge(viaA, viaB);
            redVial.setEdgeWeight(arista, peso);
        }
    }

    public List<String> obtenerCaminoRapido(String inicio, String fin) {
        try {
            return DijkstraShortestPath.findPathBetween(redVial, inicio, fin).getVertexList();
        } catch (Exception e) {
            return null;
        }
    }
}