package StdMgtSystem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class StudentManagement extends JFrame {

	private JPanel contentPane;
	private JTextField txtStudentID;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtAddress;
	
	Connection con;
	PreparedStatement pst;
	PreparedStatement pst_1;
	ResultSet rs = null;
	ResultSet rs_1 = null;
	ResultSet rs_2 = null;
	ResultSet rs_3 = null;
	private JTable table_2;
	private JPanel panel_3;
	private String student_id;
	private String name;
	private String address;
	private String s_gender;
	private String s_form;
	
	String gender[] = {"Male","Female"};
	String option[] = {"Yes","No"};
	String form[] = {"One","Two","Three","Four"};
	private JComboBox<?> cBoxGender;
	private JComboBox<?> cBoxEnglish;
	private JComboBox<?> cBoxKiswahili;
	private JComboBox<?> cBoxMathematics;
	private JComboBox<?> cBoxChemistry;
	private JComboBox<?> cBoxBiology;
	private JComboBox<?> cBoxGeography;
	private JComboBox<?> cBoxAgriculture;
	private JComboBox<?> cBoxBusiness;
	private JComboBox<?> cBoxPhysics;
	private JComboBox<?> cBoxCRE;
	private JComboBox<?> cBoxHistory;
	private JComboBox<?> cBoxComputer;
	private JPanel pnlAdd;
	private JComboBox<?> cBoxForm;
	private JTextField txtSearchID;
	private JLabel lblSubjects;
	private JPanel pnlStudent;
	private JLabel lblID;
	private JLabel lblName;
	private JLabel lblAddress;
	private JLabel lblGender;
	private JLabel lblForm;
	private JPanel pnlUpdate;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/techiq", "root", "");
		}catch (ClassNotFoundException ex) {
			
		}catch (SQLException ex) {
			
		}
	}
	
	public void tableStudents() {
		try {
			String sql = "select student_ID, first_name, last_name, address, gender, form  from studentsData";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			table_2.setModel(DbUtils.resultSetToTableModel(rs));
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteStudent() {
		String value = txtSearchID.getText();
		try {
			PreparedStatement st = con.prepareStatement("DELETE FROM studentsData WHERE student_ID = '"+ value +"';");
			st.executeUpdate();
			
			tableStudents();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void addStudent() {
		pnlAdd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!(txtStudentID.getText().equals("") || txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtAddress.getText().equals(""))) {
					String student_ID, first_name, address, last_name;
					String gender_ch, form_ch;
					String eng_ch, kisw_ch, maths_ch, chem_ch, bio_ch, geo_ch, bst_ch, agri_ch, phy_ch, cre_ch, comp_ch, hst_ch;
					student_ID = txtStudentID.getText();
					first_name = txtFirstName.getText();
					last_name = txtLastName.getText();
					address = txtAddress.getText();
					gender_ch = (String) cBoxGender.getItemAt(cBoxGender.getSelectedIndex());
					form_ch = (String) cBoxForm.getItemAt(cBoxForm.getSelectedIndex());
					eng_ch = (String) cBoxEnglish.getItemAt(cBoxEnglish.getSelectedIndex());
					kisw_ch = (String) cBoxKiswahili.getItemAt(cBoxKiswahili.getSelectedIndex());
					maths_ch = (String) cBoxMathematics.getItemAt(cBoxMathematics.getSelectedIndex());
					chem_ch = (String) cBoxChemistry.getItemAt(cBoxChemistry.getSelectedIndex());
					bio_ch = (String) cBoxBiology.getItemAt(cBoxBiology.getSelectedIndex());
					geo_ch = (String) cBoxGeography.getItemAt(cBoxGeography.getSelectedIndex());
					bst_ch = (String) cBoxBusiness.getItemAt(cBoxBusiness.getSelectedIndex());
					agri_ch = (String) cBoxAgriculture.getItemAt(cBoxAgriculture.getSelectedIndex());
					phy_ch = (String) cBoxPhysics.getItemAt(cBoxPhysics.getSelectedIndex());
					cre_ch = (String) cBoxCRE.getItemAt(cBoxCRE.getSelectedIndex());
					comp_ch = (String) cBoxComputer.getItemAt(cBoxComputer.getSelectedIndex());
					hst_ch = (String) cBoxHistory.getItemAt(cBoxHistory.getSelectedIndex());
					
					try {
						pst = con.prepareStatement("insert into studentsData(student_ID, first_name, last_name, address, gender, form, "
								+ "eng, kisw, maths, chem, bio, hst, agri, geo, bst, phy, cre, comp)values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
						pst.setString(1, student_ID);
						pst.setString(2, first_name);
						pst.setString(3, last_name);
						pst.setString(4, address);
						pst.setString(5, gender_ch);
						pst.setString(6, form_ch);
						pst.setString(7, eng_ch);
						pst.setString(8, kisw_ch);
						pst.setString(9, maths_ch);
						pst.setString(10, chem_ch);
						pst.setString(11, bio_ch);
						pst.setString(12, hst_ch);
						pst.setString(13, agri_ch);
						pst.setString(14, geo_ch);
						pst.setString(15, bst_ch);
						pst.setString(16, phy_ch);
						pst.setString(17, cre_ch);
						pst.setString(18, comp_ch);
						pst.executeUpdate();

						txtStudentID.setText("");
						txtFirstName.setText("");
						txtLastName.setText("");
						txtAddress.setText("");
												
						tableStudents();
					}catch (SQLIntegrityConstraintViolationException e2) {
						JOptionPane.showMessageDialog(null, "Sorry, The student's ID exists!!!");
					}catch (SQLException e1) {
						//lblErrorMessage.setText("Ooops! An internal error occured. Consult the system administrator.");
						e1.printStackTrace();
					}
				}else if(txtStudentID.getText().equals("") || txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtAddress.getText().equals("")){
					//lblErrorMessage.setText("Please input all fields!");
				}
			}
		});
	}
	
	public void student() {
		pnlStudent = new JPanel();
		pnlStudent.setBorder(new LineBorder(new Color(51, 153, 255)));
		pnlStudent.setOpaque(false);
		pnlStudent.setBounds(10, 239, 539, 333);
		panel_3.add(pnlStudent);
		pnlStudent.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Student's ID:");
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 86, 85, 22);
		pnlStudent.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Name:");
		lblNewLabel_5_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_5_1.setBounds(10, 111, 85, 22);
		pnlStudent.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("Address:");
		lblNewLabel_5_2.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_5_2.setBounds(10, 136, 85, 22);
		pnlStudent.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_3 = new JLabel("Gender:");
		lblNewLabel_5_3.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_5_3.setBounds(10, 161, 85, 22);
		pnlStudent.add(lblNewLabel_5_3);
		
		JLabel lblNewLabel_5_4 = new JLabel("Form:");
		lblNewLabel_5_4.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_5_4.setBounds(10, 185, 85, 22);
		pnlStudent.add(lblNewLabel_5_4);
		
		lblID = new JLabel();
		lblID.setBounds(105, 86, 313, 22);
		pnlStudent.add(lblID);
		
		lblName = new JLabel();
		lblName.setBounds(105, 111, 313, 22);
		pnlStudent.add(lblName);
		
		lblAddress = new JLabel();
		lblAddress.setBounds(105, 136, 313, 22);
		pnlStudent.add(lblAddress);
		
		lblGender = new JLabel();
		lblGender.setBounds(105, 161, 313, 22);
		pnlStudent.add(lblGender);
		
		lblForm = new JLabel();
		lblForm.setBounds(105, 185, 313, 22);
		pnlStudent.add(lblForm);
		
		JLabel lblNewLabel_6 = new JLabel("Individual Student's Record");
		lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_6.setBounds(10, 11, 194, 22);
		pnlStudent.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Enter ID:");
		lblNewLabel_7.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_7.setBounds(10, 31, 47, 22);
		pnlStudent.add(lblNewLabel_7);
		
		txtSearchID = new JTextField();
		txtSearchID.setFont(new Font("Arial", Font.BOLD, 12));
		txtSearchID.setBounds(68, 32, 86, 20);
		pnlStudent.add(txtSearchID);
		txtSearchID.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchStudent();
			}
		});
		btnNewButton.setRolloverEnabled(false);
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
		btnNewButton.setBounds(164, 31, 89, 23);
		pnlStudent.add(btnNewButton);
		
		JLabel lblNewLabel_8 = new JLabel("Subjects:");
		lblNewLabel_8.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_8.setBounds(10, 207, 85, 22);
		pnlStudent.add(lblNewLabel_8);
		
		lblSubjects = new JLabel();
		lblSubjects.setBounds(105, 207, 424, 22);
		pnlStudent.add(lblSubjects);
		
		JLabel lblNewLabel_9 = new JLabel("Note: Please insert the ID above to perform the oprations below.");
		lblNewLabel_9.setForeground(new Color(255, 0, 0));
		lblNewLabel_9.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_9.setBounds(10, 308, 360, 14);
		pnlStudent.add(lblNewLabel_9);
	}
	
	public void searchStudent() {		
		if(txtSearchID.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Insert a student ID!!!");
		}else {
			String select = txtSearchID.getText();
			String query_1 = "select * from studentsData where student_ID = '"+ select +"';";
			String query = "select eng, kisw, maths, chem, bio, hst, agri, geo, bst, phy, cre, comp from studentsData where student_ID = '"+ select +"';";
			try {				
				rs_1 = pst.executeQuery(query_1);
				while(rs_1.next()) {
					student_id = rs_1.getString("student_ID");
					name = rs_1.getString("first_name") + " " + rs_1.getString("last_name");
					address = rs_1.getString("address");
					s_gender = rs_1.getString("gender");
					s_form = rs_1.getString("form");
					
					lblID.setText(student_id);
					lblName.setText(name);
					lblAddress.setText(address);
					lblGender.setText(s_gender);
					lblForm.setText(s_form);
					
					txtStudentID.setText(student_id);
					txtFirstName.setText(rs_1.getString("first_name"));
					txtLastName.setText(rs_1.getString("last_name"));
					txtAddress.setText(address);
					
					cBoxGender.setSelectedItem(rs_1.getString("gender"));
					cBoxForm.setSelectedItem(rs_1.getString("form"));
					cBoxEnglish.setSelectedItem(rs_1.getString("eng"));
					cBoxKiswahili.setSelectedItem(rs_1.getString("kisw"));
					cBoxMathematics.setSelectedItem(rs_1.getString("maths"));
					cBoxChemistry.setSelectedItem(rs_1.getString("chem"));
					cBoxBiology.setSelectedItem(rs_1.getString("bio"));
					cBoxHistory.setSelectedItem(rs_1.getString("hst"));
					cBoxAgriculture.setSelectedItem(rs_1.getString("agri"));
					cBoxGeography.setSelectedItem(rs_1.getString("geo"));
					cBoxBusiness.setSelectedItem(rs_1.getString("bst"));
					cBoxPhysics.setSelectedItem(rs_1.getString("phy"));
					cBoxCRE.setSelectedItem(rs_1.getString("cre"));
					cBoxComputer.setSelectedItem(rs_1.getString("comp"));
					
					rs = pst.executeQuery(query);
					
					java.sql.ResultSetMetaData rsmd = rs.getMetaData();
					
					String name_1 = rsmd.getColumnName(1);
					String name_2 = rsmd.getColumnName(2);
					String name_3 = rsmd.getColumnName(3);
					String name_4 = rsmd.getColumnName(4);
					String name_5 = rsmd.getColumnName(5);
					String name_6 = rsmd.getColumnName(6);
					String name_7 = rsmd.getColumnName(7);
					String name_8 = rsmd.getColumnName(8);
					String name_9 = rsmd.getColumnName(9);
					String name_10 = rsmd.getColumnName(10);
					String name_11 = rsmd.getColumnName(11);
					String name_12 = rsmd.getColumnName(12);
					
					StringBuilder sb = new StringBuilder();
					
					String[] subj = {name_1, name_2, name_3, name_4, name_5, name_6, name_7, name_8, name_9, name_10, name_11, name_12};
					for(int i=0; i<=11; i++) {
						String query_2 = "select * from studentsData where student_ID = '"+ select +"';";
						rs_1 = pst.executeQuery(query_2);
						while(rs_1.next()) {
							String subj_i = rs_1.getString(subj[i]);
							if(subj_i.equals("Yes")) {
								sb.append(subj[i].toUpperCase()+ "  ");
							}
						}
					}							
					lblSubjects.setText(sb.toString());
				}
				
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
		
		tableStudents();
	}
	
	public void update() {
		pnlUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String select = txtSearchID.getText();
				
				String first_name = txtFirstName.getText();
				String last_name = txtLastName.getText();
				String address = txtAddress.getText();
				
				String gender = (String) cBoxGender.getSelectedItem();
				String form = (String) cBoxForm.getSelectedItem();
				String eng = (String) cBoxEnglish.getSelectedItem();
				String kisw = (String) cBoxKiswahili.getSelectedItem();
				String maths = (String) cBoxMathematics.getSelectedItem();
				String chem = (String) cBoxChemistry.getSelectedItem();
				String bio = (String) cBoxBiology.getSelectedItem();
				String hst = (String) cBoxHistory.getSelectedItem();
				String agri = (String) cBoxAgriculture.getSelectedItem();
				String geo = (String) cBoxGeography.getSelectedItem();
				String bst = (String) cBoxBusiness.getSelectedItem();
				String phy = (String) cBoxPhysics.getSelectedItem();
				String cre = (String) cBoxCRE.getSelectedItem();
				String comp = (String) cBoxComputer.getSelectedItem();
				
				String query = "update studentsData set first_name ='"+ first_name +"', "
						+ "last_name ='"+ last_name +"', address ='"+ address +"', "
								+ "gender ='"+ gender +"', form ='"+ form +"', eng ='"+ eng +"', "
										+ "kisw ='"+ kisw +"', maths ='"+ maths +"', chem ='"+ chem +"', "
												+ "bio ='"+ bio +"', hst ='"+ hst +"', agri ='"+ agri +"', "
														+ "geo ='"+ geo +"', bst ='"+ bst +"', phy ='"+ phy +"', "
																+ "cre ='"+ cre +"', comp='"+ comp +"' where student_ID = '"+ select +"';";
				try {
					pst.executeUpdate(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				tableStudents();
			}
		});
	}
	
	public void export(JTable table, File file) {
		try {
			TableModel m = table.getModel();
			FileWriter fw = new FileWriter(file);
			for(int i=0; i<m.getColumnCount(); i++) {
				fw.write(m.getColumnName(i).toUpperCase() +"\t");
			}
			fw.write("\n");
			for(int i=0; i<m.getRowCount(); i++) {
				for(int j=0; j<m.getColumnCount(); j++) {
					fw.write(m.getValueAt(i, j).toString() +"\t");
				}
				fw.write("\n");
			}
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public StudentManagement() {
		Connect();
		
		setBackground(new Color(0, 102, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 727);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 204));
		contentPane.setBorder(new LineBorder(new Color(51, 0, 102), 2));
		setContentPane(contentPane); 
		contentPane.setLayout(null);
		
		JLabel lblClose = new JLabel("X");
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0 ) {
					StudentManagement.this.dispose();
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
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 153, 204));
		panel.setBounds(2, 2, 835, 722);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(51, 204, 255)));
		panel_1.setBackground(new Color(51, 153, 255));
		panel_1.setBounds(10, 11, 815, 43);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Management Panel");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		lblNewLabel.setBounds(232, 0, 341, 43);
		panel_1.add(lblNewLabel);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new LineBorder(new Color(51, 204, 255)));
		panel_1_1.setBackground(new Color(51, 153, 255));
		panel_1_1.setBounds(10, 54, 815, 657);
		panel.add(panel_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 153, 204));
		panel_2.setBounds(10, 11, 226, 583);
		panel_1_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Student ID");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 11, 206, 21);
		panel_2.add(lblNewLabel_2);
		
		txtStudentID = new JTextField();
		txtStudentID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtStudentID.setBounds(10, 29, 206, 21);
		panel_2.add(txtStudentID);
		txtStudentID.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("First Name");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(10, 55, 206, 21);
		panel_2.add(lblNewLabel_2_1);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(10, 73, 206, 21);
		panel_2.add(txtFirstName);
		
		JLabel lblNewLabel_2_2 = new JLabel("Last Name");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_2.setBounds(10, 101, 206, 21);
		panel_2.add(lblNewLabel_2_2);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtLastName.setColumns(10);
		txtLastName.setBounds(10, 120, 206, 21);
		panel_2.add(txtLastName);
		
		JLabel lblNewLabel_2_3 = new JLabel("Address");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_3.setBounds(10, 147, 206, 21);
		panel_2.add(lblNewLabel_2_3);
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAddress.setColumns(10);
		txtAddress.setBounds(10, 166, 206, 21);
		panel_2.add(txtAddress);
		
		JLabel lblNewLabel_2_4 = new JLabel("Gender");
		lblNewLabel_2_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_4.setBounds(10, 190, 99, 21);
		panel_2.add(lblNewLabel_2_4);
		
		cBoxGender = new JComboBox<Object>(gender);
		cBoxGender.setEditable(true);
		cBoxGender.setBounds(10, 207, 99, 22);
		panel_2.add(cBoxGender);
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_6.setBorder(new LineBorder(new Color(255, 255, 255)));
		panel_6.setBounds(10, 240, 206, 332);
		panel_2.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("English");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(10, 22, 91, 14);
		panel_6.add(lblNewLabel_3);
		
		cBoxEnglish = new JComboBox<Object>(option);
		cBoxEnglish.setEditable(true);
		cBoxEnglish.setBounds(10, 40, 91, 22);
		panel_6.add(cBoxEnglish);
		
		cBoxKiswahili = new JComboBox<Object>(option);
		cBoxKiswahili.setEditable(true);
		cBoxKiswahili.setBounds(105, 40, 91, 22);
		panel_6.add(cBoxKiswahili);
		
		JLabel lblNewLabel_3_1 = new JLabel("Kiswahili");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1.setBounds(105, 22, 91, 14);
		panel_6.add(lblNewLabel_3_1);
		
		cBoxMathematics = new JComboBox<Object>(option);
		cBoxMathematics.setEditable(true);
		cBoxMathematics.setBounds(10, 89, 91, 22);
		panel_6.add(cBoxMathematics);
		
		JLabel lblNewLabel_3_2 = new JLabel("Mathematics");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_2.setBounds(10, 71, 91, 14);
		panel_6.add(lblNewLabel_3_2);
		
		cBoxChemistry = new JComboBox<Object>(option);
		cBoxChemistry.setEditable(true);
		cBoxChemistry.setBounds(105, 89, 91, 22);
		panel_6.add(cBoxChemistry);
		
		JLabel lblNewLabel_3_3 = new JLabel("Chemistry");
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_3.setBounds(105, 71, 91, 14);
		panel_6.add(lblNewLabel_3_3);
		
		cBoxBiology = new JComboBox<Object>(option);
		cBoxBiology.setEditable(true);
		cBoxBiology.setBounds(10, 138, 91, 22);
		panel_6.add(cBoxBiology);
		
		JLabel lblNewLabel_3_4 = new JLabel("Biology");
		lblNewLabel_3_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_4.setBounds(10, 120, 91, 14);
		panel_6.add(lblNewLabel_3_4);
		
		cBoxGeography = new JComboBox<Object>(option);
		cBoxGeography.setEditable(true);
		cBoxGeography.setBounds(105, 138, 91, 22);
		panel_6.add(cBoxGeography);
		
		JLabel lblNewLabel_3_5 = new JLabel("Geography");
		lblNewLabel_3_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_5.setBounds(105, 120, 91, 14);
		panel_6.add(lblNewLabel_3_5);
		
		cBoxAgriculture = new JComboBox<Object>(option);
		cBoxAgriculture.setEditable(true);
		cBoxAgriculture.setBounds(10, 187, 91, 22);
		panel_6.add(cBoxAgriculture);
		
		JLabel lblNewLabel_3_6 = new JLabel("Agriculture");
		lblNewLabel_3_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_6.setBounds(10, 169, 91, 14);
		panel_6.add(lblNewLabel_3_6);
		
		cBoxBusiness = new JComboBox<Object>(option);
		cBoxBusiness.setEditable(true);
		cBoxBusiness.setBounds(105, 187, 91, 22);
		panel_6.add(cBoxBusiness);
		
		JLabel lblNewLabel_3_7 = new JLabel("Business");
		lblNewLabel_3_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_7.setBounds(105, 169, 91, 14);
		panel_6.add(lblNewLabel_3_7);
		
		cBoxPhysics = new JComboBox<Object>(option);
		cBoxPhysics.setEditable(true);
		cBoxPhysics.setBounds(10, 234, 91, 22);
		panel_6.add(cBoxPhysics);
		
		JLabel lblNewLabel_3_8 = new JLabel("Physics");
		lblNewLabel_3_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_8.setBounds(10, 216, 91, 14);
		panel_6.add(lblNewLabel_3_8);
		
		cBoxCRE = new JComboBox<Object>(option);
		cBoxCRE.setEditable(true);
		cBoxCRE.setBounds(105, 234, 91, 22);
		panel_6.add(cBoxCRE);
		
		JLabel lblNewLabel_3_9 = new JLabel("C.R.E");
		lblNewLabel_3_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_9.setBounds(105, 216, 91, 14);
		panel_6.add(lblNewLabel_3_9);
		
		JLabel lblNewLabel_3_8_1 = new JLabel("History");
		lblNewLabel_3_8_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_8_1.setBounds(10, 267, 91, 14);
		panel_6.add(lblNewLabel_3_8_1);
		
		cBoxHistory = new JComboBox<Object>(option);
		cBoxHistory.setEditable(true);
		cBoxHistory.setBounds(10, 285, 91, 22);
		panel_6.add(cBoxHistory);
		
		JLabel lblNewLabel_3_8_2 = new JLabel("Computer");
		lblNewLabel_3_8_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_8_2.setBounds(105, 267, 91, 14);
		panel_6.add(lblNewLabel_3_8_2);
		
		cBoxComputer = new JComboBox<Object>(option);
		cBoxComputer.setEditable(true);
		cBoxComputer.setBounds(105, 285, 91, 22);
		panel_6.add(cBoxComputer);
		
		cBoxForm = new JComboBox<Object>(form);
		cBoxForm.setEditable(true);
		cBoxForm.setBounds(119, 207, 99, 22);
		panel_2.add(cBoxForm);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("Form");
		lblNewLabel_2_4_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_4_1.setBounds(117, 190, 99, 21);
		panel_2.add(lblNewLabel_2_4_1);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 153, 204));
		panel_3.setBounds(246, 11, 559, 583);
		panel_1_1.add(panel_3);
		panel_3.setLayout(null);
		
		table_2 = new JTable();
		table_2.setEnabled(false);
		table_2.setOpaque(false);
		table_2.setBounds(10, 32, 539, 196);
		table_2.setRowSelectionAllowed(true);
		JScrollPane pane = new JScrollPane(table_2);
		pane.setBackground(new Color(240, 240, 240));
		pane.setBorder(new LineBorder(new Color(51, 153, 255)));
		pane.setAutoscrolls(true);
		pane.setBounds(10, 11, 539, 217);
		panel_3.add(pane);
		tableStudents();
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 153, 204));
		panel_4.setBounds(10, 605, 795, 41);
		panel_1_1.add(panel_4);
		panel_4.setLayout(null);
		
		pnlAdd = new JPanel();
		pnlAdd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlAdd.setBackground(new Color(51, 153, 255));
		pnlAdd.setBounds(7, 7, 122, 26);
		panel_4.add(pnlAdd);
		pnlAdd.setLayout(null);
		addStudent();
		
		JLabel lblNewLabel_1 = new JLabel("Add New");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 0, 100, 26);
		pnlAdd.add(lblNewLabel_1);
		
		JPanel pnlBtnPrint = new JPanel();
		pnlBtnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser fchoose = new JFileChooser();
				int option = fchoose.showSaveDialog(StudentManagement.this);
				if(option == JFileChooser.APPROVE_OPTION) {
					String name = fchoose.getSelectedFile().getName();
					String path = fchoose.getSelectedFile().getParentFile().getPath();
					String file = path + "\\" + name;
					export(table_2, new File(file));
				} 
