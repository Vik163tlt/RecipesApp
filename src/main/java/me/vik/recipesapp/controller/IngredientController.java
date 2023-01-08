package me.vik.recipesapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vik.recipesapp.model.Ingredient;
import me.vik.recipesapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Описание операций с ингридиентами")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск ингредиента",
            description = "Для поиска ингредиента необходимо указать id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    }
    )
    public ResponseEntity<?> getIngredientId(@PathVariable int id) {
        Ingredient ingredient = ingredientService.getIngredientId(id);
        if (ingredient == null) {
            return ResponseEntity.ok().body("Список пуст");
        }
        return ResponseEntity.ok().body(ingredient);
    }


    @PostMapping("/{id}")
    @Operation(summary = "Добавление нового ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка"
            )
    }
    )
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        int id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование ингредиента",
            description = "Для редактирования ингредиента необходимо указать id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка"
            )
    }
    )
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.editIngredient(id, newIngredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Для удаления ингредиента необходимо указать id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    }
    )
    public ResponseEntity<?> deleteIngredient(@PathVariable int id) {
        boolean deleted = ingredientService.deleteIngredient(id);
        if (deleted) {
            return ResponseEntity.ok().body("Ингридиент №" + id + " удален!");
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    @Operation(summary = "Получение всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиенты не найдены"
            )
    }
    )
    public ResponseEntity<?> getAllIngredient(){
        Map<Integer, Ingredient> ingredients = ingredientService.getAllIngredient();
        return ResponseEntity.ok().body(Objects.requireNonNullElse(ingredients, "Список отсутствует"));
    }

}
