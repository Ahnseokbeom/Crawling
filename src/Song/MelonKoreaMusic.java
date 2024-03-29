package Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MelonKoreaMusic {	
	static String num = "100";
	static String[] arr = {"발라드","댄스","랩/힙합","R&B/Soul","인디음악","록/메탈","트로트","포크/블루스"};
	static int page = 0;
	public static void main(String[] args) throws IOException{
		
//		// Top100 / OST
		ExecutorService executorService = Executors.newFixedThreadPool(1);
//		executorService.submit(new MelonCrawlerTop100());
//        // 스레드 풀 종료를 기다림
//        executorService.shutdown();
//        try {
//            // 모든 작업이 완료될 때까지 최대 10분 동안 대기
//            executorService.awaitTermination(10, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // Genre
        for(int i = 0;i<arr.length;i++) {
        	System.out.println(i+1);
    		executorService = Executors.newFixedThreadPool(1);
            executorService.submit(new MelonCrawlerGenre());

            // 스레드 풀 종료를 기다림
            executorService.shutdown();
            try {
                // 모든 작업이 완료될 때까지 최대 10분 동안 대기
                executorService.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            page++;
            int number = Integer.parseInt(num)+100;
            num = String.valueOf(number);
            System.out.println("------------------------------------------------------");
        }
	}
	// melon top 100
	public static class MelonCrawlerTop100 implements Runnable {
	    @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/chart/index.htm").get();
	            Elements img = doc.select("div.wrap img");
	            Elements title = doc.select("div.wrap_song_info div.ellipsis.rank01");
	            Elements artist = doc.select("div.wrap_song_info div.ellipsis.rank02 span.checkEllipsis");
	            Elements album = doc.select("div.wrap_song_info div.ellipsis.rank03");
	            try {
	                java.sql.Statement st = null;
	                Connection con = null;
	                String sql = "";
	                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
	                		"username", "password");
	                st = con.createStatement();
	                st.executeUpdate("use music");

	                int i = 0;
	                int idx = 1;
	                for (Element e : img) {
	                    sql = "insert into top100 values(?,?,?,?,?)";
	                    PreparedStatement pst = con.prepareStatement(sql);
	                    pst.setInt(1, idx);
	                    pst.setString(2, title.get(i).text());
	                    pst.setString(3, e.attr("src"));
	                    pst.setString(4, artist.get(i).text());
	                    pst.setString(5, album.get(i).text());
	                    pst.execute();
	                    pst.close();
	                    i++;
	                    idx++;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	// Melon KoreaMusic 8가지 Genre
	public static class MelonCrawlerGenre implements Runnable {
		 @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/genre/song_list.htm?gnrCode=GN0"+num).get();
	            Elements img = doc.select("div.wrap img");
	            Elements title = doc.select("div.wrap_song_info div.ellipsis.rank01");
	            Elements artist = doc.select("div.wrap_song_info div.ellipsis.rank02 span.checkEllipsis");
	            Elements album = doc.select("div.wrap_song_info div.ellipsis.rank03");
	            int i = 0;
	            int idx = 1;
	            for(Element e : img) {
	            	System.out.println(idx+" "+e.attr("src")+" "+title.get(i).text()+" "+artist.get(i).text()+" "+album.get(i).text());
	            	i++;
	            	idx++;
	            }
	            try {
	                java.sql.Statement st = null;
	                Connection con = null;
	                String sql = "";
	                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
	                		"username", "password");
	                st = con.createStatement();
	                st.executeUpdate("use music");

	                i = 0;
	                idx = 1;
	                for (Element e : img) {
	                    sql = "insert into music(ranking, title, img, artist, album, type_id, genre_id) values(?,?,?,?,?,?,?)";
	                    PreparedStatement pst = con.prepareStatement(sql);
	                    pst.setInt(1, idx);
	                    pst.setString(2, title.get(i).text());
	                    pst.setString(3, e.attr("src"));
	                    pst.setString(4, artist.get(i).text());
	                    pst.setString(5, album.get(i).text());
	                    pst.setInt(6, type(con,"kr"));
	                    pst.setInt(7, genre(con, arr[page]));
	                    pst.execute();
	                    pst.close();
	                    i++;
	                    idx++;
	                }
	                System.out.println(sql);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	// Insert genre_id 
	public static int genre(Connection con, String genre) throws SQLException {
		String sql = "select id from genre where genre = ?";
		ResultSet resultSet = null;
		try (PreparedStatement select = con.prepareStatement(sql)){
			select.setString(1, genre);
			resultSet = select.executeQuery();
			if(resultSet.next()) {
				return resultSet.getInt("id"); 
			}
		}finally {
			if(resultSet!=null) resultSet.close();
		}
		return -1;
	}
	// Insert type_id
		public static int type(Connection con, String type) throws SQLException{
			String sql = "select id from music_type where type = ?";
			ResultSet resultSet = null;
			try (PreparedStatement select = con.prepareStatement(sql)){
				select.setString(1, type);
				resultSet = select.executeQuery();
				if(resultSet.next()) {
					return resultSet.getInt("id"); 
				}
			}finally {
				if(resultSet!=null) resultSet.close();
			}
			return -1;
		}
}
