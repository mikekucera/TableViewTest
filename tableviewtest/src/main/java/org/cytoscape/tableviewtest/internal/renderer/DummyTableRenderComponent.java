package org.cytoscape.tableviewtest.internal.renderer;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DummyTableRenderComponent extends JPanel {

	private final DummyTableRenderingEngine re;
	
	public DummyTableRenderComponent(DummyTableRenderingEngine re) {
		this.re = re;
		createContents();
	}
	
	private void createContents() {
		JLabel label = new JLabel(re.getViewModel().getModel().toString());
		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
	}
}
