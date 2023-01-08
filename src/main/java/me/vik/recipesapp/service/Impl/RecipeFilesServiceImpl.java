package me.vik.recipesapp.service.Impl;

import me.vik.recipesapp.Exception.ReadingFileException;
import me.vik.recipesapp.Exception.WritingFileException;
import me.vik.recipesapp.service.RecipeFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RecipeFilesServiceImpl implements RecipeFilesService {

    @Value("${path.to.recipes.file}")
    private String recipesFilePath;
    @Value("${name.of.recipes.file}")
    private String recipesFileName;

    @Override
    public boolean saveRecipesToFile(String json){
        try {
            cleanRecipesFile();
            Files.writeString(Path.of(recipesFilePath, recipesFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readRecipesFromFile(){
        try {
            return Files.readString(Path.of(recipesFilePath, recipesFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadingFileException();
        }
    }

    @Override
    public boolean cleanRecipesFile(){
        try {
            Files.deleteIfExists(Path.of(recipesFilePath, recipesFileName));
            Files.createFile(Path.of(recipesFilePath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File getRecipesFile() {
        return new File(recipesFilePath + "/" + recipesFileName);
    }

    @Override
    public Path createTempFile() {
        try {
            Path path = Files.createTempFile(Path.of(recipesFilePath),"tempFile",""+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
            return path;
        } catch (IOException e) {
            throw new WritingFileException();
        }
    }
}
