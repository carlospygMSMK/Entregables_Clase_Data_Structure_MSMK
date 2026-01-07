package university.msmk.unit19;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxCircleLayout;

import com.mxgraph.swing.mxGraphComponent;

import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;


import javax.swing.*;
import java.util.Map;

public class GraphViewer {

    private static Viewer graphStreamViewer;
    private static org.graphstream.graph.Graph graphStreamGraph;

    public static <V, E> void show(Graph<V, E> graph,
                                   java.util.function.Function<V, String> vertexLabel,
                                   java.util.function.Function<E, String> edgeLabel) {

        System.setProperty("java.awt.headless", "false");
        System.setProperty("org.graphstream.ui", "swing");

        // Adapter JGraphT -> JGraphX
        JGraphXAdapter<V, E> adapter = new JGraphXAdapter<>(graph);
        adapter.getStylesheet().getDefaultVertexStyle()
                .put(mxConstants.STYLE_FONTSIZE, 24);

        // Poner etiquetas (vértices y aristas)
        Map<V, mxICell> vToCell = adapter.getVertexToCellMap();
        for (var v : graph.vertexSet()) {
            Object cell = vToCell.get(v);
            adapter.getModel().setValue(cell, vertexLabel.apply(v));
        }

        Map<E, mxICell> eToCell = adapter.getEdgeToCellMap();
        for (var e : graph.edgeSet()) {
            Object cell = eToCell.get(e);
            adapter.getModel().setValue(cell, edgeLabel.apply(e));
        }

        // Layout
        mxCircleLayout layout = new mxCircleLayout(adapter);

        // Parámetros útiles en FastOrganic
//        layout.setForceConstant(200);     // “repulsión”; sube si se amontona
//        layout.setMinDistanceLimit(200);  // distancia mínima entre nodos
//        layout.setInitialTemp(200);      // energía inicial
//        layout.setMaxIterations(1800);    // más iteraciones = mejor, pero más lento
//        layout.setUseInputOrigin(true);

        // Parámetros útiles en Circle
        layout.setRadius(50);              // sube si quieres más ancho

        layout.execute(adapter.getDefaultParent());

        // Swing window
        JFrame frame = new JFrame("X Users Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mxGraphComponent graphComponent = new mxGraphComponent(adapter);
        graphComponent.zoomTo(0.50, true);
        frame.getContentPane().add(graphComponent);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void pintar(Graph<Usuario, SiguiendoArista> graph){
        System.setProperty("java.awt.headless", "false");
        System.setProperty("org.graphstream.ui", "swing");

        if (graphStreamViewer != null) {
            graphStreamViewer.close();
            graphStreamViewer = null;
        }

        // Crear el grafo visual (GraphStream)
        graphStreamGraph = new DefaultGraph("pp");
        org.graphstream.graph.Graph gs = graphStreamGraph;

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

        //Añadir nodos con una posición inicial en círculo (el auto layout parte de aquí)
        int totalVertices = Math.max(1, graph.vertexSet().size());
        double radius = Math.max(80, totalVertices * 12.0);
        int index = 0;
        for (Usuario u : graph.vertexSet()) {
            Node node = gs.addNode(u.email());
            node.setAttribute("ui.label", u.email());

            double angle = (2 * Math.PI * index) / totalVertices;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            node.setAttribute("xyz", x, y, 0);
            index++;
        }

        //Añadir aristas dirigidas con peso
        for (SiguiendoArista e : graph.edgeSet()) {
            Usuario srcUser = graph.getEdgeSource(e);
            Usuario tgtUser = graph.getEdgeTarget(e);

            String src = srcUser.email();
            String tgt = tgtUser.email(); // true = dirigido

            String id = src + "->" + tgt;
            var edge = gs.addEdge(id, src, tgt, true);

            if (graph.containsEdge(tgtUser, srcUser)) {
                edge.setAttribute("ui.style", "fill-color: orange; stroke-width: 3px;");
            }
        }

        //Mostrar ventana
        graphStreamViewer = gs.display();
        graphStreamViewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        graphStreamViewer.enableAutoLayout(new SpringBox(false));
        var camera = graphStreamViewer.getDefaultView().getCamera();
        camera.setAutoFitView(true);
        camera.resetView();
    }
}