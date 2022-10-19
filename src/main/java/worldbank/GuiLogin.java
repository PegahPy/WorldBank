package worldbank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class GuiLogin extends JFrame{
	private static GuiLogin instance;
	
	private static JLabel password1, label;
	private static JTextField username;
	private static JButton button;
	private static JPasswordField password;
	
	private static UserManager userManager;

	public static GuiLogin getInstance() {
		if (instance == null)
			instance = new GuiLogin();

		return instance;
	}
	
	private GuiLogin() {
		super("LOGIN PAGE");
		
		userManager = UserManager.getInstance();
		
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		this.setSize(900, 600);
		this.setTitle("LOGIN PAGE");
		this.setLocation(new Point(500, 300));
		this.add(panel);
		this.setSize(new Dimension(400, 200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("Username");
		label.setBounds(100, 8, 70, 20);
		panel.add(label);
		
		username = new JTextField();
		username.setBounds(100, 27, 193, 28);
		panel.add(username);
		
		password1 = new JLabel("Password");
		password1.setBounds(100, 55, 70, 20);
		panel.add(password1);
		
		password = new JPasswordField();
		password.setBounds(100, 75, 193, 28);
		panel.add(password);
		
		button = new JButton("Login");
		button.setBounds(100, 110, 90, 25);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = username.getText();
				String pass = password.getText();
				if(userManager.login(user, pass)) {
					JOptionPane.showMessageDialog(null, "Login Successful");
					dispose();
					JFrame mainFrame = GuiMain.getInstance();
					mainFrame.pack();
					mainFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Username or Password mismatch ");
					dispose();
				}
			
			}
		});
		panel.add(button);
		
		
	}

}
