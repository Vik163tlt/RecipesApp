package me.vik.recipesapp.controller;

import me.vik.recipesapp.model.Recipe;
import me.vik.recipesapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable("id") int id) {
        Recipe recipe = recipeService.getRecipeId(id);
        if (recipe == null) {
            return ResponseEntity.ok().body("Список пуст");
        }
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipeId(id, newRecipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeService.deleteRecipe(id);
        if (deleted) {
            return ResponseEntity.ok().body("Рецепт №" + id + " удален!");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipe() {
        Map<Integer, Recipe> recipes = recipeService.getAllRecipe();
        return ResponseEntity.ok().body(Objects.requireNonNullElse(recipes, "Список отсутствует"));
    }
}