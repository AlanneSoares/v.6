/* MINISTÉRIO PÚBLICO DO ESTADO DO RIO DE JANEIRO - GERÊNCIA DE INFORMAÇÃO
   SISTEMA DE CONSULTA PROCESSO
   BY ALANNE SOARES 06/02/2018 22:10
*/

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
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


        org.jsoup.nodes.Document doc = null;
        org.jsoup.nodes.Document jsoup = null;
        org.jsoup.nodes.Element id = null;
        org.jsoup.nodes.Element form = null;
        org.jsoup.nodes.Element href = null;

        String numeroTJ = null;
        String numProc = "NumProc";
        String value = null;
        String consultaProcesso = null;

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document d = null;

        Element codCNJ = null;
        Element descrClasse = null;
        Element orgaoJulgador = null;

        URL url;
        URL buscaNumeroTJ;


        // CHAMANDO PELA CLASSE CONSULTA
        consultaProcesso = Consulta.info("0036576-94.2017.8.19.0000");


        try {

            // CHAMA A URL
            jsoup = Jsoup.connect(consultaProcesso).get();

            // SE ENCONTRAR O ID NUMPROC
            if (jsoup.toString().contains(numProc)) {

                numeroTJ = jsoup.getElementById(numProc).attr("value");


                // GERA O ARQUIVO
                RecuperaUrlPost.sendPost(numeroTJ);


                // LÊ O ARQUIVO
                dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                d = db.parse(new File("dados.xml"));


                // LÊ OS ELEMENTOS DESEJADOS
                codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
                descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
                orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);


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

            } else {

                // PESQUISA A URL DESEJADA PELO VALOR DO HREF DA TAG FORM
                //jsoup = Jsoup.connect(consultaProcesso).get();
                //url = new URL(consultaProcesso);
                //doc = Jsoup.connect(url.toString()).get();
                form = jsoup.getElementById("form");
                href = form.getElementsByAttribute("href").get(1);
                value = href.getElementsByAttribute("href").attr("href");


                // CHAMA A URL
                buscaNumeroTJ = new URL(value);


                //CONECTA A URL ENCONTRADA - http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= E ENCONTRA O VALOR DO CNJ
                jsoup = Jsoup.connect(buscaNumeroTJ.toString()).get();
                id = jsoup.getElementById("NumProc");
                numeroTJ = id.getElementsByAttribute("value").attr("value");


                // GERA O ARQUIVO
                RecuperaUrlPost.sendPost(id.attr("value"));


                // LÊ O ARQUIVO
                dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                d = db.parse(new File("dados.xml"));


                // LÊ OS ELEMENTOS DESEJADOS
                codCNJ = (Element) d.getElementsByTagName("CodCNJ").item(0);
                descrClasse = (Element) d.getElementsByTagName("DescrClasse").item(0);
                orgaoJulgador = (Element) d.getElementsByTagName("OrgaoJulgador").item(0);


                // IMPRIME NO CONSOLE
                System.out.println(
                        "-----------------------------------------------------------------------" +
                                "\n                              Resultado                              " +
                                "\n                CNJ: " + codCNJ.getTextContent() +
                                "\n---------------------------------------------------------------------" +
                                "\nNúmero TJ: " + numeroTJ +
                                "\nClasse: " + descrClasse.getTextContent() +
                                "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
                );
            }

        } catch (Exception e) {
            System.out.println("Erro");

        }
    }
    }
