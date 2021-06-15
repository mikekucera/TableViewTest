package org.cytoscape.tableviewtest.internal;

import static org.cytoscape.work.ServiceProperties.*;

import java.util.Properties;

import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.application.events.SetCurrentTableListener;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.action.CreateDummyNodeTableAction;
import org.cytoscape.tableviewtest.internal.action.CreateDummyUnassignedTableAction;
import org.cytoscape.tableviewtest.internal.action.TestSetVisualPropertyAction;
import org.cytoscape.tableviewtest.internal.panel.TableTestPanel;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableViewRenderer;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableVisualLexicon;
import org.cytoscape.tableviewtest.internal.task.CountRowsTableTaskFactory;
import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.util.swing.IconManager;
import org.cytoscape.util.swing.TextIcon;
import org.cytoscape.view.model.table.CyTableViewFactory;
import org.cytoscape.view.model.table.CyTableViewFactoryProvider;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics2Factory;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;
import org.cytoscape.work.ServiceProperties;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext bc) {
		CyServiceRegistrar registrar = getService(bc, CyServiceRegistrar.class);
		CySwingApplication swingApplication = getService(bc, CySwingApplication.class);
		IconManager iconManager = getService(bc, IconManager.class);
		
		ChartListener chartListener = new ChartListener();
		registerServiceListener(bc, chartListener::add, chartListener::remove, CyCustomGraphics2Factory.class);
		registerService(bc, chartListener, ChartListener.class);
		
		BasicTableVisualLexicon lexicon = new DummyTableVisualLexicon();
		CyTableViewFactoryProvider viewFactoryProvider = getService(bc, CyTableViewFactoryProvider.class);
		CyTableViewFactory viewFactory = viewFactoryProvider.createTableViewFactory(lexicon, DummyTableViewRenderer.ID);
		DummyTableViewRenderer renderer = new DummyTableViewRenderer(viewFactory, lexicon);
		registerService(bc, renderer, TableViewRenderer.class);
		
		registerMenuAction(bc, new TestSetVisualPropertyAction(registrar));
		registerMenuAction(bc, new CreateDummyNodeTableAction(registrar));
		registerMenuAction(bc, new CreateDummyUnassignedTableAction(registrar));
		
		TableTestPanel panel = new TableTestPanel(registrar);
		registerService(bc, panel, CytoPanelComponent.class);
		registerService(bc, panel, SetCurrentTableListener.class);
		
		CountRowsTableTaskFactory tableTaskFactory = new CountRowsTableTaskFactory(swingApplication);
		
		var iconFont = iconManager.getIconFont(22.0f);
		var icon = new TextIcon(IconManager.ICON_CALCULATOR, iconFont, 32, 32);
		var iconId = "tableviewtest::countrows";
		iconManager.addIcon(iconId, icon);
		
		Properties props = new Properties();
		props.setProperty(ServiceProperties.ENABLE_FOR, "table");
		props.setProperty(TOOLTIP, "Count Table Rows...");
		props.setProperty(LARGE_ICON_ID, iconId);
		props.setProperty(TOOL_BAR_GRAVITY, "" + 99);
		props.setProperty(IN_NODE_TABLE_TOOL_BAR, "true");
		props.setProperty(IN_EDGE_TABLE_TOOL_BAR, "true");
		props.setProperty(IN_NETWORK_TABLE_TOOL_BAR, "true");
		props.setProperty(IN_UNASSIGNED_TABLE_TOOL_BAR, "true");
		registerService(bc, tableTaskFactory, TableTaskFactory.class, props);
	}
	

	private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.DummyTableTest");
        registerAllServices(bc, action);
    }
}
