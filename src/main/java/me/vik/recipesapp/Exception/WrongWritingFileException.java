package me.vik.recipesapp.Exception;

public class WrongWritingFileException extends RuntimeException{
    public WrongWritingFileException() {
        System.out.print("Ошибка записи файла");
    }
}
