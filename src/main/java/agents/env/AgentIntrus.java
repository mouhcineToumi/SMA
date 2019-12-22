package agents.env;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;

import java.util.Iterator;
import java.util.List;

public class AgentIntrus extends Agent {

    private List<Location> itineraire ;

    Object args[] = this.getArguments() ;
    protected void setup() {
        if (args == null || args.length < 1) {
            System.out.println("Usage : <itineraire>");
            doDelete() ;
        }else {
            itineraire = (List<Location>) args[0];
            addBehaviour(new TickerBehaviour(this,1000) {


                protected void onTick() {

                    Iterator<Location> iterator = itineraire.listIterator();
                    Location destination = (Location) iterator.next();
                    iterator.remove();
                    myAgent.doMove( destination);
                    doDelete();
                }


            });

    }

}

}
