package me.vik.recipesapp.service.Impl;

import me.vik.recipesapp.model.Ingredient;
import me.vik.recipesapp.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final Map<Integer, Ingredient> ingredientCatalogue = new HashMap<>();
    private int ingredientId = 0;

    @Override
    public int addIngredient(Ingredient ingredient) {
        ingredientCatalogue.put(ingredientId, ingredient);
        return ingredientId++;
    }

    @Override
    public Ingredient getIngredientId(int id) {
        return ingredientCatalogue.get(id);
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredientCatalogue.containsKey(id)) {
            ingredientCatalogue.put(id, ingredient);
            return ingredient;
        } else {
            return null;
        }
    }
    @Override
    public boolean deleteIngredient(int id) {
        if (ingredientCatalogue.containsKey(id)) {
            ingredientCatalogue.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredient() {
        return ingredientCatalogue;
    }
}