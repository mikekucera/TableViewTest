package org.cytoscape.tableviewtest.internal.renderer;

import org.cytoscape.application.TableViewRenderer;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.table.CyTableViewFactory;
import org.cytoscape.view.presentation.RenderingEngineFactory;

public class DummyTableViewRenderer implements TableViewRenderer {
	
	public static final String ID = "org.cytoscape.tableviewtest.internal.renderer.DummyTableViewRenderer";
	
	private final CyTableViewFactory tableViewFactory;
	private final VisualLexicon visualLexicon;
	
	public DummyTableViewRenderer(CyTableViewFactory tableViewFactory, VisualLexicon visualLexicon) {
		this.tableViewFactory = tableViewFactory;
		this.visualLexicon = visualLexicon;
	}

	@Override
	public RenderingEngineFactory<CyTable> getRenderingEngineFactory(String contextId) {
		if(DEFAULT_CONTEXT.equals(contextId)) {
			return new DummyTableRenderingEngineFactory(visualLexicon);
		}
		return null;
	}

	@Override
	public CyTableViewFactory getTableViewFactory() {
		return tableViewFactory;
	}

	@Override
	public String getId() {
		return ID;
	}

}
