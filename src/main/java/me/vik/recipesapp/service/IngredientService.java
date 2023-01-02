package me.vik.recipesapp.service;

import me.vik.recipesapp.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IngredientService {

    int addIngredient(Ingredient ingredient);

    Ingredient getIngredientId(int id);

    Ingredient editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);

    Map<Integer, Ingredient> getAllIngredient();
}