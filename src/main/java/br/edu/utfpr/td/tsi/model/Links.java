package br.edu.utfpr.td.tsi.model;

public class Links {
    private String description;
    private String registeredBy;
    private String date;
    private String url;

    public Links(String description, String registeredBy, String date, String url) {
        this.description = description;
        this.registeredBy = registeredBy;
        this.date = date;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
