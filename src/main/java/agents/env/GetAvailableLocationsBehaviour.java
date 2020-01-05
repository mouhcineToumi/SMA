package agents.env;

import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;
import jade.util.leap.Iterator;

public class GetAvailableLocationsBehaviour extends SimpleAchieveREInitiator {


    private ACLMessage request;
    public GetAvailableLocationsBehaviour(Agent a) {

        // call the constructor of FipaRequestInitiatorBehaviour
        super(a, new ACLMessage(ACLMessage.REQUEST));

        // fills all parameters of the request ACLMessage
        request = (ACLMessage)getDataStore().get(REQUEST_KEY);
        request.clearAllReceiver();
        request.addReceiver(a.getAMS());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setOntology(MobilityOntology.NAME);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

        // creates the content of the ACLMessage
        try {
            Action action = new Action();
            action.setActor(a.getAMS());
            QueryAgentsOnLocation q = new QueryAgentsOnLocation();
            q.setLocation(this.getAgent().here());
            action.setAction(q);
            a.getContentManager().fillContent(request, action);
        }
        catch(Exception e) { e.printStackTrace() ;}
        myAgent.send(request);
    }


    protected void handleInform(ACLMessage inform) {
        try {
            Result sitesDisponibles = (Result)myAgent.getContentManager().extractContent(inform);
            Iterator iterator = sitesDisponibles.getItems().iterator();
            System.out.println( sitesDisponibles.getItems().get(2) );
            System.out.println("\n\n\nSites disponibles :");
            System.out.println(sitesDisponibles.getItems());
            while(iterator.hasNext()){
                AID id = (AID) iterator.next();
                System.out.println(id.getLocalName());
                //System.out.println(iterator.next());
            }
            System.out.println("\n\n\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
