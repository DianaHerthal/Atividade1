package br.edu.utfpr.td.tsi.model;

public class Meetings {
    private String date;
    private String registeredBy;
    private String subject;

    public Meetings(String date, String registeredBy, String subject) {
        this.date = date;
        this.registeredBy = registeredBy;
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public String getSubject() {
        return subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
