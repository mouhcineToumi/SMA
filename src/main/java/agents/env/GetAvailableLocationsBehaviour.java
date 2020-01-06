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
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import logic.AgentMobile;

import java.util.ArrayList;

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

            ArrayList<String> arr = new ArrayList<String>();

            System.out.println("agents disponibles :");
            while(iterator.hasNext()){
                AID id = (AID) iterator.next();
                arr.add(id.getLocalName());
                //System.out.println( myAgent.here() + "-----" + myAgent.getLocalName() +  "+++++ " + id.getLocalName());
            }
            System.out.println(arr);

            if ( arr.contains("MAgent") && arr.contains("MAgent2")){
                System.out.println("KILL");
                AgentContainer container =(AgentContainer) myAgent.getContainerController();
                AgentController victim = container.getAgent("MAgent2");
                victim.kill();
                System.out.println("KILLED");
            }
            System.out.println("\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
