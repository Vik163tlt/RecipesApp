package me.vik.recipesapp.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vik.recipesapp.model.Ingredient;
import me.vik.recipesapp.service.IngredientFilesService;
import me.vik.recipesapp.service.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private Map<Integer, Ingredient> ingredientCatalogue = new HashMap<>();
    private int ingredientId = 0;

    private final IngredientFilesService ingredientFilesService;

    public IngredientServiceImpl(IngredientFilesService ingredientFilesService) {
        this.ingredientFilesService = ingredientFilesService;
    }

    @PostConstruct
    private void init() {
        readIngredientsFromFile();
    }

    @Override
    public int addIngredient(Ingredient ingredient) {
        ingredientCatalogue.put(ingredientId, ingredient);
        saveToFile();
        return ingredientId++;
    }

    @Override
    public Ingredient getIngredientId(int id) {
        return ingredientCatalogue.get(id);
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (ingredientCatalogue.containsKey(id)) {
            ingredientCatalogue.put(id, ingredient);
            saveToFile();
            return ingredient;
        } else {
            return null;
        }
    }
    @Override
    public boolean deleteIngredient(int id) {
        if (ingredientCatalogue.containsKey(id)) {
            ingredientCatalogue.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredient() {
        return ingredientCatalogue;
    }

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientCatalogue);
            ingredientFilesService.saveIngredientsToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка записи файла");
        }
    }

    private void readIngredientsFromFile(){
        String json = ingredientFilesService.readIngredientsFromFile();
        try {
            ingredientCatalogue = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }
}