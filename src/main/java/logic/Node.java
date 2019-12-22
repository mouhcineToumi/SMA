package logic;

import java.util.ArrayList;
import java.util.List;

public class Node {


    String label;
    boolean contaminated, visited;
    List<Node> childs;

    public Node(String label){
        this.label = label;
        contaminated =false;
        visited = false;
        childs = new ArrayList<Node>();
    }

    public boolean isChild(String label) {
        for ( Node node: childs){
            if ( node.label == label){
                return  true;
            }
        }
        return false;
    }

    public void addChild(Node node) {
        if( ! childs.contains(node)) {
            childs.add(node);
        }
    }
}
