package logic;

import agents.env.AgentFix;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class AgentMobile extends Agent {


    private List<Node> itineraire ;
    private int compteur = 0;


    public void foundIntrus( String loc) {
        System.out.println("Intrus found in :" + loc);
    }


    public void setup() {
        Object args[] = this.getArguments() ;
        if (args == null || args.length < 1) {
            System.out.println("Usage : <itineraire>");
            doDelete() ; //appelle takeDown()
        }
        else if (args.length == 2) {
            ContainerController mainController = (ContainerController) args[0];
            try {
                AgentController mobileAgent = mainController.createNewAgent(
                        "MAgent2", "logic.AgentMobile",new Object []{ args[1]}) ;
                mobileAgent.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
        else if ( args.length == 1) {
            System.out.println("name: " + getLocalName() );
            itineraire = (List<Node>) args[0];
            addBehaviour(new TickerBehaviour(this, 1000) {
                public void onTick() {
                    Iterator<Node> iterator = itineraire.listIterator();
                    try{
                        // Move to next container
                        etat++;
                        destination =iterator.next();
                        if ( destination.isContaminated() ){
                            foundIntrus(destination.label);
                        }
                        System.out.println(destination.label);
                        iterator.remove();
                        myAgent.doMove(destination.loc);
                    } catch (Exception e) {
                        myAgent.doDelete();
                    }

                }
                private int etat=0;
                private Node destination;
            });

        }


    }

    @Override
    protected void beforeMove() {
        super.beforeMove();
//        System.out.println("moving: " + this.getLocalName() );
    }

    @Override
    protected void afterMove() {
        super.afterMove();
//        System.out.println("moved: " + this.getLocalName()  );
    }
}
