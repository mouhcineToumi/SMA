package logic;

import agents.env.JadeContainer;
import agents.env.MainContainer;
import jade.core.ContainerID;
import jade.core.Location;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo2  {

    Graph graph = new Graph();



    private ContainerController mainContainer ;
    private AgentController mobileAgent, fixAgent;
    private List<Node> itinéraire;
    public Demo2() {
        mainContainer = new MainContainer().getContainer();
        // init contaners
        for (int i = 0; i < 5; i++) {
            new JadeContainer().getContainer();
        }

        // define the graph nodes
        graph.addNode(new Node("Node"));
        for( int i =1; i<6; i++ ){
            graph.addNode(new Node( "Node-" + i ));
        }
        graph.addEdge("Node", "Node-1");
        graph.addEdge("Node", "Node-2");
        graph.addEdge("Node-2", "Node-3");
        graph.addEdge("Node-2", "Node-4");

        itinéraire = graph.dfs(graph.nodes.get("Node"), new ArrayList<Node>());

        try {
            // fixAgent = mainContainer.createNewAgent("fixed", "ensias.tp3.AgentFixe", new Object[]{""});
            // fixAgent.start();
            mobileAgent = mainContainer.createNewAgent(
                    "Touré", "logic.AgentMobile",new Object []{itinéraire}) ;
            mobileAgent.start();
        } catch (StaleProxyException e) { e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Demo2();
    }

}
