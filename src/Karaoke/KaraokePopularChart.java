package Karaoke;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Song.MelonEtcMusic.MelonCrawlerJazz;

public class KaraokePopularChart {
    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new karaoke());
        executorService.shutdown();
        try {
            // 모든 작업이 완료될 때까지 최대 10분 동안 대기
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class karaoke implements Runnable {
        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect("https://www.tjmedia.com/tjsong/song_monthPopular.asp").get();
                Elements e1 = doc.select("div#BoardType1 td");
                try {
                    java.sql.Statement st = null;
                    Connection con = null;
                    con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false &allowPublicKeyRetrieval=true",
                            "username", "password");
                    st = con.createStatement();
                    st.executeUpdate("use music");
                    for (int i = 0; i < e1.size(); i += 4) {
                        String sql = "insert into song(ranking, song_num, title, artist) values(?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, Integer.parseInt(e1.get(i).text()));
                        pst.setString(2, e1.get(i + 1).text());
                        pst.setString(3, e1.get(i + 2).text());
                        pst.setString(4, e1.get(i + 3).text());
                        pst.execute();
                        pst.close();
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
