import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecuperaUrlPost {

    public static void sendPost(String processoCNJ){

            String request = "http://www4.tjrj.jus.br/ejud/WS/ConsultaEjud.asmx/DadosProcesso_1";
            URL url;
            int qtde = 0;

            try {

                url = new URL(request + "?nAntigo=" + processoCNJ + "&pCPF=");
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setUseCaches(false);

                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer buffer = new StringBuffer();

                OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("dados.xml"));
                for (int c = in.read(); c != -1; c = in.read()) {
                    qtde++;
                    if (qtde > 200000) {
                        break;
                    }
                    buffer.append((char) c);
                    bufferOut.write((char) c);
                }

                bufferOut.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

