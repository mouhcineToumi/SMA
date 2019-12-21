package agents.env;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

public class JadeContainer {


    private ContainerController container;
    public JadeContainer() {
        Runtime rt = Runtime.instance() ;
        ProfileImpl profile = new ProfileImpl(false);

        //Le main container associé est déjà démarré sur localhost
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost") ;
        profile.setParameter(ProfileImpl.CONTAINER_NAME, "BookMarket") ;
        container = rt.createAgentContainer(profile);
    }
    public ContainerController getContainer(){ return container; }
}
