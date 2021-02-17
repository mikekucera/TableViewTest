package org.cytoscape.tableviewtest.internal.renderer;

import org.cytoscape.view.model.NullDataType;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.NullVisualProperty;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;

public class DummyTableVisualLexicon extends BasicTableVisualLexicon {

	public static final VisualProperty<NullDataType> TABLE_ROOT = new NullVisualProperty("DUMMY_ROOT", "BLAH");
	
	public DummyTableVisualLexicon() {
		super(TABLE_ROOT);
	}

}
