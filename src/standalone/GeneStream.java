package standalone;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Graph;

public class GeneStream {

    private Graph graph;
    private MainGenerator csv;

    public GeneStream(String filename) {
        // Set the program to run using javafx GUI system
        System.setProperty("org.graphstream.ui", "javafx");

        // Create a new multigraph
        this.graph = new MultiGraph("g");

        // Create a stylesheet for nicely displaying the graph nodes and edges
        this.graph.setAttribute("ui.stylesheet", "url('file://graph.css')");

        // Configure the graph

        this.csv = new MainGenerator(graph, filename);
        this.graph.display();

        // Begin reading and displaying data on the graph
        this.csv.begin();
        this.csv.nextEvents();
        this.csv.end();
    }

    public static void main(String[] args) {
        String filename = "data/Kelley Family Tree.ged";
        GeneStream client = new GeneStream(filename);
    }
}
