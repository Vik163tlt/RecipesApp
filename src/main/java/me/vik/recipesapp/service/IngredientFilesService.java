package me.vik.recipesapp.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface IngredientFilesService {

    boolean saveIngredientsToFile(String json);

    String readIngredientsFromFile();

    File getIngredientsFile();
}
