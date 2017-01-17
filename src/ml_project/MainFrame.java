package ml_project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class MainFrame {

	private JFrame frmTruckTackingApp;
	private JTable table;
	private JTextField txfSearch;
	private JTextField txfAddPlate;
	private JTextField txfAddCargo;
	private JTextField txfAddName;
	private JTextField txfAddSurname;
	private JTextField txfAddLocation;
	private JTextField txfUpdateID;
	private JTextField txfUpdatePlate;
	private JTextField txfUpdateCargo;
	private JTextField txfUpdateName;
	private JTextField txfUpdateSurname;
	private JTextField txfUpdateLocation;
	private JTextField txfDeleteID;
	private JTextField txfDeletePlate;
	private JTextField txfDeleteCargo;
	private JTextField txfDeleteName;
	private JTextField txfDeleteSurname;
	private JTextField txfDeleteLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmTruckTackingApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTruckTackingApp = new JFrame();
		frmTruckTackingApp.setTitle("Truck tacking app");
		frmTruckTackingApp.setBounds(100, 100, 1280, 720);
		frmTruckTackingApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTruckTackingApp.getContentPane().setLayout(new MigLayout("", "[][30px][grow]", "[][][grow]"));
		
		/* load btn on top of the table, was used for debugging 
		JButton btnLoad = new JButton("Load table");
		frmTruckTackingApp.getContentPane().add(btnLoad, "cell 2 0,alignx center");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Database.fillJTable(table, null, null);		// the function will be used without WHERE keyword
			}
		});
		*/
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTruckTackingApp.getContentPane().add(tabbedPane, "cell 0 2,width 30%,height 70%,aligny top");
		
		JPanel panelSearch = new JPanel();
		tabbedPane.addTab("Search", null, panelSearch, null);
		panelSearch.setLayout(new MigLayout("", "[grow][10px][grow]", "[120px:n][][][][120px]"));
		
		JTextPane txtpnChooseFromThe = new JTextPane();
		txtpnChooseFromThe.setEditable(false);
		txtpnChooseFromThe.setBackground(SystemColor.info);
		txtpnChooseFromThe.setText("How to use :\r\n\r\nChoose from the combo box the column of the table you want to search into, then type in the text field next to it what you want to search for.\r\nAfter you press the Search button the matches will be shown on the table.");
		panelSearch.add(txtpnChooseFromThe, "cell 0 0 3 1,height 10%,grow");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		panelSearch.add(comboBox, "cell 0 2,width 40%");
		
		txfSearch = new JTextField();
		panelSearch.add(txfSearch, "cell 2 2,growx");
		txfSearch.setColumns(10);
		
		JPanel panelAdd = new JPanel();
		tabbedPane.addTab("Add", null, panelAdd, null);
		panelAdd.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][][grow]"));
		
		JTextPane txtpnHowToUse = new JTextPane();
		txtpnHowToUse.setEditable(false);
		txtpnHowToUse.setText("How to use :\r\n\r\nFill the text files with the values you want your entry to have. Make sure you don't leave any fields blank.\r\nPress the Save button to save.");
		txtpnHowToUse.setBackground(SystemColor.info);
		panelAdd.add(txtpnHowToUse, "cell 0 0 3 1,height 10%,grow");
		
		JLabel lblNewLabel = new JLabel("Plate number");
		panelAdd.add(lblNewLabel, "cell 0 2,width 40%");
		
		txfAddPlate = new JTextField();
		panelAdd.add(txfAddPlate, "cell 2 2,growx");
		txfAddPlate.setColumns(10);
		
		JLabel lblNewLabel1 = new JLabel("Cargo");
		panelAdd.add(lblNewLabel1, "cell 0 4,width 40%");
		
		txfAddCargo = new JTextField();
		panelAdd.add(txfAddCargo, "cell 2 4,growx");
		txfAddCargo.setColumns(10);
		
		JLabel lblNewLabel2 = new JLabel("Driver's name");
		panelAdd.add(lblNewLabel2, "cell 0 6,width 40%");
		
		txfAddName = new JTextField();
		panelAdd.add(txfAddName, "cell 2 6,growx");
		txfAddName.setColumns(10);
		
		JLabel lblNewLabel3 = new JLabel("Driver's surname");
		panelAdd.add(lblNewLabel3, "cell 0 8,width 40%");
		
		txfAddSurname = new JTextField();
		panelAdd.add(txfAddSurname, "cell 2 8,growx");
		txfAddSurname.setColumns(10);
		
		JLabel lblNewLabel4 = new JLabel("Location");
		panelAdd.add(lblNewLabel4, "cell 0 10,width 40%");
		
		txfAddLocation = new JTextField();
		panelAdd.add(txfAddLocation, "cell 2 10,growx");
		txfAddLocation.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			// HAVE NOT ADDED INPUT VALIDATION
				String plate    = txfAddPlate.getText().trim();
				String cargo    = txfAddCargo.getText().trim();
				String name     = txfAddName.getText().trim();
				String surname  = txfAddSurname.getText().trim();
				String location = txfAddLocation.getText().trim();
			
				plate    = (plate == null) ? "" : plate;
				cargo    = (cargo == null) ? "" : cargo;
				name     = (name == null) ? "" : name;
				surname  = (surname == null) ? "" : surname;
				location = (location == null) ? "" : location;
				
				if (plate.isEmpty() || cargo.isEmpty() || name.isEmpty() ||
						surname.isEmpty() || location.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please make sure you fill all the fields.");
				} else {
					Database.addEntry(plate, cargo, name, surname, location);
					Database.fillJTable(table, null, null);			// show updated table
				}
			}
		});
		panelAdd.add(btnSave, "cell 2 12,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		frmTruckTackingApp.getContentPane().add(scrollPane, "cell 2 2,growx,height 90%,aligny top");
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		Database.fillJComboBox(comboBox);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String label = (String) comboBox.getSelectedItem();
				String word = txfSearch.getText().trim();
				if (word != null && !word.isEmpty()) {
					Database.fillJTable(table, label, word);
				} else {
					JOptionPane.showMessageDialog(null, "Please add the keyword you want to search for.");
				}
			}
		});
		panelSearch.add(btnSearch, "cell 2 4,alignx center,aligny center");
		
		JPanel panelUpdate = new JPanel();
		tabbedPane.addTab("Update", null, panelUpdate, null);
		panelUpdate.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][][][][][][grow]"));
		
		JTextPane txtpnHowToUse1 = new JTextPane();
		txtpnHowToUse1.setEditable(false);
		txtpnHowToUse1.setText("How to use :\r\n\r\nFill the ID of the entry you want to update. That entry's values will be shown on the text fields below. Change the values you want.\r\nPress the Update button to update the entry.");
		txtpnHowToUse1.setBackground(SystemColor.info);
		panelUpdate.add(txtpnHowToUse1, "cell 0 0 3 1,height 10%,grow");
		
		JLabel lblNewLabel5 = new JLabel("ID");
		panelUpdate.add(lblNewLabel5, "cell 0 2");
		
		txfUpdateID = new JTextField();
		txfUpdateID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				Database.fillTextfields(txfUpdateID.getText(), txfUpdatePlate, txfUpdateCargo, txfUpdateName, txfUpdateSurname, txfUpdateLocation);
			}
		});
		panelUpdate.add(txfUpdateID, "cell 2 2,growx");
		txfUpdateID.setColumns(10);
		
		JSeparator separator = new JSeparator();
		panelUpdate.add(separator, "cell 0 4 3 1,grow");
		
		JLabel lblNewLabel6 = new JLabel("Plate number");
		panelUpdate.add(lblNewLabel6, "cell 0 6");
		
		txfUpdatePlate = new JTextField();
		panelUpdate.add(txfUpdatePlate, "cell 2 6,growx");
		txfUpdatePlate.setColumns(10);
		
		JLabel lblNewLabel7 = new JLabel("Cargo");
		panelUpdate.add(lblNewLabel7, "cell 0 8");
		
		txfUpdateCargo = new JTextField();
		panelUpdate.add(txfUpdateCargo, "cell 2 8,growx");
		txfUpdateCargo.setColumns(10);
		
		JLabel lblNewLabel8 = new JLabel("Driver's name");
		panelUpdate.add(lblNewLabel8, "cell 0 10");
		
		txfUpdateName = new JTextField();
		panelUpdate.add(txfUpdateName, "cell 2 10,growx");
		txfUpdateName.setColumns(10);
		
		JLabel lblNewLabel9 = new JLabel("Driver's surname");
		panelUpdate.add(lblNewLabel9, "cell 0 12");
		
		txfUpdateSurname = new JTextField();
		panelUpdate.add(txfUpdateSurname, "cell 2 12,growx");
		txfUpdateSurname.setColumns(10);
		
		JLabel lblNewLabel10 = new JLabel("Location");
		panelUpdate.add(lblNewLabel10, "cell 0 14");
		
		txfUpdateLocation = new JTextField();
		panelUpdate.add(txfUpdateLocation, "cell 2 14,growx");
		txfUpdateLocation.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to make an update ?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					String inputID  = txfUpdateID.getText();
					String plate    = txfUpdatePlate.getText();
					String cargo    = txfUpdateCargo.getText();
					String name     = txfUpdateName.getText();
					String surname  = txfUpdateSurname.getText();
					String location = txfUpdateLocation.getText();
					Database.updateEntry(inputID, plate, cargo, name, surname, location);
					Database.fillJTable(table, null, null);			// show updated table
				}
				
			}
	
		});
		panelUpdate.add(btnUpdate, "cell 2 16,alignx center");
		
		JPanel panelDelete = new JPanel();
		tabbedPane.addTab("Delete", null, panelDelete, null);
		panelDelete.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][][][][][][grow]"));
		
		JTextPane txtpnHowToUse2 = new JTextPane();
		txtpnHowToUse2.setEditable(false);
		txtpnHowToUse2.setText("How to use :\r\n\r\nFill the ID of the entry you want to delete. The entry's values will be shown on the text fields below. Make sure you delete the right entry.\r\nPress the Delete buton to delete the entry.");
		txtpnHowToUse2.setBackground(SystemColor.info);
		panelDelete.add(txtpnHowToUse2, "cell 0 0 3 1,height 10%,grow");
		
		JLabel lblNewLabel11 = new JLabel("ID");
		panelDelete.add(lblNewLabel11, "cell 0 2");
		
		txfDeleteID = new JTextField();
		txfDeleteID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				Database.fillTextfields(txfDeleteID.getText(), txfDeletePlate, txfDeleteCargo, txfDeleteName, txfDeleteSurname, txfDeleteLocation);
			}
		});
		panelDelete.add(txfDeleteID, "cell 2 2,growx");
		txfDeleteID.setColumns(10);
		
		JSeparator separator2 = new JSeparator();
		panelDelete.add(separator2, "cell 0 4 3 1,grow");
		
		JLabel lblNewLabel12 = new JLabel("Plate number");
		panelDelete.add(lblNewLabel12, "cell 0 6");
		
		txfDeletePlate = new JTextField();
		panelDelete.add(txfDeletePlate, "cell 2 6,growx");
		txfDeletePlate.setColumns(10);
		
		JLabel lblNewLabel13 = new JLabel("Cargo");
		panelDelete.add(lblNewLabel13, "cell 0 8");
		
		txfDeleteCargo = new JTextField();
		panelDelete.add(txfDeleteCargo, "cell 2 8,growx");
		txfDeleteCargo.setColumns(10);
		
		JLabel lblNewLabel14 = new JLabel("Driver's name");
		panelDelete.add(lblNewLabel14, "cell 0 10");
		
		txfDeleteName = new JTextField();
		panelDelete.add(txfDeleteName, "cell 2 10,growx");
		txfDeleteName.setColumns(10);
		
		JLabel lblNewLabel15 = new JLabel("Driver's surname");
		panelDelete.add(lblNewLabel15, "cell 0 12");
		
		txfDeleteSurname = new JTextField();
		panelDelete.add(txfDeleteSurname, "cell 2 12,growx");
		txfDeleteSurname.setColumns(10);
		
		JLabel lblNewLabel16 = new JLabel("Location");
		panelDelete.add(lblNewLabel16, "cell 0 14");
		
		txfDeleteLocation = new JTextField();
		panelDelete.add(txfDeleteLocation, "cell 2 14,growx");
		txfDeleteLocation.setColumns(10);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to make a delete ?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					String inputID = txfDeleteID.getText();
					Database.deleteEntry(inputID);
					Database.fillJTable(table, null, null);			// show updated table
				}
			}
		});
		panelDelete.add(btnDelete, "cell 2 16,alignx center");
		Database.fillJTable(table, null, null);			// fills the table for the first time
	}
	
	public void setVisible(boolean b) {
		frmTruckTackingApp.setVisible(true);
	}

}
