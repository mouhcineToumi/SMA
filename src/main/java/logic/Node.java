package logic;

import java.util.ArrayList;
import java.util.List;

public class Node {

    boolean contaminated;
    List<Node> childs;
    Node parent;


    public Node() {
        contaminated =false;
        childs = new ArrayList<Node>();
        parent = new Node();
    }
}
