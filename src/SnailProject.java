
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

class Background extends JPanel { // 1번째 패널
	JLabel lblback;
	JLabel lbllogo;
	JLabel lblstart;
	JLabel lblway;
	JLabel lblrank;
	ImageIcon start;
	ImageIcon back;
	SnailProject win;
	Connection connection;

	public Background(SnailProject win) throws SQLException, ClassNotFoundException {
		
		this.win = win;
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1500, 1000));
		lblback = new JLabel(new ImageIcon("img/background_f.jpg"));
		lbllogo = new JLabel(new ImageIcon("img/logo.png"));
		lblstart = new JLabel(new ImageIcon("img/start.png"));
		lblway = new JLabel(new ImageIcon("img/method.png"));
		lblrank = new JLabel(new ImageIcon("img/ranking.png"));

		lblback.setBounds(0, 0, 1500, 1000);
		lbllogo.setBounds(270, 140, 800, 500);
		lblstart.setBounds(100, 640, 400, 225);
		lblway.setBounds(520, 640, 400, 225);
		lblrank.setBounds(950, 640, 400, 225);
		add(lblstart);
		add(lblway);
		add(lblrank);
		add(lbllogo);
		add(lblback);
		lblway.addMouseListener(new WayListener());
		lblstart.addMouseListener(new StartListener());
		lblrank.addMouseListener(new RankListner());
		
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:sample26.db");
	}

	class StartListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			System.out.println(e.getClass());
			win.change("start");			
			  
			try {
				new Log(win,connection);
			} catch (ClassNotFoundException | SQLException e2) {
				e2.printStackTrace();
			}
				}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	class WayListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			win.change("way");

}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
	
	class RankListner implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("dd");
			System.out.println(e.getClass());
			win.change("rank");		
			
			try {
				try {
					new RankingR(win , connection);
				} catch (FontFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}

class StartGame extends JPanel { // 2번째 패널
	private SnailProject win;

	public StartGame(SnailProject win) throws ClassNotFoundException, SQLException {
		setLayout(null);
		this.win = win;
		
		
		
	}
}// Start

class HowToPlay extends JPanel {
	private SnailProject win;
	JLabel lblback;
	JLabel lblhow;
    JButton lblexit;
	public HowToPlay(SnailProject win) throws FontFormatException, IOException {
		this.win = win;
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1500, 1000));
		/*
		 * start = new ImageIcon("img/start.png"); back = new
		 * ImageIcon("img/start.png");
		 */
		lblback = new JLabel(new ImageIcon("img/background_f.jpg"));
		lblhow = new JLabel(new ImageIcon("img/HowToPlay.png"));
		lblexit = new JButton("나가기");
		Font f = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(25f);
		lblexit.setFont(f);
		lblexit.setBorderPainted(false);
		lblexit.setContentAreaFilled(false);
		lblexit.setFocusPainted(false);
		lblback.setBounds(0, 0, 1500, 1000);
		lblhow.setBounds(160, 25, 1200, 950);
		lblexit.setBounds(950, 700, 200, 100);
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
				win.dispose();
				SnailProject win = new SnailProject();

                try {
					win.back = new Background(win);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				win.add(win.back);
				try {
					win.start = new StartGame(win);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					win.way = new HowToPlay(win);
				} catch (FontFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				win.rank = new Ranking(win);

				win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				win.setSize(1500, 1050);
				win.setVisible(true);	
			}
		});
		add(lblexit);
		add(lblhow);
		add(lblback);
	}

}// HowToPlay

class Ranking extends JPanel {
	private SnailProject win;


	public Ranking(SnailProject win) {
		setLayout(null);
		this.win = win;

	}

}// Ranking

public class SnailProject extends JFrame {

	public Background back = null;
	public StartGame start = null;
	public HowToPlay way = null;
	public Ranking rank = null;
	

	public void change(String panelName) {

		// 패널 1번과 2번 변경 후 재설정

		if (panelName.equals("start")) {
			getContentPane().removeAll();
			getContentPane().add(start);
			revalidate();
			repaint();
		} else if (panelName.equals("way")) {
			getContentPane().removeAll();
			getContentPane().add(way);
			revalidate();
			repaint();
		}else if (panelName.equals("rank")) {
			getContentPane().removeAll();
			getContentPane().add(rank);
			revalidate();
			repaint();}
		else if (panelName.equals("back")) {
			getContentPane().removeAll();
			getContentPane().add(back);
			revalidate();
			repaint();
			
		}
	}// change

	static Music introMusic = new Music("FUNNY.MP3", true);
	public static void main(String[] args) throws ClassNotFoundException, SQLException, FontFormatException, IOException {

		SnailProject win = new SnailProject();

		win.setTitle("SnailHelper");
		
		introMusic.start();

		win.back = new Background(win);
		win.add(win.back);
		win.start = new StartGame(win);
		win.way = new HowToPlay(win);
		win.rank = new Ranking(win);

		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setSize(1500, 1050);
		win.setVisible(true);

		win.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) { 
				introMusic.close();
				System.exit(0);
				
			}

		});

	}// main
}// JFrame