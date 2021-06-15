package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.ChartListener;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics2;
import org.cytoscape.view.presentation.property.values.CyColumnIdentifier;
import org.cytoscape.view.presentation.property.values.CyColumnIdentifierFactory;
import org.cytoscape.view.vizmap.TableVisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;

@SuppressWarnings("serial")
public class CreateSparklineGalFilteredAction extends AbstractCyAction {

	private final CyServiceRegistrar registrar;
	private final ChartListener chartManager;

	public CreateSparklineGalFilteredAction(CyServiceRegistrar registrar) {
		super("Create Sparkline for GalFiltered");
		this.registrar = registrar;
		this.chartManager = registrar.getService(ChartListener.class);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CyApplicationManager appManager = registrar.getService(CyApplicationManager.class);
		CyColumnIdentifierFactory columnIdFactory = registrar.getService(CyColumnIdentifierFactory.class);
		TableVisualMappingManager visualMappingManager = registrar.getService(TableVisualMappingManager.class);
		CyTableViewManager tableViewManager = registrar.getService(CyTableViewManager.class);
		VisualStyleFactory styleFactory = registrar.getService(VisualStyleFactory.class);
		
		// get current network, it needs to be galfiltered
		CyNetworkView networkView = appManager.getCurrentNetworkView();
		CyTable nodeTable = networkView.getModel().getDefaultNodeTable();
		CyTableView tableView = tableViewManager.getTableView(nodeTable);
		
		// create target column
		if(nodeTable.getColumn("sparkline") == null) {
			nodeTable.createColumn("sparkline", String.class, false);
		}
		View<CyColumn> columnView = tableView.getColumnView("sparkline");
		
		// get list of data columns
		List<String> dataCols = Arrays.asList("gal1RGexp", "gal4RGexp", "gal80Rexp", "gal1RGsig", "gal4RGsig", "gal80Rsig");
		List<CyColumnIdentifier> dataColIds = dataCols.stream().map(columnIdFactory::createColumnIdentifier).collect(Collectors.toList());
		
		// set chart properties
		Map<String,Object> chartProps = new HashMap<String,Object>();
		chartProps.put("cy_dataColumns", dataColIds); 
		chartProps.put("cy_colorScheme", "RAINBOW");
		
		// create the chart instance
		var chartFactory = chartManager.getFactory();
		CyCustomGraphics2<?> customGraphics = chartFactory.getInstance(chartProps);
		
		// get the visual property
		VisualLexicon lexicon = getVisualLexicon(tableView);
		var visualProperty = (VisualProperty<CyCustomGraphics>) lexicon.lookup(CyColumn.class, "CELL_CUSTOMGRAPHICS");
		
		// Set the custom graphics on the visual style
		VisualStyle visualStyle = visualMappingManager.getVisualStyle(columnView);
		if(visualStyle == null) {
			visualStyle = styleFactory.createVisualStyle(UUID.randomUUID().toString());
			visualMappingManager.setVisualStyle(columnView, visualStyle);
		}
		visualStyle.setDefaultValue(visualProperty, customGraphics);
	}
	
	
	private VisualLexicon getVisualLexicon(CyTableView view) {
    	final CyApplicationManager applicationManager = registrar.getService(CyApplicationManager.class);
    	
    	TableViewRenderer renderer = applicationManager.getTableViewRenderer(view.getRendererId());
    	if(renderer == null)
    		return null;
    	
    	var factory = renderer.getRenderingEngineFactory(TableViewRenderer.DEFAULT_CONTEXT);
    	if(factory == null)
    		return null;
    	
		return factory.getVisualLexicon();
	}

}
