import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// CLASSE QUE CHAMA PELO HEADER
public class RecuperaUrlPost {

    public static void sendPost(String processoCNJ) throws MalformedURLException {

        try {

            URL url = new URL(Request.r() + "?nAntigo=" + processoCNJ + "&pCPF=");

            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("dados.xml"));

            int n = 0;
            for (int c = in.read(); c != -1; c = in.read()) {
                n++;
                if (n > 200000) {
                    break;
                }
                bufferOut.write((char) c);
            }
            bufferOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}