package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableViewRenderer;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewFactory;
import org.cytoscape.view.model.table.CyTableViewManager;

@SuppressWarnings("serial")
public class CreateDummyNodeTableAction extends AbstractCyAction {
	
	private final CyServiceRegistrar registrar;

	public CreateDummyNodeTableAction(CyServiceRegistrar registrar) {
		super("Create Node Dummy Table");
		this.registrar = registrar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CyApplicationManager appManager = registrar.getService(CyApplicationManager.class);
		CyNetwork network = appManager.getCurrentNetwork();
		if(network == null)
			return;
		
		CyTableManager tableManager = registrar.getService(CyTableManager.class);
		CyNetworkTableManager networkTableManager = registrar.getService(CyNetworkTableManager.class);
		CyTableFactory tableFactory = registrar.getService(CyTableFactory.class);
		
		CyTable table = tableFactory.createTable("Dummy Node Table", "SUID", Long.class, true, false);
		
		networkTableManager.setTable(network, CyNode.class, "MyNamespace", table);
		tableManager.addTable(table);
		
		// Create a table view
		TableViewRenderer tableViewRenderer = appManager.getTableViewRenderer(DummyTableViewRenderer.ID);
		CyTableViewFactory tableViewFactory = tableViewRenderer.getTableViewFactory();
		CyTableView tableView = tableViewFactory.createTableView(table);
		
		CyTableViewManager tableViewManager = registrar.getService(CyTableViewManager.class);
		tableViewManager.setTableView(tableView);
	}

}
