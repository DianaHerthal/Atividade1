package br.edu.utfpr.td.tsi;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.model.Documents;
import br.edu.utfpr.td.tsi.model.Links;
import br.edu.utfpr.td.tsi.model.Meetings;
import br.edu.utfpr.td.tsi.model.Students;
import jakarta.annotation.PostConstruct;

@Component
public class Scraper {
    private Logger logger = LoggerFactory.getLogger(Scraper.class);

    @PostConstruct 
    public void scrapStudents() throws KeyManagementException, NoSuchAlgorithmException {
        Document doc = null;
        Document advisorDoc = null;
        List<Students> studentsList = new ArrayList<>();
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }
            }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        try { 
            logger.atLevel(Level.INFO).log("Starting scraping from site...");
            doc = Jsoup.connect("https://lds.td.utfpr.edu.br/sistemas/orienta.acoes/publico/orientacoes").sslSocketFactory(sslContext.getSocketFactory()).get();
        } catch (IOException e) {
            logger.atLevel(Level.ERROR).setCause(e).setMessage("Error to connect to site").log();
            throw new RuntimeException("Error to connect to site: " + e.getMessage());
        }
        Elements advisors = doc.select(".form-select-sm option");
        logger.atLevel(Level.INFO).log("Scraping completed, starting data processing...");
        List<String> advisorUserList = new ArrayList<>();
        for (Element advisor : advisors) {
            advisorUserList.add(advisor.attr("value"));
        }
        logger.atLevel(Level.INFO).log("Data "+advisorUserList);
        for (String advisorUser : advisorUserList) {
            if (!advisorUser.isEmpty()) {
                try {
                    advisorDoc = Jsoup.connect("https://lds.td.utfpr.edu.br/sistemas/orienta.acoes/publico/orientacoes?usernameOrientador=" + advisorUser).sslSocketFactory(sslContext.getSocketFactory()).get();
                } catch (IOException e) {
                    logger.atLevel(Level.ERROR).setCause(e).setMessage("Error to connect to site for advisor: " + advisorUser).log();
                }
                Elements students = advisorDoc.select(".card-body");
                for (Element student : students) {
                    if (!student.select("h5.card-title").isEmpty()) {
                        String title = student.select("h5.card-title").text();
                        String name = student.select("div.d-flex span span").first().text();
                        String email = student.select("div.d-flex span span[style*=italic]").text();
                        String course = student.select("p strong:contains(Curso:) + span").text();
                        String advisor = student.select("p strong:contains(Orientador:) + span").text();
                        String startDate = student.select("p strong:contains(Início:) + span").text();
                        String conclusionDate = student.select("p strong:contains(Conclusão:) + span").text();
                        String situationText = student.select("p strong:contains(Situação:) + span").text();
                        int situationId = situationText.equalsIgnoreCase("Em Andamento") ? 1 : 2;
                        String stage = student.select("p strong:contains(Etapa:) + span").text();
                        String observations = student.select("p strong:contains(Observações:) + span").text();
                        List<Meetings> meetings = new ArrayList<>();
                        List<Documents> documents = new ArrayList<>();
                        List<Links> links = new ArrayList<>();

                        // Meetings
                        Elements meetingsElements = student.select("[id^=reunioes] tr"); 
                        for (Element row : meetingsElements) {
                            Elements tdElements = row.select("td");
                            if (tdElements.size() >= 3) {
                                String meetingDate = tdElements.get(0).text();
                                String meetingRegisteredBy = tdElements.get(1).text();
                                String meetingSubject = tdElements.get(2).text();
                                meetings.add(new Meetings(meetingDate, meetingRegisteredBy, meetingSubject));
                            }
                        }

                        // Documents
                        Elements documentElements = student.select("[id^=documentos] tr");
                        for (Element row : documentElements) {
                            Elements tdElements = row.select("td");
                            if (tdElements.size() >= 3) {
                                String documentUrl = tdElements.get(0).select("a").attr("href");
                                String documentName = tdElements.get(0).select("a").text();
                                String documentSentBy = tdElements.get(1).text();
                                String documentDate = tdElements.get(2).text();
                                String documentAccess = tdElements.get(3).text();
                                documents.add(new Documents(documentName, documentUrl, documentSentBy, documentDate, documentAccess));
                            }
                        }

                        // Links
                        Elements linkElements = student.select("[id^=links] tr");
                        for (Element row : linkElements) {
                            Elements tdElements = row.select("td");
                            if (tdElements.size() >= 3) {
                                String linkUrl = tdElements.get(0).select("a").attr("href");
                                String linkName = tdElements.get(0).select("a").text();
                                String linkRegisteredBy = tdElements.get(1).text();
                                String linkDate = tdElements.get(2).text();
                                links.add(new Links(linkName, linkRegisteredBy, linkDate, linkUrl));
                            }
                        }

                        studentsList.add(new Students(name, title, email, course, advisor, startDate, conclusionDate, situationId, stage, observations, meetings, documents, links));
                    }     
                }
            }
        }
        logger.atLevel(Level.INFO).log("Escrita do arquivo JSON concluída.");
        JsonFileRecorder record = new JsonFileRecorder();   
        record.gravarArquivo(studentsList);
    }
}
