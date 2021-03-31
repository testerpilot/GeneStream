package standalone;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.Submitter;
import org.gedcom4j.relationship.Relationship;
import org.gedcom4j.relationship.RelationshipName;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.AttributeSink;
import org.graphstream.stream.ElementSink;
import org.graphstream.stream.Sink;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.relationship.RelationshipCalculator;
import org.gedcom4j.relationship.Relationship;
import org.gedcom4j.relationship.SimpleRelationship;

import static org.gedcom4j.relationship.RelationshipName.*;


public class MainGenerator implements Generator {
    private Graph graph;
    private Scanner scan;
    private String filename;

    private GedcomParser gp;

    MainGenerator(Graph g, String fname){
        graph = g;
        filename = fname;
    }



    @Override
    public void begin(){
        gp = new GedcomParser();

        try {
            gp.load(filename);
        }
        catch(Exception IOException){

        }

    }

    @Override
    public boolean nextEvents() {

        for(Individual i : gp.gedcom.individuals.values()) {
            RelationshipCalculator r;

            Node n1 = graph.getNode(i.formattedName().replace('/',' '));

            if(n1 == null) {
                n1 = graph.addNode(i.formattedName().replace('/',' '));
                n1.setAttribute("label", i.formattedName().replace('/',' '));
            }

            for(Individual a : gp.gedcom.individuals.values()) {
                r = new RelationshipCalculator();
                r.calculateRelationships(a, i, false);

                Node n2 = graph.getNode(a.formattedName().replace('/',' '));

                if (n2 == null) {
                    n2 = graph.addNode(a.formattedName().replace('/',' '));
                    n2.setAttribute("label", a.formattedName().replace('/',' '));
                }


                for(Relationship rel : r.relationshipsFound) {
                    for(SimpleRelationship sim : rel.chain) {
                        if(sim.name == SON && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n2, n1, true);
                            }
                        }
                        if(sim.name == DAUGHTER && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n2, n1, true);
                            }
                        }
                        else if(sim.name == MOTHER && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n1, n2, true);
                            }
                        }
                        else if(sim.name == FATHER && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n1, n2, true);
                            }
                        }
                        else if(sim.name == HUSBAND && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n1, n2, false);
                            }
                        }
                        else if(sim.name == WIFE && sim.individual1 == a && sim.individual2 == i) {
                            System.out.println(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));
                            Edge e = graph.getEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '));

                            if (e == null) {
                                e = graph.addEdge(i.formattedName().replace('/',' ') + "-" + a.formattedName().replace('/',' '), n1, n2, false);
                            }
                        }
                    }
                }
            }

        /*for(Family f : gp.gedcom.families.values()) {
            Individual husband = f.husband;
            Individual wife = f.wife;

            Node husbandNode = graph.getNode("");
            Node wifeNode = graph.getNode("");

            if(husband != null) {
                husbandNode = graph.getNode(husband.formattedName().replace('/',' '));

                if(husbandNode == null) {
                    husbandNode = graph.addNode(husband.formattedName().replace('/',' '));
                    husbandNode.setAttribute("label", husband.formattedName().replace('/',' '));
                }
            }

            if(wife != null) {
                wifeNode = graph.getNode(wife.formattedName().replace('/',' '));

                if(wifeNode == null) {
                    wifeNode = graph.addNode(wife.formattedName().replace('/',' '));
                    wifeNode.setAttribute("label", wife.formattedName().replace('/',' '));
                }
            }

            for(Individual child : f.children) {
                Node childNode = graph.getNode(child.formattedName().replace('/',' '));

                Edge motherEdge = graph.getEdge(wife.formattedName().replace('/',' ') + "→" + child.formattedName().replace('/',' '));
                Edge fatherEdge = graph.getEdge(husband.formattedName().replace('/',' ') + "→" + child.formattedName().replace('/',' '));

                if(childNode == null) {
                    childNode = graph.addNode(child.formattedName().replace('/',' '));
                    childNode.setAttribute("label", child.formattedName().replace('/',' '));
                }

                if(motherEdge == null) {
                    motherEdge = graph.addEdge(wife.formattedName().replace('/',' ') + "→" + child.formattedName().replace('/',' '), wifeNode, childNode, true);
                }
                if(fatherEdge == null) {
                    fatherEdge = graph.addEdge(husband.formattedName().replace('/',' ') + "→" + child.formattedName().replace('/',' ') husbandNode, childNode, true);
                }
            }


            /*if(childNode == null) {
                childNode = graph.addNode(childName);
                childNode.setAttribute("label", childName);
            }

            for(Individual a : i {
                String descendantName = a.names.get(0).basic;

                System.out.println(childName + " is a descendant of " + descendantName);

                Node descendantNode = graph.getNode(descendantName);

                if(descendantNode == null) {
                    descendantNode = graph.addNode(descendantName);
                    descendantNode.setAttribute("label", descendantName);
                }

                Edge e = graph.getEdge(descendantName + ":" + childName);

                if(e == null) {
                    e = graph.addEdge(descendantName + ":" + childName, descendantNode, childNode);
                }
            } */
        }

        return false;
    }

    @Override
    public void end() {
        scan.close();
    }

    @Override
    public void addSink(Sink sink) {
        this.graph = (Graph) sink;
    }

    @Override
    public void removeSink(Sink sink) {
        this.graph = null;
    }

    @Override
    public void addAttributeSink(AttributeSink attributeSink) {
        this.graph = (Graph) attributeSink;
    }

    @Override
    public void removeAttributeSink(AttributeSink attributeSink) {
        this.graph = null;
    }

    @Override
    public void addElementSink(ElementSink elementSink) {
        this.graph = (Graph) elementSink;
    }

    @Override
    public void removeElementSink(ElementSink elementSink) {
        this.graph = null;
    }

    @Override
    public void clearElementSinks() {
        this.graph = null;
    }

    @Override
    public void clearAttributeSinks() {
        this.graph = null;
    }

    @Override
    public void clearSinks() {
        this.graph = null;
    }
}
