package giallo.zafferano;

import java.util.ArrayList;

abstract class RecipeSelector {
    // Metodo astratto per selezionare una ricetta da una lista
    public abstract Ricetta selectRecipe(ArrayList<Ricetta> ricette);
}
