import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Updateresult {
	Updateresult(boolean turn, String id1, String id2, Connection con) throws SQLException {
		System.out.println(id1);
		System.out.println(id2);
		Statement ss = con.createStatement();
		ResultSet rs = ss.executeQuery("select * from player;");
		
		while (rs.next()) {
			int win1 = 0, lose1 = 0, total1 = 0;
			int win2 = 0, lose2 = 0, total2 = 0;
			String id = rs.getString("id");
			int win = rs.getInt("win");
			int lose = rs.getInt("lose");
			int total = rs.getInt("total");

			if (id.equals(id1) || id.equals(id2)) {
				if (id.equals(id1)) {
					win1 = win;
					lose1 = lose;
					total1 = total;
				}
				if (id.equals(id2)) {
					win2 = win;
					lose2 = lose;
					total2 = total;	
				}
			}
			if(total1 != 0 && total != 0) {
				if (turn == true) {
					PreparedStatement ps1 = con
							.prepareStatement("update player SET win = ?, total = ? WHERE id = '" + id1 + "';");
					PreparedStatement ps2 = con
							.prepareStatement("update player SET lose = ?, total = ? WHERE id = '" + id2 + "';");
					
					win1+=1;
					lose2+=1;
					total1+=1;
					total2+=1;
					
					ps1.setInt(1, win1);
					ps1.setInt(2, total1);
					ps2.setInt(1, lose2);
					ps2.setInt(2, total2);
					
					int res = ps1.executeUpdate();
					
					// res가 0보다 크다면 UPDATE로 인해 영향 받은 행이 1개 이상 있다는 의미
					if(res > 0) System.out.println(String.format("%d row update success", res));


					ps1.close();
					ps2.close();
				} else {
					PreparedStatement ps3 = con
							.prepareStatement("update player SET lose = ?, total = ? WHERE id = '" + id1 + "';");
					PreparedStatement ps4 = con
							.prepareStatement("update player SET win =  ?, total = ? WHERE id = '" + id2 + "';");

					
					lose1+=1;
					win2+=1;
					total1+=1;
					total2+=1;
					
					ps3.setInt(1, lose1);
					ps3.setInt(2, total1);
					ps4.setInt(1, win2);
					ps4.setInt(2, total2);

					int res = ps3.executeUpdate();
					
					// res가 0보다 크다면 UPDATE로 인해 영향 받은 행이 1개 이상 있다는 의미
					if(res > 0) System.out.println(String.format("%d row update success", res));
					
					ps3.close();
					ps4.close();
			}
			}
		}
		GAME.end = true;
		ss.close();
		rs.close();
		con.close();

	}

}
