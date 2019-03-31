import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.KeyManager;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTreeUI.MouseHandler;
import javax.xml.crypto.KeySelectorResult;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GAME extends JFrame {
	JFrame frame = new JFrame();
	static ImageIcon scard = new ImageIcon("img/scard.png");
	static ImageIcon ccard = new ImageIcon("img/ccard.png");
	static ImageIcon scard_tb = new ImageIcon("img/scard_tb.png");
	static ImageIcon ccard_right = new ImageIcon("img/ccard_right.png");
	static ImageIcon ccard_bottom = new ImageIcon("img/ccard_down.png");
	static ImageIcon ccard_dr = new ImageIcon("img/ccard_dr.png");
	static ImageIcon start = new ImageIcon("img/startcard.png");
	private ImageIcon finish = new ImageIcon("img/finishcard.png");
	private ImageIcon item = new ImageIcon("img/itemcard.png");
	private JButton cardkind = new JButton(scard);
	private JPanel panbackround = new JPanel();
	private JButton areaButton[][];
	int i, j, us1 = 16, us2 = 16, no = 0;
	int ran1 = (int) (Math.random() * 10);
	int ran2 = (int) (Math.random() * 10);
	int item1 = (int) (Math.random() * 10);
	int item2 = (int) (Math.random() * 10);
	public static boolean ok = true, end = false;
	boolean turn = false;
	JLabel roadkind = new JLabel("");
	String ch1 = "USER1", ch2 = "USER2";
	JTextField id1 = new JTextField(ch1);
	JTextField id2 = new JTextField(ch2);
	JLabel timerLabel = new JLabel("0");
	Connection con;
	Timer timer = new Timer();
	T task = new T();
	Music click;
	String name = "NAME";

	public GAME(String id, Connection connection)
			throws ClassNotFoundException, SQLException, FontFormatException, IOException {
		frame.setTitle("SNAIL HELPER");
		frame.setPreferredSize(new Dimension(1500, 1000));
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		con = connection;

		// 게임판 공간
		JPanel panbackround = new JPanel();
		panbackround.setBackground(Color.BLACK);
		panbackround.setLayout(null);
		frame.add(panbackround);
		con = connection;
		// 게임판 공간
		JPanel gamepan = new JPanel();
		gamepan.setBounds(0, 2, 950, 950);
		gamepan.setLayout(new GridLayout(10, 10));
		gamepan.setBackground(Color.white);
		panbackround.add(gamepan);
		con = connection;

		areaButton = new JButton[10][10];

		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				areaButton[i][j] = new JButton();
				areaButton[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				areaButton[i][j].setPreferredSize(new Dimension(40, 40));
				areaButton[i][j].setBackground(new Color(112, 217, 87));

				areaButton[i][j].addMouseListener(new AreaButtonListener(i, j, areaButton[i][j]));
				gamepan.add(areaButton[i][j]);

			} // for j
		} // for i

		while (ran1 - ran2 < 3) {
			ran1 = (int) (Math.random() * 10);
			ran2 = (int) (Math.random() * 10);
		}

		while (ran1 - item1 < 3 && ran2 - item2 < 3 || ran1 == item1 && ran2 == item2) {
			item1 = (int) (Math.random() * 10);
			item2 = (int) (Math.random() * 10);
		}

		Image gets = start.getImage();
		Image changedI = gets.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon startcard = new ImageIcon(changedI);

		Image getf = finish.getImage();
		Image changedM = getf.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon finishcard = new ImageIcon(changedM);

		Image geti = item.getImage();
		Image changedG = geti.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon itemcard = new ImageIcon(changedG);

		areaButton[ran1][ran2].setIcon(startcard);
		areaButton[ran2][ran1].setIcon(finishcard);
		areaButton[item1][item2].setIcon(itemcard);

		// 사용자 공간
		JPanel players = new JPanel();
		players.setBounds(950, 2, 535, 950);
		players.setLayout(null);
		players.setBackground(Color.WHITE);
		panbackround.add(players);

		// 사용자 정보 공개
		ImageIcon img = new ImageIcon("img/man-user.png");
		JPanel user1 = new JPanel();
		JLabel profile1 = new JLabel(img, SwingConstants.LEFT);
		id1.setText(ch1);
		id1.setBorder(null);
		Font fontf = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(15f);
		id1.setFont(fontf);
		user1.setBackground(Color.WHITE);
		user1.setBounds(0, 50, 300, 100);
		user1.add(profile1);
		user1.add(id1);
		players.add(user1);

		JPanel user2 = new JPanel();
		JLabel profile2 = new JLabel(img, SwingConstants.LEFT);
		user2.setBackground(Color.WHITE);
		user2.setBounds(300, 50, 200, 100);
		user2.add(profile2);
		user2.add(id2);
		id2.setText(ch2);
		id2.setFont(fontf);
		id2.setBorder(null);
		players.add(user2);

		Panel card = new Panel();
		card.setLayout(new CardLayout());

		JPanel cardselect = new JPanel();
		cardselect.setBackground(Color.white);
		cardselect.setLayout(null);
		cardselect.setBounds(0, 200, 535, 800);
		players.add(cardselect);

		cardkind.setBounds(30, 20, 300, 300);
		cardkind.setContentAreaFilled(false);
		cardkind.setFocusPainted(false);
		cardselect.add(cardkind);

		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(20f);

		JButton clickroad = new JButton();
		clickroad.addMouseListener(new mouselistener());
		clickroad.setBounds(375, 40, 120, 60);
		clickroad.setText("길의 종류");
		clickroad.setFocusPainted(false);
		clickroad.setBackground(Color.orange);
		clickroad.setFont(font);
		cardselect.add(clickroad);

		JButton direction = new JButton();
		direction.addMouseListener(new mouselistener2());
		direction.setBounds(375, 140, 120, 60);
		direction.setText("길의 방향");
		direction.setFont(font);
		direction.setFocusPainted(false);
		direction.setBackground(Color.orange);
		cardselect.add(direction);

		JButton gg = new JButton();
		gg.setBounds(375, 240, 120, 60);
		gg.setText("불가능");
		gg.setFont(font);
		gg.setFocusPainted(false);
		gg.setBackground(Color.orange);

		gg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (turn == true) {
					us2 += us1;
					us1 = 0;
					no = 1;
					turn = false;

				} else {
					us1 += us2;
					us2 = 0;
					no = 2;
					turn = true;
				}
			}
		});
		cardselect.add(gg);
		Font f = Font.createFont(Font.TRUETYPE_FONT, new File("font/Typo_SsangmunDongB.ttf")).deriveFont(25f);
		roadkind.setText(id1.getText() + "님에게 남은 카드는 " + us1 + "장입니다.");
		roadkind.setFont(f);
		roadkind.setBounds(50, 280, 500, 200);
		cardselect.add(roadkind);

		timerLabel.setBounds(243, 405, 300, 300);
		timerLabel.setFont(f);
		cardselect.add(timerLabel);

		timer.schedule(task, 1000, 1000);

		ImageIcon time = new ImageIcon("img/alarmclock.png");

		Image t = time.getImage();
		Image changed = t.getScaledInstance(270, 270, java.awt.Image.SCALE_SMOOTH);
		ImageIcon timet = new ImageIcon(changed); // Image로 ImageIcon 생성
		JLabel alram = new JLabel(timet);
		alram.setBounds(120, 400, 300, 300);
		cardselect.add(alram);

		JButton exit = new JButton("나가기");
		exit.setBackground(Color.lightGray);
		exit.setBounds(400, 680, 100, 50);
		exit.addActionListener(new exitlistener());
		exit.setFont(font);
		exit.setBackground(Color.orange);
		cardselect.add(exit);

		frame.addWindowListener(new WindowAdapter() { // 내부 무명클래스로서

			@Override

			public void windowClosing(WindowEvent e) { // 이벤트프로그램
				SnailProject.introMusic.close();
				timer.cancel();
				task.cancel();
				System.exit(0);

			}

		});

		frame.pack();
		frame.setVisible(true);
	}

	class T extends TimerTask {
		String num;
		int count = 30, count2 = 50;

		@Override
		public void run() {

			if (no == 1 || no == 2) {
				if (count2 < 1) {
					timer.cancel();
					task.cancel();

					if (turn == true) {
						JOptionPane.showMessageDialog(null, ch2 + "님  WIN!");
						turn = false;
					} else {
						JOptionPane.showMessageDialog(null, ch1 + "님  WIN!");
						turn = true;
					}

					try {
						new Updateresult(turn, ch1, ch2, con);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
				num = Integer.toString(count2);
				timerLabel.setText(num);
				count2--;
			} else {
				if (count < 1) {

					timer.cancel();
					task.cancel();
					timer = new Timer();
					task = new T();
					timer.schedule(task, 0, 1000);

					if (turn == true) {
						roadkind.setText(id1.getText() + "님에게 남은 카드는 " + us1 + "장입니다.");
						us1 = -1;
						turn = false;
					} else {
						roadkind.setText(id2.getText() + "님에게 남은 카드는 " + us2 + "장입니다.");
						turn = true;
						us2--;
					}
				}
				System.out.println(count);
				num = Integer.toString(count);
				timerLabel.setText(num);
				count--;
			}

			if (end == true) {
				timer.cancel();
				task.cancel();
			}

		}

	}

	class AreaButtonListener implements MouseListener {

		JButton areaButton[][] = new JButton[10][10];
		int i, j;

		public AreaButtonListener(int i, int j, JButton areaButton) {
			this.i = i;
			this.j = j;
			this.areaButton[this.i][this.j] = new JButton();
			this.areaButton[this.i][this.j] = areaButton;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (no == 1) {
				turn = false;
			} else if (no == 2) {
				turn = true;
			}
			try {
				new CheckBlock(ran1, ran2, item1, item2, i, j, cardkind.getIcon(), turn, ch1, ch2, frame, con);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			if (ok == false) {
				JOptionPane.showMessageDialog(null, "카드를 놓을 수 없는 위치입니다.");
				return;
			}

			if (turn == true) {
				roadkind.setText(id1.getText() + "님에게 남은 카드는 " + us1 + "장입니다.");
				us1--;
				turn = false;
			} else {

				roadkind.setText(id2.getText() + "님에게 남은 카드는 " + us2 + "장입니다.");
				us2--;
				turn = true;
			}

			Icon icon = cardkind.getIcon();
			java.awt.Image img = ((ImageIcon) icon).getImage();
			java.awt.Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newimg);
			areaButton[i][j].setIcon(icon);
			click = new Music("Pen_Clicking.mp3", true);
			click.start();


			if (no == 1 || no == 2) {
				return;
			}

			timer.cancel();
			task.cancel();
			timer = new Timer();
			task = new T();
			timer.schedule(task, 0, 1000);

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

	}// AreaButtonListener

	class mouselistener implements MouseListener {
		boolean count = true;

		public void mouseClicked(MouseEvent e) {

			if (count == true) {
				cardkind.setIcon(ccard);
				count = false;
			} else {
				cardkind.setIcon(scard);
				count = true;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	class mouselistener2 implements MouseListener {
		int count = 0, count_c = 0;

		public void mouseClicked(MouseEvent e) {
			count++;
			count_c++;
			if (count_c > 3)
				count_c = 0;
			if (cardkind.getIcon() == scard || cardkind.getIcon() == scard_tb) {
				if (count % 2 == 0)
					cardkind.setIcon(scard);
				else
					cardkind.setIcon(scard_tb);
			} else {
				switch (count_c) {
				case 0:
					cardkind.setIcon(ccard);
					break;
				case 1:
					cardkind.setIcon(ccard_right);
					break;
				case 2:
					cardkind.setIcon(ccard_bottom);
					break;
				case 3:
					cardkind.setIcon(ccard_dr);
					break;
				}

			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	class exitlistener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			timer.cancel();
			task.cancel();
			SnailProject win = new SnailProject();

			try {
				win.back = new Background(win);
				win.add(win.back);
				win.start = new StartGame(win);
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
			} catch (ClassNotFoundException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}

	class USER {
		public void test() {
			System.out.println("안녕");
		}
	}
}

public class GameRoom {

	public static void main(String[] args) throws FontFormatException, IOException {
		try {
			new GAME("wander", null);
		} catch (ClassNotFoundException | SQLException e) {
			// // TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
	}
}
