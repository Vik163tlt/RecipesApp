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
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientCatalogue.put(ingredientId++, ingredient);
    }

    @Override
    public Ingredient getIngredientId(int id) {
        return ingredientCatalogue.get(id);
    }
}