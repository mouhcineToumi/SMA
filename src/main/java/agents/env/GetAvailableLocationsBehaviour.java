package agents.env;

import jade.content.lang.sl.SLCodec;
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
import logic.AgentMobile;

public class GetAvailableLocationsBehaviour extends SimpleAchieveREInitiator {


    private ACLMessage request;
    public GetAvailableLocationsBehaviour(Agent a) {
        super(a, new ACLMessage(ACLMessage.REQUEST));
        request = (ACLMessage)getDataStore().get(REQUEST_KEY);

        // fills all parameters of the request ACLMessage
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
        // reset(request);
    }


    protected void handleNotUnderstood(ACLMessage reply) {
        System.out.println(myAgent.getLocalName()+ " handleNotUnderstood : "+reply.toString());
    }

    protected void handleRefuse(ACLMessage reply) {
        System.out.println(myAgent.getLocalName()+ " handleRefuse : "+reply.toString());
    }

    protected void handleFailure(ACLMessage reply) {
        System.out.println(myAgent.getLocalName()+ " handleFailure : "+reply.toString());
    }

    protected void handleAgree(ACLMessage reply) {
    }


    protected void handleInform(ACLMessage inform) {
        try {
            myAgent.getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
            myAgent.getContentManager().registerOntology(MobilityOntology.getInstance());
            Result sitesDisponibles = (Result)myAgent.getContentManager().extractContent(inform);
            Iterator iterator = sitesDisponibles.getItems().iterator();

            System.out.println("\n\n\nSites disponibles :");
            while(iterator.hasNext()){
                AID id = (AID) iterator.next();
                System.out.println( myAgent.here() + "-----" + myAgent.getLocalName() +  "+++++ " + id.getLocalName());
                //System.out.println(iterator.next());
            }
            System.out.println("\n\n\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
