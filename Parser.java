package giallo.zafferano;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class Parser {
    
    public ArrayList<Ricetta> parseRecipes(String html) {
        ArrayList<Ricetta> ricette = new ArrayList<>();
        
        try {
            Document doc = Jsoup.parse(html);
            
            // Seleziona i contenitori delle ricette nella pagina
            Elements recipeElements = doc.select(".gz-card"); // Sostituire con il selettore CSS specifico della pagina

            for (Element recipeElement : recipeElements) {
                String titolo = recipeElement.select(".gz-title").text(); // Sostituisci con il selettore corretto
                String url = recipeElement.select("a").attr("href");
                String linkImmagine = recipeElement.select("img").attr("src");

                // Alcune ricette potrebbero non avere un valore di calorie
                int calorie = 0; // Default
                Element calorieElement = recipeElement.select(".calorie-info").first(); // Sostituisci con il selettore corretto
                if (calorieElement != null) {
                    try {
                        calorie = Integer.parseInt(calorieElement.text().replaceAll("[^\\d]", ""));
                    } catch (NumberFormatException e) {
                        System.out.println("Calorie non trovate o formato non valido");
                    }
                }

                // Crea un nuovo oggetto Ricetta e aggiungilo alla lista
                Ricetta ricetta = new Ricetta(titolo, url, calorie, linkImmagine);
                ricette.add(ricetta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ricette;
    }
}
