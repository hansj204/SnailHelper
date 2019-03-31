import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class RankingR {
	Connection con;
	JFrame frame = new JFrame();
	String id, pwd;
	int win, lose, total;

	public RankingR(SnailProject winn, Connection connection) throws SQLException, ClassNotFoundException, FontFormatException, IOException {
		con = connection;
        winn.dispose();
		frame.setTitle("Ranking");
		frame.setPreferredSize(new Dimension(1500, 1000));
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel back = new JPanel();
		back.setBackground(Color.WHITE);
		back.setLayout(null);
		back.setBounds(0, 0, 1500 , 1000);
		frame.add(back);

//		ImageIcon r =  new ImageIcon("img/result.jpg");
//		Image gets = r.getImage();
//		Image changedI = gets.getScaledInstance(1450, 950, java.awt.Image.SCALE_SMOOTH);
//		ImageIcon rr = new ImageIcon(changedI);
//		JLabel result = new JLabel(rr); 
//		result.setBackground(Color.WHITE);
//		result.setBounds(0, 0, 	1300 , 800);
//		back.add(result);

		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,8));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1500, 1000);
		back.add(panel);
		

		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(25f);
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select id, (win*100)/total, (lose*100)/total from player ");
		
		while (rs.next()) {
			id = rs.getString("id");
			win = rs.getInt("(win*100)/total");
			lose = rs.getInt("(lose*100)/total");

			JLabel rank1 = new JLabel("ID : " + id);
			JLabel rank2 = new JLabel("승률 : " + win+"%");
			JLabel rank3 = new JLabel("패률 : " + lose+"%");
			rank1.setFont(font);
			rank2.setFont(font);
			rank3.setFont(font);
			panel.add(rank1);
			panel.add(rank2);
			panel.add(rank3);

		}
		
		Font f = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(40f);
		JPanel exit = new JPanel();
		exit.setBounds(1000,800, 300, 300);
		back.add(exit);
	    
		
		
		JButton lblexit = new JButton("나가기");
		exit.add(lblexit);
		lblexit.setFont(f);
		lblexit.setBorderPainted(false);
		lblexit.setContentAreaFilled(false);
		lblexit.setFocusPainted(false);
		lblexit.setBounds(0, 0, 300, 200);
		lblexit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				SnailProject winn = new SnailProject();

                try {
					winn.back = new Background(winn);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				winn.add(winn.back);
				try {
					winn.start = new StartGame(winn);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					winn.way = new HowToPlay(winn);
				} catch (FontFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				winn.rank = new Ranking(winn);

				winn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				winn.setSize(1500, 1050);
				winn.setVisible(true);	
			}
		});

		s.close();
		rs.close();
		connection.close();

		frame.pack();
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() { // 내부 무명클래스로서

			@Override

			public void windowClosing(WindowEvent e) { // 이벤트프로그램
				SnailProject.introMusic.close();
				System.exit(0);

			}

		});
	}

}


