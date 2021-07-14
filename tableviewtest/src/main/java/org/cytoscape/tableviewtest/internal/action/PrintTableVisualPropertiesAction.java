package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;
import org.cytoscape.view.presentation.RenderingEngineManager;

@SuppressWarnings("serial")
public class PrintTableVisualPropertiesAction extends AbstractCyAction {

	private CyServiceRegistrar registrar;

	public PrintTableVisualPropertiesAction(CyServiceRegistrar registrar) {
		super("Print Table Visual Properties");
		this.registrar = registrar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		var appManager = registrar.getService(CyApplicationManager.class);
		var tableViewManager = registrar.getService(CyTableViewManager.class);

		CyNetwork net = appManager.getCurrentNetwork();
		if (net == null)
			return;

		// Get the node or edge table from the CyNetwork
		CyTable nodeTable = net.getDefaultNodeTable();

		// Get the table view.
		// Note, table views are created on-demand by the table browser, so must check
		// for null
		CyTableView tableView = tableViewManager.getTableView(nodeTable);
		if (tableView == null)
			return;

		// Get a column view (may need to iterate over all the columns, this is just an
		// example)
		View<CyColumn> colView = tableView.getColumnView("name");
		if (colView == null)
			return;

		var renderingEngineManager = registrar.getService(RenderingEngineManager.class);
		var renderingEngineSet = renderingEngineManager.getRenderingEngines(tableView);

		VisualLexicon lexicon = null;
		for (var re : renderingEngineSet) {
			if (re.getRendererId().equals(tableView.getRendererId())) {
				lexicon = re.getVisualLexicon();
				break;
			}
		}

		for (var vp : lexicon.getAllVisualProperties()) {
			System.out.println(vp.getDisplayName());
		}
	}

}