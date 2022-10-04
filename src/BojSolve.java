import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BojSolve {
	public static void main(String[] args) throws IOException{
		String sql;
		// 유저 ID
		String id = null;
		// 문제 번호
		int num = 0;
		// 성공회대학교 학생목록
		int number = 1;
		// 학생의 인원 수
		int nick = 1;
		int n = 1;
			// 유저마다 페이지 수 계산
			try {
				java.sql.Statement st = null;
				ResultSet rs = null;
				Connection con = null;
				
				// mysql 연결
				con = DriverManager.getConnection("jdbc:mysql://132.145.93.241:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
						"Project", "testing00");
				st = con.createStatement();
				// database 선택
				Document doc = Jsoup.connect("https://www.acmicpc.net/school/ranklist/309/3").get();
				Elements name = doc.select("div.table-responsive td");
				st.executeUpdate("use swp;");
				while(nick <= 61) {
					Document doc2 = Jsoup.connect("https://www.acmicpc.net/problemset?user="+name.get(n).text()+"&user_solved=1").get();
					Elements page = doc2.select("div.text-center");
					// User별 page
					String[] str = page.text().split(" ");
					System.out.println(name.get(n).text()+" "+str[str.length-1]);
					// 0 6
					for(int i = 1;i<=Integer.parseInt(str[str.length-1]);i++) {
						Document doc3 = Jsoup.connect("https://www.acmicpc.net/problemset?user="+name.get(n).text()+"&user_solved="+i).get();
						Elements pro = doc3.select("div.table-responsive td");
						int k = 0;
						for(int j = 0;j<100;j++) {
							id = name.get(n).text();
							try {
							num = Integer.parseInt(pro.get(k).text());
							System.out.println(num);
							k+=6;
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
					n+=6;
				}
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
}
