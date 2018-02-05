import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// CLASSE QUE CHAMA PELO HEADER
public class RecuperaUrlPost {

    public static void sendPost() throws MalformedURLException {

        String consultaProcesso = Consulta.info("0348331-73.2016.8.19.0001");
        int n = 0;

        try {
            org.jsoup.nodes.Document docUrl = Jsoup.connect(consultaProcesso).get();
            org.jsoup.nodes.Element element = docUrl.getElementById("form");                      //pegar o elemento da tag pelo ID do formulário
            Elements a = element.getElementsByAttribute("href");                               //procurando a tag href do form
            org.jsoup.nodes.Element href = a.last();                                                //buscando o próximo link desejado
            String value = href.getElementsByAttribute("href").attr("href");

            URL buscaNumeroTJ = new URL(value);
            BufferedReader br = new BufferedReader(new InputStreamReader(buscaNumeroTJ.openStream()));
            while ((br.readLine()) != null) ;
            br.close();

            org.jsoup.nodes.Document jsoup = Jsoup.connect(buscaNumeroTJ.toString()).get();
            org.jsoup.nodes.Element id = jsoup.getElementById("NumProc");
            String processoCNJ = id.getElementsByAttribute("value").attr("value");

            // -------------------------- fim de busca cnj -------------------

            URL xml = new URL(Request.r() + "?nAntigo=" + processoCNJ + "&pCPF=");
            BufferedReader b = new BufferedReader(new InputStreamReader(xml.openStream()));
            String i;
            while ((i = b.readLine()) != null) ;
            b.close();
            System.out.println(i);
            /*HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);*/

            /*Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer buffer = new StringBuffer();

            OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("dados.xml"));
            for (int c = in.read(); c != -1; c = in.read()) {
                n++;
                if (n > 200000) {
                    break;
                }
                buffer.append((char) c);
                bufferOut.write((char) c);
            }

            bufferOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}