package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

@Service
public interface IngredientFilesService {

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();
}
