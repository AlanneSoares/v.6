import java.io.*;
import java.net.*;

public class LoadPage {

    public void getPage(URL url, File file) throws IOException {
        //File file = new File("dados.xml");
        //URL url = new URL("");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {

//            Imprime página no console
            //System.out.println(inputLine);

//            Grava pagina no arquivo
            out.write(inputLine);
            out.newLine();
        }

        in.close();
        out.flush();
        out.close();

    }
}