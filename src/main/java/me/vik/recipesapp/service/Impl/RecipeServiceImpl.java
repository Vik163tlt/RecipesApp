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
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeCatalogue.put(recipeId++,recipe);
    }

    @Override
    public Recipe getRecipeId(int id) {
        return recipeCatalogue.get(id);
    }
}