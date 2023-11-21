package Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MelonETCMusic {
	static String num = "1800";
	static String[] arr = {"뉴에이지","J-POP","월드뮤직","CCM","어린이/태교","종교음악","국악"};
	static int page = 0;
	public static void main(String[] args) {
		
		// OST, Classic, Jazz
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(new MelonCrawlerOST());
		executorService.submit(new MelonCrawlerClassic());
		executorService.submit(new MelonCrawlerJazz());
		
		executorService.shutdown();
        try {
            // 모든 작업이 완료될 때까지 최대 10분 동안 대기
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		for(int i = 0;i<arr.length;i++) {
        	System.out.println(i+1);
        	executorService = Executors.newFixedThreadPool(1);
            executorService.submit(new MelonCrawlerETC());

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
	// OST
	public static class MelonCrawlerOST implements Runnable {
	    @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/genre/song_list.htm?gnrCode=GN1500").get();
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
                    sql = "insert into etcmusic values(?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, idx);
                    pst.setString(2, e.attr("src"));
                    pst.setString(3, title.get(i).text());
                    pst.setString(4, artist.get(i).text());
                    pst.setString(5, album.get(i).text());
                    pst.setString(6, "OST");
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
	// 클래식
	public static class MelonCrawlerClassic implements Runnable {
	    @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/genre/classic_list.htm?gnrCode=GN1600").get();
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
                    sql = "insert into etcmusic values(?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, idx);
                    pst.setString(2, e.attr("src"));
                    pst.setString(3, title.get(i).text());
                    pst.setString(4, artist.get(i).text());
                    pst.setString(5, album.get(i).text());
                    pst.setString(6, "클래식");
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
	// 재즈
	public static class MelonCrawlerJazz implements Runnable {
	    @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/genre/jazz_list.htm?gnrCode=GN1700").get();
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
                    sql = "insert into etcmusic values(?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, idx);
                    pst.setString(2, e.attr("src"));
                    pst.setString(3, title.get(i).text());
                    pst.setString(4, artist.get(i).text());
                    pst.setString(5, album.get(i).text());
                    pst.setString(6, "재즈");
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
	// 그 외 모든 종류
	public static class MelonCrawlerETC implements Runnable {
	    @Override
	    public void run() {
	        try {
	            Document doc = Jsoup.connect("https://www.melon.com/genre/classic_list.htm?gnrCode=GN1600").get();
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
                    sql = "insert into etcmusic values(?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, idx);
                    pst.setString(2, e.attr("src"));
                    pst.setString(3, title.get(i).text());
                    pst.setString(4, artist.get(i).text());
                    pst.setString(5, album.get(i).text());
                    pst.setString(6, arr[page]);
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
}
