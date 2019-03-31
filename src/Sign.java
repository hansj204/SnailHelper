import java.awt.Color;
import java.awt.Dialog;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class SignIn extends JFrame implements ActionListener {
	// private String id = "fnfkd";
	// private String pwd = "forever";
	private JTextField tf;
	private JPasswordField pf;
	private JPasswordField pf1;
	private JButton checkok, back;
	JLabel loginText = new JLabel();
	private boolean isLogin = false;
	public String id, pwd, pwdok;
	Connection con;

	public SignIn(Connection connection) throws SQLException, ClassNotFoundException {
		con = connection;
		
		JPanel idPanel = new JPanel();
		JPanel passPanel = new JPanel();
		JPanel passonePanel = new JPanel();
		tf = new JTextField(12);
		pf = new JPasswordField(10);
		pf1 = new JPasswordField(10);
		loginText.setForeground(Color.RED);

		JLabel idLabel = new JLabel("ID : ");
		JLabel passLabel = new JLabel("PASSWORD : ");
		JLabel passoneLabel = new JLabel("PASSWORD CHECK: ");
		checkok = new JButton("회원가입 완료");
		back = new JButton("뒤로 가기");
		checkok.addActionListener(this);
		back.addActionListener(this);

		idPanel.add(idLabel);
		idPanel.add(tf);

		passPanel.add(passLabel);
		passPanel.add(pf);
		
		passonePanel.add(passoneLabel);
		passonePanel.add(pf1);

		add(idPanel);
		add(passPanel);
		add(passonePanel);
		add(checkok);
		add(back);
		add(loginText);

		setLayout(new FlowLayout());
		setLocation(600, 400);
		setTitle("SIGN");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		id = new String(tf.getText());
		pwd = new String(pf.getPassword());
		pwdok = new String(pf1.getPassword());

		   System.out.println(id);
	       System.out.println(pwd);
	       System.out.println(pwdok);
               
		
		if (e.getSource() == checkok) {
			
			try {
				 if(!pwd.equals(pwdok)) {System.out.println("비밀번호가 일치하지 않습니다."); return;}
				PreparedStatement ps = con.prepareStatement("insert into player values (?,?,?,?,?)");
				ps.setString(1, id);
				ps.setString(2, pwd);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				int res = ps.executeUpdate();
				if (res == 1) {
					System.out.println("성공");
				}
				ps.close();
				new Log(null, con);
				this.dispose();
			} catch (Exception e1) {
				System.out.println("false");
			}
		} else if (e.getSource() == back) {
			try {
				new Log(null, con);
				this.dispose();
			} catch (Exception e2) {
	
			}
		}
	}

}

