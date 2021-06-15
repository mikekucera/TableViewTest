package org.cytoscape.tableviewtest.internal.task;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class CountRowsTableTask extends AbstractTask {

	private final CyTable table;
	private final CySwingApplication swingApplication;
	
	public CountRowsTableTask(CySwingApplication swingApplication, CyTable table) {
		this.swingApplication = swingApplication;
		this.table = table;
	}
	
	
	@Override
	public void run(TaskMonitor tm) {
		int rowCount = table.getRowCount();
		SwingUtilities.invokeLater(() -> {
			String message = "Table '" + table.getTitle() + "' has " + rowCount + " rows.";
			JOptionPane.showConfirmDialog(swingApplication.getJFrame(), message, "Count Rows", JOptionPane.OK_CANCEL_OPTION);
		});
	}

}
