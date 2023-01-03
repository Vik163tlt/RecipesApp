package me.vik.recipesapp.controller;

import me.vik.recipesapp.model.Ingredient;
import me.vik.recipesapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientId(@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredientId(id);
        if (ingredient == null) {
            return ResponseEntity.ok().body("Список пуст");
        }
        return ResponseEntity.ok().body(ingredient);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        int id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int id) {
        boolean deleted = ingredientService.deleteIngredient(id);
        if (deleted) {
            return ResponseEntity.ok().body("Ингридиент №" + id + " удален!");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllIngredient(){
        Map<Integer, Ingredient> ingredients = ingredientService.getAllIngredient();
        return ResponseEntity.ok().body(Objects.requireNonNullElse(ingredients, "Список отсутствует"));
    }
}
