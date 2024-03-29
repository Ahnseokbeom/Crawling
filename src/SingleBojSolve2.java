import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SingleBojSolve2 {
	public static void main(String[] args) throws IOException{
		String sql;
		// 유저 ID
		String id = "asb0313";
		// 총 유저 수
		String count = "";
		int skhurank = 45;
		int rankUp = 0;
		HashMap<String, Integer> map = new HashMap<>();
		int num = 0;
			try {
				java.sql.Statement st = null;
				ResultSet rs = null;
				Connection con = null;
				// mysql 연결
				con = DriverManager.getConnection("jdbc:mysql://132.145.93.241:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
						"Project", "testing00");
				st = con.createStatement();
				// database 선택
				Document page = Jsoup.connect("https://www.acmicpc.net/problemset?user="+id+"+&user_solved=1").get();
				Elements Userpage = page.select("div.text-center");
				st.executeUpdate("use swp;");
					// User별 page
					String[] str = Userpage.text().split(" ");
					System.out.println("User : "+id+" page : "+str[str.length-1]);
					rs = st.executeQuery("select count(*) count from user;");
					// 현재 데이터베이스에 들어간 값 출력하기
				while(rs.next()) {
					count = rs.getString("count");
				}
				System.out.println(count);
				rs = st.executeQuery("select * from user where skhurank >= "+skhurank+" order by skhurank");
				String[] userName = new String[Integer.parseInt(count)-skhurank+1];
				int size = 0; 
				while(rs.next()) {
					userName[size] = rs.getString("ID");
					map.put(rs.getString("ID"),rs.getInt("skhurank"));
					size++;
				}
				System.out.println(Arrays.toString(userName));
				
				for(int i = 0;i<userName.length;i++) {
					try {
					sql = "update user set  skhurank = ? where ID = ?";
					PreparedStatement pst = con.prepareStatement(sql);
					rankUp = map.get(userName[i])-1;
					pst.setInt(1,rankUp);
					pst.setString(2, userName[i]);
					pst.execute();
					pst.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
}
