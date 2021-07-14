package org.cytoscape.tableviewtest.internal.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.Action;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.VirtualColumnInfo;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;
import org.cytoscape.service.util.CyServiceRegistrar;

@SuppressWarnings("serial")
public class PrintAllTablesAction extends AbstractCyAction {

	private CyApplicationManager applicationManager;
	private CyNetworkTableManager networkTableManager;

	private boolean printStructure = false;

	public PrintAllTablesAction(CyServiceRegistrar registrar) {
		super("Print All Tables");
		this.applicationManager = registrar.getService(CyApplicationManager.class);
		this.networkTableManager = registrar.getService(CyNetworkTableManager.class);
	}

	public PrintAllTablesAction setPrintStructure(boolean printStructure) {
		this.printStructure = printStructure;
		putValue(Action.NAME, getName() + " (Structure)");
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CyNetwork network = applicationManager.getCurrentNetwork();

		CySubNetwork subNetwork = (CySubNetwork) network;
		CyRootNetwork rootNetwork = subNetwork.getRootNetwork();

		System.out.println("************************************");
		System.out.println("SubNetwork: " + subNetwork);
		printTables(subNetwork, CyNode.class);
//        printTables(subNetwork, CyEdge.class);

		System.out.println("Root Network: " + rootNetwork);
		printTables(rootNetwork, CyNode.class);
//        printTables(rootNetwork, CyEdge.class);

		System.out.println("************************************");
	}

	private void printTables(CyNetwork network, Class<? extends CyIdentifiable> type) {
		Map<String, CyTable> tables = networkTableManager.getTables(network, type);
		List<String> tableNames = new ArrayList<>(tables.keySet());
		tableNames.sort(Comparator.naturalOrder());

		for (String name : tableNames) {
			CyTable table = tables.get(name);
			String namespace = networkTableManager.getTableNamespace(table);
			System.out.println("  Table(" + table.getSUID() + "): " + table + ":" + namespace + ":"
					+ table.getClass().getSimpleName() + ", rows: " + table.getRowCount());
			Collection<CyColumn> cols = table.getColumns();

			if (printStructure) {
				for (CyColumn col : cols) {
					VirtualColumnInfo info = col.getVirtualColumnInfo();
					if (info.isVirtual()) {
						System.out.println("  V:" + col.getName() + ":" + col.getType().getSimpleName() + ", source:"
								+ info.getSourceTable().getSUID());
					} else {
						System.out.println("  L:" + col.getName() + ":" + col.getType().getSimpleName());
					}
				}
			} else {
				System.out.append("  ").append(cols.stream().map(CyColumn::getName).collect(Collectors.joining(", ")))
						.append('\n');
				table.getAllRows().stream().limit(10).forEach(row -> printRow(row, table));
			}
			System.out.println();
		}
	}

	private void printRow(CyRow row, CyTable table) {
		Collection<CyColumn> cols = table.getColumns();

		String rs = cols.stream().map(col -> row.get(col.getName(), col.getType())).map(String::valueOf)
				.collect(Collectors.joining(", "));
		System.out.append("  ").append(rs).append('\n');
	}
}
