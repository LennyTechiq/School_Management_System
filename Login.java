package StdMgtSystem;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {
	private Image badge_image = new ImageIcon(Login.class.getResource("background.jpg")).getImage().getScaledInstance(920, 705, Image.SCALE_SMOOTH);

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtUsername_1;
	private JPasswordField pwdPassword;
	private JPasswordField pwdPassword_1;
	
	ResultSet rs = null;
	PreparedStatement pst;
	Connection con;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/techiq", "root", "");
		}catch (ClassNotFoundException ex) {
			
		}catch (SQLException ex) {
			
		}
	}

	public Login() {
		Connect();
		setBackground(new Color(0, 102, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 727);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 102, 204));
		contentPane.setBorder(new LineBorder(new Color(51, 0, 102), 2));
		setContentPane(contentPane); 
		contentPane.setLayout(null);
		
		JLabel lblClose = new JLabel("X");
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0 ) {
					Login.this.dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblClose.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblClose.setForeground(Color.WHITE);
			}
		});
		lblClose.setForeground(new Color(255, 255, 255));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		lblClose.setBounds(810, 11, 20, 20);
		contentPane.add(lblClose);

		JPanel panelLogin = new JPanel();
		panelLogin.setBackground(new Color(153, 0, 153));
		panelLogin.setBounds(10, 11, 820, 705);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(255, 102, 204), 2));
		panel.setOpaque(false);
		panel.setBackground(new Color(153, 0, 0));
		panel.setBounds(10, 425, 300, 269);
		panelLogin.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 19));
		lblNewLabel.setBounds(121, 11, 59, 32);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBackground(UIManager.getColor("Label.foreground"));
		lblNewLabel_1.setForeground(UIManager.getColor("Label.foreground"));
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 92, 71, 24);
		panel.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtUsername.getText().equals("Username")) {
					txtUsername.setText("");
				}else {
					txtUsername.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUsername.getText().equals("")) {
					txtUsername.setText("Username");
				}
			}
		});
		txtUsername.setText("Username");
		txtUsername.setBackground(new Color(255, 102, 204));
		txtUsername.setBorder(null);
		txtUsername.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		txtUsername.setBounds(91, 92, 199, 24);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setForeground(UIManager.getColor("Label.foreground"));
		lblNewLabel_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 127, 71, 24);
		panel.add(lblNewLabel_1_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = txtUsername.getText();
				String password = pwdPassword.getText();
				if(txtUsername.getText().equals("") || pwdPassword.getText().equals("") || txtUsername.getText().equals("Username") || pwdPassword.getText().equals("Password")){
					JOptionPane.showMessageDialog(null, "Please fill all the spaces!!!");
				}else {
					try {
						String sql = "select userName, password from users where userName = '" + userName + "' && password='" + password + "'";
						pst = con.prepareStatement(sql); 
						rs = pst.executeQuery();
						if(rs.next()) {
							txtUsername.setText("");
							pwdPassword.setText("");
							StudentManagement panel = new StudentManagement();
							panel.setVisible(true);
							dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Your credentials didn't match!");
						}
						
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnLogin.setRequestFocusEnabled(false);
		btnLogin.setForeground(UIManager.getColor("Button.highlight"));
		btnLogin.setBackground(SystemColor.activeCaption);
		btnLogin.setBorder(null);
		btnLogin.setBounds(104, 205, 89, 23);
		panel.add(btnLogin);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(pwdPassword.getText().equals("Password")) {
					pwdPassword.setEchoChar('●');
					pwdPassword.setText("");
				}else {
					pwdPassword.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(pwdPassword.getText().equals("")) {
					pwdPassword.setText("Password");
					pwdPassword.setEchoChar((char)0);
				}
			}
		});
		pwdPassword.setEchoChar((char)0);
		pwdPassword.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		pwdPassword.setBorder(null);
		pwdPassword.setBackground(new Color(255, 102, 204));
		pwdPassword.setBounds(91, 127, 199, 24);
		panel.add(pwdPassword);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setOpaque(false);
		panel_1.setBorder(new LineBorder(new Color(255, 102, 204), 2));
		panel_1.setBackground(new Color(153, 0, 0));
		panel_1.setBounds(510, 425, 300, 269);
		panelLogin.add(panel_1);
		
		JLabel lblRegister = new JLabel("Sign Up");
		lblRegister.setVerticalAlignment(SwingConstants.TOP);
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setForeground(new Color(255, 0, 0));
		lblRegister.setFont(new Font("Segoe UI Black", Font.BOLD, 19));
		lblRegister.setBounds(116, 11, 89, 32);
		panel_1.add(lblRegister);
		
		JLabel lblNewLabel_1_2 = new JLabel("Username");
		lblNewLabel_1_2.setForeground(UIManager.getColor("Label.foreground"));
		lblNewLabel_1_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 92, 71, 24);
		panel_1.add(lblNewLabel_1_2);
		
		txtUsername_1 = new JTextField();
		txtUsername_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtUsername_1.getText().equals("Username")) {
					txtUsername_1.setText("");
				}else {
					txtUsername_1.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUsername_1.getText().equals("")) {
					txtUsername_1.setText("Username");
				}
			}
		});
		txtUsername_1.setText("Username");
		txtUsername_1.setBackground(new Color(255, 102, 204));
		txtUsername_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		txtUsername_1.setColumns(10);
		txtUsername_1.setBorder(null);
		txtUsername_1.setBounds(91, 92, 199, 24);
		panel_1.add(txtUsername_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Password");
		lblNewLabel_1_1_1.setForeground(UIManager.getColor("Label.foreground"));
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 127, 71, 24);
		panel_1.add(lblNewLabel_1_1_1);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(txtUsername_1.getText().equals("") || pwdPassword_1.getText().equals("") || txtUsername_1.getText().equals("Username") || pwdPassword_1.getText().equals("Password"))) {
					String userName, password;
					userName = txtUsername_1.getText();
					password = pwdPassword_1.getText();
					System.out.println(userName);
					System.out.println(password);
					try {
						pst = con.prepareStatement("insert into users(userName, password)values(?, ?)");
						pst.setString(1, userName);
						pst.setString(2, password);
						pst.executeUpdate();
						
						txtUsername_1.setText("");
						pwdPassword_1.setText("");
						
						JOptionPane.showMessageDialog(null, "Successfully registred!");
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else if(txtUsername_1.getText().equals("") || pwdPassword_1.getText().equals("") || txtUsername_1.getText().equals("Username") || pwdPassword_1.getText().equals("Password")){
					JOptionPane.showMessageDialog(null, "Please fill all the spaces!!!");
				}
			}
		});
		btnSignUp.setRequestFocusEnabled(false);
		btnSignUp.setForeground(UIManager.getColor("Button.highlight"));
		btnSignUp.setBorder(null);
		btnSignUp.setBackground(SystemColor.activeCaption);
		btnSignUp.setBounds(104, 205, 89, 23);
		panel_1.add(btnSignUp);
		
		pwdPassword_1 = new JPasswordField();
		pwdPassword_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(pwdPassword_1.getText().equals("Password")) {
					pwdPassword_1.setEchoChar('●');
					pwdPassword_1.setText("");
				}else {
					pwdPassword_1.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(pwdPassword_1.getText().equals("")) {
					pwdPassword_1.setText("Password");
					pwdPassword_1.setEchoChar((char)0);
				}
			}
		});
		pwdPassword_1.setEchoChar((char)0);
		pwdPassword_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		pwdPassword_1.setText("Password");
		pwdPassword_1.setBorder(null);
		pwdPassword_1.setBackground(new Color(255, 102, 204));
		pwdPassword_1.setBounds(91, 127, 199, 24);
		panel_1.add(pwdPassword_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 0, 820, 705);
		panelLogin.add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon(badge_image));
		
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
}
