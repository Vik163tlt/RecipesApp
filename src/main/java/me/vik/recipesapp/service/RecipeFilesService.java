package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface RecipeFilesService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    File getRecipesFile();
}
