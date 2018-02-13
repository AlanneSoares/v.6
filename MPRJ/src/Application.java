/* MINISTÉRIO PÚBLICO DO ESTADO DO RIO DE JANEIRO - GERÊNCIA DE INFORMAÇÃO
   SISTEMA DE CONSULTA PROCESSO
   BY ALANNE SOARES 13/02/2018 20:58

   v.6.3
*/

import org.jsoup.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;


public class Application {

    // 0018903-25.2016.8.19.0000 , 0036576-94.2017.8.19.0000 , 0348331-73.2016.8.19.0001 , 0011042-43.2016.8.19.0014

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        org.jsoup.nodes.Document jsoup;
        org.jsoup.nodes.Element form;
        org.jsoup.nodes.Element href;

        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document d;

        Element codCNJ;
        Element descrClasse;
        Element orgaoJulgador;

        String numeroTJ;
        String value;
        String consultaProcesso;


        // CHAMANDO PELA CLASSE CONSULTA
        consultaProcesso = Consulta.info("0011042-43.2016.8.19.0014");


        try {

            // CHAMA A URL
            jsoup = Jsoup.connect(consultaProcesso).get();


            // SE ENCONTRAR O ID NUMPROC
            if (jsoup.toString().contains("NumProc")) {

                numeroTJ = jsoup.getElementById("NumProc").attr("value");


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

                form = jsoup.getElementById("form");
                href = form.getElementsByAttribute("href").get(1);

                if (href.toString().contains("CAMARA")) {
                    value = href.attr("href");


                    //CONECTA A URL ENCONTRADA - http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N= E ENCONTRA O VALOR DO CNJ
                    jsoup = Jsoup.connect(value).get();
                    numeroTJ = jsoup.getElementById("NumProc").attr("value");


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
                            "-----------------------------------------------------------------------" +
                                    "\n                              Resultado                              " +
                                    "\n                CNJ: " + codCNJ.getTextContent() +
                                    "\n---------------------------------------------------------------------" +
                                    "\nNúmero TJ: " + numeroTJ +
                                    "\nClasse: " + descrClasse.getTextContent() +
                                    "\nÓrgão Julgador: " + orgaoJulgador.getTextContent()
                    );

                } else {
                    System.out.println("Não encontrado!");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
