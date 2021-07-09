package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.service.util.CyServiceRegistrar;

@SuppressWarnings("serial")
public class PrintCurrentNetworkAction extends AbstractCyAction {

	private final CyServiceRegistrar registrar;
    
    public PrintCurrentNetworkAction(CyServiceRegistrar registrar) {
        super("Print Current Network");
        this.registrar = registrar;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        var appManager = registrar.getService(CyApplicationManager.class);
        var currNet = appManager.getCurrentNetwork();
        var currView = appManager.getCurrentNetworkView();
        
        System.out.println("Current Network: " + currNet);
        System.out.println("Current NetView: " + currView);
    }
}
