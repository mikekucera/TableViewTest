package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;

@SuppressWarnings("serial")
public class PrintTableViewsAction extends AbstractCyAction {

	private CyServiceRegistrar registrar;
    
    public PrintTableViewsAction(CyServiceRegistrar registrar) {
        super("Print Table Views");
        this.registrar = registrar;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        var tableManager = registrar.getService(CyTableManager.class);
        var tableViewManager = registrar.getService(CyTableViewManager.class);
        
        System.out.println("Tables...");
        for(CyTable table : tableManager.getAllTables(true)) {
            var tableView = tableViewManager.getTableView(table);
            System.out.println("Table: " + table.getSUID() + ", " + table + ", view: " + (tableView == null ? null : tableView.getSUID()));
        }
        System.out.println();
        
        System.out.println("Views...");
        for(CyTableView view : tableViewManager.getTableViewSet()) {
            System.out.println("View: " + view.getSUID() + " for table " + view.getModel().getSUID());
        }
        System.out.println();
    }
    

}
