package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public interface RecipeFilesService {

    boolean saveRecipesToFile(String json);

    String readRecipesFromFile();

    boolean cleanRecipesFile();

    File getRecipesFile();

    Path createTempFile();
}
