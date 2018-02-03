/* MINISTÉRIO PÚBLICO DO ESTADO DO RIO DE JANEIRO - GERÊNCIA DE INFORMAÇÃO
   SISTEMA DE CONSULTA PROCESSO
   BY ALANNE SOARES 03/02/2018 17:11*/

import org.jsoup.*;
import org.jsoup.select.Elements;
import org.w3c.dom.*; import org.xml.sax.*; import javax.xml.parsers.*; import java.io.*; import java.net.*;

public class Application {

    // 0018903-25.2016.8.19.0000 , 0036576-94.2017.8.19.0000 , 0348331-73.2016.8.19.0001 , 0011042-43.2016.8.19.0014

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // CHAMANDO PELA CLASSE CONSULTA
        String consultaPorNumero = Consulta.info("0348331-73.2016.8.19.0001");

        // CASO PROCESSO DE 1ª INSTÂNCIA
        try {

            // CHAMAR URL E GERAR ARQUIVO
            org.jsoup.nodes.Document docURL = Jsoup.connect(consultaPorNumero).get();
            org.jsoup.nodes.Element element = docURL.selectFirst("#NumProc");
            RecuperaUrlPost.sendPost(element.attr("value"));

            // CHAMAR ARQUIVO GERADO
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("dados.xml"));

            // LER ELEMENTOS DESEJADOS
            Element codCNJ = (Element) doc.getElementsByTagName("CodCNJ").item(0);
            Element codProcLink = (Element) doc.getElementsByTagName("CodProcLink").item(0);
            Element descrClasse = (Element) doc.getElementsByTagName("DescrClasse").item(0);
            Element orgaoJulgador = (Element) doc.getElementsByTagName("OrgaoJulgador").item(0);

            // IMPRIMIR NO CONSOLE
            System.out.println(
                    "-----------------------------------------------------------------------" +
                            "\n                             Resultado" +
                            "\n                Processo: " + codCNJ.getTextContent() +
                            "\n-----------------------------------------------------------------------" +
                            "\nNúmero TJ: " + codProcLink.getTextContent().replace(".","") +
                            "\nClasse: " + descrClasse.getTextContent() +
                            "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
            );
        }

        //CASO PROCESSO DE 2ª INSTÂNCIA
        catch (Exception e) {

            // CHAMAR URL
            URL url = new URL(consultaPorNumero);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((in.readLine()) != null);
            in.close();

            // PESQUISAR URL DESEJADA NA TAG DO FORM
            org.jsoup.nodes.Document docIndex = Jsoup.connect(url.toString()).get(); //conectar o document pela url
            org.jsoup.nodes.Element element = docIndex.getElementById("form"); //pegar o elemento da tag pelo ID do formulário
            Elements a = element.getElementsByAttribute("href");
            org.jsoup.nodes.Element href = a.last();
            String value = href.getElementsByAttribute("href").attr("href"); //valor do atributo

            // PESQUISAR VALOR http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= DO ATRIBUTO HREF
            URL consultaProcesso = new URL(value);
            BufferedReader br = new BufferedReader(new InputStreamReader(consultaProcesso.openStream()));
            while ((br.readLine()) != null);
            in.close();

            //ENTRAR NA URL ENCONTRADA - http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= E GERAR ARQUIVO
            org.jsoup.nodes.Document jsoup = Jsoup.connect(consultaProcesso.toString()).get();
            org.jsoup.nodes.Element id = jsoup.getElementById("#NumProc");
            String valueID = id.getElementsByAttribute("value").attr("value");

            // LER ARQUIVO GERADO
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document d = dB.parse(new File("dados.xml"));

            // LER ELEMENTOS DESEJADOS
            Element codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
            Element descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
            Element orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);

            // IMPRIMIR NO CONSOLE
            System.out.println(
                    "-----------------------------------------------------------------------" +
                    "\n                             Resultado" +
                    "\n                Processo: " + codCNJ.getTextContent() +
                    "\n-----------------------------------------------------------------------" +
                    "\nNúmero TJ: " + valueID +
                    "\nClasse: " + descrClasse.getTextContent() +
                    "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
            );
        }
    }
}