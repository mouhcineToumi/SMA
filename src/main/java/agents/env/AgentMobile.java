package agents.env;

import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;


import java.util.Iterator;
import java.util.List;

public class AgentMobile extends Agent {
    private List<Location> itineraire ;


    Object args[] = this.getArguments() ;

    public void setup(){
        if (args == null || args.length < 1) {
            System.out.println("Usage : <itineraire>");
            doDelete() ;
        }else {
            itineraire = (List<Location>) args[0];
            addBehaviour(new CyclicBehaviour(this) {

                public void action() {

                    Iterator<Location> iterator = itineraire.listIterator();
                    Location destination = (Location) iterator.next();
                    iterator.remove();
                    myAgent.doMove( destination);
                    doDelete();


                }



            });
        }

        //parcours DFS sur les conteneur existant

        //retour au noeud principal if (intrus n'existe pas || intrue found et nettoyé)

    }
}
