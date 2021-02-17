package org.cytoscape.tableviewtest.internal;

import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.application.events.SetCurrentTableListener;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.action.CreateDummyNodeTableAction;
import org.cytoscape.tableviewtest.internal.action.CreateDummyUnassignedTableAction;
import org.cytoscape.tableviewtest.internal.action.TestSetVisualPropertyAction;
import org.cytoscape.tableviewtest.internal.panel.TableTestPanel;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableViewRenderer;
import org.cytoscape.tableviewtest.internal.renderer.DummyTableVisualLexicon;
import org.cytoscape.view.model.table.CyTableViewFactory;
import org.cytoscape.view.model.table.CyTableViewFactoryProvider;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext bc) {
		registerTableViewServices(bc);
		registerActions(bc);
		registerPanel(bc);
	}

	
	private void registerPanel(BundleContext bc) {
		CyServiceRegistrar registrar = getService(bc, CyServiceRegistrar.class);
		TableTestPanel panel = new TableTestPanel(registrar);
		registerService(bc, panel, CytoPanelComponent.class);
		registerService(bc, panel, SetCurrentTableListener.class);
	}
	
	
	private void registerTableViewServices(BundleContext bc) {
		BasicTableVisualLexicon lexicon = new DummyTableVisualLexicon();
		CyTableViewFactoryProvider viewFactoryProvider = getService(bc, CyTableViewFactoryProvider.class);
		CyTableViewFactory viewFactory = viewFactoryProvider.createTableViewFactory(lexicon, DummyTableViewRenderer.ID);
		DummyTableViewRenderer renderer = new DummyTableViewRenderer(viewFactory, lexicon);
		registerService(bc, renderer, TableViewRenderer.class);
	}
	
	
	private void registerActions(BundleContext bc) {
		CyServiceRegistrar registrar = getService(bc, CyServiceRegistrar.class);
		registerMenuAction(bc, new TestSetVisualPropertyAction(registrar));
		registerMenuAction(bc, new CreateDummyNodeTableAction(registrar));
		registerMenuAction(bc, new CreateDummyUnassignedTableAction(registrar));
	}
	

	private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.DummyTableTest");
        registerAllServices(bc, action);
    }
}
