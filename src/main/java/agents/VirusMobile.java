package agents;

public class VirusMobile extends AgentMobile {


    @Override
    protected void beforeMove() {
        super.beforeMove();

        if ( destination.isContaminated() ){
            foundIntrus(destination.label);
        }
        System.out.println("Virus : " + destination.label);
    }


}
