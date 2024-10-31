package giallo.zafferano;

import java.util.ArrayList;

class RecipeLowCalorieSelector extends RecipeSelector {
    @Override
    public Ricetta selectRecipe(ArrayList<Ricetta> ricette) {
        if (ricette == null || ricette.isEmpty()) {
            return null; // Restituisce null se la lista è vuota
        }

        // Trova la ricetta con il minor numero di calorie
        Ricetta lowCalorieRecipe = ricette.get(0);
        for (Ricetta ricetta : ricette) {
            if (ricetta.getCalorie() < lowCalorieRecipe.getCalorie()) {
                lowCalorieRecipe = ricetta;
            }
        }
        return lowCalorieRecipe;
    }
}