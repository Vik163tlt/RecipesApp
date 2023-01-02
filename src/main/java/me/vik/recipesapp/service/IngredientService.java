package me.vik.recipesapp.service;

import me.vik.recipesapp.model.Ingredient;
import org.springframework.stereotype.Service;

@Service
public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredientId(int id);
}