package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;
import java.util.Collection;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;
import org.cytoscape.view.vizmap.TableVisualMappingManager;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualStyle;

@SuppressWarnings("serial")
public class PrintTableStyleAction extends AbstractCyAction {

	private CyServiceRegistrar registrar;
    
    public PrintTableStyleAction(CyServiceRegistrar registrar) {
        super("Print Table Style");
        this.registrar = registrar;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	var appManager = registrar.getService(CyApplicationManager.class);
        var tableViewManager = registrar.getService(CyTableViewManager.class);
        var tableVisualMappingManager = registrar.getService(TableVisualMappingManager.class);
        
        
        CyNetwork net = appManager.getCurrentNetwork();
        if(net == null)
        	return;
        
        // Get the node or edge table from the CyNetwork
        CyTable nodeTable = net.getDefaultNodeTable();
        
        // Get the table view.
        // Note, table views are created on-demand by the table browser, so must check for null
        CyTableView tableView = tableViewManager.getTableView(nodeTable);
        if(tableView == null)
        	return;
        
        // Get a column view (may need to iterate over all the columns, this is just an example)
        View<CyColumn> colView = tableView.getColumnView("name");
        if(colView == null)
        	return;
        
        // Check if a visual style has been created for the column. This happens automatically when the user
        // sets a visual property on a column. Must check for null.
        VisualStyle style = tableVisualMappingManager.getVisualStyle(colView);
        if(style == null)
        	return;
        
        Collection<VisualMappingFunction<?,?>> mappingFunctions = style.getAllVisualMappingFunctions();
        
        System.out.println("Column 'name' this many mappings " + mappingFunctions.size());
    }
    

}