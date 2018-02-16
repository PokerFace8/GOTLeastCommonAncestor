package com.gotlca.java;

import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

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
        DefaultDirectedGraph<String, DefaultEdge> g =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        try
        {
            importer.importGraph(g, new StringReader(input));
        }
        catch (ImportException e)
        {
            e.printStackTrace();
        }

        NaiveLcaFinder<String, DefaultEdge> finder = new NaiveLcaFinder<>(g);
        String ancestor = finder.findLca(args[1],args[2]);
        // Set<String> ancestors = finder.findLcas(args[1],args[2]);
        // for(String ancestor:ancestors)
        System.out.println("The least common ancestor of " + args[1] + " and " + args[2] + " is " + ancestor);    }
}
