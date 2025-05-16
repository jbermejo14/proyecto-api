package com.svalero.proyectoapi.exception;

public class StudentNotFoundException extends Throwable {

    public StudentNotFoundException() {
        super("The student does not exist");
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
