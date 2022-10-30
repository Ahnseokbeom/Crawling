import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;


public class BOJ {
	public static void main(String[] args) throws IOException{
		String sql;
		String count = "";
		int skhurank = 45;
			try {
				java.sql.Statement st = null;
				ResultSet rs = null;
				Connection con = null;
				// mysql 연결
				con = DriverManager.getConnection("jdbc:mysql://132.145.93.241:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
						"Project", "testing00");
				st = con.createStatement();
				// database 선택
				st.executeUpdate("use swp;");
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
					System.out.println(rs.getString("ID"));
					size++;
				}
				System.out.println(Arrays.toString(userName));
				for(int i = 0;i<userName.length;i++) {
					try {
					sql = "update user set  skhurank = ? where ID = ?";
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setInt(1, ++skhurank);
					System.out.println(skhurank);
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
