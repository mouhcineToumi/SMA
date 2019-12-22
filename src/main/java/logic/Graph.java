package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<String, List<String>> adjList;
    HashMap<String, Node> nodes;

    public  Graph() {
        nodes = new HashMap<>();
        adjList = new HashMap<>();
    }

    public void addNode(String label) {
        if (!nodes.containsKey(label) ){
            nodes.put(label, new Node(label));
            adjList.put(label, new ArrayList<String>());
        }
    }

    public void addEdge(String from, String to) {
        if ( ! nodes.get(from).isChild(to) ){
            nodes.get(from).addChild( nodes.get(to) );
            adjList.get(from).add(to);
            addEdge(to, from);
        }
    }


    public void dfs( String label ){
        if( nodes.get(label).isVisited() ) {
            return;
        }
        System.out.println(label);
        nodes.get(label).visit();
        for ( String child: adjList.get(label)){
            dfs(child);
        }
    }

    public void dfs( Node root ){
        if( root.isVisited() ) {
            return;
        }
        System.out.println(root.label);
        root.visit();
        for ( Node child: root.childs){
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

        graph.dfs( graph.nodes.get("0") );
    }




}
