/* MINISTÉRIO PÚBLICO DO ESTADO DO RIO DE JANEIRO - GERÊNCIA DE INFORMAÇÃO
   SISTEMA DE CONSULTA PROCESSO
   BY ALANNE SOARES 03/02/2018 17:11*/

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Application {

    // 0018903-25.2016.8.19.0000 , 0036576-94.2017.8.19.0000 , 0348331-73.2016.8.19.0001 , 0011042-43.2016.8.19.0014

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // CHAMANDO PELA CLASSE CONSULTA
        String consultaProcesso = Consulta.info("0348331-73.2016.8.19.0001");

        try {

            // CHAMAR URL E GERAR ARQUIVO
            org.jsoup.nodes.Document docUrl = Jsoup.connect(consultaProcesso).get();
            org.jsoup.nodes.Element element = docUrl.getElementById("NumProc");
            //RecuperaUrlPost.sendPost(element.attr("value"));

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
                            "\nNúmero TJ: " + codProcLink.getTextContent().replace(".", "") +
                            "\nClasse: " + descrClasse.getTextContent() +
                            "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
            );
        }


        catch (Exception e) {

            // CHAMAR URL
            /*URL url = new URL(consultaProcesso);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((in.readLine()) != null) ;
            in.close();*/


            // PESQUISAR URL DESEJADA NA TAG DO FORM
            /*org.jsoup.nodes.Document docIndex = Jsoup.connect(url.toString()).get();                //conectar o document pela url
            org.jsoup.nodes.Element element = docIndex.getElementById("form");                      //pegar o elemento da tag pelo ID do formulário
            Elements a = element.getElementsByAttribute("href");                                //procurando a tag href do form
            org.jsoup.nodes.Element href = a.last();                                                //buscando o próximo link desejado
            String valueHref = href.getElementsByAttribute("href").attr("href"); */   //valor do atributo

            // PROCURA URL http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= DO ATRIBUTO HREF
            /*URL buscaNumeroTJ = new URL(valueHref);
            BufferedReader br = new BufferedReader(new InputStreamReader(buscaNumeroTJ.openStream()));
            while ((br.readLine()) != null) ;
            br.close();*/


            //CONECTA A URL ENCONTRADA - http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= E ENCONTRA O VALOR DO CNJ
            /*org.jsoup.nodes.Document jsoup = Jsoup.connect(buscaNumeroTJ.toString()).get();
            org.jsoup.nodes.Element id = jsoup.getElementById("NumProc");
            String valueID = id.getElementsByAttribute("value").attr("value");
            System.out.println(valueID);*/

            /*DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document d = dB.parse(new File ("dados.xml"));
            System.out.println(d);*/
            //RecuperaUrlPost.sendPost(element.attr("value"));

            // ---------- Fim de busca -------------


            // LER ARQUIVO GERADO
            /*DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document d = dB.parse(new File ("dados.xml"));*/


            // LER ELEMENTOS DESEJADOS
            /*Element codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
            Element descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
            Element orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);*/


            // IMPRIMIR NO CONSOLE
            /*System.out.println(
                    "-----------------------------------------------------------------------" +
                    "\n                             Resultado" +
                    "\n                Processo: " + codCNJ.getTextContent() +
                    "\n-----------------------------------------------------------------------" +
                    "\nNúmero TJ: " + codProcLink.getTextContent().replace(".", "")  +
                    "\nClasse: " + descrClasse.getTextContent() +
                    "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
            );*/

            RecuperaUrlPost.sendPost();
        }
    }
}