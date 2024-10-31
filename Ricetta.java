package giallo.zafferano;

public class Ricetta {
    private String titolo;
    private String url;
    private int calorie;
    private String linkImmagine;

    // Costruttore principale
    public Ricetta(String titolo, String url, int calorie, String linkImmagine) {
        this.titolo = titolo;
        this.url = url;
        this.calorie = calorie;
        this.linkImmagine = linkImmagine;
    }

    // Costruttore alternativo senza link immagine
    public Ricetta(String titolo, String url, int calorie) {
        this(titolo, url, calorie, null);
    }

    // Getter e Setter
    public String getTitolo() {
        return titolo;
    }

    public String getUrl() {
        return url;
    }

    public int getCalorie() {
        return calorie;
    }

    public String getLinkImmagine() {
        return linkImmagine;
    }

    // Metodo per stampare una descrizione della ricetta
    @Override
    public String toString() {
        return "Ricetta{" +
                "titolo='" + titolo + '\'' +
                ", url='" + url + '\'' +
                ", calorie=" + calorie +
                ", linkImmagine='" + (linkImmagine != null ? linkImmagine : "Nessuna immagine") + '\'' +
                '}';
    }
}

