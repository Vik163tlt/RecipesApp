package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public interface IngredientFilesService {

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();

    boolean cleanIngredientsFile();

    File getIngredientsFile();

    Path createTempIngredientsFile();
}
