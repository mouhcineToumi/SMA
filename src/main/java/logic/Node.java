package logic;

import java.util.ArrayList;
import java.util.List;

public class Node {

    String label;
    boolean contaminated;
    List<Node> childs;



    public Node(String label){
        this.label = label;
        contaminated =false;
        childs = new ArrayList<Node>();
    }
}
