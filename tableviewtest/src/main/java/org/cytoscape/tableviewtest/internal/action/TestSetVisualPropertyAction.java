package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

@SuppressWarnings("serial")
public class TestSetVisualPropertyAction extends AbstractCyAction {

	private final CyServiceRegistrar registrar;
	
	
	public TestSetVisualPropertyAction(CyServiceRegistrar registrar) {
		super("Test Setting Visual Property Programmatically");
		this.registrar = registrar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var appManager = registrar.getService(CyApplicationManager.class);
		CyNetworkView networkView = appManager.getCurrentNetworkView();
		if(networkView == null)
			return;
		CyNetwork network = networkView.getModel();

		List<CyNode> selectedNodes = CyTableUtil.getNodesInState(network, CyNetwork.SELECTED, true);
		
		for(CyNode node : selectedNodes) {
			View<CyNode> nodeView = networkView.getNodeView(node);
			nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL, "HELLO");
		}
	}

}
