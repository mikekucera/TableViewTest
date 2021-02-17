package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableViewRenderer;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewFactory;
import org.cytoscape.view.model.table.CyTableViewManager;

@SuppressWarnings("serial")
public class CreateDummyUnassignedTableAction extends AbstractCyAction {
	
	private final CyServiceRegistrar registrar;

	public CreateDummyUnassignedTableAction(CyServiceRegistrar registrar) {
		super("Create Unassigned Dummy Table");
		this.registrar = registrar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CyTableManager tableManager = registrar.getService(CyTableManager.class);
		CyTableFactory tableFactory = registrar.getService(CyTableFactory.class);
		
		CyTable table = tableFactory.createTable("Dummy Table", "ID", Long.class, true, false);
		tableManager.addTable(table);
		
		CyApplicationManager appManager = registrar.getService(CyApplicationManager.class);
		TableViewRenderer tableViewRenderer = appManager.getTableViewRenderer(DummyTableViewRenderer.ID);
		CyTableViewFactory tableViewFactory = tableViewRenderer.getTableViewFactory();
		
		CyTableView tableView = tableViewFactory.createTableView(table);
		
		CyTableViewManager tableViewManager = registrar.getService(CyTableViewManager.class);
		tableViewManager.setTableView(tableView);
	}

}
