package logic;

import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {

        Graph graph = new Graph();
        for( int i =0; i<7; i++ ){
            graph.addNode(new Node(Integer.toString(i)));
        }
        graph.addNode(new Node(Integer.toString(20)));

        graph.addEdge("0", "1");
        graph.addEdge("0", "2");
        graph.addEdge("2", "3");
        graph.addEdge("2", "4");
        graph.addEdge("1", "20");

        System.out.println( graph.dfs( graph.nodes.get("0"), new ArrayList<Node>()) );
    }

}
