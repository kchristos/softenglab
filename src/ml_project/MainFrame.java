package ml_project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class MainFrame implements ActionListener {
	public static JFrame frame;
	public JMenuBar menubar;  
	public JPanel searchPanel; 
	public JPanel infoPanel;
	
	// elements for the action listener
	JTextField id_txf;
	JTextField pnum_txf;
	JTextField name_txf;
	JTextField surname_txf;
	JTextField cargo_txf;
	JTextField country_txf;
	JTextField city_txf;
	JTextField street_txf;
	JTextField day_txf;
	JTextField month_txf;
	JTextField year_txf;
	JTextField dist_txf1;
	JTextField dist_txf2;
	JCheckBox onroad_cbox;
	
	public MainFrame(String frameName) {
		frame = new JFrame(frameName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// frame layout and appearance
		frame.setLayout(new MigLayout("fill"));
		frame.setSize(1500, 900);		
		//frame.pack();
		
		// elements contained in frame
		menubar     = this.createMenu();
		searchPanel = this.createSearchPanel();
		infoPanel   = this.createInformationPanel(null);	// if query is null then the default query is executed
		
		//frame.setJMenuBar(menubar);
		//frame.add(searchPanel, "w 20%");
		//frame.add(infoPanel, "w 70%");
		frame.setVisible(true);
	}
	
	public JMenuBar createMenu() {
		// create menu bar
		JMenuBar menubar = new JMenuBar();
		
		// create menus
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Edit");
		JMenu menu3 = new JMenu("View");
		JMenu menu4 = new JMenu("Help");
		
		// create menu items
		JMenuItem item1 = new JMenuItem("New Windows");
		JMenuItem item2 = new JMenuItem("New File");
		JMenuItem item3 = new JMenuItem("Undo");
		JMenuItem item4 = new JMenuItem("Redo");
		
		// add items to menus
		menu1.add(item1);
		menu1.add(item2);
		menu2.add(item3);
		menu2.add(item4);
		
		// add menus to menu bar
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		menubar.add(menu4);
		
		frame.setJMenuBar(menubar);
		return menubar;
	}
	
	public JPanel createSearchPanel() {
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new MigLayout("fill"));
		sPanel.setBorder(BorderFactory.createTitledBorder("Search Options"));
		
		// create the elements of the search panel
		JLabel id_label = new JLabel("ID");
		id_txf = new JTextField();
		sPanel.add(id_label);
		sPanel.add(id_txf, "span, grow");
		
		JLabel pnum_label = new JLabel("Plate number");
		pnum_txf = new JTextField();
		sPanel.add(pnum_label);
		sPanel.add(pnum_txf, "span, grow");
		
		JLabel name_label = new JLabel("Driver's name");
		name_txf = new JTextField();
		sPanel.add(name_label);
		sPanel.add(name_txf, "span, grow");
		
		JLabel surname_label = new JLabel("Driver's surname");
		surname_txf = new JTextField();
		sPanel.add(surname_label);
		sPanel.add(surname_txf, "span, grow");
		
		JLabel cargo_label   = new JLabel("Cargo");
		cargo_txf = new JTextField();
		sPanel.add(cargo_label);
		sPanel.add(cargo_txf, "span, grow, wrap");
		
		JPanel destPanel = new JPanel();
		destPanel.setLayout(new MigLayout("fill"));
		destPanel.setBorder(BorderFactory.createTitledBorder("Destination"));
		JLabel country_label = new JLabel("Country");
		country_txf = new JTextField();
		JLabel city_label = new JLabel("City");
		city_txf = new JTextField();
		JLabel street_label = new JLabel("Street name");
		street_txf = new JTextField();
		destPanel.add(country_label);
		destPanel.add(country_txf, "span, grow");
		destPanel.add(city_label);
		destPanel.add(city_txf, "span, grow");
		destPanel.add(street_label);
		destPanel.add(street_txf, "span, grow");
		sPanel.add(destPanel, "span, grow, wrap");
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new MigLayout("fill"));
		datePanel.setBorder(BorderFactory.createTitledBorder("Date of arrival"));
		JLabel day_label = new JLabel("Day");
		day_txf = new JTextField();
		JLabel month_label = new JLabel("Month");
		month_txf = new JTextField();
		JLabel year_label = new JLabel("Year");
		year_txf = new JTextField();
		datePanel.add(day_label);
		datePanel.add(day_txf, "span, grow");
		datePanel.add(month_label);
		datePanel.add(month_txf, "span, grow");
		datePanel.add(year_label);
		datePanel.add(year_txf, "span, grow");
		sPanel.add(datePanel, "span, grow, wrap");
		
		JPanel distPanel = new JPanel();
		distPanel.setLayout(new MigLayout("fill"));
		distPanel.setBorder(BorderFactory.createTitledBorder("Total driving distance"));
		dist_txf1 = new JTextField();
		JLabel to = new JLabel(" to ");
		dist_txf2 = new JTextField(10);
		distPanel.add(dist_txf1, "w 40%");
		distPanel.add(to);
		distPanel.add(dist_txf2, "w 40%");
		sPanel.add(distPanel, "span, grow, wrap");
		
		onroad_cbox = new JCheckBox("On road");
		onroad_cbox.setSelected(true);
		sPanel.add(onroad_cbox, "wrap");
				
		// created this panel because I want the search btn outside the search options border 
		// so the p JPanel contains the sPanel and the sbtn
		// because the p JPanel is the bigger container it is added to the frame
		JPanel p = new JPanel();
		p.setLayout(new MigLayout("fill"));
		p.add(sPanel, "span, grow");
		JButton sbtn = new JButton("Search");
		sbtn.addActionListener(this);
		p.add(sbtn, "wrap");

		frame.add(p, "w 20%");
		return p;
	}
	
	public JPanel createInformationPanel(String query){
		// the way in works is:
		// 1. create the panel
		// 2. create scroll pane around table (so that the table header are displayed)
		// 3. add scroll pane to panel
		JPanel infopanel = new JPanel();
		infopanel.setLayout(new MigLayout("fill"));
		
		// result from database table
		if (query == null)
			query = "SELECT * FROM test_table1 WHERE onroad = 1;";
		ResultSet rs = Database.executeQuery(query);
		Vector<String> columnNames = Database.tableLabels(rs);
		Vector<Vector<String>> content = Database.tableContent(rs);
		
		// elements contained in the panel
		JTable table = new JTable(new DefaultTableModel(content, columnNames));
		JScrollPane scrollPane = new JScrollPane(table);
		//DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		infopanel.add(scrollPane, "grow");
		frame.add(infopanel, "w 70%");
		return infopanel;
	}
	
	public void replaceInformationPanel(String query) {
		frame.invalidate();
		frame.getContentPane().remove(infoPanel);
		infoPanel = createInformationPanel(query);
		//mframe.frame.getContentPane().add(mframe.infoPanel);
		frame.validate();
		frame.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		String query = "SELECT * FROM test_table1 ";
		ArrayList<String> queryConditions = new ArrayList();
		String id, pnum, name, surname, cargo;
		String country, city, street;
		String day, month, year;
		String dist1, dist2;
		boolean onroad;
		
		// create the SELECT query based on user input from the search panel
		id      = id_txf.getText().trim();
		pnum    = pnum_txf.getText().trim();
		name    = name_txf.getText().trim();
		surname = surname_txf.getText().trim();
		cargo   = cargo_txf.getText().trim();
		country = country_txf.getText().trim();
		city    = city_txf.getText().trim();
		street  = street_txf.getText().trim();
		day     = day_txf.getText().trim();
		month   = month_txf.getText().trim();
		year    = year_txf.getText().trim();
		dist1   = dist_txf1.getText().trim();
		dist2   = dist_txf2.getText().trim();
		onroad  = onroad_cbox.isSelected();
		
		try {
			if (!id.equals("") && Integer.parseInt(id) > 0)
				queryConditions.add("id = " + id);
			if(!pnum.equals(""))
				queryConditions.add("plate_number = " + pnum);
			if(!name.equals(""))
				queryConditions.add("name = " + name);
			if(!surname.equals(""))
				queryConditions.add("surname = " + surname);
			if(!cargo.equals(""))
				queryConditions.add("cargo = " + cargo);
			if(!country.equals(""))
				queryConditions.add("country = " + country);
			if(!city.equals(""))
				queryConditions.add("town = " + city);	// make a new test table where the town column is named city instead, also make it work when the city name is more than 1 word
			if(!street.equals(""))
				queryConditions.add("street = " + street);
			if(!day.equals("") && Integer.parseInt(day) > 0 && Integer.parseInt(day) <= 31)
				queryConditions.add("DAY(date_of_arrival) = " + day);
			if(!month.equals("") && Integer.parseInt(month) > 0 && Integer.parseInt(month) <= 12)
				queryConditions.add("MONTH(date_of_arrival) = " + month);
			if(!year.equals("") && Integer.parseInt(year) > 0)
				queryConditions.add("YEAR(date_of_arrival) = " + year);
			if(!dist1.equals("") && !dist2.equals("") && Integer.parseInt(dist1) < Integer.parseInt(dist2)) {
				queryConditions.add("dist1 > " + dist1);
				queryConditions.add("dist2 < " + dist2);
			}
		}
		catch (Exception buildingQueryE) {
			System.out.println("got em");
		}
		
		if (onroad)
			query += "WHERE onroad = 1";
		else
			query += "WHERE onroad = 0";
		
		System.out.format("id = %s, plate_number = %s, name = %s, surname = %s\n", id, pnum, name, surname);
		System.out.format("destination = [%s %s %s], date = [%s %s %s]\n", country, city, street, day, month, year);
		System.out.format("cargo = %s, dist = [%s %s], onroad = %s\n", cargo, dist1, dist2, onroad);
		replaceInformationPanel(buildQuery(query, queryConditions));
	}
	
	public String buildQuery(String query, ArrayList<String> queryConditions) {
		if (queryConditions.isEmpty()) {
			query += ";";
			return query;
		} else {
			query += " AND " + queryConditions.remove(0);
			return buildQuery(query, queryConditions);
		}
	}
	
	
}
