package me.vik.recipesapp.service.Impl;

import me.vik.recipesapp.model.Recipe;
import me.vik.recipesapp.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private int recipeId = 0;

    private final Map<Integer, Recipe> recipeCatalogue = new HashMap<>();


    @Override
    public int addRecipe(@RequestBody Recipe recipe) {
        recipeCatalogue.put(recipeId,recipe);
        return recipeId++;
    }

    @Override
    public Recipe getRecipeId(int id) {
        return recipeCatalogue.get(id);
    }

    @Override
    public Recipe editRecipeId(int id, Recipe recipe) {
        if (recipeCatalogue.containsKey(id)) {
            recipeCatalogue.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeCatalogue.containsKey(id)) {
            recipeCatalogue.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() {
        return recipeCatalogue;
    }

}