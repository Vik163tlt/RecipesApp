package me.vik.recipesapp.Exception;

public class WritingFileException extends RuntimeException{
    public WritingFileException() {
        System.out.print("Ошибка записи файла");
    }
}
