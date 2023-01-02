package me.vik.recipesapp.service;

import me.vik.recipesapp.model.Recipe;
import org.springframework.stereotype.Service;

@Service
public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipeId(int id);
}