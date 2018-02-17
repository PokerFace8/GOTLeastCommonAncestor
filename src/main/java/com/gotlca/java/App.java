package com.gotlca.java;

import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

/**
 * Since the graph input into the program considers romantic relationship between person a and b
 *  as a bidirectional edge between a and b, these edges have to be removed. This makes sense since a bidirectional
 *  edge between 2 nodes a and b means that a is ancestor of b and also b is the ancestor of a, which is absurd.
 */
public class App
{
    public static void main(String[] args ) throws IllegalArgumentException
    {
        if(args.length!=3) {
            throw new IllegalArgumentException("3 arguments are required."+args.length+" arguments provided.");
        }
        VertexProvider<String> vp = (a, b) -> a;
        EdgeProvider<String, DefaultEdge> ep = (f, t, l, a) -> new DefaultEdge();
        GraphImporter<String, DefaultEdge> importer = new DOTImporter<String, DefaultEdge>(vp, ep);
        DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        try
        {
            importer.importGraph(g, new java.io.File(args[0]));
        }
        catch (ImportException e)
        {
            e.printStackTrace();
        }

        if(!g.containsVertex(args[1]))
        {
            throw new IllegalArgumentException("Please make sure \'"+ args[1]+"\' belongs to the family tree");
        }
        if(!g.containsVertex(args[2]))
        {
            throw new IllegalArgumentException("Please make sure \'"+ args[2]+"\' belongs to the family tree");
        }

        removeRomanticRelations(g);

        NaiveLcaFinder<String, DefaultEdge> finder = new NaiveLcaFinder<>(g);

        Set<String> ancestors = finder.findLcas(args[1],args[2]);

        printAncestors(ancestors,args[1],args[2]);
    }

    /**
    * Removes all bidirectional edges in the graph
    * @param graph the input graph to the problem
    * */

    private static void removeRomanticRelations(DefaultDirectedGraph<String,DefaultEdge> graph)
    {
        Set<DefaultEdge> edgeset = graph.edgeSet();
        Iterator it = edgeset.iterator();
        // Since deleting the edges while traversing them will modify the edgeSet,
        // store them in an ArrayList to delete after traversal
        ArrayList<DefaultEdge> edgesToremove = new ArrayList<>();
        while(it.hasNext())
        {
            DefaultEdge e = (DefaultEdge) it.next();

            String v1 = graph.getEdgeSource(e);
            String v2 = graph.getEdgeTarget(e);

            if(graph.containsEdge(v2,v1))
            {
                edgesToremove.add(e);
            }
        }

        for(DefaultEdge de: edgesToremove)
        {
            String v1 = graph.getEdgeSource(de);
            String v2 = graph.getEdgeTarget(de);
            graph.removeEdge(de);
            graph.removeEdge(v2,v1);
        }
    }

    /**
     * Utility function to print all ancestors
     * @param ancestors the set of all vertices that are ancestors
     * @param person1 the first person whose ancestor is to be found
     * @param person2 the second person whose ancestor is to be found
     */

    private static void printAncestors(Set<String> ancestors,String person1,String person2)
    {
        Iterator iter = ancestors.iterator();
        if(ancestors.isEmpty())
        {
            System.out.println("There are no common ancestors between " + person1 + " and " + person2);
        }
        else if(ancestors.size()==1)
        {
            System.out.println("The least common ancestor of " + person1 + " and " + person2 + " is " + iter.next());
        }
        else
        {
            System.out.print("The least common ancestors of " + person1 + " and " + person2 + " are ");
            System.out.println(String.join(",",ancestors));
        }
    }
}
