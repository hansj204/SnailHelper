import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Log extends JFrame implements ActionListener {
//	private String id = "fnfkd";
//	private String pwd = "forever";
	private JTextField tf;
	private JPasswordField pf;
	private JButton login, login_gaib;
	private JLabel loginText = new JLabel();
	private boolean isLogin = false;
	public String id;
	public String pwd;
	Connection con;
	SnailProject win;

	public Log(SnailProject winn, Connection connection) throws SQLException, ClassNotFoundException {	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		con = connection;
	    win = winn;

		
	    JPanel idPanel = new JPanel();
		JPanel passPanel = new JPanel();
		tf = new JTextField(12);
		pf = new JPasswordField(10);
		loginText.setForeground(Color.RED);

		JLabel idLabel = new JLabel("ID : ");
		JLabel passLabel = new JLabel("PASSWORD : ");
		login = new JButton("LOGIN");
		login_gaib = new JButton("회원가입");
		login.addActionListener(this);
		login_gaib.addActionListener(this);

		idPanel.add(idLabel);
		idPanel.add(tf);

		passPanel.add(passLabel);
		passPanel.add(pf);

		this.add(idPanel);
		this.add(passPanel);
		this.add(login);
		this.add(login_gaib);
		this.add(loginText);

		setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.WHITE);
		setTitle("LOGIN");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(600, 400);
		setVisible(true);
		

	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getSource() == login) {
			try {
				Statement s= con.createStatement();
				ResultSet rs =s.executeQuery("select * from player where id = '"+tf.getText()+"';");	
				
				while(rs.next()) {
					id = rs.getString("id");
					pwd = rs.getString("pwd");
		

					if (id.equals(tf.getText()) && pwd.equals(pf.getText()))
						isLogin = true;
					else
						isLogin = false;
				}
			
				if (isLogin == true) {					
					loginText.setText("로그인하였습니다.");
					new GAME(id, con);
					this.dispose();
					win.dispose();
					
					
				} else {
					if (id.equals(tf.getText()) && !pwd.equals(pf.getText())||!id.equals(tf.getText()) && pwd.equals(pf.getText()))
						loginText.setText("ID 또는 password가 잘못되었습니다.");
					if (!id.equals(tf.getText()) && !pwd.equals(pf.getText())) 
						loginText.setText("없는 계정입니다.");
							
				}
				
				s.close();
				rs.close();
			} catch (Exception e1) {
				System.out.println("false");
			}
		}
		else if(e.getSource() == login_gaib) {
			try {
				this.setVisible(false);
			     new SignIn(con);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	

}


