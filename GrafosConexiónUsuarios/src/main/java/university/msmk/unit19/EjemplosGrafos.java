package university.msmk.unit19;

import lombok.extern.slf4j.Slf4j;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;


@Slf4j
public class EjemplosGrafos {
    public void creaGrafoDirigido(){
        // Crear un grafo dirigido y ponderado
        Graph<String, DefaultWeightedEdge> graph =
                new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        // 1️⃣ Añadir nodos
        String[] nodes = {"A", "B", "C", "D", "E", "F"};
        for (String v : nodes) graph.addVertex(v);

        // 2️⃣ Añadir aristas con pesos
        addEdge(graph, "A", "B", 4);
        addEdge(graph, "A", "C", 2);
        addEdge(graph, "B", "C", 1);
        addEdge(graph, "B", "D", 5);
        addEdge(graph, "C", "D", 8);
        addEdge(graph, "C", "E", 10);
        addEdge(graph, "D", "E", 2);
        addEdge(graph, "D", "F", 6);
        addEdge(graph, "E", "F", 3);

        /// PINTAR
        pintar(graph);

        /// / EJEMPLO DIJKSTRA
        String origen = "A";
        String destino = "F";
        var dijkstra = new DijkstraShortestPath<>(graph);
        var path = dijkstra.getPath(origen, destino);

        log.info("Camino mínimo de {} a {} : {}",origen,destino, path.getVertexList());
        log.info("Peso total: {}", path.getWeight());
    }

    private static void addEdge(Graph<String, DefaultWeightedEdge> g, String s, String t, double w) {
        var e = g.addEdge(s, t);
        g.setEdgeWeight(e, w);
    }

    private void pintar(Graph<String, DefaultWeightedEdge> graph){
        System.setProperty("java.awt.headless", "false");
        System.setProperty("org.graphstream.ui", "swing");

        // 2️⃣ Crear el grafo visual (GraphStream)
        org.graphstream.graph.Graph gs = new SingleGraph("Grafo dirigido con pesos");

        // --- Estilo visual (CSS) ---
        String styleSheet = """
            node {
              fill-color: #3366cc;
              size: 25px;
              text-size: 16px;
              text-alignment: at-right;
              text-offset: 5px, 0px;
            }
            edge {
              fill-color: #999;
              arrow-shape: arrow;
              arrow-size: 12px, 8px;
              text-alignment: above;
              text-size: 14px;
            }
            """;
        gs.setAttribute("ui.stylesheet", styleSheet);

        // 3️⃣ Añadir nodos
        for (String v : graph.vertexSet()) {
            var n = gs.addNode(v);
            n.setAttribute("ui.label", v);
        }

        // 4️⃣ Añadir aristas dirigidas con peso
        for (DefaultWeightedEdge e : graph.edgeSet()) {
            String src = graph.getEdgeSource(e);
            String tgt = graph.getEdgeTarget(e);
            double w = graph.getEdgeWeight(e);

            String id = src + "->" + tgt;
            var edge = gs.addEdge(id, src, tgt, true); // true = dirigido
            edge.setAttribute("ui.label", w);
        }

        // 5️⃣ Mostrar ventana
        Viewer viewer = gs.display();
        viewer.enableAutoLayout();
    }
}
