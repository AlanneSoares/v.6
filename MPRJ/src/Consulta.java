import java.net.MalformedURLException;
import java.net.URL;

// CLASSE QUE CHAMA A URL PADRÃO
public class Consulta {
    public static String info(String processoCNJ) throws MalformedURLException {

        URL request = new URL("http://www4.tjrj.jus.br/numeracaoUnica/faces/index.jsp?numProcesso=");
        request.toString();
        return request + processoCNJ;
    }
}


