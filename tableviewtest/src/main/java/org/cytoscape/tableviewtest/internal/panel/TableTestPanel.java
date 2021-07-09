package org.cytoscape.tableviewtest.internal.panel;

import static org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon.COLUMN_GRAVITY;

import java.awt.Component;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.events.SetCurrentTableEvent;
import org.cytoscape.application.events.SetCurrentTableListener;
import org.cytoscape.application.swing.CyColumnComboBox;
import org.cytoscape.application.swing.CyColumnPresentationManager;
import org.cytoscape.application.swing.CytoPanelComponent2;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.tableviewtest.internal.action.CreateDummyNodeTableAction;
import org.cytoscape.tableviewtest.internal.action.CreateDummyUnassignedTableAction;
import org.cytoscape.tableviewtest.internal.action.CreateSparklineGalFilteredAction;
import org.cytoscape.tableviewtest.internal.action.PrintAllTablesAction;
import org.cytoscape.tableviewtest.internal.action.PrintColumnGravitiesAction;
import org.cytoscape.tableviewtest.internal.action.PrintTableViewsAction;
import org.cytoscape.util.swing.LookAndFeelUtil;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.table.CyTableView;
import org.cytoscape.view.model.table.CyTableViewManager;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;
import org.cytoscape.view.presentation.property.table.CellFormat;

@SuppressWarnings("serial")
public class TableTestPanel extends JPanel implements CytoPanelComponent2, SetCurrentTableListener {

	private enum Direction {
		LEFT, RIGHT
	}
	
	private final CyServiceRegistrar registrar;
	
	private CyColumnComboBox columnCombo;
	
	public TableTestPanel(CyServiceRegistrar registrar) {
		this.registrar = registrar;
		createContents();
	}
	
	
	private void createContents() {
		JButton createNodeTableButton = new JButton(new CreateDummyNodeTableAction(registrar));
		JButton createUnasTableButton = new JButton(new CreateDummyUnassignedTableAction(registrar));
		JButton chartButton = new JButton(new CreateSparklineGalFilteredAction(registrar));
		
		JButton printGravityButton = new JButton(new PrintColumnGravitiesAction(registrar));
		JButton printTablesButton = new JButton(new PrintAllTablesAction(registrar));
		JButton printTableViewsButton = new JButton(new PrintTableViewsAction(registrar));
		
		
		var columnPresentationManager = registrar.getService(CyColumnPresentationManager.class);
		columnCombo = new CyColumnComboBox(columnPresentationManager, Collections.emptyList());
		
		JButton visibilityButton = new JButton("Toggle Visibility");
		visibilityButton.addActionListener(e -> toggleVisibility(columnCombo.getSelectedItem()));
		
		JButton moveLeftButton = new JButton("Move Left");
		moveLeftButton.addActionListener(e -> move(columnCombo.getSelectedItem(), Direction.LEFT));
		
		JButton moveRightButton = new JButton("Move Right");
		moveRightButton.addActionListener(e -> move(columnCombo.getSelectedItem(), Direction.RIGHT));
		
		JButton setFormatButton = new JButton("Set format like 12.34");
		setFormatButton.addActionListener(e -> setFormat(columnCombo.getSelectedItem()));
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateContainerGaps(LookAndFeelUtil.isWinLAF());
		layout.setAutoCreateGaps(!LookAndFeelUtil.isAquaLAF());
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(createNodeTableButton)
			.addComponent(createUnasTableButton)
			.addComponent(chartButton)
			
			.addComponent(printGravityButton)
			.addComponent(printTablesButton)
			.addComponent(printTableViewsButton)
			
			.addComponent(columnCombo)
			.addComponent(visibilityButton)
			.addComponent(moveLeftButton)
			.addComponent(moveRightButton)
			.addComponent(setFormatButton)
		);
		
   		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
   			.addComponent(createNodeTableButton)
   			.addComponent(createUnasTableButton)
   			.addComponent(chartButton)
   			
   			.addComponent(printGravityButton)
			.addComponent(printTablesButton)
			.addComponent(printTableViewsButton)
   			
			.addComponent(columnCombo)
			.addComponent(visibilityButton)
			.addComponent(moveLeftButton)
			.addComponent(moveRightButton)
			.addComponent(setFormatButton)
   		);
	}
	
	
	private void setFormat(CyColumn column) {
		var colView = getView(column);
		colView.setLockedValue(BasicTableVisualLexicon.COLUMN_FORMAT, new CellFormat("%.2f"));
	}


	@Override
	public void handleEvent(SetCurrentTableEvent e) {
		CyTable table = e.getTable();
		DefaultComboBoxModel<CyColumn> model = new DefaultComboBoxModel<>();
		if(table != null) {
			model.addAll(table.getColumns());
		}
		columnCombo.setModel(model);
	}
	
	
	
	private void toggleVisibility(CyColumn column) {
		var colView = getView(column);
		boolean visible = !Boolean.FALSE.equals(colView.getVisualProperty(BasicTableVisualLexicon.COLUMN_VISIBLE));
		colView.setLockedValue(BasicTableVisualLexicon.COLUMN_VISIBLE, !visible);
	}
	
	private void move(CyColumn column, Direction direction) {
		var appManager = registrar.getService(CyApplicationManager.class);
		var tableViewManager = registrar.getService(CyTableViewManager.class);
		
		var table = appManager.getCurrentTable();
		var tableView = tableViewManager.getTableView(table);
		
		var cols = PrintColumnGravitiesAction.getColumnViewsSortedByGravity(tableView);
		
		var colView1 = tableView.getColumnView(column);
		var index = cols.indexOf(colView1);
		
		View<CyColumn> colView2;
		
		if(direction == Direction.LEFT && index > 0) {
			colView2 = cols.get(index - 1);
		} else if(direction == Direction.RIGHT && index < cols.size()-1) {
			colView2 = cols.get(index + 1);
		} else {
			return;
		}
			
		double grav1 = colView1.getVisualProperty(COLUMN_GRAVITY);
		double grav2 = colView2.getVisualProperty(COLUMN_GRAVITY);
		
		// swap the gravity values
		colView1.setLockedValue(COLUMN_GRAVITY, grav2);
		colView2.setLockedValue(COLUMN_GRAVITY, grav1);
	}
	
	
	private CyTableView getTableView(CyTable table) {
		var tableViewManager = registrar.getService(CyTableViewManager.class);
		return tableViewManager.getTableView(table);
	}
	
	private View<CyColumn> getView(CyColumn column) {
		CyTableView tableView = getTableView(column.getTable());
		return tableView.getColumnView(column);
	}
	
	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}

	@Override
	public String getTitle() {
		return "Table Test";
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	@Override
	public String getIdentifier() {
		return "TableTestPanel";
	}

}
