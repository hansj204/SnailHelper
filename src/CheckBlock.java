import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CheckBlock {
	static int pre_x = 0, pre_y = 0, count = 0;
	static Icon pre_img;
	Connection con;

	public CheckBlock(int ran1, int ran2, int item1, int item2, int i, int j, Icon img, boolean turn, String id,
			String id2, JFrame frame, Connection connection) throws ClassNotFoundException, SQLException {
		int Q = 0, P = 0;
		if (count == 0) {
			pre_x = ran1;
			pre_y = ran2;
			count = 1;
		}
		con = connection;
//		System.out.println(pre_x);
//		System.out.println(pre_y);

		if (i == ran1 && i == ran2 || i == item1 && j == item2) {
			pre_img = GAME.start;
		}

		if (pre_x != ran1 && pre_y != ran2) {
			if (item1 == i && item2 == j - 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard) {
					pre_x = i;
				pre_y = j - 1;
				pre_img = GAME.start;
				Q = 2;
				}
			} else if (item1 == i && item2 == j + 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard) {
					pre_x = i;
				pre_y = j + 1;
				Q = 2;
				}
			} else if (item1 == i - 1 && item2 == j) {
				if (img == GAME.ccard_right || img == GAME.ccard || img == GAME.scard_tb) {
					pre_x = i - 1;
				pre_y = j;
				Q = 2;
				}
			} else if (item1 == i + 1 && item2 == j) {
				if (img == GAME.ccard_dr || img == GAME.ccard_bottom || img == GAME.scard_tb) {
					pre_x = i + 1;
				pre_y = j;
				Q = 2;
			}
		}else {
			Q = 1;
		}
		}

		if (pre_x == ran1 && pre_y == ran2 || pre_x == item1 && pre_y == item2) {
			if (i == pre_x && j == pre_y + 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard)
					Q = 1;
			} else if (i == pre_x && j == pre_y - 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard|| img == GAME.scard)
					Q = 1;

			} else if (i == pre_x + 1 && j == pre_y) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard_tb)
					Q = 1;
			} else if (i == pre_x - 1 && j == pre_y) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard_tb)
					Q = 1;
			}
		} // 출발지에서 시작할 때
			//// -----------------------------------------------------------------------------------------------------
		if (pre_img == GAME.scard) {
			if (i == pre_x && j == pre_y + 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard) {
					Q = 1;
				}
			} else if (i == pre_x && j == pre_y - 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard) {
					Q = 1;
				}
			}
		} // scard
		else if (pre_img == GAME.scard_tb) {
			if (i == pre_x + 1 && j == pre_y) {
				if (img == GAME.ccard_dr || img == GAME.ccard_bottom || img == GAME.scard_tb) {
					Q = 1;
				}
			} else if (i == pre_x - 1 && j == pre_y) {
				if (img == GAME.ccard_right || img == GAME.ccard || img == GAME.scard_tb) {
					Q = 1;
				}
			}
		} // scard_tb
		else if (pre_img == GAME.ccard_dr) {
			if (i == pre_x && j == pre_y - 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard) {
					Q = 1;
				}
			} else if (i == pre_x - 1 && j == pre_y) {
				if (img == GAME.ccard_right || img == GAME.ccard || img == GAME.scard_tb) {
					Q = 1;
				}
			}
		} // ccard_dr
		else if (pre_img == GAME.ccard_bottom) {
			if (i == pre_x && j == pre_y + 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard) {
					Q = 1;
				}
			} else if (i == pre_x - 1 && j == pre_y) {
				if (img == GAME.ccard_right || img == GAME.ccard || img == GAME.scard_tb) {
					Q = 1;
				}
			}
		} // ccard_bottom
		else if (pre_img == GAME.ccard) {
			if (i == pre_x && j == pre_y + 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard) {
					Q = 1;
				}
			} else if (i == pre_x + 1 && j == pre_y) {
				if (img == GAME.ccard_dr || img == GAME.ccard_bottom || img == GAME.scard_tb) {
					Q = 1;
				}
			}
		} // ccard
		else if (pre_img == GAME.ccard_right) {
			if (i == pre_x && j == pre_y - 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard) {
					Q = 1;
				}
			} else if (i == pre_x + 1 && j == pre_y) {
				if (img == GAME.ccard_dr || img == GAME.ccard_bottom || img == GAME.scard_tb) {
					Q = 1;
				}
			}
		} // ccard_bottom

		if (Q == 1) {
			pre_x = i;
			pre_y = j;
			pre_img = img;
			GAME.ok = true;

			if (ran2 == pre_x && ran1 == pre_y + 1) {
				if (img == GAME.ccard_bottom || img == GAME.ccard || img == GAME.scard)
					P = 1;
			} else if (ran2 == pre_x && ran1 == pre_y - 1) {
				if (img == GAME.ccard_dr || img == GAME.ccard_right || img == GAME.scard)
					P = 1;
			} else if (ran2 == pre_x + 1 && ran1 == pre_y) {
				if (img == GAME.ccard_dr || img == GAME.ccard_bottom || img == GAME.scard_tb)
					P = 1;
			} else if (ran2 == pre_x - 1 && ran1 == pre_y) {
				if (img == GAME.ccard_right || img == GAME.ccard || img == GAME.scard_tb)
					P = 1;
			}
			if (P == 1) {
             	    new Updateresult(turn , id, id2, con);
				if (turn == true) {
					JOptionPane.showMessageDialog(null, id + "님  WIN!"); 
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null, id2 + "님 !WIN!"); 
					System.exit(0);}
				SnailProject.introMusic.close();
			}

		} // 위치선정이 맞으면 ok!
		else if (Q == 2) {
			pre_img = img;
			GAME.ok = true;
		} else {
			GAME.ok = false;
		}
	}
}


	