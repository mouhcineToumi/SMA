package logic;

public class Demo {

    public static void main(String[] args) {

        Graph graph = new Graph();
        for( int i =0; i<7; i++ ){
            graph.addNode(new Node(Integer.toString(i)));
        }

        graph.addEdge("0", "1");
        graph.addEdge("0", "2");
        graph.addEdge("2", "3");
        graph.addEdge("2", "4");
        graph.addEdge("1", "5");
        graph.addEdge("5", "6");

        graph.bfs( graph.nodes.get("0") );
    }

}