//				PrinterJob pjob = PrinterJob.getPrinterJob();
//				PageFormat preformat = pjob.defaultPage();
//				preformat.setOrientation(PageFormat.PORTRAIT);
//				PageFormat postformat = pjob.pageDialog(preformat);
//				
//				if(preformat != postformat) {
//					pjob.setPrintable(new Printer(pnlStudent), postformat);
//					if(pjob.printDialog()) {
//						try {
//							pjob.print();
//						} catch (PrinterException e1) {
//							e1.printStackTrace();
//						}
//					}
//				}
			}
		});
		pnlBtnPrint.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlBtnPrint.setLayout(null);
		pnlBtnPrint.setBackground(new Color(51, 153, 255));
		pnlBtnPrint.setBounds(138, 7, 122, 26);
		panel_4.add(pnlBtnPrint);
		
		JLabel lblNewLabel_1_1 = new JLabel("Export");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 0, 100, 26);
		pnlBtnPrint.add(lblNewLabel_1_1);
		
		pnlUpdate = new JPanel();
		pnlUpdate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlUpdate.setLayout(null);
		pnlUpdate.setBackground(new Color(51, 153, 255));
		pnlUpdate.setBounds(269, 7, 122, 26);
		panel_4.add(pnlUpdate);
		update();
		
		JLabel lblNewLabel_1_2 = new JLabel("Update");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 0, 100, 26);
		pnlUpdate.add(lblNewLabel_1_2);
		
		JPanel pnlReset = new JPanel();
		pnlReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PreparedStatement st;
				try {
					st = con.prepareStatement("DELETE FROM studentsData");
					st.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableStudents();
				student();
			}
		});
		pnlReset.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlReset.setLayout(null);
		pnlReset.setBackground(new Color(51, 153, 255));
		pnlReset.setBounds(401, 7, 122, 26);
		panel_4.add(pnlReset);
		
		JLabel lblNewLabel_1_3 = new JLabel("Reset");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(10, 0, 100, 26);
		pnlReset.add(lblNewLabel_1_3);
		
		JPanel pnlDelete = new JPanel();
		pnlDelete.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteStudent();
				tableStudents();
			}
		});
		pnlDelete.setLayout(null);
		pnlDelete.setBackground(new Color(51, 153, 255));
		pnlDelete.setBounds(533, 7, 122, 26);
		panel_4.add(pnlDelete);
		
		JLabel lblNewLabel_1_4 = new JLabel("Delete");
		lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_4.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_4.setBounds(10, 0, 100, 26);
		pnlDelete.add(lblNewLabel_1_4);
		
		JPanel pnlExit = new JPanel();
		pnlExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login panel = new Login();
				panel.setVisible(true);
				dispose();
			}
		});
		pnlExit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlExit.setLayout(null);
		pnlExit.setBackground(new Color(51, 153, 255));
		pnlExit.setBounds(665, 7, 122, 26);
		panel_4.add(pnlExit);
		
		JLabel lblNewLabel_1_5 = new JLabel("Exit");
		lblNewLabel_1_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_5.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_5.setBounds(10, 0, 100, 26);
		pnlExit.add(lblNewLabel_1_5);
		
		student();
		
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
}

class Printer implements Printable {
	final Component comp;
	
	public Printer(Component comp) {
		this.comp = comp;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		
		Dimension dim = comp.getSize();
		double cHeight = dim.getHeight();
		double cWidth = dim.getWidth();
		
		double pHeight = pageFormat.getImageableHeight();
		double pWidth = pageFormat.getImageableWidth();
		
		double pXStart = pageFormat.getImageableX();
		double pYStart = pageFormat.getImageableY();
		
		double xRatio = pWidth / cWidth;
		double yRatio = pHeight / cHeight;
		
		Graphics2D g2 = (Graphics2D) graphics;
		g2.translate(pXStart, pYStart);
		g2.scale(xRatio, yRatio);
		comp.paint(g2);
		
		return Printable.PAGE_EXISTS;
	}
	
}