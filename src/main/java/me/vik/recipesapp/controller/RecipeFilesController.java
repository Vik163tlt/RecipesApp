package me.vik.recipesapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vik.recipesapp.service.RecipeFilesService;
import me.vik.recipesapp.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(name = "Работа с файлами", description = "Операции с вложениями рецептов и ингридиентов")
public class RecipeFilesController {
    private final RecipeFilesService recipeFilesService;
    private final RecipeService recipeService;

    public RecipeFilesController(RecipeFilesService recipeFilesService, RecipeService recipeService) {
        this.recipeFilesService = recipeFilesService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipeExport")
    @Operation(summary = "Скачать список рецептов")
    public ResponseEntity<InputStreamResource> downloadRecipesFile() throws FileNotFoundException {
        File file = recipeFilesService.getRecipesFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "recipeImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить список рецептов")
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        recipeFilesService.cleanRecipesFile();
        File importFile = recipeFilesService.getRecipesFile();

        try (FileOutputStream fos = new FileOutputStream(importFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/recipeExportTXTFile")
    @Operation(summary = "Скачать каталог рецептов в ТХТ формате")
    public ResponseEntity<InputStreamResource> downloadRecipesCatalogueTXT() throws IOException {

        Path path = recipeService.saveAllRecipe();
        if(Files.size(path)!=0){
            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
                return ResponseEntity.ok()
                        .contentLength(path.toFile().length())
                        .contentType(MediaType.TEXT_PLAIN)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipesCatalogue.txt\"")
                        .body(resource);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }else {
            return ResponseEntity.noContent().build();
        }
    }
}

