package me.vik.recipesapp.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vik.recipesapp.model.Recipe;
import me.vik.recipesapp.service.RecipeFilesService;
import me.vik.recipesapp.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
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

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeCatalogue);
            recipeFilesService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readRecipesFromFile(){
        String json = recipeFilesService.readRecipesFromFile();
        try {
            recipeCatalogue = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>(){
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}