package logic;

import jade.core.ContainerID;

import java.util.ArrayList;
import java.util.List;

public class Node {

    ContainerID loc;
    String label;
    boolean contaminated, visited;
    List<Node> childs;

    public Node(String label){
        this.label = label;
        contaminated =false;
        visited = false;
        childs = new ArrayList<Node>();
        setContainerId(label);
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void visit() {
        this.visited = true;
    }

    public boolean isChild(String label) {
        for ( Node node: childs){
            if ( node.label.equals(label)){
                return  true;
            }
        }
        return false;
    }

    public void addChild(Node node) {
        if ( !isChild(node.label)) {
            childs.add(node);
        }
    }


    public void setContainerId( String name ) {
        loc = new ContainerID();
        loc.setName(name);
        loc.setAddress("localhost");
    }
}



