import org.jsoup.Jsoup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // CNJ (0018903-25.2016.8.19.0000 , 0036576-94.2017.8.19.0000, 0348331-73.2016.8.19.0001 )

        String consulta = "http://www4.tjrj.jus.br/numeracaoUnica/faces/index.jsp?numProcesso=0348331-73.2016.8.19.0001";
        //boolean url = Boolean.getBoolean(consulta);
        //url = true;
        // http://www4.tjrj.jus.br/ejud/ConsultaProcesso.aspx?N=201707601084

        try {
            org.jsoup.nodes.Document docURL = Jsoup.connect(consulta).get();
            org.jsoup.nodes.Element elemento = docURL.selectFirst("#NumProc");

            RecuperaUrlPost recoverUrlPost = new RecuperaUrlPost();
            recoverUrlPost.sendPost(elemento.attr("value"));

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {

            File file = new File("index.html");
            URL url = new URL(consulta);
            new LoadPage().getPage(url, file);

            /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document index = docBuilder.parse(new File("index.html"));*/

            /*FileReader arq;
            BufferedReader lerArq;
            String linha;

            if (file != null) {
                arq = new FileReader(file);
                lerArq = new BufferedReader(arq);
                linha = lerArq.readLine();         //lê primeira linha

                while (linha != null) {
                    //System.out.println(linha);

                    linha = lerArq.readLine();     //lê da segunda linha pra frente
                }

                arq.close();*/
                /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbf.newDocumentBuilder();
                Document index = docBuilder.parse(new File("index.html"));
            Element element = (Element) index.getDocumentElement();
            Element link = (Element) element.getElementsByTagName("<link>").item(0);
                Element tag = (Element) element.getElementsByTagName("<link></link>").item(0);
            Node tagLink = (Node) link.replaceChild(link, tag);*/

            /*FileReader ler = new FileReader("index.html");
            BufferedReader leitor = new BufferedReader(ler);
            String linha;
            String linhaReescrita;
            while((linha = leitor.readLine())!= null) {
                System.out.println(linha);
                linhaReescrita = linha.replaceAll(linha, "Novo texto");
                System.out.println("\n" + linhaReescrita);
            }
        }*/
            OutputStream bytes = new FileOutputStream("index.html", true); // passado "true" para gravar no mesmo arquivo
            OutputStreamWriter chars = new OutputStreamWriter(bytes);
            BufferedWriter strings = new BufferedWriter(chars);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document index = docBuilder.parse(new File("index.html"));

            Element element = index.getDocumentElement();
            Element tag = (Element) element.getElementsByTagName("link").item(0);

            strings.write("ALANNE");
            strings.close();


            //replaceLinha(new File("index.html"), "<link>", "<link/>");
                /*Element element = (Element) index.getDocumentElement();
                Element link = (Element) element.getElementsByTagName("<link></link>");

                Node node = link.appendChild(link);
                System.out.println(node);*/
                /*Element form = (Element) index.getElementById("form");
                Element a = (Element) form.getElementsByTagName("a").item(1);
                Element href = (Element) a.getElementsByTagName("href").item(1);
                System.out.println(href.getAttribute("href"));*/
        }
    }
}

//MÉTODO QUE ALTERA LINHA

    /*public static void replaceLinha(File f, String linhaAlterar, String linhaAlterada) {
        File nf = new File("index.html");
        FileWriter fw = null;
        Scanner s = null;
        try {
            fw = new FileWriter(nf);
            s = new Scanner(f);

            while (s.hasNextLine()) {
                String linha = s.nextLine();

                linha = linha.replace(linhaAlterar, linhaAlterada);

                try {
                    fw.write(linha + System.getProperty("line.separator"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        f.delete();
        nf.renameTo(f);
    }
}*/
