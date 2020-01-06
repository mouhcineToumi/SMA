package agents;

import jade.content.lang.sl.SLCodec;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;

public class PoliceMobile extends AgentMobile {


    @Override
    protected void beforeMove() {
        super.beforeMove();

    }


    @Override
    protected void afterMove() {
        super.afterMove();

        if ( this.destination.isContaminated() ){
            System.out.println("purifying " + this.destination.label);
            this.destination.unContaminate();
        }

        getContentManager().registerOntology(MobilityOntology.getInstance());
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
        addBehaviour(new GetAvailableLocationsBehaviour((AgentMobile) this));
    }
}
