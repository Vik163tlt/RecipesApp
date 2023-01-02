package me.vik.recipesapp.controller;

import me.vik.recipesapp.model.Recipe;
import me.vik.recipesapp.service.RecipeService;
import org.springframework.web.bind.annotation.*;

public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/getRecipe/{id}")
    public Recipe getRecipeId(@PathVariable("id") int id) {
        return recipeService.getRecipeId(id);
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.addRecipe(recipe);
    }
}