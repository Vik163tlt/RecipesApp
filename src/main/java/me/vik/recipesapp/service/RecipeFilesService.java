package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

@Service
public interface RecipeFilesService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();
}
