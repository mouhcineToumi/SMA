package agents.env;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.FIPAManagementOntology;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.Iterator;
import java.util.List;

public class AgentFix extends Agent {


    public void setup() {

        //create agent mobile sur container principale

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                createNewAgent(myAgent,"AgentMobile","AgentMobile",new Object[] {/* Partie de l'arbre en argument (right or left side or the tree*/});
            }
        });

        // wait for mobile agent ( if intrus n'existe pas or found)
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                query();

            }
        });

        addBehaviour(new GetAvailableLocationsBehaviour(this));
        // cleans current container if intrus comes back up

    }

    private void query() {
        Location myLocation = here();
        ACLMessage query = prepareRequestToAMS(myLocation);
        send(query);
        MessageTemplate mt = MessageTemplate.MatchSender(getAMS());
        ACLMessage response = blockingReceive(mt);
        List residents = parseAMSResponse(response);
        for (Iterator it = residents.iterator(); it.hasNext();) {
            AID r = (AID) it.next();
            if (r.getLocalName().equals("AgentMobile")){
                //Give the other part of the tree
            }else if(r.getLocalName().equals("AgentIntrus")){
                AgentController a = null;
                try {
                    a = getContainerController().getAgent("AgentIntrus");
                    a.kill();
                    System.out.println("Intrus Killed by AgentFixe");
                } catch (ControllerException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    private ACLMessage prepareRequestToAMS(Location where) {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(getAMS());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setOntology(MobilityOntology.NAME);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

        // creates the content of the ACLMessage
        Action act = new Action();
        act.setActor(getAMS());
        QueryAgentsOnLocation action = new QueryAgentsOnLocation();
        action.setLocation(where);
        act.setAction(action);
        try {
            getContentManager().fillContent(request, act);
        } catch (Exception ignore) {
        }
        return request;
    }

    private List parseAMSResponse(ACLMessage response) {
        Result results = null;
        try {
            results = (Result) getContentManager().extractContent(response);
        } catch (Exception ingore) {
        }
        return (List) results.getItems();

    }

    //this is for the creation of an Agent from another Agent
    private static Codec codec = new SLCodec();
    private static ContentManager cm = new ContentManager();
    static
    {
        cm.registerLanguage(codec, FIPANames.ContentLanguage.FIPA_SL0);
        cm.registerOntology(FIPAManagementOntology.getInstance());
        cm.registerOntology(JADEManagementOntology.getInstance());
    }

    public static AID createNewAgent(Agent agent, String name, String className,Object[] args)
    {
        CreateAgent ca = new CreateAgent();
        ca.setAgentName(name);
        ca.setClassName(className);
        ca.addArguments(args);
        ca.setContainer((ContainerID) agent.here());

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(agent.getAMS());
        request.setOntology(JADEManagementOntology.getInstance().getName());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setReplyWith("new-agent-" + agent.getName() + System.currentTimeMillis());

        try
        {
            cm.fillContent(request, new Action(agent.getAMS(), ca));
            agent.send(request);

            MessageTemplate mt = MessageTemplate.MatchInReplyTo(request.getReplyWith());
            ACLMessage reply = agent.blockingReceive(mt, 10000);
            if(reply == null)
                throw new RuntimeException("cannot create agent [" + name + "; " + className + "]");

            return new AID(name, AID.ISLOCALNAME);
        }
        catch (Codec.CodecException e)
        {
            throw new RuntimeException(e);
        }
        catch (OntologyException e)
        {
            throw new RuntimeException(e);
        }
    }
    /////////////////////
}
