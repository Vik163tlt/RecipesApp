package me.vik.recipesapp.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vik.recipesapp.Exception.ReadingFileException;
import me.vik.recipesapp.Exception.WritingFileException;
import me.vik.recipesapp.model.Ingredient;
import me.vik.recipesapp.model.Recipe;
import me.vik.recipesapp.service.RecipeFilesService;
import me.vik.recipesapp.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private int recipeId = 0;

    private Map<Integer, Recipe> recipeCatalogue = new HashMap<>();

    private final RecipeFilesService recipeFilesService;

    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }
    @PostConstruct
    private void init() {
        readRecipesFromFile();
    }

    @Override
    public int addRecipe(@RequestBody Recipe recipe) {
        recipeCatalogue.put(recipeId,recipe);
        saveToFile();
        return recipeId++;
    }

    @Override
    public Recipe getRecipeId(int id) {
        return recipeCatalogue.get(id);
    }

    @Override
    public Recipe editRecipeId(int id, Recipe recipe) {
        if (recipeCatalogue.containsKey(id)) {
            recipeCatalogue.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeCatalogue.containsKey(id)) {
            recipeCatalogue.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() {
        return recipeCatalogue;
    }

    @Override
    public Path saveAllRecipe() {
        Path path = recipeFilesService.createTempFile();
        for (Recipe recipe : recipeCatalogue.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getRecipeName() +
                        "\nВремя приготовления: " + recipe.getTimeOfCooking() + " минут." +
                        "\nИнгридиенты:\n");
                for (Ingredient ingredient : recipe.getIngredientList()) {
                    writer.append(ingredient.getIngredientName()
                            + " - " + ingredient.getNumber() + " " + ingredient.getMeasureUnit() + "\n");
                }
                int i = 1;
                for (String step : recipe.getSteps()) {
                    writer.append(i + ". " + step + "\n");
                    i++;
                }
            } catch (IOException e) {
                throw new ReadingFileException("Ошибка чтения файла");
            }
        }
        return path;
    }

    @Override
    public void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeCatalogue);
            recipeFilesService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            throw new WritingFileException("Ошибка чтения файла");
        }
    }

    private void readRecipesFromFile(){
        String json = recipeFilesService.readRecipesFromFile();
        try {
            recipeCatalogue = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>(){
            });
        } catch (JsonProcessingException e) {
            throw new ReadingFileException("Ошибка записи файла");
        }
    }

}