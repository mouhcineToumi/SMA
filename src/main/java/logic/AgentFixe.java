package logic;

import agents.env.GetAvailableLocationsBehaviour;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;

public class AgentFixe extends Agent {

    public void setup() {
// register the SL0 content language and the mobility ontology
        getContentManager().registerLanguage(new SLCodec(),
                FIPANames.ContentLanguage.FIPA_SL0);
        getContentManager().registerOntology(MobilityOntology.getInstance());
// get the list of available locations and show it
        addBehaviour(new GetAvailableLocationsBehaviour(this));
    }

}
