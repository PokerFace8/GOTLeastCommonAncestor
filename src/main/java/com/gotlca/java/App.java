package com.gotlca.java;

import org.jgrapht.Graph;
import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;
import java.util.Iterator;


/* Since the graph input into the program considers romantic relationship between person a and b
*  as a bidirectional edge between a and b, these edges have to be removed. This makes sense since a bidirectional
*  edge between 2 nodes a and b means that a is ancestor of b and also b is the ancestor of a, which is absurd.
* */
public class App
{
    public static void main( String[] args )
    {
        String input = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0])) ;
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            input = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        VertexProvider<String> vp = (a, b) -> a;
        EdgeProvider<String, DefaultEdge> ep = (f, t, l, a) -> new DefaultEdge();
        GraphImporter<String, DefaultEdge> importer = new DOTImporter<String, DefaultEdge>(vp, ep);
        DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        try
        {
            importer.importGraph(g, new StringReader(input));
        }
        catch (ImportException e)
        {
            e.printStackTrace();
        }

        removeRomanticRelations(g);

        NaiveLcaFinder<String, DefaultEdge> finder = new NaiveLcaFinder<>(g);

        Set<String> ancestors = finder.findLcas(args[1],args[2]);

        printAncestors(ancestors,args[1],args[2]);

    }

    /*
    *
    * Removes all bidirectional edges in the graph
    *
    * */

    private static void removeRomanticRelations(DefaultDirectedGraph graph)
    {
        Set<String> vs = graph.vertexSet();
        Iterator it = vs.iterator();

        String curr = "";
        while(it.hasNext())
        {
            curr = it.next().toString();
            Iterator itt = vs.iterator();
            while(itt.hasNext())
            {
                String temp = itt.next().toString();
                if(graph.containsEdge(curr,temp) && graph.containsEdge(temp,curr))
                {
                    graph.removeEdge(curr,temp);
                    graph.removeEdge(temp,curr);
                }
            }


        }
    }

    /*
    *   Utility function to print the set of ancestors
    * */

    private static void printAncestors(Set<String> ancestors,String person1,String person2)
    {
        Iterator iter = ancestors.iterator();
        if(ancestors.size()==0)
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
            String temp = "";
            while(iter.hasNext())
            {
                temp = iter.next().toString();
                if(!iter.hasNext())break;
                System.out.print(temp + ", ");
            }
            System.out.println(temp+".");
        }
    }
}
