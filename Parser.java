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
                String kcalString = recipeElement.select(".gz-single-data-recipe").text(); // Sostituisci con il selettore corretto
                calorie = Integer.parseInt(kcalString.split(" ")[kcalString.split(" ").length-1]);


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
