package ml_project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * This class produces the login frame
 */
public class LoginFrame {

	private JFrame frame;
	private JTextField txfUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
		frame.setResizable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.getContentPane().setLayout(new MigLayout("", "[50px][grow][grow][50px]", "[60px][][20px][][50px][30px]"));
		
		JLabel lblNewLabel = new JLabel("Username :");
		frame.getContentPane().add(lblNewLabel, "cell 1 1,alignx right");
		
		txfUsername = new JTextField();
		frame.getContentPane().add(txfUsername, "cell 2 1,alignx center");
		txfUsername.setColumns(12);
		
		JLabel lblNewLabel1 = new JLabel("Password :");
		frame.getContentPane().add(lblNewLabel1, "cell 1 3,alignx right");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(12);
		frame.getContentPane().add(passwordField, "cell 2 3,alignx center");
		
		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = txfUsername.getText().trim();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText().trim();
				if (Database.login(username, password)) {
					// JOptionPane.showMessageDialog(null, "CORRECT Username and Password");
					frame.dispose();
					MainFrame mframe = new MainFrame();
					mframe.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "INCORRECT Username and/or Password");
				}
			}
		});
		frame.getContentPane().add(btnLogin, "cell 1 5 2 1,width 25%,alignx center,growy");
		frame.setTitle("Log in");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
