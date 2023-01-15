package me.vik.recipesapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Запуск приложения", description = "Информация о запуске Сайта рецептов")
public class FirstController {

    @GetMapping("/")
    public String startProject() {
        return "Сайт рецептов - запущен!";
    }

    @GetMapping("/info")
    public String getProjectInfo() {
        return "Имя ученика: Конченков Виталий<br>Название проекта: Сайт рецептов<br> Дата создания: 10.12.2022<br>Описание проекта: " +
                "Сайт рецептов, написан на Java с использованием Spring и Maven";
    }
}