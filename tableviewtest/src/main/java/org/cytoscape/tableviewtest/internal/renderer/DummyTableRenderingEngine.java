package org.cytoscape.tableviewtest.internal.renderer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.Printable;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JComponent;

import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.presentation.RenderingEngine;

public class DummyTableRenderingEngine implements RenderingEngine<CyTable> {

	private final CyTableView viewModel;
	private final VisualLexicon lexicon;
	
	public DummyTableRenderingEngine(CyTableView viewModel, VisualLexicon lexicon) {
		this.viewModel = viewModel;
		this.lexicon = lexicon;
	}

	public void install(JComponent component) {
		DummyTableRenderComponent renderer = new DummyTableRenderComponent(this);
		component.setLayout(new BorderLayout());
		component.add(renderer, BorderLayout.CENTER);
	}

	@Override
	public View<CyTable> getViewModel() {
		return viewModel;
	}

	@Override
	public VisualLexicon getVisualLexicon() {
		return lexicon;
	}

	@Override
	public Properties getProperties() {
		return null;
	}

	@Override
	public Printable createPrintable() {
		return null;
	}

	@Override
	public Image createImage(int width, int height) {
		return null;
	}

	@Override
	public <V> Icon createIcon(VisualProperty<V> vp, V value, int width, int height) {
		return null;
	}

	@Override
	public void printCanvas(Graphics printCanvas) {
	}

	@Override
	public String getRendererId() {
		return DummyTableViewRenderer.ID;
	}

	@Override
	public void dispose() {
	}
	
}
