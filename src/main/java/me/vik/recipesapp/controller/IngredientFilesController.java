package me.vik.recipesapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vik.recipesapp.service.IngredientFilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Работа с файлами", description = "Операции с вложениями рецептов и ингридиентов")
public class IngredientFilesController {
    private final IngredientFilesService ingredientFilesService;

    public IngredientFilesController(IngredientFilesService ingredientFilesService) {
        this.ingredientFilesService = ingredientFilesService;
    }

    @GetMapping("ingredientExport")
    @Operation(summary = "Скачать список ингридиентов")
    public ResponseEntity<InputStreamResource> downloadIngredientsFile() throws FileNotFoundException {
        File exportFile = ingredientFilesService.getIngredientsFile();

        if (exportFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(exportFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(exportFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"IngredientsLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "ingredientImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить список ингридиентов")
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        ingredientFilesService.cleanIngredientsFile();
        File importFile = ingredientFilesService.getIngredientsFile();

        try (FileOutputStream fos = new FileOutputStream(importFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
