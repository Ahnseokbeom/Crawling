import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SingleBojSolve {
	public static void main(String[] args) throws IOException{
		/*
		사용법(숫자는 줄 수를 의미함)
		21 : id = 유저 아이디 입력
		그리고 command+F11로 실행
		*/ 
		String sql;
		// 유저 ID
		String id = "asb0313";
		// 문제 번호
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
					for(int i = 1;i<=Integer.parseInt(str[str.length-1]);i++) {
						//https://www.acmicpc.net/problemset?user=eoehd1ek&user_solved=1
						Document pro = Jsoup.connect("https://www.acmicpc.net/problemset?user="+id+"&user_solved=1&page="+i).get();
						Elements Userpro = pro.select("div.table-responsive td.list_problem_id");
						String[] proStr = Userpro.text().split(" ");
						System.out.println(Arrays.toString(proStr));
						for(int j = 0;j<proStr.length;j++) {
							num = Integer.parseInt(proStr[j]);
							System.out.println(" num : "+num+" page : "+i);
							try {
								sql = "insert into solve(USER_ID, PROBLEM_ID) values(?, ?)";
								PreparedStatement pst = con.prepareStatement(sql);
								pst.setString(1, id);
								pst.setInt(2, num);
								pst.execute();
								pst.close();
								}catch(Exception e) {
									continue;
							}
						}
					}
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
}
