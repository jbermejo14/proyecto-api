package com.svalero.proyectoapi.exception;

public class CourseNotFoundException extends Throwable {

    public CourseNotFoundException() {
        super("The customer does not exist");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
