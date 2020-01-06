package agents;

public class PoliceMobile extends AgentMobile {


    @Override
    protected void beforeMove() {
        super.beforeMove();

        if ( destination.isContaminated() ){
            foundIntrus(destination.label);
        }
        System.out.println("Police : " + destination.label);
    }
}
