package org.cytoscape.tableviewtest.internal;

import java.util.Map;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics2Factory;

public class ChartListener {

	private static final String FACTORY_ID = "org.cytoscape.LineChart";
	
	private CyCustomGraphics2Factory<?> factory;

	
	public void add(CyCustomGraphics2Factory<?> factory, Map<String,String> serviceProps) {
		if(FACTORY_ID.equals(factory.getId())) {
			this.factory = factory;
		}
	}
	
	public void remove(CyCustomGraphics2Factory<?> factory, Map<String,String> serviceProps) {
		this.factory = null;
	}
	
	public CyCustomGraphics2Factory<?> getFactory() {
		return factory;
	}
	
	
}