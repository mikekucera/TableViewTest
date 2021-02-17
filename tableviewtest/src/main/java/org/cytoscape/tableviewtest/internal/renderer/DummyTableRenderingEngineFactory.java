package org.cytoscape.tableviewtest.internal.renderer;

import javax.swing.JComponent;

import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.presentation.RenderingEngine;
import org.cytoscape.view.presentation.RenderingEngineFactory;

public class DummyTableRenderingEngineFactory implements RenderingEngineFactory<CyTable> {

	private final VisualLexicon visualLexicon;
	
	public DummyTableRenderingEngineFactory(VisualLexicon visualLexicon) {
		this.visualLexicon = visualLexicon;
	}
	
	@Override
	public RenderingEngine<CyTable> createRenderingEngine(Object container, View<CyTable> viewModel) {
		DummyTableRenderingEngine re = new DummyTableRenderingEngine((CyTableView)viewModel, visualLexicon);
		re.install((JComponent)container);
		return re;
	}

	@Override
	public VisualLexicon getVisualLexicon() {
		return visualLexicon;
	}

}
