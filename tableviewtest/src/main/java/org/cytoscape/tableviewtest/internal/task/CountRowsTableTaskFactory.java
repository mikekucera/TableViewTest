package org.cytoscape.tableviewtest.internal.task;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;

public class CountRowsTableTaskFactory implements TableTaskFactory {

	private final CySwingApplication swingApplication;
	
	public CountRowsTableTaskFactory(CySwingApplication swingApplication) {
		this.swingApplication = swingApplication;
	}
	
	@Override
	public TaskIterator createTaskIterator(CyTable table) {
		return new TaskIterator(new CountRowsTableTask(swingApplication, table));
	}

	@Override
	public boolean isReady(CyTable table) {
		return true;
	}

}
