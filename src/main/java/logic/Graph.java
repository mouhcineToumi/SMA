package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<String, List<String>> adjList;
    HashMap<String, Node> nodes;
    HashMap<String, Boolean> visited;

    public  Graph() {
        nodes = new HashMap<>();
        adjList = new HashMap<>();
        visited = new HashMap<>();
    }

    public void addNode(String label) {
        if (!nodes.containsKey(label) ){
            nodes.put(label, new Node(label));
            visited.put(label, false);
            adjList.put(label, new ArrayList<String>());

        }
    }

    public void addEdge(String from, String to) {
        if ( !adjList.get(from).contains(to) ){
            adjList.get(from).add(to);
        }
    }


    public void dfs( String label ){
        if( visited.get(label) ) {
            return;
        }
        System.out.println(label);
        visited.replace(label, true);
        for ( String child: adjList.get(label)){
            dfs(child);
        }
    }


    public static void main(String[] args) {

        Graph graph = new Graph();
        for( int i =0; i<5; i++ ){
            graph.addNode(Integer.toString(i));
        }

        graph.addEdge("0", "1");
        graph.addEdge("0", "2");
        graph.addEdge("2", "3");
        graph.addEdge("2", "4");


        graph.dfs("0");
    }




}
