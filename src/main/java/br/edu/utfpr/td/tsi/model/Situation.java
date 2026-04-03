package br.edu.utfpr.td.tsi.model;

public enum Situation {
    EM_ANDAMENTO(1, "Em Andamento"),
    CONCLUIDO(2, "Concluído");

    private int id;
    private String description;

    Situation(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public static Situation getById(int id) {
        for (Situation situation : Situation.values()) {
            if (situation.getId() == id) {
                return situation;
            }
        }
        return null; // ou lançar uma exceção, dependendo do caso
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
