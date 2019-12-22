package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
            // because undirected
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



    public void bfs(String label) {
        if( nodes.get(label).childs.size() == 0){
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(nodes.get(label));

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            if (currentFirst.isVisited())
                continue;

            System.out.println(currentFirst.label);
            currentFirst.visit();
            List<Node> allNeighbors = nodes.get(currentFirst.label).childs;
            if (allNeighbors == null)
                continue;
            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
    }


    public void bfs(Node root) {
        if( root.childs.size() == 0){
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            if (currentFirst.isVisited())
                continue;

            System.out.println(currentFirst.label);
            currentFirst.visit();
            List<Node> allNeighbors = nodes.get(currentFirst.label).childs;
            if (allNeighbors == null)
                continue;
            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
    }

}
