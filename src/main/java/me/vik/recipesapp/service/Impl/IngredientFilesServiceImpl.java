package me.vik.recipesapp.service.Impl;

import me.vik.recipesapp.Exception.ReadingFileException;
import me.vik.recipesapp.Exception.WritingFileException;
import me.vik.recipesapp.service.IngredientFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class IngredientFilesServiceImpl implements IngredientFilesService {

    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;
    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;

    @Override
    public boolean saveIngredientsToFile(String json){
        try {
            cleanIngredientsFile();
            Files.writeString(Path.of(ingredientsFilePath, ingredientsFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readIngredientsFromFile(){
        try {
            return Files.readString(Path.of(ingredientsFilePath, ingredientsFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadingFileException();
        }
    }

    @Override
    public boolean cleanIngredientsFile(){
        try {
            Files.deleteIfExists(Path.of(ingredientsFilePath, ingredientsFileName));
            Files.createFile(Path.of(ingredientsFilePath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File getIngredientsFile() {
        return new File(ingredientsFilePath + "/" + ingredientsFileName);
    }

    @Override
    public Path createTempIngredientsFile() {
        try {
            Path path = Files.createTempFile(Path.of(ingredientsFilePath),"tempFile",""+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
            return path;
        } catch (IOException e) {
            throw new WritingFileException();
        }
    }
}
