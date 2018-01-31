import org.jsoup.Jsoup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Application {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // CNJ (0018903-25.2016.8.19.0000, 0036576-94.2017.8.19.0000, 0348331-73.2016.8.19.0001)

        String consulta = "http://www4.tjrj.jus.br/numeracaoUnica/faces/index.jsp?numProcesso=0018903-25.2016.8.19.0000";
        // http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N=201707601084


        org.jsoup.nodes.Document docURL = Jsoup.connect(consulta).get();
        org.jsoup.nodes.Element elemento = docURL.selectFirst("#NumProc");

        try {

            RecuperaUrlPost recoverUrlPost = new RecuperaUrlPost();
            recoverUrlPost.sendPost(elemento.attr("value"));


            if (recoverUrlPost != null) {

                URL url = null;
                File file = new File("index.html");

                url = new URL(consulta);
                new LoadPage().getPage(url, file);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(false);
                DocumentBuilder docBuilder = dbf.newDocumentBuilder();
                Document index = docBuilder.parse(new File("index.html"));

                Element form = (Element) index.getElementById("form");
                Element a = (Element) index.getElementsByTagName("a").item(1);


                System.out.println(a.getAttribute("href").indexOf(1));
            }

            else {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(false);
                DocumentBuilder docBuilder = dbf.newDocumentBuilder();
                Document doc = docBuilder.parse(new File("dados.xml"));

                Element codCNJ = (Element) doc.getElementsByTagName("CodCNJ").item(0);
                Element codProcLink = (Element) doc.getElementsByTagName("CodProcLink").item(0);
                Element descrClasse = (Element) doc.getElementsByTagName("DescrClasse").item(0);
                Element orgaoJulgador = (Element) doc.getElementsByTagName("OrgaoJulgador").item(0);

                System.out.println(
                        "---------------------------------------------------------------------" +
                                "\n                             Resultado" +
                                "\n---------------------------------------------------------------------" +
                                "\nProcesso: " + codCNJ.getTextContent() +
                                "\nNúmero TJ: " + codProcLink.getTextContent() +
                                "\nClasse: " + descrClasse.getTextContent() +
                                "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
                );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }



    }
}


