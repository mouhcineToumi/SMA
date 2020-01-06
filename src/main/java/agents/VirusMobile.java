package agents;

import jade.content.lang.sl.SLCodec;
import jade.domain.FIPANames;
import jade.domain.mobility.MobilityOntology;

public class VirusMobile extends AgentMobile {


    @Override
    protected void beforeMove() {
        super.beforeMove();

        System.out.println("\t\t\t\t\t\t\t\t\tVirus : " + "from : " + here().getName() + " ----> to : " + destination.loc.getName() );
    }


    @Override
    protected void afterMove() {
        super.afterMove();
        destination.contaminate();

        getContentManager().registerOntology(MobilityOntology.getInstance());
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
        addBehaviour( new GetAvailableLocationsBehaviour((AgentMobile) this) );
    }


}
