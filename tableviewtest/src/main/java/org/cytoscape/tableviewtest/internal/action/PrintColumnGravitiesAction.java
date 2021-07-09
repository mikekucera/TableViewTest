package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;

@SuppressWarnings("serial")
public class PrintColumnGravitiesAction extends AbstractCyAction {

	private final CyServiceRegistrar registrar;

	public PrintColumnGravitiesAction(CyServiceRegistrar registrar) {
		super("Print Column Gravities");
		this.registrar = registrar;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		var appManager = registrar.getService(CyApplicationManager.class);
		var tableViewManager = registrar.getService(CyTableViewManager.class);
		
		var table = appManager.getCurrentTable();
		var tableView = tableViewManager.getTableView(table);
		
		var cols = getColumnViewsSortedByGravity(tableView);
		
		for(var colView : cols) {
			Double grav = colView.getVisualProperty(BasicTableVisualLexicon.COLUMN_GRAVITY);
			System.out.printf("%d - %2.2f : %s\n", colView.getSUID(), grav, colView.getModel().getName());
		}
	}
	
	public static List<View<CyColumn>> getColumnViewsSortedByGravity(CyTableView tableView) {
		List<View<CyColumn>> cols =  new ArrayList<>(tableView.getColumnViews());
		cols.sort((v1, v2) -> {
			var grav1 = v1.getVisualProperty(BasicTableVisualLexicon.COLUMN_GRAVITY);
			var grav2 = v2.getVisualProperty(BasicTableVisualLexicon.COLUMN_GRAVITY);
			return Double.compare(grav1, grav2);
		});
		return cols;
	}

}
