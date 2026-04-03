package br.edu.utfpr.td.tsi.model;

import java.util.List;

public class Students {
    private String title;
    private String name;
    private String email;
    private String course;
    private String advisor;
    private String startDate;
    private String conclusionDate;
    private Situation situation;
    private String stage;
    private String observations;
    private List<Meetings> meetings;
    private List<Documents> documents;
    private List<Links> links;

    public Students(String name, String title, String email, String course, String advisor, String startDate, String conclusionDate, int situation, String stage, String observations, List<Meetings> meetings, List<Documents> documents, List<Links> links) {
        this.name = name;
        this.title = title;
        this.email = email;
        this.course = course;
        this.advisor = advisor;
        this.startDate = startDate;
        this.conclusionDate = conclusionDate;
        this.situation = Situation.getById(situation);
        this.stage = stage;
        this.observations = observations;
        this.meetings = meetings;
        this.documents = documents;
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return course;
    }

    public String getEmail() {
        return email;
    }

    public String getAdvisor() {
        return advisor;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getConclusionDate() {
        return conclusionDate;
    }

    public Situation getSituation() {
        return situation;
    }

    public String getStage() {
        return stage;
    }

    public String getObservations() {
        return observations;
    }

    public List<Meetings> getMeetings() {
        return meetings;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setConclusionDate(String conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setMeetings(List<Meetings> meetings) {
        this.meetings = meetings;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }
}
