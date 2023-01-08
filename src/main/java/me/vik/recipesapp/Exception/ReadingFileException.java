package me.vik.recipesapp.Exception;

public class ReadingFileException extends RuntimeException{
    public ReadingFileException() {
        System.out.print("Ошибка чтения файла");
    }
}