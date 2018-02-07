/* MINISTÉRIO PÚBLICO DO ESTADO DO RIO DE JANEIRO - GERÊNCIA DE INFORMAÇÃO
   SISTEMA DE CONSULTA PROCESSO
   BY ALANNE SOARES 06/02/2018 22:10
*/

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Application {

    // 0018903-25.2016.8.19.0000 , 0036576-94.2017.8.19.0000 , 0348331-73.2016.8.19.0001 , 0011042-43.2016.8.19.0014

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // CHAMANDO PELA CLASSE CONSULTA
        String consultaProcesso = Consulta.info("0018903-25.2016.8.19.0000");

        try {

            // CHAMA A URL
            org.jsoup.nodes.Document doc = Jsoup.connect(consultaProcesso).get();
            org.jsoup.nodes.Element id = doc.getElementById("NumProc");
            String numeroTJ = id.getElementsByAttribute("value").attr("value");


            // GERA O ARQUIVO
            RecuperaUrlPost.sendPost(id.attr("value"));


            // LÊ O ARQUIVO
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document d = docBuilder.parse(new File("dados.xml"));


            // LÊ OS ELEMENTOS DESEJADOS
            Element codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
            Element descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
            Element orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);


            // IMPRIME NO CONSOLE
            System.out.println(
                      "---------------------------------------------------------------------" +
                    "\n                             Resultado                               " +
                    "\n                CNJ: " + codCNJ.getTextContent() +
                    "\n---------------------------------------------------------------------" +
                    "\nNúmero TJ: " + numeroTJ +
                    "\nClasse: " + descrClasse.getTextContent() +
                    "\nÓrgão Julgador: " + orgaoJulgador.getTextContent() +
                    "\n---------------------------------------------------------------------"
            );
        }

        catch (Exception e) {

            // CHAMA A URL
            URL url = new URL(consultaProcesso);


            // PESQUISA A URL DESEJADA PELO VALOR DO HREF DA TAG FORM
            org.jsoup.nodes.Document doc = Jsoup.connect(url.toString()).get();
            org.jsoup.nodes.Element form = doc.getElementById("form");
            org.jsoup.nodes.Element href = form.getElementsByAttribute("href").get(1);
            String value = href.getElementsByAttribute("href").attr("href");


            // CHAMA A URL
            URL buscaNumeroTJ = new URL(value);


            //CONECTA A URL ENCONTRADA - http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= E ENCONTRA O VALOR DO CNJ
            org.jsoup.nodes.Document jsoup = Jsoup.connect(buscaNumeroTJ.toString()).get();
            org.jsoup.nodes.Element id = jsoup.getElementById("NumProc");
            String numeroTJ = id.getElementsByAttribute("value").attr("value");


            // GERA O ARQUIVO
            RecuperaUrlPost.sendPost(id.attr("value"));


            // LÊ O ARQUIVO
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(new File ("dados.xml"));


            // LÊ OS ELEMENTOS DESEJADOS
            Element codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
            Element descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
            Element orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);


            // IMPRIME NO CONSOLE
            System.out.println(
                    "-----------------------------------------------------------------------" +
                    "\n                              Resultado                              " +
                    "\n                CNJ: " + codCNJ.getTextContent() +
                    "\n---------------------------------------------------------------------" +
                    "\nNúmero TJ: " + numeroTJ  +
                    "\nClasse: " + descrClasse.getTextContent() +
                    "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
            );
        }
    }
}