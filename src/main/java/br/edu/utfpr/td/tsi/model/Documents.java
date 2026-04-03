package br.edu.utfpr.td.tsi.model;

public class Documents {
    private String document;
    private String url;
    private String sentBy;
    private String data;
    private String access;

    public Documents(String document, String url, String sentBy, String data, String access) {
        this.document = document;
        this.url = "https://lds.td.utfpr.edu.br" + url;
        this.sentBy = sentBy;
        this.data = data;
        this.access = access;
    }

    public String getDocument() {
        return document;
    }

    public String getUrl() {
        return url;
    }

    public String getSentBy() {
        return sentBy;
    }

    public String getAccess() {
        return access;
    }

    public String getData() {
        return data;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setUrl(String url) {
        this.url = "https://lds.td.utfpr.edu.br" + url;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setData(String data) {
        this.data = data;
    }
}