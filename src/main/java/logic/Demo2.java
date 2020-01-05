package logic;

import agents.env.JadeContainer;
import agents.env.MainContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

public class Demo2  {

    Graph graph = new Graph();

    private ContainerController mainContainer ;
    private AgentController mobileAgent, fixAgent, mobileAgent2;
    private List<Node> itinéraire;
    private List<Node> itinéraire2;

    public Demo2() {
        mainContainer = new MainContainer().getContainer();
        // init contaners
        for (int i = 0; i < 9; i++) {
            new JadeContainer().getContainer();
        }
        // define the graph nodes
        graph.addNode(new Node("Node"));
        for( int i =1; i<9; i++ ){
            graph.addNode(new Node( "Node-" + i ));
        }
        // defining edges
        graph.addEdge("Node", "Node-1");
        graph.addEdge("Node", "Node-2");
        graph.addEdge("Node-2", "Node-3");
        graph.addEdge("Node-2", "Node-4");
        graph.addEdge("Node-2", "Node-5");
        graph.addEdge("Node-4", "Node-6");
        graph.addEdge("Node-4", "Node-7");
        graph.addEdge("Node-4", "Node-8");
        // define intrus
        graph.nodes.get("Node-4").contaminate();

        itinéraire = graph.dfs(graph.nodes.get("Node"), new ArrayList<Node>());
        graph.clear();
        itinéraire2 = graph.bfs(graph.nodes.get("Node-4"), new ArrayList<Node>());

        try {
            // fixAgent = mainContainer.createNewAgent("fixed", "ensias.tp3.AgentFixe", new Object[]{""});
            // fixAgent.start();
            mobileAgent = mainContainer.createNewAgent(
                    "MAgent", "logic.AgentMobile",new Object []{itinéraire, "police"}) ;
            mobileAgent2 = mainContainer.createNewAgent(
                    "MAgent2", "logic.AgentMobile",new Object []{itinéraire2, "sick"}) ;
            mobileAgent.start();
            mobileAgent2.start();
        } catch (StaleProxyException e) { e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Demo2();
    }

}
