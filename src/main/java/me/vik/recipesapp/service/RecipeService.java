package me.vik.recipesapp.service;

import me.vik.recipesapp.model.Recipe;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Map;

@Service
public interface RecipeService {

    int addRecipe(Recipe recipe);

    Recipe getRecipeId(int id);

    Recipe editRecipeId(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    Map<Integer, Recipe> getAllRecipe();

    Path saveAllRecipe();

    void saveToFile();
}