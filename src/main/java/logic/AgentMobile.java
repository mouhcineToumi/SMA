package logic;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;

import java.util.Iterator;
import java.util.List;

public class AgentMobile extends Agent {


    private List<Node> itineraire ;
    private int compteur = 0;


    public void setup() {
        Object args[] = this.getArguments() ;
        if (args == null || args.length < 1) {
            System.out.println("Usage : <itineraire>");
            doDelete() ; //appelle takeDown()
        }
        else {
            itineraire = (List<Node>) args[0];
            addBehaviour(new CyclicBehaviour(this) {
                public void action() {
                    Iterator<Node> iterator = itineraire.listIterator();
                    try{
                        //L'agent MOVE vers le premier container
                        etat++;
                        destination =(Node)iterator.next();
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
