package me.vik.recipesapp.Exception;

public class WrongReadingFileException extends RuntimeException{
    public WrongReadingFileException() {
        System.out.print("Ошибка чтения файла");
    }
}